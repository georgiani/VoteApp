package svse.models.utente;

public abstract class Utente {
	private String nome, cognome, cf, comune;
	// TODO: da eliminare comune
	public Utente(String n, String cg, String cf, String com) {
		this.nome = n;
		this.cognome = cg;
		this.cf = cf;
		this.comune = com;
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
	
	public String getComune() {
		return this.comune;
	}
	
	public abstract boolean isGestore();
	
	public abstract boolean isElettore();
}
