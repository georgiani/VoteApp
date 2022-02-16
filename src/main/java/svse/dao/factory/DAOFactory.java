package svse.dao.factory;

import svse.dao.candidato.ICandidatoDAO;
import svse.dao.lista.IListaDAO;
import svse.dao.sessione.ISessioneDAO;
import svse.dao.totem.ITotemDAO;
import svse.dao.utente.IUtenteDAO;
import svse.dao.votazione.IVotazioneDAO;

// abstract factory che si occupa di creare piu famiglie di DAO.
// Nel nostro caso per ogni famiglia abbiamo un solo tipo di DAO, JDBC.
// L'implementazione permette di aggiungere in futuro altre implementazioni
//		e cambiare ulteriormente il tipo di factory ritornato
public abstract class DAOFactory {
	// singleton DAOFactory
	private static DAOFactory factory = null;
	public static DAOFactory getFactory() {
		if (factory == null) 
			factory = new JDBCDAOFactory();
		return factory;
	}
	
	// factory methods per tutti i dao
	public abstract IUtenteDAO getUtenteDAOInstance();
	public abstract ISessioneDAO getSessioneDAOInstance();
	public abstract ITotemDAO getTotemDAOInstance();
	public abstract ICandidatoDAO getCandidatoDAOInstance();
	public abstract IListaDAO getListaDAOInstance();
	public abstract IVotazioneDAO getVotazioneDAOInstance();
}
