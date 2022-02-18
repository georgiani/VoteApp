package svse.models.voto;

import svse.models.sessione.SessioneDiVoto;

public class VotoBianco extends Voto {
	public VotoBianco(SessioneDiVoto s) {
		this.sessione = s;
	}
	
	@Override
	public String getTipo() {
		return "b";
	}
}
