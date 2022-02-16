package svse.dao.sessione;

import svse.dao.IDAO;
import svse.models.sessione.*;
import svse.models.utente.Elettore;
import svse.models.voto.Voto;

import java.util.List;

import ps.IObservable;

public interface ISessioneDAO extends IDAO<SessioneDiVoto>, IObservable {
	public int getId(SessioneDiVoto s);
	public SessioneDiVoto getById(int id);
	public void start(SessioneDiVoto s);
	public void stop(SessioneDiVoto s);
	public List<SessioneDiVoto> getAll(Elettore e);
	
	// metodi che potrebbero essere spostati in DAO specifiche
	public List<Voto> getVoti(SessioneDiVoto s);
}
