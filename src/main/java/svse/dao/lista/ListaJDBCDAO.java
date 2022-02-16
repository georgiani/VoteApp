package svse.dao.lista;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import svse.dao.factory.DAOFactory;
import svse.data.DBManager;
import svse.exceptions.DatabaseException;
import svse.models.sessione.Candidato;
import svse.models.sessione.Lista;
import svse.models.sessione.Partito;
import svse.models.sessione.SessioneDiVoto;

public class ListaJDBCDAO implements IListaDAO {

	@Override
	public Lista get(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Lista> getAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void save(Lista t) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(Lista t, Lista u) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(Lista t) {
		// TODO Auto-generated method stub
		
	}

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
				
			// prendi i risultati
			while (res.next())
				result.add(getListaFromResult(res, id));
		} catch (SQLException e) {
			throw new DatabaseException("Problemi con la base dati, riprovare!");
		}
			
		return result;
	}
	
	private Lista getListaFromResult(ResultSet res, int sessione_id) {
		Lista result = null;
		String nome;
		
		try {
			nome = res.getString(3);
			List<Candidato> candidati = DAOFactory.getFactory().getCandidatoDAOInstance().getCandidati(nome);
			result = new Lista(new Partito(nome), candidati);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return result;
	}

	@Override
	public void save(Lista l, SessioneDiVoto s) {
		int id = DAOFactory.getFactory().getSessioneDAOInstance().getId(s);
		String q = "insert into Lista(id_sessione, partito) values (?, ?)";
				
		// prepara e gira la query
		PreparedStatement p = DBManager.getInstance().preparaStatement(q);
		try {	
			p.setInt(1, id); // id_sessione
			p.setString(2, l.getPartito().getNome()); // partito
			p.execute();
		} catch (SQLException e) {
			throw new DatabaseException("Problemi con la base dati, riprovare!");
		}
	}

	@Override
	public int getId(String partito) {
		String q = "select id from Lista where partito = ?;";
		
		// prepara e gira la query
		int result = 0;
		PreparedStatement p = DBManager.getInstance().preparaStatement(q);
		try {
			p.setString(1, partito);
			ResultSet res = p.executeQuery();
				
			// TODO: controllo 0 liste
			
			// prendi i risultati
			while (res.next())
				result = res.getInt(1);
		} catch (SQLException e) {
			throw new DatabaseException("Problemi con la base dati, riprovare!");
		}
			
		return result;
	}

}
