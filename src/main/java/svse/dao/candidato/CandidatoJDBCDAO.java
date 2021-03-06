package svse.dao.candidato;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import svse.dao.factory.DAOFactory;
import svse.data.DBManager;
import svse.exceptions.CandidatoNotFoundException;
import svse.exceptions.DatabaseException;
import svse.models.sessione.Candidato;
import svse.models.sessione.Lista;
import svse.models.sessione.Partito;

public class CandidatoJDBCDAO implements ICandidatoDAO {

	@Override
	public List<Candidato> getCandidati(Partito partito) {
		int id = DAOFactory.getFactory().getListaDAOInstance().getId(partito);
		String q = "select * from Candidato where id_lista = ?;";
		
		// prepara e gira la query
		List<Candidato> result = new ArrayList<Candidato>();
		PreparedStatement p = DBManager.getInstance().preparaStatement(q);
		try {	
			p.setInt(1, id);
			ResultSet res = p.executeQuery();
			
			if (!res.isBeforeFirst())
				return new ArrayList<Candidato>(); // vuoto
				
			while (res.next())
				result.add(getCandidatoFromResult(res));
		} catch (SQLException e) {
			throw new DatabaseException("Problemi con la base dati, riprovare! Context: getCandidati");
		}
			
		return result;
	}
	
	private Candidato getCandidatoFromResult(ResultSet res) {
		Candidato result = null;
		String n, c;
		
		try {
			n = res.getString(2);
			c = res.getString(3);
			result = new Candidato(n, c);
		} catch (SQLException e) {
			throw new DatabaseException("Problemi con la base dati, riprovare! Context: getCandidatoFromResult");
		}
		
		return result;
	}

	@Override
	public void save(Candidato c, Lista l) {
		int id = DAOFactory.getFactory().getListaDAOInstance().getId(l.getPartito());
		String q = "insert into Candidato(nome, cognome, id_lista) values (?, ?, ?)";
				
		// prepara e gira la query
		PreparedStatement p = DBManager.getInstance().preparaStatement(q);
		try {	
			p.setString(1, c.getNome()); // nome
			p.setString(2, c.getCognome()); // cognome
			p.setInt(3, id); // id_lista
			p.execute();
		} catch (SQLException e) {
			throw new DatabaseException("Problemi con la base dati, riprovare! Context: save Candidato");
		}
	}

	@Override
	public int getId(Candidato c) {
		String q = "select id from Candidato where nome = ? and cognome = ?;";
		
		// prepara e gira la query
		int result = 0;
		PreparedStatement p = DBManager.getInstance().preparaStatement(q);
		try {	
			p.setString(1, c.getNome());
			p.setString(2, c.getCognome());
			ResultSet res = p.executeQuery();
				
			if (!res.isBeforeFirst())
				throw new CandidatoNotFoundException("Candidato non esiste!");
			
			// prendi i risultati
			while (res.next())
				result = res.getInt(1);
		} catch (SQLException e) {
			throw new DatabaseException("Problemi con la base dati, riprovare! Context: getId");
		}
			
		return result;
	}

	@Override
	public int getIdLista(Candidato c) {
		String q = "select id_lista from Candidato where nome = ? and cognome = ?;";
		
		// prepara e gira la query
		int result = 0;
		PreparedStatement p = DBManager.getInstance().preparaStatement(q);
		try {	
			p.setString(1, c.getNome());
			p.setString(2, c.getCognome());
			ResultSet res = p.executeQuery();
				
			if (!res.isBeforeFirst())
				throw new CandidatoNotFoundException("Candidato non esiste!");
			
			// prendi i risultati
			while (res.next())
				result = res.getInt(1);
		} catch (SQLException e) {
			throw new DatabaseException("Problemi con la base dati, riprovare! Context: getIdLista");
		}
			
		return result;
	}

	@Override
	public Candidato get(String id) {
		// non usato
		return null;
	}

	@Override
	public List<Candidato> getAll() {
		// non usato
		return null;
	}

	@Override
	public void save(Candidato t) {
		// non usato
		
	}

	@Override
	public void update(Candidato t, Candidato u) {
		// non usato
	}

	@Override
	public void delete(Candidato t) {
		// non usato
	}
}
