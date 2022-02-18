package svse.controllers.gestoremain;

import java.util.List;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.text.Text;
import svse.controllers.Controller;
import svse.dao.factory.DAOFactory;
import svse.exceptions.DatabaseException;
import svse.models.sessione.SessioneDiVoto;
import svse.models.utente.Gestore;
import svse.risultati.Risultato;

public class VisualizzaRisultatiController extends Controller {
	// questo controller deve andare a fare getVoti sul dao della
	// sessione su cui si e cliccato e poi passare la lista di voti attraverso la strategia
	// della sessione
	private Gestore gestore;
	private SessioneDiVoto sessione;
	
	@FXML
	private Text testoRisultato;
	
	@FXML
	private Button indietroButton;
	
	private Risultato risultato;
	
	@SuppressWarnings("unchecked")
	@Override
	public void init(Object parameter) {
		List<Object> lstParam = (List<Object>) parameter;
		gestore = (Gestore)lstParam.get(0);
		sessione = (SessioneDiVoto)lstParam.get(1);
		
		try {
			risultato = DAOFactory.getFactory().getSessioneDAOInstance().getRisultato(sessione);
		} catch (DatabaseException e) {
			Alert alert = new Alert(AlertType.WARNING, "Impossibile ricavare risultati sessione!");
			alert.showAndWait();
			return;
		}

		testoRisultato.setText(risultato.getVincitore() + "\n" + risultato.getRisultatiInteri());
	}
	
	public void indietro() {
		changeView("views/HomeGestore.fxml", gestore);
	}
}
