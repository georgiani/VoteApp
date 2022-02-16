package svse.models.sessione;

import java.util.List;
import java.util.ArrayList;

public class Lista {
	private Partito partitoAssociato;
	private List<Candidato> candidati;
	
	public Lista(Partito p, List<Candidato> cand) {
		partitoAssociato = p;
		candidati = new ArrayList<Candidato>(cand);
	}
	
	public List<Candidato> getCandidati() {
		return new ArrayList<Candidato>(candidati);
	}
	
	public Partito getPartito() {
		return partitoAssociato;
	}
	
}
