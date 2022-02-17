package svse.models.risultati;

import java.util.ArrayList;
import java.util.List;

import svse.models.sessione.Partito;

public class RisultatoPartito extends Risultato {
	private Partito partito;
	
	public RisultatoPartito(Partito p) {
		this.partito = p;
	}
	
	public Partito getPartio() {
		return this.partito;
	}
}
