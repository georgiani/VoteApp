package svse.controllers.elettoremain;

import java.util.List;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import svse.controllers.Controller;
import svse.dao.factory.DAOFactory;
import svse.dao.sessione.ISessioneDAO;
import svse.models.sessione.SessioneDiVoto;
import svse.models.utente.Elettore;

public class VotoSessioneController extends Controller {
	private Elettore elettore; // TODO: nel caso di voto in presenza
	// devo cercare un modo per eliminare la necessita di una riferenza ad elettore
	// Possibilita: fare un'altra schermata apposta per le votazioni in presenza.
	private SessioneDiVoto sessione;
	
	private ISessioneDAO sessioneDao;
	
	@FXML
	private Label nomeSessioneTemp;
	
	@FXML
	private Button annullaButton;
	
	@SuppressWarnings("unchecked")
	@Override
	public void init(Object parameter) {
		sessioneDao = DAOFactory.getFactory().getSessioneDAOInstance();
		
		List<Object> paramList = (List<Object>) parameter;
		String s = (String)paramList.get(1);
		elettore = (Elettore)paramList.get(0);
		
		sessione = sessioneDao.get(s);
		
		nomeSessioneTemp.setText(sessione + " " + elettore.getNome());
	}
	
	@FXML
	public void annulla() {
		changeView("views/HomeElettore.fxml", elettore);
	}
	
}
