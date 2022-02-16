package svse.models.utente;

public abstract class Utente {
	private String nome, cognome, cf;
	// TODO: da eliminare comune
	public Utente(String n, String cg, String cf) {
		this.nome = n;
		this.cognome = cg;
		this.cf = cf;
	}
	
	public String getNome() {
		return this.nome;
	}
	
	public String getCognome() {
		return this.cognome;
	}
	
	public String getCF() {
		return this.cf;
	}
	
	public abstract boolean isGestore();
	
	public abstract boolean isElettore();
}
