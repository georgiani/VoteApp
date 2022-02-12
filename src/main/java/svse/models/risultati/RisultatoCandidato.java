package svse.models.risultati;

public class RisultatoCandidato extends Risultato {
	private String nome, cognome;
	private int numeroVoti;
	
	public RisultatoCandidato(String nome, String cognome, int nr) {
		this.nome = nome;
		this.cognome = cognome;
		this.numeroVoti = nr;
	}
	
	public String getNome() {
		return this.nome;
	}
	
	public String getCognome() {
		return this.cognome;
	}
	
	public int getNumeroVoti() {
		return this.numeroVoti;
	}
}
