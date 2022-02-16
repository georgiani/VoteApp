package svse.controllers.elettoremain;

import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import svse.controllers.Controller;
import svse.dao.factory.DAOFactory;
import svse.dao.sessione.ISessioneDAO;
import svse.models.sessione.SessioneDiVoto;

public class VotoSessioneController extends Controller {
	private SessioneDiVoto sessione;
	private ISessioneDAO sessioneDao;
	
	@FXML
	private Label nomeSessione;
	
	@FXML
	private Button avantiButton;
	
	@FXML
	private ScrollPane scrollPane;
	
	@Override
	public void init(Object parameter) {
		int s = (int)parameter;
		
		sessioneDao = DAOFactory.getFactory().getSessioneDAOInstance();
		
		sessione = sessioneDao.getById(s);
		
		nomeSessione.setText(sessione.getNome());
		if (sessione.getStrategiaVoto().equals("r"))
			setReferendum();
		else if (sessione.getStrategiaVoto().equals("c"))
			setVotazioneCategorica();
		else if (sessione.getStrategiaVoto().equals("p"))
			setVotazioneCatConPreferenza();
		else setVotazioneOrdinale();
	}
	
	private void setVotazioneCategorica() {
		
	}
	
	private void setVotazioneCatConPreferenza() {
		
	}
	
	private void setVotazioneOrdinale() {
		
	}
	
	private void setReferendum() {
		VBox domandaETasti = new VBox(10);
		domandaETasti.setAlignment(Pos.CENTER);
		domandaETasti.getChildren().add(new Label(sessione.getDomandaReferendum()));
		
		HBox tasti = new HBox(20);
		ToggleGroup tg = new ToggleGroup();
		tasti.setAlignment(Pos.CENTER);
		
		RadioButton favorevole = new RadioButton("Favorevole");
		RadioButton nonFavorevole = new RadioButton("Non Favorevole");
		favorevole.setToggleGroup(tg);
		nonFavorevole.setToggleGroup(tg);
		tasti.getChildren().addAll(favorevole, nonFavorevole);
		
		domandaETasti.getChildren().add(tasti);
		scrollPane.setFitToWidth(true);
		scrollPane.setFitToHeight(true);
		scrollPane.setContent(domandaETasti);
	}
	
	public void avanti() {
		
	}
}
