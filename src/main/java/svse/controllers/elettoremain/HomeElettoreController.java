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
    
    @FXML
    public void logout(ActionEvent event) {
    	changeView("views/Login.fxml");
    }
    
    public void apriSessione() {
    	changeView("views/VotoSessione.fxml", List.of(elettore, sessioneSelezionata));
    }
    
    private void infoSessione() {
    	// pulizia panello info
		infoPane.getChildren().clear();
		
		// titolo sessione
		infoPane.getChildren().add(new Label(sessioneSelezionata));
		
		// creazione tasto per entrare nella sessione
		entraButton = new Button("Entra");
		entraButton.setOnAction(e -> apriSessione());
		
		// tast per entrare
		infoPane.getChildren().add(entraButton);
    }
    
    private void updateSessioni() {
    	sessioni.getItems().clear();
    	List<SessioneDiVoto> ses = sessioneDao.getAll();
    	
		for (SessioneDiVoto s: ses)
			sessioni.getItems().add(s.getNome());
    }

	@Override
	public void init(Object parameter) {
		elettore = (Elettore)parameter;
		sessioneDao = DAOFactory.getFactory().getSessioneDAOInstance();
		
		// Listener per cambio selezione
		sessioni.getSelectionModel().selectedItemProperty().addListener(
				new ChangeListener<String>() {
					@Override
					public void changed(ObservableValue<? extends String> observable, String oldVal, String newVal) {
						sessioneSelezionata = newVal;
						infoSessione();
					}
				}
		);
		
		// primo aggiornamento sessioni disponibili
		updateSessioni();
	}

	@Override
	public void update() {
		updateSessioni();
	}
}
