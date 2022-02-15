package svse.dao.factory;

import svse.dao.utente.*;
import svse.dao.sessione.*;
import svse.dao.totem.*;

public class JDBCDAOFactory extends DAOFactory {
	private static IUtenteDAO daoUtente = null;
	private static ISessioneDAO daoSessione = null;
	private static ITotemDAO daoTotem = null;

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
	
	@Override
	public ITotemDAO getTotemDAOInstance() {
		if (daoTotem == null) 
			daoTotem = new TotemJDBCDAO();
		return daoTotem;
	}
}
