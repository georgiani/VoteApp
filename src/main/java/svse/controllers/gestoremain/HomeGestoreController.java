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
	
	private String sessioneSelezionata;
	private SessioneDiVoto sessione;
	private Button modificaButton, startButton, stopButton, totemButton, risultatiButton;
	private ISessioneDAO sessioneDao;
	
	private void infoSessione() {
    	// pulizia panello info
		infoPane.getChildren().clear();
		
		HBox hss = new HBox(10);
		HBox hmtr = new HBox(10);
		
		// titolo sessione
		infoPane.getChildren().addAll(new Label(sessioneSelezionata), hss, hmtr);
		
		modificaButton = new Button("Modifica");
		startButton = new Button("Start");
		stopButton = new Button("Stop");
		totemButton = new Button("Totem");
		risultatiButton = new Button("Risultati");
		
		modificaButton.setOnAction(e -> modifica());
		startButton.setOnAction(e -> start());
		stopButton.setOnAction(e -> stop());
		totemButton.setOnAction(e -> totem());
		risultatiButton.setOnAction(e -> risultati());
		
		hss.getChildren().addAll(startButton, stopButton);
		hmtr.getChildren().addAll(modificaButton, totemButton, risultatiButton);
		
		//if (!sessione.isStarted())
		//stopButton.setDisable(true);
		//totemButton.setDisable(true);
		//modificaButton.setDisable(true)
		
		//if (sessione.isStarted())
		//modificaButton.setDisable(true);
		//startButton.setDisable(true);
		//risultatiButton.setDisable(true);
		
		//if (sessione.isNew())
		stopButton.setDisable(true);
		totemButton.setDisable(true);
		risultatiButton.setDisable(true);
    }
	
	private void cambiaSessioneSelezionata(String sessioneNuova) {
		sessioneSelezionata = sessioneNuova;
		//TODO: sessione = sessioneDao.get(sessioneNuova);
	}
	
	private void updateSessioni() {
    	sessioni.getItems().clear();
    	List<SessioneDiVoto> ses = sessioneDao.getAll();
    	
		for (SessioneDiVoto s: ses)
			sessioni.getItems().add(s.getNome());
    }
	
	@Override
	public void init(Object parameter) {
		gestore = (Gestore)parameter;
		sessioneDao = DAOFactory.getFactory().getSessioneDAOInstance();
		
		// Listener per cambio selezione
		sessioni.getSelectionModel().selectedItemProperty().addListener(
				new ChangeListener<String>() {
					@Override
					public void changed(ObservableValue<? extends String> observable, String oldVal, String newVal) {
						cambiaSessioneSelezionata(newVal);
						infoSessione();
					}
				}
		);
		
		updateSessioni();
	}

    public void logout(ActionEvent event) {
    	changeView("views/Login.fxml");
    }
	
	public void modifica() {
		changeView("views/ConfigurazioneSessione.fxml", List.of(gestore, sessioneSelezionata));
	}
	
	public void start() {
		if (!sessione.isStarted()) {
			//sessioneDao.start(sessioneSelezionata)
			sessione.start();
		}
	}
	
	public void stop() {
		if (sessione.isStarted()) {
			//sessioneDao.stop(sessioneSelezionata)
			sessione.stop();
		}
	}
	
	public void totem() {
		changeView("views/SceltaTotem.fxml", List.of(gestore, sessioneSelezionata));
	}
	
	public void risultati() {
		changeView("views/Risultati.fxml", List.of(gestore, sessioneSelezionata));
	}

	@Override
	public void update() {
		updateSessioni();
	}
	
	public void addSessione() {
		changeView("views/ConfigurazioneNuovaSessione.fxml", gestore);
	}
}
