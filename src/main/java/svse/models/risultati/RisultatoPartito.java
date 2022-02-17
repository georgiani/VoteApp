package svse.models.risultati;

import java.util.HashMap;
import java.util.Map;

import svse.models.sessione.Partito;

public class RisultatoPartito extends Risultato {
	private Map<Partito, Integer> risultati;
	
	public RisultatoPartito(Map<Partito, Integer> m) {
		this.risultati = new HashMap<Partito, Integer>(m);
	}
	
	public Map<Partito, Integer> getRisultati() {
		return new HashMap<Partito, Integer>(this.risultati);
	}
}
