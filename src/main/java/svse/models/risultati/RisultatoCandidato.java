package svse.models.risultati;

import java.util.HashMap;
import java.util.Map;
import svse.models.sessione.Candidato;

public class RisultatoCandidato extends Risultato {
	private Map<Candidato, Integer> risultati;
	
	public RisultatoCandidato(Map<Candidato, Integer> m) {
		this.risultati = new HashMap<Candidato, Integer>(m);
	}
	
	public Map<Candidato, Integer> getRisultati() {
		return new HashMap<Candidato, Integer>(this.risultati);
	}
}
