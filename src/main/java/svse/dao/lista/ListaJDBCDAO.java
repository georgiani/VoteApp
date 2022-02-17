package svse.dao.lista;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import svse.dao.factory.DAOFactory;
import svse.data.DBManager;
import svse.exceptions.DatabaseException;
import svse.exceptions.ListaNotFoundException;
import svse.models.sessione.Candidato;
import svse.models.sessione.Lista;
import svse.models.sessione.Partito;
import svse.models.sessione.SessioneDiVoto;

public class ListaJDBCDAO implements IListaDAO {
	@Override
	public List<Lista> getListe(SessioneDiVoto s) {
		int id = DAOFactory.getFactory().getSessioneDAOInstance().getId(s);
		String q = "select * from Lista where id_sessione = ?;";
		
		// prepara e gira la query
		List<Lista> result = new ArrayList<Lista>();
		PreparedStatement p = DBManager.getInstance().preparaStatement(q);
		try {	
			p.setInt(1, id);
			ResultSet res = p.executeQuery();
				
			while (res.next())
				result.add(getListaFromResult(res, id));
		} catch (SQLException e) {
			throw new DatabaseException("Problemi con la base dati, riprovare! Context: getListe");
		}
			
		return result;
	}
	
	private Lista getListaFromResult(ResultSet res, int sessione_id) {
		Lista result = null;
		
		try {
			Partito partito = new Partito(res.getString(3));
			List<Candidato> candidati = DAOFactory.getFactory().getCandidatoDAOInstance().getCandidati(partito);
			result = new Lista(partito, candidati);
		} catch (SQLException e) {
			throw new DatabaseException("Problemi con la base dati, riprovare! Context: getListaFromResult");
		}
		
		return result;
	}

	@Override
	public void save(Lista l, SessioneDiVoto s) {
		int id = DAOFactory.getFactory().getSessioneDAOInstance().getId(s);
		String q = "insert into Lista(id_sessione, partito) values (?, ?)";
			
		PreparedStatement p = DBManager.getInstance().preparaStatement(q);
		try {	
			p.setInt(1, id); // id_sessione
			p.setString(2, l.getPartito().getNome()); // partito
			p.execute();
		} catch (SQLException e) {
			throw new DatabaseException("Problemi con la base dati, riprovare! Context: save Lista");
		}
	}

	@Override
	public int getId(Partito par) {
		String partito = par.getNome();
		String q = "select id from Lista where partito = ?;";
		
		// prepara e gira la query
		int result = 0;
		PreparedStatement p = DBManager.getInstance().preparaStatement(q);
		try {
			p.setString(1, partito);
			ResultSet res = p.executeQuery();
				
			if (!res.isBeforeFirst())
				throw new ListaNotFoundException("Lista non esiste!");
			
			// prendi i risultati
			while (res.next())
				result = res.getInt(1);
		} catch (SQLException e) {
			throw new DatabaseException("Problemi con la base dati, riprovare! Context: getId");
		}
			
		return result;
	}

	@Override
	public Lista get(String id) {
		// non usato
		return null;
	}

	@Override
	public List<Lista> getAll() {
		// non usato
		return null;
	}

	@Override
	public void save(Lista t) {
		// non usato
	}

	@Override
	public void update(Lista t, Lista u) {
		// non usato
	}

	@Override
	public void delete(Lista t) {
		// non usato
	}

}
