package svse.controllers.gestoremain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import svse.controllers.Controller;
import svse.dao.factory.DAOFactory;
import svse.dao.sessione.ISessioneDAO;
import svse.models.componentistica.Candidato;
import svse.models.componentistica.Partito;
import svse.models.sessione.Lista;
import svse.models.sessione.SessioneDiVoto;
import svse.models.utente.Gestore;

public class ConfigurazioneNuovaSessioneController extends Controller {
private Gestore gestore;
	
	@FXML
    private Button aggiungiCandidatoButton;

    @FXML
    private Button aggiungiNomeListaButton;

    @FXML
    private Button avantiButton;

    @FXML
    private VBox dettagliLista;

    @FXML
    private TextField nomeSessione;

    @FXML
    private TextField partitoLista;

    @FXML
    private ChoiceBox<String> selezioneVincita;

    @FXML
    private ChoiceBox<String> selezioneVoto;
    
    @FXML
    private Button annullaButton;
    
    @FXML
    private TextField nomeCandidato;
    
    @FXML
    private TextField cognomeCandidato;
    
    @FXML
    private Label titoloBoxListe;

    @FXML
    private CheckBox soloPartiti;
    
    private String nomeS;
    private Map<String, List<Candidato>> liste;
    private String listaCorrente, chVoto, chVincita, chTipo;
    private ISessioneDAO sessioneDao;
    
    @FXML
    public void avanti(ActionEvent e) {
    	// da fare la sessione di voto con tutte le cose e passare al ripielogo
    	// o dare un errore se qualcosa non fosse completato
    	
    	// cose da fare in ripielogo... non qua
    	chTipo = (soloPartiti.isDisabled() ? null : (soloPartiti.isSelected() ? "p" : "c"));
    	SessioneDiVoto s = new SessioneDiVoto(nomeS, chVincita, chVoto, "n", chTipo);
//    	sessioneDao.save(s);
//    	
//    	// inserimento liste + candidati
//    	for (Map.Entry<String, List<Candidato>> entry : liste.entrySet()) {
//    		Lista l = new Lista(new Partito(entry.getKey()), entry.getValue());
//    		sessioneDao.save(l, s); // salvataggio lista in coerenza con la sua sessione
//    		
//    		for (Candidato c : l.getCandidati()) {
//    			sessioneDao.save(c, l);
//    		}
//    	}
    	
    	changeView("views/RipielogoNuovaSessione.fxml", List.of(s, liste));
    }
	
    public void aggiungiNome() {
    	listaCorrente = partitoLista.getText();
    	if (!liste.containsKey(listaCorrente))
    		liste.put(listaCorrente, new ArrayList<Candidato>());
    	titoloBoxListe.setText("Aggiungi alla lista: " + listaCorrente);	
    }
    
    public void aggiungiCandidato() {
    	Candidato newC = new Candidato(nomeCandidato.getText(), cognomeCandidato.getText());
    	// da inserire protezione per duplicati
    	liste.get(listaCorrente).add(newC);
    	dettagliLista.getChildren().add(new Label("Candidato: " + newC.getNome() + " " + newC.getCognome() + "\nLista: " + listaCorrente));
    }
    
    public void annulla() {
    	changeView("views/HomeGestore.fxml", gestore);
    }
    
    // cambia tipo di vincita rispetto al tipo di voto
    private void cambiaModVoto(String voto) {
    	if (voto.equals("Referendum")) {
    		chVoto = "r";
    		selezioneVincita.getItems().clear();
    		selezioneVincita.getItems().addAll("Referendum Senza Quorum", "Referendum Con Quorum");
    	} else {
    		selezioneVincita.getItems().clear();
    		selezioneVincita.getItems().addAll("Maggioranza", "Maggioranza Assoluta");
    		chVoto = (voto.equals("Ordinale") ? "o" : (voto.equals("Categorico") ? "c" : "p"));
    		
    		if (chVoto.equals("o") || chVoto.equals("c")) {
    			soloPartiti.setDisable(false);
    		}
    	}
    }
    
    private void cambiaModVincita(String vincita) {
    	chVincita = (vincita.equals("Maggioranza") ? "m" : (vincita.equals("Maggioranza Assoluta") ? "a" : (vincita.equals("Referendum Senza Quroum") ? "r" : "q")));
    }
    
	@Override
	public void init(Object parameter) {
		gestore = (Gestore)parameter;
		liste = new HashMap<>();
		sessioneDao = DAOFactory.getFactory().getSessioneDAOInstance();
		selezioneVoto.getItems().addAll("Ordinale", "Categorico", "Categorico Con Preferenze", "Referendum");
		selezioneVincita.getItems().addAll("Maggioranza", "Maggioranza Assoluta", "Referendum Senza Quorum", "Referendum Con Quorum");
		selezioneVoto.getSelectionModel().selectedItemProperty().addListener((ObservableValue<? extends String> obs, String oldVal, String newVal) -> cambiaModVoto(newVal));
		selezioneVincita.getSelectionModel().selectedItemProperty().addListener((ObservableValue<? extends String> obs, String oldVal, String newVal) -> cambiaModVincita(newVal));
		nomeSessione.textProperty().addListener((ObservableValue<? extends String> obs, String oldVal, String newVal) -> {nomeS = newVal;});
	}
}
