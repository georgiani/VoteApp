package svse.controllers.common;
import java.net.DatagramSocket;
import java.net.InetAddress;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.paint.Color;
import logger.ProjectLogger;
import svse.controllers.Controller;
import svse.dao.factory.DAOFactory;
import svse.dao.totem.ITotemDAO;
import svse.dao.utente.IUtenteDAO;
import svse.exceptions.UtenteNotFoundException;
import svse.models.utente.Elettore;
import svse.models.utente.Gestore;
import svse.models.utente.Utente;
import svse.totem.GestoreTotem;
import svse.totem.Totem;

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
	private ITotemDAO totemDAO = DAOFactory.getFactory().getTotemDAOInstance();
	private Totem thisTotem;
	private DatagramSocket totemSocket;
	private Thread gestioneTotem;
	
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
			if (cfElettore.getText() == null || cfElettore.getText().isEmpty()) {
				Alert al = new Alert(AlertType.ERROR, "Campo codice fiscale non compilato!");
				al.showAndWait();
				return;
			}
			
			if (passElettore.getText() == null || passElettore.getText().isEmpty()) {
				Alert al = new Alert(AlertType.ERROR, "Campo password non compilato!");
				al.showAndWait();
				return;
			}
			
			Utente u = utenteDAO.login(cfElettore.getText(), passElettore.getText());
			
			// si verifica se le credenziali inserite dall'utente sono davvero di un Elettore
			if (u.isElettore()) {
				chiudiTotem();
				Elettore e = (Elettore)u;
				changeView("views/HomeElettore.fxml", e);
			}
			else showErroreElettore();
		} catch (UtenteNotFoundException nfe) {
			showErroreElettore();
		}
	}
	
	/***
	 * Passa alla schermata di Home del gestore, usando le credenziali passate 
	 * attraverso la gui.
	 */
	public void autenticaGestore() {
		Utente u = null;
		try {
			if (cfGestore.getText() == null || cfGestore.getText().isEmpty()) {
				Alert al = new Alert(AlertType.ERROR, "Campo codice fiscale non compilato!");
				al.showAndWait();
				return;
			}
			
			if (passGestore.getText() == null || passGestore.getText().isEmpty()) {
				Alert al = new Alert(AlertType.ERROR, "Campo password non compilato!");
				al.showAndWait();
				return;
			}
			
			u = utenteDAO.login(cfGestore.getText(), passGestore.getText());
			
			// si verifica se le credenziali inserite dall'utente sono davvero di un Gestore 
			if (u.isGestore()) {
				chiudiTotem();
				Gestore g = (Gestore)u;
				changeView("views/HomeGestore.fxml", g);
			}
			else showErroreGestore();
		} catch (UtenteNotFoundException nfe) {
			showErroreGestore();
		}
	}
	

	public void registraElettore() {
		changeView("views/Registrazione.fxml");
		chiudiTotem();
	}

	@Override
	public void init(Object parameter) {
		accendiTotem();
	}
	
	public void accendiTotem() {
		try {
			totemSocket = new DatagramSocket(0);
			thisTotem = new Totem(InetAddress.getLocalHost().getHostAddress(), totemSocket.getLocalPort());
			
			totemDAO.save(thisTotem);
			if (gestioneTotem == null) {
				gestioneTotem = new GestoreTotem(this, totemSocket);
				gestioneTotem.start();
			} 
		} catch(Exception e) {
			ProjectLogger.getInstance().log("e", e.getMessage());
		}
	}

	public void chiudiTotem() {
		totemDAO.delete(thisTotem);
		gestioneTotem.interrupt();
		totemSocket.close();
	}
	
	public void passaInVoto(int id) {
		chiudiTotem();
		changeView("views/VotoSessione.fxml", id);
	}
}
