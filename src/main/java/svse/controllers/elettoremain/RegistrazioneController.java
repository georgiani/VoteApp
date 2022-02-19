package svse.controllers.elettoremain;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import svse.controllers.Controller;
import svse.dao.factory.DAOFactory;
import svse.models.utente.Elettore;
import svse.models.utente.Utente;

public class RegistrazioneController extends Controller {

	@FXML
	private TextField nomeField;
	
	@FXML
	private TextField cognomeField;
	
	@FXML
	private TextField cfField;
	
	@FXML
	private TextField etaField;
	
	@FXML
	private PasswordField passwordField;
	
	@FXML
	private PasswordField ripetiPasswordField;
	
	@FXML
	private Button registraButton;
	
	@FXML
	private Button annullaButton;
	
	@Override
	public void init(Object parameter) {
		
	}
	
	@FXML
	private void registra() {
		if (controllo()) {
			askConfirmation();
		}
	}
	
	private void askConfirmation() {
		Utente e = new Elettore(nomeField.getText(), cognomeField.getText(), cfField.getText());
		Alert confirm = new Alert(AlertType.CONFIRMATION, "Sei sicuro di voler creare l'account?");
		confirm.showAndWait();
		
		if (confirm.getResult() == ButtonType.OK) {
			DAOFactory.getFactory().getUtenteDAOInstance().registraElettore(e, passwordField.getText());
			changeView("views/Login.fxml");			
		}
	}
	
	private boolean controllo() {
		if (nomeField.getText() == null || nomeField.getText().isEmpty()) {
			Alert al = new Alert(AlertType.ERROR, "Nome non inserito!");
			al.showAndWait();
			return false;
		}
		
		if (cognomeField.getText() == null || cognomeField.getText().isEmpty()) {
			Alert al = new Alert(AlertType.ERROR, "Cognome non inserito!");
			al.showAndWait();
			return false;
		}
		
		if (cfField.getText() == null || cfField.getText().isEmpty()) {
			Alert al = new Alert(AlertType.ERROR, "Codice fiscale non inserito!");
			al.showAndWait();
			return false;
		}
		
		if (etaField.getText() == null || etaField.getText().isEmpty()) {
			Alert al = new Alert(AlertType.ERROR, "Eta non inserita!");
			al.showAndWait();
			return false;
		}
		
		int eta;
		try {
			eta = Integer.parseInt(etaField.getText().trim());
		} catch (Exception e) {
			Alert al = new Alert(AlertType.ERROR, "L'eta deve essere un numero!");
			al.showAndWait();
			return false;
		}
		
		if (eta < 18) {
			Alert al = new Alert(AlertType.ERROR, "Devi essere maggiorene per poter registrarti!");
			al.showAndWait();
			return false;
		}
		
		if (passwordField.getText() == null || passwordField.getText().isEmpty()) {
			Alert al = new Alert(AlertType.ERROR, "Password non inserita!");
			al.showAndWait();
			return false;
		}
		
		if (ripetiPasswordField.getText() == null || ripetiPasswordField.getText().isEmpty()) {
			Alert al = new Alert(AlertType.ERROR, "Password ripetuta non inserita!");
			al.showAndWait();
			return false;
		}
		
		if (!passwordField.getText().equals(ripetiPasswordField.getText())) {
			Alert al = new Alert(AlertType.ERROR, "Password non coincidono!");
			al.showAndWait();
			return false;
		}
		
		Utente e = new Elettore(nomeField.getText(), cognomeField.getText(), cfField.getText());
		
		if (DAOFactory.getFactory().getUtenteDAOInstance().getId(e) != 0) {
			Alert al = new Alert(AlertType.ERROR, "Account gia esistente con questo codice fiscale!");
			al.showAndWait();
			return false;
		}
		
		return true;
	}
	
	@FXML
	private void annulla() {
		changeView("views/Login.fxml");
	}
 
}
