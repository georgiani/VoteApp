package svse.dao.votazione;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import svse.data.DBManager;
import svse.exceptions.DatabaseException;
import svse.models.sessione.SessioneDiVoto;
import svse.models.utente.Elettore;
import svse.models.voto.Voto;

public class VotazioneJDBCDAO implements IVotazioneDAO {

	@Override
	public Voto get(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Voto> getAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void save(Voto t) {
		// TODO Auto-generated method stub

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
