package svse.models.sessione;

import java.util.List;

import svse.models.risultati.Risultato;
import svse.models.sessione_vincita_strategies.VincitaStrategy;


public class SessioneDiVoto {
	private String nomeSessione;
	private VincitaStrategy strategiaVincita;
	private String strategiaVoto; // TODO: capire se fare questa
	// come strategia o lasciare stringa
	private Boolean inCorso = null;
	private String tipo = null;
	
	public SessioneDiVoto(String n, String vincita, String voto, String status, String t) {
		nomeSessione = n;
		strategiaVincita = VincitaStrategy.getStrategy(vincita);
		strategiaVoto = voto;
		inCorso = status.equals("n") ? null : status.equals("s"); // se e n -> new, else se e s -> in corso, else stopped/false
		tipo = t;
	}
	
	public String getNome() {
		return nomeSessione;
	}
	
	public Risultato getRisultati(List<Voto> v) {
		return strategiaVincita.getVincitore(v);
	}
	
	public boolean isStarted() {
		return inCorso == null ? false : inCorso;
	}
	
	public boolean isNew() {
		return inCorso == null;
	}
	
	public void start() {
		inCorso = true;
	}
	
	public void stop() {
		inCorso = false;
	}
	
	public String getStrategiaVoto() {
		return strategiaVoto;
	}
	
	public String getOrdinaleCategoricoType() {
		return (strategiaVoto.equals("o") || strategiaVoto.equals("c")) ? tipo : "0";
	}
}
