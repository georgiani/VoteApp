package svse.models.sessione_vincita_strategies;

import java.util.List;

import svse.models.risultati.Risultato;
import svse.models.sessione.SessioneDiVoto;
import svse.models.voto.Voto;

public abstract class VincitaStrategy {
	private static VincitaStrategy m = new MaggioranzaStrategy();
	private static VincitaStrategy a = new MaggioranzaAssolutaStrategy();
	private static VincitaStrategy q = new ReferendumConQuorumStrategy();
	private static VincitaStrategy r = new ReferendumSenzaQuorumStrategy();
	
	public static VincitaStrategy getStrategy(String s) {
		VincitaStrategy result = m;
		if (s.equals("a")) 
			result = a;
		else if (s.equals("q"))
			return q;
		else if (s.equals("r"))
			return r;
		return result;
	}
	
	public abstract Risultato getVincitore(SessioneDiVoto s);
}
