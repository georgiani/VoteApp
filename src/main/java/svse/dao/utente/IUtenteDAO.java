package svse.dao.utente;

import svse.dao.IDAO;
import svse.models.utente.Utente;

import java.util.List;

import ps.IObservable;

// famiglia di prodotti
public interface IUtenteDAO extends IDAO<Utente>, IObservable {
	public Utente login(String cf, String password); // login
	public List<Utente> getAllElettori();
	public List<Utente> getAllGestori();
	public boolean registraElettore(Utente t, String pass);
	public int getId(Utente u);
}
