package svse.controllers.gestoremain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.VBox;
import svse.App;
import svse.controllers.Controller;
import svse.dao.candidato.ICandidatoDAO;
import svse.dao.factory.DAOFactory;
import svse.dao.lista.IListaDAO;
import svse.dao.sessione.ISessioneDAO;
import svse.models.sessione.Candidato;
import svse.models.sessione.Lista;
import svse.models.sessione.Partito;
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
    
    @FXML
    private TextField domandaRef;
    
    private String nomeS;
    private Map<String, List<Candidato>> liste;
    private String listaCorrente, chVoto, chVincita, chTipo;
    private ISessioneDAO sessioneDao;
    private IListaDAO listaDao;
    private ICandidatoDAO candidatoDao;
    
    @FXML
    public void avanti(ActionEvent e) {
    	if (controlloCampi()) {
    		Alert alert = new Alert(AlertType.CONFIRMATION, "Confermi di voler creare la sessione " + nomeSessione.getText() + "?");
        	alert.showAndWait();
        	if (alert.getResult() == ButtonType.OK) {
        		chTipo = (soloPartiti.isDisabled() ? null : (soloPartiti.isSelected() ? "p" : "c"));
        		nomeS = nomeSessione.getText();
        		// inserire anche modo per creare referendum
            	SessioneDiVoto s = new SessioneDiVoto(nomeS, chVincita, chVoto, "n", chTipo, domandaRef.getText());
            	sessioneDao.save(s); // insert sessione
            	
            	if (!chVoto.equals("r")) {
            		for (Map.Entry<String, List<Candidato>> entry : liste.entrySet()) {
                		Lista l = new Lista(new Partito(entry.getKey()), entry.getValue());
                		listaDao.save(l, s); // salvataggio lista in coerenza con la sua sessione
                		
                		for (Candidato c : l.getCandidati())
                			candidatoDao.save(c, l); // salvataggio candidati con riferimento alla lista
                	}
            	}
            	// finisci ed esci
            	changeView("views/HomeGestore.fxml", gestore);
        	}
    	}
    }
    
    private boolean isEmptyOrNull(String s) {
    	if (s == null)
    		return false;
    	if (s.isEmpty())
    		return true;
    	return false;
    }
    
    private boolean controlloCampi() {
    	if (selezioneVoto.getSelectionModel().isEmpty()) {
    		Alert alert = new Alert(AlertType.INFORMATION, "Voto non selezionato!");
    		alert.showAndWait();
    		return false;
    	}
    	
    	if (selezioneVincita.getSelectionModel().isEmpty()) {
    		Alert alert = new Alert(AlertType.INFORMATION, "Vincita non selezionato!");
    		alert.showAndWait();
    		return false;
    	}
    	
    	if (isEmptyOrNull(nomeSessione.getText())) {
    		Alert alert = new Alert(AlertType.INFORMATION, "Nome sessione non inserito!");
    		alert.showAndWait();
    		return false;
    	}
    	
    	if (liste.isEmpty() && !chVoto.equals("r")) {
    		Alert alert = new Alert(AlertType.INFORMATION, "Inserire delle liste!");
    		alert.showAndWait();
    		return false;
    	}
    	
    	if (isEmptyOrNull(domandaRef.getText()) && chVoto.equals("r")) {
    		Alert alert = new Alert(AlertType.INFORMATION, "Inserire la domanda del referendum!");
    		alert.showAndWait();
    		return false;
    	}
    	
    	return true;
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
    		soloPartiti.setDisable(true);
    	} else {
    		selezioneVincita.getItems().clear();
    		selezioneVincita.getItems().addAll("Maggioranza", "Maggioranza Assoluta");
    		chVoto = (voto.equals("Ordinale") ? "o" : (voto.equals("Categorico") ? "c" : "p"));
    		
    		if (chVoto.equals("o") || chVoto.equals("c")) {
    			soloPartiti.setDisable(false);
    		} else {
    			soloPartiti.setDisable(true);
    		}
    	}
    }
    
    private void cambiaModVincita(String vincita) {
    	if (vincita == null) {
    		chVincita = "";
    		return;
    	}
    	chVincita = (vincita.equals("Maggioranza") ? "m" : (vincita.equals("Maggioranza Assoluta") ? "a" : (vincita.equals("Referendum Senza Quroum") ? "r" : "q")));
    }
    
	@Override
	public void init(Object parameter) {
		gestore = (Gestore)parameter;
		liste = new HashMap<>();
		App.resize();
		sessioneDao = DAOFactory.getFactory().getSessioneDAOInstance();
		candidatoDao = DAOFactory.getFactory().getCandidatoDAOInstance();
		listaDao = DAOFactory.getFactory().getListaDAOInstance();
		selezioneVoto.getItems().addAll("Ordinale", "Categorico", "Categorico Con Preferenze", "Referendum");
		selezioneVincita.getItems().addAll("Maggioranza", "Maggioranza Assoluta", "Referendum Senza Quorum", "Referendum Con Quorum");
		selezioneVoto.getSelectionModel().selectedItemProperty().addListener((ObservableValue<? extends String> obs, String oldVal, String newVal) -> cambiaModVoto(newVal));
		selezioneVincita.getSelectionModel().selectedItemProperty().addListener((ObservableValue<? extends String> obs, String oldVal, String newVal) -> cambiaModVincita(newVal));
	}
}
