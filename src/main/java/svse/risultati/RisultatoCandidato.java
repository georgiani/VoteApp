package svse.risultati;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import svse.dao.factory.DAOFactory;
import svse.models.sessione.Candidato;
import svse.models.sessione.SessioneDiVoto;

public class RisultatoCandidato extends Risultato {
	private Map<Candidato, Integer> risultati;
	
	public RisultatoCandidato(Map<Candidato, Integer> m, SessioneDiVoto s) {
		this.risultati = new HashMap<Candidato, Integer>(m);
		this.sessione = s;
	}
	
	public Map<Candidato, Integer> getRisultati() {
		return new HashMap<Candidato, Integer>(this.risultati);
	}

	@Override
	public String getVincitore() {
		String result = "";
		
		if (sessione.getStrategiaVincita().equals("m") || sessione.getStrategiaVincita().equals("p")) {
			List<Candidato> vincitori = new ArrayList<Candidato>();
			
			int maxVoti = -1;
			for (Map.Entry<Candidato, Integer> entry : risultati.entrySet()) {
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
				for (Candidato c : vincitori)
					result += "\nCandidato: " + c.getNome() + " " + c.getCognome();
			} else if (vincitori.size() == 1) {
				result = "Vincitore: " + vincitori.get(0).getNome() + " " + vincitori.get(0).getCognome();
			} else {
				result = "Nessun Vincitore!";
			}
		} else if (sessione.getStrategiaVincita().equals("a")) {
			int numeroVotanti = DAOFactory.getFactory().getVotazioneDAOInstance().getTotaleVotanti(sessione);
			
			for (Map.Entry<Candidato, Integer> entry : risultati.entrySet()) {
				if (entry.getValue() > (0.5 * numeroVotanti)) {
					result = "Vincitore: " + entry.getKey().getNome() + " " + entry.getKey().getCognome(); 
					break;
				}
			}
			
			if (result.equals(""))
				result = "Nessun candidato con maggioranza assoluta!";
		}
		
		return result;
	}

	@Override
	public String getRisultatiInteri() {
		int numeroVotanti = DAOFactory.getFactory().getVotazioneDAOInstance().getTotaleVotanti(sessione);
		String result =  "Numero totale votanti: " + numeroVotanti;
		for (Map.Entry<Candidato, Integer> entry : risultati.entrySet()) {
			result += "\n" + entry.getKey().getNome() + " " + entry.getKey().getCognome() + ": " + entry.getValue() + " voti";
		}
		return result;
	}
}
