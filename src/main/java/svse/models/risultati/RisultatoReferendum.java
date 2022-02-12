package svse.models.risultati;

public class RisultatoReferendum extends Risultato {
	private String rispostaVincente;
	public RisultatoReferendum(String r) {
		rispostaVincente = r;
	}
	public String getRispostaVincente() {
		return rispostaVincente;
	}
}
