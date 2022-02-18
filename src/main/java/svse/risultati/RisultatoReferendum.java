package svse.risultati;

import svse.dao.factory.DAOFactory;
import svse.models.sessione.SessioneDiVoto;

public class RisultatoReferendum extends Risultato {
	private int risposteFavorevoli, risposteNonFavorevoli;
	
	public RisultatoReferendum(int f, int n, SessioneDiVoto s) {
		this.risposteFavorevoli = f;
		this.risposteNonFavorevoli = n;
		this.sessione = s;
	}	

	public int getFavorevoli() {
		return this.risposteFavorevoli;
	}
	
	public int getNonFavorevoli() {
		return this.risposteNonFavorevoli;
	}
	
	public String getVincitore() {
		String result = "";
		
		if (sessione.getStrategiaVincita().equals("q")) {
			int numeroVotanti = DAOFactory.getFactory().getVotazioneDAOInstance().getTotaleVotanti(sessione);
			int numeroAventiDiritto = DAOFactory.getFactory().getVotazioneDAOInstance().getTotaleAventiDirittoAlVoto();
			if (numeroVotanti > numeroAventiDiritto) {
				if (risposteFavorevoli > risposteNonFavorevoli)
					result =  "Vincita: Favorevole";
				else if (risposteFavorevoli < risposteNonFavorevoli)
					result = "Vincita: Non Favorevolte";
				else result = "Pareggio";
			} else {
				result = "Non hanno partecipato alla consultazione la maggioranza degli aventi diritto al voto";
			}
		} else if (sessione.getStrategiaVincita().equals("r")) {
			if (risposteFavorevoli > risposteNonFavorevoli)
				result = "Vincita: Favorevole";
			else if (risposteFavorevoli < risposteNonFavorevoli)
				result = "Vincita: Non Favorevolte";
			else result = "Pareggio";
		}
			
		return result;
	}

	@Override
	public String getRisultatiInteri() {
		int numeroVotanti = DAOFactory.getFactory().getVotazioneDAOInstance().getTotaleVotanti(sessione);
		return "Numero totale votanti: " + numeroVotanti + "\nNumero voti favorevoli: " + risposteFavorevoli + "\nNumero voti non favorevoli: " + risposteNonFavorevoli;
	}
}
