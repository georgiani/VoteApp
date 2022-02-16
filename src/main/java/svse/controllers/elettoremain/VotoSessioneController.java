package svse.controllers.elettoremain;

import java.awt.Font;
import java.util.List;

import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import svse.controllers.Controller;
import svse.dao.candidato.ICandidatoDAO;
import svse.dao.factory.DAOFactory;
import svse.dao.lista.IListaDAO;
import svse.dao.sessione.ISessioneDAO;
import svse.models.sessione.Candidato;
import svse.models.sessione.Lista;
import svse.models.sessione.SessioneDiVoto;

public class VotoSessioneController extends Controller {
	private SessioneDiVoto sessione;
	
	@FXML
	private Label nomeSessione;
	
	@FXML
	private Button avantiButton;
	
	@FXML
	private ScrollPane scrollPane;
	
	private ISessioneDAO sessioneDao;
	private ICandidatoDAO candidatoDao;
	private IListaDAO listaDao;
	private ToggleGroup tg;
	
	@Override
	public void init(Object parameter) {
		int s = (int)parameter;
		
		sessioneDao = DAOFactory.getFactory().getSessioneDAOInstance();
		listaDao = DAOFactory.getFactory().getListaDAOInstance();
		candidatoDao = DAOFactory.getFactory().getCandidatoDAOInstance();
		
		sessione = sessioneDao.getById(s);
		
		scrollPane.setFitToWidth(true);
		scrollPane.setFitToHeight(true);
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
		GridPane g = new GridPane();
		g.setAlignment(Pos.CENTER);
		g.setGridLinesVisible(true);
		
		tg = new ToggleGroup();
		
		List<Lista> liste = listaDao.getListe(sessione);
		
		int i = 0, j = 0;
		
		if (sessione.getOrdinaleCategoricoType().equals("p")) {
			for (Lista l : liste) {
				RadioButton lista = new RadioButton();
				VBox boxLista = new VBox(10);
				boxLista.setAlignment(Pos.CENTER);
				boxLista.getChildren().add(new Label("Partito: " + l.getPartito().getNome()));
				
				for (Candidato c : l.getCandidati())
					boxLista.getChildren().add(new Label(c.getNome() + " " + c.getCognome()));
				
				lista.setGraphic(boxLista);
				lista.setToggleGroup(tg);
				
				GridPane.setMargin(lista, new Insets(20, 20, 20, 20));
				g.add(lista, j, i);
				if (j + 1 == 3) {
					i++;
					j = 0;
				} else {
					j++;
				}
			}
		} else {
			for (Lista l : liste) {
				VBox boxLista = new VBox(10);
				boxLista.setAlignment(Pos.CENTER);
				boxLista.getChildren().add(new Label("Partito: " + l.getPartito().getNome()));
				
				for (Candidato c : l.getCandidati()) {
					RadioButton cand = new RadioButton();
					cand.setToggleGroup(tg);
					cand.setGraphic(new Label(c.getNome() + " " + c.getCognome()));
					boxLista.getChildren().add(cand);
				}
				
				GridPane.setMargin(boxLista, new Insets(20, 20, 20, 20));
				g.add(boxLista, j, i);
				if (j + 1 == 3) {
					i++;
					j = 0;
				} else {
					j++;
				}
			}
		}

		scrollPane.setContent(g);
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
		scrollPane.setContent(domandaETasti);
	}
	
	public void avanti() {
		
	}
}
