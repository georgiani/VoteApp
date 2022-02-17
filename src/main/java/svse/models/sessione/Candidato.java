package svse.models.sessione;

public class Candidato implements Partecipante {
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

	@Override
	public boolean isPartito() {
		return false;
	}

	@Override
	public boolean isCandidato() {
		return true;
	}
}
