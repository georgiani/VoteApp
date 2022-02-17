package svse.models.risultati;

public class RisultatoReferendum extends Risultato {
	private Boolean rispostaVincente;
	public RisultatoReferendum(Boolean r) {
		rispostaVincente = r;
	}
	public Boolean getRispostaVincente() {
		return rispostaVincente;
	}
}
