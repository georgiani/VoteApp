package svse.models.risultati;

public class RisultatoReferendum extends Risultato {
	private int risposteFavorevoli, risposteNonFavorevoli;
	
	public RisultatoReferendum(int f, int n) {
		this.risposteFavorevoli = f;
		this.risposteNonFavorevoli = n;
	}	

	public int getFavorevoli() {
		return this.risposteFavorevoli;
	}
	
	public int getNonFavorevoli() {
		return this.risposteNonFavorevoli;
	}
}
