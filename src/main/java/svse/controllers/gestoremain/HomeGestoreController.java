package svse.controllers.gestoremain;

import java.util.List;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import ps.IObserver;
import svse.controllers.Controller;
import svse.dao.factory.DAOFactory;
import svse.dao.sessione.ISessioneDAO;
import svse.models.sessione.SessioneDiVoto;
import svse.models.utente.Gestore;

public class HomeGestoreController extends Controller implements IObserver {
	private Gestore gestore;
	
	@FXML
	private ListView<String> sessioni;
	
	@FXML
	private VBox infoPane;
	
	@FXML
	private Button logoutButton;
	
	@FXML
	private Button addSessioneButton;
	
	private String sessioneSelezionata = null;
	private SessioneDiVoto sessione;
	private ISessioneDAO sessioneDao;
	private ChangeListener<? super String> listener;
	
	private void infoSessione(String newVal) {
		sessioneSelezionata = newVal;
		sessione = sessioneDao.get(sessioneSelezionata);
    	// pulizia panello info
		infoPane.getChildren().clear();
		
		HBox hss = new HBox(10);
		HBox hmtr = new HBox(10);
		
		// titolo sessione
		infoPane.getChildren().addAll(new Label(sessioneSelezionata), new Label(sessione.getNome()), new Label("Status: " + sessione.getPrettyStatus()), hss, hmtr);
		
		Button startButton = new Button("Start");
		Button stopButton = new Button("Stop");
		Button totemButton = new Button("Totem");
		Button risultatiButton = new Button("Risultati");
		
		startButton.setOnAction(e -> start());
		stopButton.setOnAction(e -> stop());
		totemButton.setOnAction(e -> totem());
		risultatiButton.setOnAction(e -> risultati());
		
		hss.getChildren().addAll(startButton, stopButton);
		hmtr.getChildren().addAll(totemButton, risultatiButton);
		
		if (sessione.isNew()) {
			startButton.setDisable(false);
			stopButton.setDisable(true);
			totemButton.setDisable(true);
			risultatiButton.setDisable(true);
		} else if(sessione.isFinished()) {
			startButton.setDisable(true);
			stopButton.setDisable(true);
			totemButton.setDisable(true);
			risultatiButton.setDisable(false);
		} else if (sessione.isStarted()) {
			startButton.setDisable(true);
			stopButton.setDisable(false);
			totemButton.setDisable(false);
			risultatiButton.setDisable(true);
		}
    }
	
	@Override
	public void init(Object parameter) {
		gestore = (Gestore)parameter;
		sessioneDao = DAOFactory.getFactory().getSessioneDAOInstance();
		sessioneDao.addObserver(this);
		
		// creazione listener
		listener = (ObservableValue<? extends String> obs, String o, String newVal) -> infoSessione(newVal);
		
		// Listener per cambio selezione
		sessioni.getSelectionModel().selectedItemProperty().addListener(listener);
		
		update();
	}

    public void logout(ActionEvent event) {
    	changeView("views/Login.fxml");
    }
	
	public void start() {
		if (sessione.isNew() || !sessione.isStarted())
			sessioneDao.start(sessione);
	}
	
	public void stop() {
		if (sessione.isStarted())
			sessioneDao.stop(sessione);
	}
	
	public void totem() {
		changeView("views/SceltaTotem.fxml", List.of(gestore, sessioneSelezionata));
	}
	
	public void risultati() {
		changeView("views/Risultati.fxml", List.of(gestore, sessioneSelezionata));
	}
	
	@Override
	public void update() {
		// disattivare il listener per pulire la lista
		sessioni.getSelectionModel().selectedItemProperty().removeListener(listener);
		
		// pulire la lista
    	sessioni.getItems().clear();
    	
    	// ricaricare le sessioni
    	List<SessioneDiVoto> ses = sessioneDao.getAll();
    	
		for (SessioneDiVoto s: ses)
			sessioni.getItems().add(s.getNome());
		
		if (sessioneSelezionata != null) {
			infoSessione(sessioneSelezionata);
			sessioni.getSelectionModel().select(sessioneSelezionata);
		}
		
		sessioni.getSelectionModel().selectedItemProperty().addListener(listener);
	}
	
	public void addSessione() {
		changeView("views/ConfigurazioneNuovaSessione.fxml", gestore);
	}
}
