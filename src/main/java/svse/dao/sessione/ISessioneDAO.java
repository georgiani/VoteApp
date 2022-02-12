package svse.dao.sessione;

import svse.dao.IDAO;
import svse.models.sessione.*;
import java.util.List;

public interface ISessioneDAO extends IDAO<SessioneDiVoto>{
	public int getId(SessioneDiVoto s);
	public void start(SessioneDiVoto s);
	public void stop(SessioneDiVoto s);
	public List<Voto> getVoti(String nomeSessione);
	public List<Lista> getListe(String nomeSessione);
}
