package svse.models.risultati;

import svse.models.sessione.Candidato;

public class RisultatoCandidato extends Risultato {
	private Candidato c;
	private int numeroVoti;
	
	public RisultatoCandidato(Candidato candidato, int nr) {
		this.c = candidato;
		this.numeroVoti = nr;
	}
	
	public Candidato getCandidato() {
		return this.c;
	}
	
	public int getNumeroVoti() {
		return this.numeroVoti;
	}
}
