package svse.dao.votazione;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import javafx.util.Pair;
import svse.dao.factory.DAOFactory;
import svse.data.DBManager;
import svse.exceptions.DatabaseException;
import svse.models.sessione.Candidato;
import svse.models.sessione.Partecipante;
import svse.models.sessione.Partito;
import svse.models.sessione.SessioneDiVoto;
import svse.models.utente.Elettore;
import svse.models.voto.Voto;
import svse.models.voto.VotoCategorico;
import svse.models.voto.VotoCategoricoConPreferenze;
import svse.models.voto.VotoOrdinale;
import svse.models.voto.VotoReferendum;

public class VotazioneJDBCDAO implements IVotazioneDAO {
	@Override
	public void save(Voto t) {
		int idSessione = DAOFactory.getFactory().getSessioneDAOInstance().getId(t.getSessione());
		if (t.getTipo().equals("r")) {
			VotoReferendum vr = (VotoReferendum)t;
			
			String q = "insert into Voto(risposta_referendum, id_sessione) values (?, ?);";
			
			// prepara e gira la query
			PreparedStatement p = DBManager.getInstance().preparaStatement(q);
			try {	
				p.setBoolean(1, vr.isFavorevole());
				p.setInt(2, idSessione);
				p.execute();
			} catch (SQLException e) {
				throw new DatabaseException("Problemi con la base dati, riprovare! Context: save Voto");
			}
		} else if (t.getTipo().equals("c")) {
			VotoCategorico vc = (VotoCategorico)t;
			
			if (vc.getSessione().getOrdinaleCategoricoType().equals("p")) {
				String q = "insert into Voto(id_lista, id_sessione) values (?, ?);";
				int idLista = DAOFactory.getFactory().getListaDAOInstance().getId((Partito)vc.getPartecipanteScelto());
				
				PreparedStatement p = DBManager.getInstance().preparaStatement(q);
				try {	
					p.setInt(1, idLista);
					p.setInt(2, idSessione);
					p.execute();
				} catch (SQLException e) {
					throw new DatabaseException("Problemi con la base dati, riprovare! Context: save Voto");
				}
			} else {
				String q = "insert into Voto(id_lista, id_cand, id_sessione) values (?, ?, ?);";
				int idLista = DAOFactory.getFactory().getCandidatoDAOInstance().getIdLista((Candidato)vc.getPartecipanteScelto());
				int idCandidato = DAOFactory.getFactory().getCandidatoDAOInstance().getId((Candidato)vc.getPartecipanteScelto());
				
				PreparedStatement p = DBManager.getInstance().preparaStatement(q);
				try {	
					p.setInt(1, idLista);
					p.setInt(2, idCandidato);
					p.setInt(3, idSessione);
					p.execute();
				} catch (SQLException e) {
					throw new DatabaseException("Problemi con la base dati, riprovare! Context: save Voto");
				}
			}
		} else if (t.getTipo().equals("p")) {
			VotoCategoricoConPreferenze vcp = (VotoCategoricoConPreferenze)t;
			int idLista = DAOFactory.getFactory().getListaDAOInstance().getId(vcp.getPartito());
			String q = "insert into Voto(id_lista, id_cand, id_sessione) values (?, ?, ?);";
			
			for (Candidato c : vcp.getCandidati()) {
				int idCandidato = DAOFactory.getFactory().getCandidatoDAOInstance().getId(c);
				
				PreparedStatement p = DBManager.getInstance().preparaStatement(q);
				try {	
					p.setInt(1, idLista);
					p.setInt(2, idCandidato);
					p.setInt(3, idSessione);
					p.execute();
				} catch (SQLException e) {
					throw new DatabaseException("Problemi con la base dati, riprovare!");
				}
			}
		} else if (t.getTipo().equals("o")) {
			VotoOrdinale vo = (VotoOrdinale)t;
			if (vo.getSessione().getOrdinaleCategoricoType().equals("p")) {
				String q = "insert into Voto(id_lista, id_sessione, ordine) values (?, ?, ?);";
				
				for (Pair<Partecipante, Integer> entry : vo.getPartecipantiOrdinati()) {
					int idLista = DAOFactory.getFactory().getListaDAOInstance().getId((Partito)entry.getKey());
					int ordine = entry.getValue();
					
					PreparedStatement p = DBManager.getInstance().preparaStatement(q);
					try {	
						p.setInt(1, idLista);
						p.setInt(2, idSessione);
						p.setInt(3, ordine);
						p.execute();
					} catch (SQLException e) {
						throw new DatabaseException("Problemi con la base dati, riprovare! Context: save Voto");
					}
				}
			} else if (t.getTipo().equals("r")) {
				String q = "insert into Voto(id_lista, id_cand, id_sessione, ordine) values (?, ?, ?, ?);";
				
				for (Pair<Partecipante, Integer> entry : vo.getPartecipantiOrdinati()) {
					int idLista = DAOFactory.getFactory().getCandidatoDAOInstance().getIdLista((Candidato)entry.getKey());
					int idCandidato = DAOFactory.getFactory().getCandidatoDAOInstance().getId((Candidato)entry.getKey());
					int ordine = entry.getValue();
					
					PreparedStatement p = DBManager.getInstance().preparaStatement(q);
					try {	
						p.setInt(1, idLista);
						p.setInt(2, idCandidato);
						p.setInt(3, idSessione);
						p.setInt(4, ordine);
						p.execute();
					} catch (SQLException e) {
						throw new DatabaseException("Problemi con la base dati, riprovare! Context: save Voto");
					}
				}
			} else if (t.getTipo().equals("b")) {
				String q = "insert into Voto(id_sessione) values (?);";
				PreparedStatement p = DBManager.getInstance().preparaStatement(q);
				
				try {
					p.setInt(1, idSessione);
					p.execute();
				} catch (SQLException e) {
					throw new DatabaseException("Problemi con la base dati, riprovare! Context: saveVuoto");
				}
			}
		}
	}

	@Override
	public void confermaVotazione(Elettore e, SessioneDiVoto s) {
		String q = "insert into Votato(id_utente, id_sessione) values((select id from Utente where cf = ?), (select id from Sessione where nome = ?))";
		// prepara e gira la query
		PreparedStatement p = DBManager.getInstance().preparaStatement(q);
		try {	
			p.setString(1, e.getCF()); 
			p.setString(2, s.getNome());
			p.execute();
		} catch (SQLException sqe) {
			throw new DatabaseException("Problemi con la base dati, riprovare!" + sqe.getMessage());
		}
	}
	
	@Override
	public int getTotaleAventiDirittoAlVoto() {
		int result = 0;
		String q = "select count(id) from Utente where ruolo = 'e'";
		PreparedStatement p = DBManager.getInstance().preparaStatement(q);
		try {
			ResultSet res = p.executeQuery();
			while(res.next())
				result = res.getInt(1);
		} catch (SQLException sqe) {
			throw new DatabaseException("Problemi con la base dati, riprovare!" + sqe.getMessage());
		}
		
		return result;
	}
	
	@Override
	public int getTotaleVotanti(SessioneDiVoto s) {
		int idSessione = DAOFactory.getFactory().getSessioneDAOInstance().getId(s);
		int result = 0;
		
		if (s.getStrategiaVoto().equals("p")) {
			String q = "select count(id) from Voto where id_sessione = ?;";
			PreparedStatement p = DBManager.getInstance().preparaStatement(q);
			try {
				p.setInt(1, idSessione);
				ResultSet res = p.executeQuery();
				while(res.next())
					result = res.getInt(1);
			} catch (SQLException sqe) {
				throw new DatabaseException("Problemi con la base dati, riprovare!" + sqe.getMessage());
			}
		} else {
			String q = "select count(id) from Votato where id_sessione = ?";
			PreparedStatement p = DBManager.getInstance().preparaStatement(q);
			try {
				p.setInt(1, idSessione);
				ResultSet res = p.executeQuery();
				while(res.next())
					result = res.getInt(1);
			} catch (SQLException sqe) {
				throw new DatabaseException("Problemi con la base dati, riprovare!" + sqe.getMessage());
			}
		}
		
		return result;
	}
	
	@Override
	public boolean haPartecipatoASessione(Elettore e, SessioneDiVoto s) {
		int idElettore = DAOFactory.getFactory().getUtenteDAOInstance().getId(e);
		int idSessione = DAOFactory.getFactory().getSessioneDAOInstance().getId(s);
		boolean result;
		String q = "select count(id) from Votato where id_utente = ? and id_sessione = ?;";
		PreparedStatement p = DBManager.getInstance().preparaStatement(q);
		try {
			p.setInt(1, idElettore);
			p.setInt(2, idSessione);
			ResultSet res = p.executeQuery();
			if (!res.isBeforeFirst())
				result = false;
			else result = true;
		} catch (SQLException sqe) {
			throw new DatabaseException("Problemi con la base dati, riprovare!" + sqe.getMessage());
		}
		
		return result;
	}
	
	@Override
	public void update(Voto t, Voto u) {
		// non usato
	}

	@Override
	public void delete(Voto t) {
		// non usato
	}
	
	@Override
	public Voto get(String id) {
		// non usato
		return null;
	}

	@Override
	public List<Voto> getAll() {
		// non usato
		return null;
	}
}
