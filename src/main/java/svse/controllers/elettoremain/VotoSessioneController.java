package svse.controllers.elettoremain;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import svse.controllers.Controller;
import svse.dao.factory.DAOFactory;
import svse.dao.sessione.ISessioneDAO;
import svse.models.sessione.SessioneDiVoto;

public class VotoSessioneController extends Controller {
	private int sessioneId;
	private ISessioneDAO sessioneDao;
	
	@FXML
	private Label nomeSessioneTemp;
	
	@FXML
	private Button annullaButton;
	
	@Override
	public void init(Object parameter) {
		sessioneDao = DAOFactory.getFactory().getSessioneDAOInstance();
		int s = (int)parameter;
		
		nomeSessioneTemp.setText(sessioneDao.getById(s).getNome());
	}
}
