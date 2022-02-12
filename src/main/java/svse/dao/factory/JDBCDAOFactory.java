package svse.dao.factory;

import svse.dao.sessione.*;
import svse.dao.utente.*;

public class JDBCDAOFactory extends DAOFactory {
	private static IUtenteDAO daoUtente = null;
	private static ISessioneDAO daoSessione = null;

	public IUtenteDAO getUtenteDAOInstance() {
		if (daoUtente == null)
			daoUtente = new UtenteJDBCDAO();
		return daoUtente;
	}

	@Override
	public ISessioneDAO getSessioneDAOInstance() {
		if (daoSessione == null) 
			daoSessione = new SessioneJDBCDAO();
		return daoSessione;
	}
}
