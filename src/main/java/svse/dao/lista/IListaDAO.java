package svse.dao.lista;

import java.util.List;

import svse.dao.IDAO;
import svse.models.sessione.Lista;
import svse.models.sessione.Partito;
import svse.models.sessione.SessioneDiVoto;

public interface IListaDAO extends IDAO<Lista> {
	public List<Lista> getListe(SessioneDiVoto s);
	public void save(Lista l, SessioneDiVoto s);
	public int getId(Partito par);
}
