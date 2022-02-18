package svse.risultati;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import svse.dao.factory.DAOFactory;
import svse.models.sessione.Partito;
import svse.models.sessione.SessioneDiVoto;

public class RisultatoPartito extends Risultato {
	private Map<Partito, Integer> risultati;
	
	public RisultatoPartito(Map<Partito, Integer> m, SessioneDiVoto s) {
		this.risultati = new HashMap<Partito, Integer>(m);
		this.sessione = s;
	}
	
	public Map<Partito, Integer> getRisultati() {
		return new HashMap<Partito, Integer>(this.risultati);
	}

	@Override
	public String getVincitore() {
		String result = "";
		
		if (sessione.getStrategiaVincita().equals("m") || sessione.getStrategiaVincita().equals("p")) {
			List<Partito> vincitori = new ArrayList<Partito>();
			
			int maxVoti = -1;
			for (Map.Entry<Partito, Integer> entry : risultati.entrySet()) {
				if (entry.getValue() > maxVoti) {
					maxVoti = entry.getValue();
					vincitori.clear();
					vincitori.add(entry.getKey());
				} else if (entry.getValue() == maxVoti) {
					vincitori.add(entry.getKey());
				}
			}
			
			if (vincitori.size() > 1) {
				result = "Pareggio.\n";
				for (Partito p : vincitori)
					result += "\nPartito: " + p.getNome();
			} else {
				result = "Vincitore: " + vincitori.get(0).getNome();
			}
		} else if (sessione.getStrategiaVincita().equals("a")) {
			int numeroVotanti = DAOFactory.getFactory().getVotazioneDAOInstance().getTotaleVotanti(sessione);
			
			for (Map.Entry<Partito, Integer> entry : risultati.entrySet()) {
				if (entry.getValue() > (0.5 * numeroVotanti)) {
					result = "Vincitore: " + entry.getKey().getNome(); 
					break;
				}
			}
			
			if (result.equals(""))
				result = "Nessun partito con maggioranza assoluta!";
		}
		
		return result;
	}

	@Override
	public String getRisultatiInteri() {
		int numeroVotanti = DAOFactory.getFactory().getVotazioneDAOInstance().getTotaleVotanti(sessione);
		String result =  "Numero totale votanti: " + numeroVotanti;
		for (Map.Entry<Partito, Integer> entry : risultati.entrySet()) {
			result += "\n" + entry.getKey().getNome() + ": " + entry.getValue() + " voti";
		}
		return result;
	}
}
