package svse.models.sessione_vincita_strategies;

import java.util.List;

import svse.models.risultati.Risultato;
import svse.models.risultati.RisultatoReferendum;
import svse.models.sessione.Voto;
import svse.models.sessione.VotoReferendum;

public class ReferendumSenzaQuorumStrategy extends VincitaStrategy {

	@Override
	public Risultato getVincitore(List<Voto> v) {
		int si = 0, no = 0;
		for (Voto voto : v)
			si += (((VotoReferendum)voto).isFavorevole() ? 1 : 0);
		
		no = v.size() - si;
		return new RisultatoReferendum(si > no ? "si" : "no");
	}

}
