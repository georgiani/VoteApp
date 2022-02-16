package svse.dao.votazione;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import svse.dao.factory.DAOFactory;
import svse.data.DBManager;
import svse.exceptions.DatabaseException;
import svse.models.sessione.SessioneDiVoto;
import svse.models.utente.Elettore;
import svse.models.voto.Voto;
import svse.models.voto.VotoReferendum;

public class VotazioneJDBCDAO implements IVotazioneDAO {

	@Override
	public Voto get(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Voto> getAll() {
		// non usato
		return null;
	}
	
	@Override
	public List<Voto> getAll(SessioneDiVoto s) {
		int id = DAOFactory.getFactory().getSessioneDAOInstance().getId(s);
		String q = "select * from Sessione where id_sessione = ?;";
		
		// prepara e gira la query
		List<Voto> result = new ArrayList<Voto>();
		PreparedStatement p = DBManager.getInstance().preparaStatement(q);
		try {
			p.setInt(1, id);
			ResultSet res = p.executeQuery();
			while (res.next())
				result.add(getVotoFromResult(res, s));
		} catch (SQLException e) {
			throw new DatabaseException("Problemi con la base dati, riprovare!");
		}
			
		return result;
	}
	
	private Voto getVotoFromResult(ResultSet res, SessioneDiVoto s) {
		Voto result = null;
		int id, id_lista;
		boolean rispostaRef;
		
		try {
			if (s.getStrategiaVoto().equals("r")) {
				rispostaRef = res.getBoolean(4);
				result = new VotoReferendum(rispostaRef, s);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public void save(Voto t) {
		if (t.getTipo().equals("r")) {
			VotoReferendum vr = (VotoReferendum)t;
			int id = DAOFactory.getFactory().getSessioneDAOInstance().getId(vr.getSessione());
			
			String q = "insert into Voto(risposta_referendum, id_sessione) values (?, ?)";
			
			// prepara e gira la query
			PreparedStatement p = DBManager.getInstance().preparaStatement(q);
			try {	
				p.setBoolean(1, vr.isFavorevole());
				p.setInt(2, id);
				p.execute();
			} catch (SQLException e) {
				throw new DatabaseException("Problemi con la base dati, riprovare!");
			}
		}
	}

	@Override
	public void update(Voto t, Voto u) {
		// TODO Auto-generated method stub

	}

	@Override
	public void delete(Voto t) {
		// TODO Auto-generated method stub

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
}
