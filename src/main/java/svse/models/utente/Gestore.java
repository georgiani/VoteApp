package svse.models.utente;

public class Gestore extends Utente {
	public Gestore(String nome, String cognome, String cf) {
		super(nome, cognome, cf);
	}

	@Override
	public boolean isGestore() {
		return true;
	}

	@Override
	public boolean isElettore() {
		return false;
	}
}
