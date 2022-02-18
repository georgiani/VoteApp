package svse.controllers.elettoremain;

import svse.controllers.Controller;
import svse.dao.factory.DAOFactory;
import svse.dao.sessione.ISessioneDAO;
import svse.models.sessione.SessioneDiVoto;
import svse.models.utente.Elettore;

import java.util.List;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import ps.IObserver;


public class HomeElettoreController extends Controller implements IObserver {
	private Elettore elettore;
	
    @FXML
    private Button logoutButton;

    @FXML
    private ListView<String> sessioni;
    
    @FXML
    private VBox infoPane;
    
    private String sessioneSelezionata;
    private Button entraButton;
    private ISessioneDAO sessioneDao;
    private ChangeListener<? super String> listener;
    private SessioneDiVoto sessione;
    
    @FXML
    public void logout(ActionEvent event) {
    	changeView("views/Login.fxml");
    }
    
    public void apriSessione() {
    	DAOFactory.getFactory().getVotazioneDAOInstance().confermaVotazione(elettore, sessione);
    	changeView("views/VotoSessione.fxml", sessioneDao.getId(sessione));
    }
    
    private void infoSessione(String newVal) {
    	sessioneSelezionata = newVal;
    	sessione = sessioneDao.get(sessioneSelezionata);
    	System.out.println(sessione.getNome());
    	
    	// pulizia panello info
		infoPane.getChildren().clear();
		
		// titolo sessione
		infoPane.getChildren().add(new Label(sessione.getNome()));
		infoPane.getChildren().add(new Label("Status: " + sessione.getPrettyStatus()));
		
		// creazione tasto per entrare nella sessione
		entraButton = new Button("Entra");
		entraButton.setOnAction(e -> apriSessione());
		
		// tast per entrare
		infoPane.getChildren().add(entraButton);
    }

	@Override
	public void init(Object parameter) {
		elettore = (Elettore)parameter;
		sessioneDao = DAOFactory.getFactory().getSessioneDAOInstance();
		sessioneDao.addObserver(this);
		
		listener = (ObservableValue<? extends String> obs, String o, String newVal) -> infoSessione(newVal);
		
		// Listener per cambio selezione
		sessioni.getSelectionModel().selectedItemProperty().addListener(listener);
		
		// primo aggiornamento sessioni disponibili PER QUESTO UTENTE (TODO: select sessioni rispetto a un'elettore)
		update();
	}

	@Override
	public void update() {
		sessioni.getSelectionModel().selectedItemProperty().removeListener(listener);
		
		// pulire lista
		sessioni.getItems().clear();
		
		// ricaricare sessioni
		List<SessioneDiVoto> ses = sessioneDao.getAll(elettore);
		for (SessioneDiVoto s: ses)
			sessioni.getItems().add(s.getNome());
		
		if (sessioneSelezionata != null) {
			infoSessione(sessioneSelezionata);
			sessioni.getSelectionModel().select(sessioneSelezionata);
		}
		
		sessioni.getSelectionModel().selectedItemProperty().addListener(listener);
	}
}
