package svse.models.voto;

import svse.models.sessione.SessioneDiVoto;

public abstract class Voto {
	protected SessioneDiVoto sessione;
	public abstract String getTipo();
	
	public SessioneDiVoto getSessione() {
		return this.sessione;
	}
}
