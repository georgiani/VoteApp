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
		return null;
	}

}
