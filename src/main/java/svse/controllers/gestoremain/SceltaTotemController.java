package svse.controllers.gestoremain;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.List;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import svse.controllers.Controller;
import svse.dao.factory.DAOFactory;
import svse.dao.sessione.ISessioneDAO;
import svse.dao.totem.ITotemDAO;
import svse.exceptions.UtenteNotFoundException;
import svse.models.sessione.SessioneDiVoto;
import svse.models.utente.Elettore;
import svse.models.utente.Gestore;
import svse.totem.Totem;

public class SceltaTotemController extends Controller {
	private Gestore gestore;
	private String sessioneS;
	private SessioneDiVoto sessione;
	
	@FXML
	private Button annullaButton;
	
	@FXML
	private ListView<String> totems;
	
	@FXML
	private VBox infoPane;
	
	@FXML
	private Button refreshButton;
	
	@FXML
	private TextField cfElettore;
	
	private ITotemDAO totemDao;
	private ISessioneDAO sessioneDao;
	private ChangeListener<? super String> listener;
	private String totemSelezionato;
	
	private void infoTotem(String newVal) {
		totemSelezionato = newVal;
    	
    	// pulizia panello info
		infoPane.getChildren().clear();
		
		// titolo sessione
		infoPane.getChildren().add(new Label(totemSelezionato));
		
		// creazione tasto per entrare nella sessione
		Button entraButton = new Button("Entra");
		entraButton.setOnAction(e -> apriSessione());
		
		// tast per entrare
		infoPane.getChildren().add(entraButton);
	}
	
	private void apriSessione() {
		if (cfElettore.getText() == null || cfElettore.getText().isEmpty()) {
			Alert alert = new Alert(AlertType.INFORMATION, "Inserire il codice fiscale dell'elettore!");
			alert.showAndWait();
			return;
		}
	
		Elettore e;
		try {
			e = (Elettore)DAOFactory.getFactory().getUtenteDAOInstance().get(cfElettore.getText());
			
		} catch (UtenteNotFoundException unfe) {
			Alert alert = new Alert(AlertType.WARNING, "L'elettore inserito non esiste!");
			alert.showAndWait();
			return;
		}
		
		if (DAOFactory.getFactory().getVotazioneDAOInstance().haPartecipatoASessione(e, sessione)) {
			Alert alert = new Alert(AlertType.WARNING, "L'elettore inserito ha gia partecipato a questa sessione!");
			alert.showAndWait();
			return;
		}
			
			
		String[] parts = totemSelezionato.split(" ");
		String ip = parts[0].trim();
		int port = Integer.parseInt(parts[1].trim());
		
		try {
			DatagramSocket gestoreSocket = new DatagramSocket();
			
			byte[] toSend = Integer.toString(sessioneDao.getId(sessione)).getBytes();
			DatagramPacket toTotem = new DatagramPacket(toSend, toSend.length, InetAddress.getByName(ip), port);
			gestoreSocket.send(toTotem);
			DAOFactory.getFactory().getVotazioneDAOInstance().confermaVotazione(e, sessione);
			gestoreSocket.close();
		} catch (Exception ne) {
			ne.printStackTrace();
		}
	}
	
	@FXML
	private void annulla() {
		changeView("views/HomeGestore.fxml", gestore);
	}
	
	@FXML
	private void refresh() {
		update();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void init(Object parameter) {
		List<Object> paramL = (List<Object>) parameter;
		gestore = (Gestore) paramL.get(0);
		sessioneS = (String) paramL.get(1);
		
		totemDao = DAOFactory.getFactory().getTotemDAOInstance();
		sessioneDao = DAOFactory.getFactory().getSessioneDAOInstance();
		sessione =  sessioneDao.get(sessioneS);
		
		listener = (ObservableValue<? extends String> obs, String o, String newVal) -> infoTotem(newVal);
		
		totems.getSelectionModel().selectedItemProperty().addListener(listener);
		
		update();
	}

	public void update() {
		// disattivare il listener per pulire la lista
		totems.getSelectionModel().selectedItemProperty().removeListener(listener);
				
		// pulire la lista
		totems.getItems().clear();
		    		
		// ricaricare le sessioni
		List<Totem> tot = totemDao.getAll();
		    	
		for (Totem t : tot)
			totems.getItems().add(t.getSocketAddress().getHostString() + " " + t.getSocketAddress().getPort());
				
		if (totemSelezionato != null) {
			infoTotem(totemSelezionato);
			totems.getSelectionModel().select(totemSelezionato);
		}
				
		totems.getSelectionModel().selectedItemProperty().addListener(listener);
	}

}
