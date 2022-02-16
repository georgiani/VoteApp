package svse.models.voto;

import svse.models.sessione.SessioneDiVoto;

public class VotoReferendum extends Voto {
	private boolean risposta;
	
	public VotoReferendum(boolean risp, SessioneDiVoto s) {
		risposta = risp;
		sessione = s;
	}
	
	public boolean isFavorevole() {
		return risposta;
	}

	@Override
	public String getTipo() {
		return "r";
	}
	
	public SessioneDiVoto getSessione() {
		return this.sessione;
	}
}
