package svse.models.sessione;

import java.util.List;

import svse.models.risultati.Risultato;
import svse.models.sessione_vincita_strategies.VincitaStrategy;


public class SessioneDiVoto {
	private String nomeSessione;
	private VincitaStrategy strategiaVincita;
	private String strategiaVincitaS;
	private String strategiaVoto;
	private String stato = null;
	private String tipo = null;
	
	public SessioneDiVoto(String n, String vincita, String voto, String status, String t) {
		nomeSessione = n;
		strategiaVincitaS = vincita;
		strategiaVincita = VincitaStrategy.getStrategy(vincita);
		strategiaVoto = voto;
		stato = status;
		tipo = t;
	}
	
	public String getNome() {
		return nomeSessione;
	}
	
	public Risultato getRisultati(List<Voto> v) {
		return strategiaVincita.getVincitore(v);
	}
	
	public boolean isStarted() {
		return stato.equals("s");
	}
	
	public boolean isNew() {
		return stato.equals("n");
	}
	
	public boolean isFinished() {
		return stato.equals("f");
	}
	
	public String getStatus() {
		return stato;
	}
	
	public String getStrategiaVoto() {
		return strategiaVoto;
	}
	
	public String getStrategiaVincita() {
		return strategiaVincitaS;
	}
	
	public String getOrdinaleCategoricoType() {
		return (strategiaVoto.equals("o") || strategiaVoto.equals("c")) ? tipo : null;
	}
	
}
