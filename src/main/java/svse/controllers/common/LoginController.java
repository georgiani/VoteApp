package svse.controllers.common;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
import svse.controllers.Controller;
import svse.dao.factory.DAOFactory;
import svse.dao.utente.IUtenteDAO;
import svse.exceptions.NotFoundException;
import svse.models.utente.Elettore;
import svse.models.utente.Gestore;
import svse.models.utente.Utente;

public class LoginController extends Controller {
	@FXML
	private Button authElettore;
	
	@FXML
	private Button signupElettore;
	
	@FXML
	private Button authGestore;
	
	@FXML
	private TextField cfElettore;
	
	@FXML
	private PasswordField passElettore;
	
	@FXML
	private TextField cfGestore;
	
	@FXML
	private PasswordField passGestore;
	
	@FXML
	private Label erroreDb;
	
	@FXML
	private Label erroreLoginElettore;
	
	@FXML
	private Label erroreLoginGestore;
	
	private IUtenteDAO utenteDAO = DAOFactory.getFactory().getUtenteDAOInstance();
	
	private void showErroreElettore() {
		erroreLoginElettore.setText("L'elettore non esiste!");
		erroreLoginElettore.setTextFill(Color.RED);
	}
	
	private void showErroreGestore() {
		erroreLoginGestore.setText("Il gestore non esiste!");
		erroreLoginGestore.setTextFill(Color.RED);
	}

	
	/***
	 * Passa alla schermata di Home dell'elettore, usando le credenziali passate 
	 * attraverso la gui.
	 */
	public void autenticaElettore() {
		try {
			Utente u = utenteDAO.login(cfElettore.getText(), passElettore.getText());
			
			// si verifica se le credenziali inserite dall'utente sono davvero di un Elettore
			if (u.isElettore()) {
				Elettore e = (Elettore)u;
				changeView("views/HomeElettore.fxml", e);
			}
			else showErroreElettore();
		} catch (NotFoundException nfe) {
			showErroreElettore();
		}
	}
	
	/***
	 * Passa alla schermata di Home del gestore, usando le credenziali passate 
	 * attraverso la gui.
	 */
	public void autenticaGestore() {
		try {
			Utente u = utenteDAO.login(cfGestore.getText(), passGestore.getText());
			
			// si verifica se le credenziali inserite dall'utente sono davvero di un Gestore 
			if (u.isGestore()) {
				Gestore g = (Gestore)u;
				changeView("views/HomeGestore.fxml", g);
			}
			else showErroreGestore();
		} catch (NotFoundException nfe) {
			showErroreGestore();
		}
	}
	

	public void registraElettore() {
		// da implementare
		System.out.println("Navigazione su pagina registrazione");
	}

	@Override
	public void init(Object parameter) {}
}
