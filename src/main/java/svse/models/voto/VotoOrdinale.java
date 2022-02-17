package svse.models.voto;

import java.util.List;
import java.util.ArrayList;
import javafx.util.Pair;
import svse.models.sessione.Partecipante;
import svse.models.sessione.SessioneDiVoto;

public class VotoOrdinale extends Voto {
	private List<Pair<Partecipante, Integer>> partecipantiOrdinati;
	
	public VotoOrdinale(SessioneDiVoto s) {
		this.sessione = s;
		partecipantiOrdinati = new ArrayList<Pair<Partecipante, Integer>>();
	}
	
	public void addPartecipante(Partecipante p, int o) {
		partecipantiOrdinati.add(new Pair<Partecipante, Integer>(p, o));
	}
	
	public List<Pair<Partecipante, Integer>> getPartecipantiOrdinati() {
		return new ArrayList<Pair<Partecipante, Integer>>(partecipantiOrdinati);
	}
	
	@Override
	public String getTipo() {
		return "o";
	}

}
