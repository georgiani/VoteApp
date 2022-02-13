package svse.dao.sessione;

import svse.dao.IDAO;
import svse.models.componentistica.Candidato;
import svse.models.sessione.*;
import java.util.List;

public interface ISessioneDAO extends IDAO<SessioneDiVoto>{
	public int getId(SessioneDiVoto s);
	public void start(SessioneDiVoto s);
	public void stop(SessioneDiVoto s);
	
	// metodi che potrebbero essere spostati in DAO specifiche
	public List<Voto> getVoti(SessioneDiVoto s);
	
	public List<Lista> getListe(SessioneDiVoto s);
	public void save(Lista l, SessioneDiVoto s);
	public int getId(Lista l); // supponiamo nomi partiti come unici
	
	public void save(Candidato c, Lista l);
	public int getId(Candidato c); // supponiamo unique(nome, cognome)
}
