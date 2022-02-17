package svse.models.sessione_vincita_strategies;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import svse.dao.factory.DAOFactory;
import svse.models.risultati.Risultato;
import svse.models.risultati.RisultatoCandidato;
import svse.models.risultati.RisultatoPartito;
import svse.models.sessione.Partecipante;
import svse.models.sessione.SessioneDiVoto;
import svse.models.voto.Voto;
import svse.models.voto.VotoCategorico;
import svse.models.sessione.*;

public class MaggioranzaStrategy extends VincitaStrategy {

	@Override
	public Risultato getVincitore(SessioneDiVoto s) {
		return null;
	}

}
