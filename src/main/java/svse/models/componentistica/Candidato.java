package svse.models.componentistica;

public class Candidato {
	private String nome, cognome;
	public Candidato(String nome, String cognome) {
		this.nome = nome;
		this.cognome = cognome;
	}
	
	public String getNome() {
		return this.nome;
	}
	
	public String getCognome() {
		return this.cognome;
	}
}
