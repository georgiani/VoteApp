package svse.controllers.elettoremain;

import java.util.List;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.*;
import svse.controllers.Controller;
import svse.dao.candidato.ICandidatoDAO;
import svse.dao.factory.DAOFactory;
import svse.dao.lista.IListaDAO;
import svse.dao.sessione.ISessioneDAO;
import svse.dao.votazione.IVotazioneDAO;
import svse.models.sessione.*;
import svse.models.voto.*;
import svse.App;

public class VotoSessioneController extends Controller {
	private SessioneDiVoto sessione;
	
	@FXML
	private Label nomeSessione;
	
	@FXML
	private Button avantiButton;
	
	@FXML
	private ScrollPane scrollPane;
	
	private ISessioneDAO sessioneDao;
	private IListaDAO listaDao;
	private IVotazioneDAO votazioneDao;
	private ToggleGroup tg;
	private GridPane g;
	
	@Override
	public void init(Object parameter) {
		int s = (int)parameter;
		
		sessioneDao = DAOFactory.getFactory().getSessioneDAOInstance();
		listaDao = DAOFactory.getFactory().getListaDAOInstance();
		votazioneDao = DAOFactory.getFactory().getVotazioneDAOInstance();
		
		sessione = sessioneDao.getById(s);
		
		scrollPane.setFitToWidth(true);
		scrollPane.setFitToHeight(true);
		
		g = new GridPane();
		g.setAlignment(Pos.CENTER);
		g.setGridLinesVisible(true);
		
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
		tg = new ToggleGroup();
		
		List<Lista> liste = listaDao.getListe(sessione);
		
		int i = 0, j = 0;
		
		for (Lista l : liste) {
			VBox boxLista = new VBox(10);
			boxLista.setAlignment(Pos.CENTER);
			boxLista.getChildren().add(new Label("Partito: " + l.getPartito().getNome()));
			
			if (sessione.getOrdinaleCategoricoType().equals("p")) {
				RadioButton lista = new RadioButton();
				lista.setToggleGroup(tg);
				lista.setUserData(l.getPartito());
				
				for (Candidato c : l.getCandidati())
					boxLista.getChildren().add(new Label(c.getNome() + " " + c.getCognome()));

				lista.setGraphic(boxLista); 
				
				GridPane.setMargin(lista, new Insets(20, 20, 20, 20));
				g.add(lista, j, i);
			} else {
				for (Candidato c : l.getCandidati()) {
					RadioButton cand = new RadioButton(c.getNome() + " " + c.getCognome());
					cand.setToggleGroup(tg);
					cand.setUserData(c);
					
					boxLista.getChildren().add(cand);
				}
				
				GridPane.setMargin(boxLista, new Insets(20, 20, 20, 20));
				g.add(boxLista, j, i);
			}

			if (j + 1 == 4) {
				i++;
				j = 0;
			} else {
				j++;
			}
		}

		scrollPane.setContent(g);
	}
	
	private void categoricaConPreferenzaRadio(Lista l, int j, int i) {
		RadioButton result = new RadioButton();
		
		VBox boxLista = new VBox(10);
		
		boxLista.setAlignment(Pos.CENTER);
		boxLista.getChildren().add(new Label("Partito: " + l.getPartito().getNome()));
		
		for (Candidato c : l.getCandidati())
			boxLista.getChildren().add(new Label(c.getNome() + " " + c.getCognome()));
		
		result.setGraphic(boxLista);
		result.setToggleGroup(tg);
		
		GridPane.setMargin(result, new Insets(20, 20, 20, 20));
		g.add(result, j, i);
		if (j + 1 == 3) {
			i++;
			j = 0;
		} else {
			j++;
		}
	}
	
	private void setVotazioneCatConPreferenza() {		
		tg = new ToggleGroup();
		
		List<Lista> liste = listaDao.getListe(sessione);
		
		int i = 0, j = 0;
		for (Lista l : liste) 
			categoricaConPreferenzaRadio(l, j, i);
	}
	
	private void setVotazioneOrdinale() {
		
	}
	
	private void setReferendum() {
		VBox domandaETasti = new VBox(10);
		domandaETasti.setAlignment(Pos.CENTER);
		domandaETasti.getChildren().add(new Label(sessione.getDomandaReferendum()));
		
		HBox tasti = new HBox(20);
		tg = new ToggleGroup();
		tasti.setAlignment(Pos.CENTER);
		
		RadioButton favorevole = new RadioButton("Favorevole");
		favorevole.setUserData(true);
		RadioButton nonFavorevole = new RadioButton("Non Favorevole");
		nonFavorevole.setUserData(false);
		
		favorevole.setToggleGroup(tg);
		nonFavorevole.setToggleGroup(tg);
		tasti.getChildren().addAll(favorevole, nonFavorevole);
		
		domandaETasti.getChildren().add(tasti);
		scrollPane.setContent(domandaETasti);
	}
	
	public void avanti() {
		Alert alert = new Alert(AlertType.CONFIRMATION, "Sicuro di voler confermare questo voto ?");
    	alert.showAndWait();
    	if (alert.getResult() == ButtonType.OK) {
			if (sessione.getStrategiaVoto().equals("r")) {
				boolean rispostaSelezionata = (Boolean)((RadioButton)tg.getSelectedToggle()).getUserData(); 
				Voto v = new VotoReferendum(rispostaSelezionata, sessione);
				votazioneDao.save(v);
				confermaVoto();
			} else if (sessione.getStrategiaVoto().equals("c")) {
				if (sessione.getOrdinaleCategoricoType().equals("c")) {
					Candidato c = (Candidato)((RadioButton)tg.getSelectedToggle()).getUserData();
					Voto v = new VotoCategorico(c, sessione);
					votazioneDao.save(v);
					confermaVoto();
				} else {
					Partito p = (Partito)((RadioButton)tg.getSelectedToggle()).getUserData();
					Voto v = new VotoCategorico(p, sessione);
					votazioneDao.save(v);
					confermaVoto();
				}	
			} else if (sessione.getStrategiaVoto().equals("p")) {
				
			} else {
				
			}
    	}
	}
	
	private void confermaVoto() {
		Alert conferma = new Alert(AlertType.INFORMATION, "Voto Confermato");
		conferma.showAndWait();
		changeView("views/Login.fxml");
	}
}
