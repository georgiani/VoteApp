package svse.models.voto;

import svse.models.sessione.Partecipante;
import svse.models.sessione.SessioneDiVoto;

public class VotoCategorico extends Voto {
	private Partecipante partecipanteScelto;
	
	public VotoCategorico(Partecipante partecipante, SessioneDiVoto s) {
		this.partecipanteScelto = partecipante;
		this.sessione = s;
	}
	
	public Partecipante getPartecipanteScelto() {
		return this.partecipanteScelto;
	}
	
	@Override
	public String getTipo() {
		return "c";
	}
}
