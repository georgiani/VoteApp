package svse.models.sessione;

public class Partito implements Partecipante {
	private String nome;
	
	public Partito(String nome) {
		this.nome = nome;
	}
	
	public String getNome() {
		return this.nome;
	}

	@Override
	public boolean isPartito() {
		return true;
	}

	@Override
	public boolean isCandidato() {
		return false;
	}
}
