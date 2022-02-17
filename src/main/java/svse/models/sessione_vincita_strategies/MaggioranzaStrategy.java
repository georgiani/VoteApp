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
		List<Voto> v = DAOFactory.getFactory().getVotazioneDAOInstance().getAll(s);
		Map<Partecipante, Integer> counter = new HashMap<Partecipante, Integer>();
		
		for (Voto voto : v) {
			if (s.getStrategiaVoto().equals("c")) {
				Partecipante p = ((VotoCategorico)voto).getPartecipanteScelto();
				if (counter.containsKey(p))
					counter.put(p, counter.get(p) + 1);
				else
					counter.put(p, 1);
			}
		}
		
		int maxVoti = -1;
		List<Partecipante> vincitori = new ArrayList<Partecipante>();
		for (Map.Entry<Partecipante, Integer> p : counter.entrySet()) {
			if (p.getValue() > maxVoti) {
				maxVoti = p.getValue();
				vincitori.clear();
				vincitori.add(p.getKey());
			} else if (p.getValue() == maxVoti) {
				vincitori.add(p.getKey());
			}
		}
		
		if (s.getOrdinaleCategoricoType().equals("p")) {
			if (vincitori.size() > 1)
				return new RisultatoPartito(null);
			return new RisultatoPartito((Partito)(vincitori.get(0)));
		}
	
		// da cambiare costruttore cosi da mostrare anche pareggi multipli
		if (vincitori.size() > 1)
			return new RisultatoCandidato(null, 0);
		return new RisultatoCandidato((Candidato)(vincitori.get(0)), maxVoti);
	}

}
