package svse.dao.factory;

import svse.dao.utente.*;
import svse.dao.votazione.IVotazioneDAO;
import svse.dao.votazione.VotazioneJDBCDAO;
import svse.dao.candidato.CandidatoJDBCDAO;
import svse.dao.candidato.ICandidatoDAO;
import svse.dao.lista.IListaDAO;
import svse.dao.lista.ListaJDBCDAO;
import svse.dao.sessione.*;
import svse.dao.totem.*;

public class JDBCDAOFactory extends DAOFactory {
	private static IUtenteDAO daoUtente = null;
	private static ISessioneDAO daoSessione = null;
	private static ITotemDAO daoTotem = null;
	private static ICandidatoDAO daoCandidato = null;
	private static IListaDAO daoLista = null;
	private static IVotazioneDAO daoVotazione = null;

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

	@Override
	public ICandidatoDAO getCandidatoDAOInstance() {
		if (daoCandidato == null) 
			daoCandidato = new CandidatoJDBCDAO();
		return daoCandidato;
	}

	@Override
	public IListaDAO getListaDAOInstance() {
		if (daoLista == null) 
			daoLista = new ListaJDBCDAO();
		return daoLista;
	}
	
	@Override
	public IVotazioneDAO getVotazioneDAOInstance() {
		if (daoVotazione == null) 
			daoVotazione = new VotazioneJDBCDAO();
		return daoVotazione;
	}
}
