package svse.dao.sessione;

import svse.dao.IDAO;
import svse.models.sessione.*;
import svse.models.utente.Elettore;
import svse.risultati.Risultato;

import java.util.List;
import ps.IObservable;

public interface ISessioneDAO extends IDAO<SessioneDiVoto>, IObservable {
	public int getId(SessioneDiVoto s);
	public SessioneDiVoto getById(int id);
	public void start(SessioneDiVoto s);
	public void stop(SessioneDiVoto s);
	public List<SessioneDiVoto> getAll(Elettore e);
	public Risultato getRisultato(SessioneDiVoto s);
}
