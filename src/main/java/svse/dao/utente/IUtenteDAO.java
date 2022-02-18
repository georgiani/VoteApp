package svse.dao.utente;

import svse.dao.IDAO;
import svse.models.utente.Utente;

// famiglia di prodotti
public interface IUtenteDAO extends IDAO<Utente> {
	public Utente login(String cf, String password); // login
	public boolean registraElettore(Utente t, String pass);
	public int getId(Utente u);
}
