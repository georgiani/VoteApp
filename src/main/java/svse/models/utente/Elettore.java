package svse.models.utente;

public class Elettore extends Utente {
	public Elettore(String nome, String cognome, String cf) {
		super(nome, cognome, cf);
	}

	@Override
	public boolean isGestore() {
		return false;
	}

	@Override
	public boolean isElettore() {
		return true;
	}
}
