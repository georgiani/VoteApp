package svse.models.sessione_vincita_strategies;

import java.util.List;

import svse.dao.factory.DAOFactory;
import svse.models.risultati.Risultato;
import svse.models.risultati.RisultatoReferendum;
import svse.models.sessione.SessioneDiVoto;
import svse.models.voto.Voto;
import svse.models.voto.VotoReferendum;

public class ReferendumConQuorumStrategy extends VincitaStrategy {

	@Override
	public Risultato getVincitore(SessioneDiVoto s) {
		List<Voto> v = DAOFactory.getFactory().getVotazioneDAOInstance().getAll(s);
		int si = 0, no = 0;
		for (Voto voto : v)
			si += (((VotoReferendum)voto).isFavorevole() ? 1 : 0);
		
		no = v.size() - si;
		
		if (DAOFactory.getFactory().getVotazioneDAOInstance().getTotaleVotanti(s) > (DAOFactory.getFactory().getVotazioneDAOInstance().getTotaleAventiDirittoAlVoto() / 2))
			return new RisultatoReferendum(si > no);
		return new RisultatoReferendum(null);
	}

}
