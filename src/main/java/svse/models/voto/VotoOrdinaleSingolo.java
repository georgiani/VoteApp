package svse.models.voto;

import svse.models.sessione.*;
import javafx.util.Pair;

public class VotoOrdinaleSingolo extends Voto {
	private Pair<Partecipante, Integer> sceltaOrdine;
	
	public VotoOrdinaleSingolo(Partecipante p, int o, SessioneDiVoto s) {
		this.sceltaOrdine = new Pair<Partecipante, Integer>(p, o);
		this.sessione = s;
	}
	
	public Pair<Partecipante, Integer> getSingolo() {
		return this.sceltaOrdine;
	}
	
	@Override
	public String getTipo() {
		return "o";
	}

}
