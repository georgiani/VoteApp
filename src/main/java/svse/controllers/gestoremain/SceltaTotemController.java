package svse.controllers.gestoremain;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.List;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;
import svse.controllers.Controller;
import svse.dao.factory.DAOFactory;
import svse.dao.sessione.ISessioneDAO;
import svse.dao.totem.ITotemDAO;
import svse.models.Totem;
import svse.models.sessione.SessioneDiVoto;
import svse.models.utente.Gestore;

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
		String[] parts = totemSelezionato.split(" ");
		String ip = parts[0].trim();
		int port = Integer.parseInt(parts[1].trim());
		
		try {
			DatagramSocket gestoreSocket = new DatagramSocket();
			
			byte[] toSend = Integer.toString(sessioneDao.getId(sessione)).getBytes();
			DatagramPacket toTotem = new DatagramPacket(toSend, toSend.length, InetAddress.getByName(ip), port);
			gestoreSocket.send(toTotem);
			// prendere dal campo cf elettore e metterlo come votato nella tabella Votato (voti dao)
			gestoreSocket.close();
		} catch (Exception e) {
			e.printStackTrace();
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
