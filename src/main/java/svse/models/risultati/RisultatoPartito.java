package svse.models.risultati;

import java.util.ArrayList;
import java.util.List;

public class RisultatoPartito extends Risultato {
	private String nome;
	private List<RisultatoCandidato> componenti;
	
	public RisultatoPartito(String nome, final List<RisultatoCandidato> comp) {
		this.nome = nome;
		this.componenti = new ArrayList<RisultatoCandidato>(comp);
	}
	
	public String getNome() {
		return this.nome;
	}
	
	public List<RisultatoCandidato> getRisultatiCandidati() {
		return new ArrayList<RisultatoCandidato>(this.componenti);
	}
}
