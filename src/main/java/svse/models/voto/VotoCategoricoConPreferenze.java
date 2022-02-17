package svse.models.voto;

import java.util.ArrayList;
import java.util.List;

import svse.models.sessione.Candidato;
import svse.models.sessione.Partito;
import svse.models.sessione.SessioneDiVoto;

public class VotoCategoricoConPreferenze extends Voto {
	private Partito p;
	private List<Candidato> cs;
	
	public VotoCategoricoConPreferenze(Partito p, List<Candidato> cs, SessioneDiVoto s) {
		this.p = p;
		this.cs = new ArrayList<Candidato>(cs);
		this.sessione = s;
	}
	
	public Partito getPartito() {
		return this.p;
	}
	
	public List<Candidato> getCandidati() {
		return new ArrayList<Candidato>(this.cs);
	}
	
	@Override
	public String getTipo() {
		return "p";
	}

}
