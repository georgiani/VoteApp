package svse.models.voto;

public class VotoReferendum extends Voto {
	private String risposta;
	
	public VotoReferendum(String risp) {
		risposta = risp;
	}
	
	public boolean isFavorevole() {
		return risposta.equals("si");
	}
}