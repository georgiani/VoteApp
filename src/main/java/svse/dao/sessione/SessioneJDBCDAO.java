package svse.dao.sessione;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import ps.IObserver;
import svse.dao.factory.DAOFactory;
import svse.data.DBManager;
import svse.exceptions.*;
import svse.models.sessione.*;
import svse.models.utente.Elettore;
import svse.models.voto.Voto;

public class SessioneJDBCDAO implements ISessioneDAO {
	
	private List<IObserver> obs = new ArrayList<IObserver>();

	@Override
	public SessioneDiVoto get(String id) {
		String q = "select * from Sessione where nome = ?;";
			
		// prepara e gira la query
		SessioneDiVoto result = null;
		PreparedStatement p = DBManager.getInstance().preparaStatement(q);
		try {
			p.setString(1, id);
					
			ResultSet res = p.executeQuery();
			
			// TODO: controllo sessione non esistente
			
			// prendi i risultati
			while (res.next())
				result = getSessioneFromResult(res);
		} catch (SQLException e) {
			throw new DatabaseException("Problemi con la base dati, riprovare!");
		}
			
		return result;
	}
	
	public List<SessioneDiVoto> getAll(Elettore e) {
		int id = DAOFactory.getFactory().getUtenteDAOInstance().getId(e);
		String q = "select * from Sessione as S where status = 's' and not exists (select * from Votato as V where V.id_sessione = S.id and V.id_utente = ?)";
		
		// prepara e gira la query
		List<SessioneDiVoto> result = new ArrayList<SessioneDiVoto>();
		PreparedStatement p = DBManager.getInstance().preparaStatement(q);
		try {	
			p.setInt(1, id);
			ResultSet res = p.executeQuery();
			
			// TODO: controllo 0 sessioni
				
			// prendi i risultati
			while (res.next())
				result.add(getSessioneFromResult(res));
		} catch (SQLException sqe) {
			throw new DatabaseException("Problemi con la base dati, riprovare!");
		}
			
		return result;
	}
	
	@Override
	public SessioneDiVoto getById(int id) {
		String q = "select * from Sessione where id = ?;";
		
		// prepara e gira la query
		SessioneDiVoto result = null;
		PreparedStatement p = DBManager.getInstance().preparaStatement(q);
		try {
			p.setInt(1, id);
					
			ResultSet res = p.executeQuery();
			
			// TODO: controllo sessione non esistente
			
			// prendi i risultati
			while (res.next())
				result = getSessioneFromResult(res);
		} catch (SQLException e) {
			throw new DatabaseException("Problemi con la base dati, riprovare!");
		}
			
		return result;
	}
	
	private SessioneDiVoto getSessioneFromResult(ResultSet res) {
		SessioneDiVoto result = null;
		String n, vinc, vot, status, t, q;
		
		try {
			n = res.getString(2);
			vinc = res.getString(3);
			vot = res.getString(4);
			status = res.getString(5);
			t = res.getString(6);
			q = res.getString(7);
			
			result = new SessioneDiVoto(n, vinc, vot , status, t, q);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public List<SessioneDiVoto> getAll() {
		String q = "select * from Sessione;";
		
		// prepara e gira la query
		List<SessioneDiVoto> result = new ArrayList<SessioneDiVoto>();
		PreparedStatement p = DBManager.getInstance().preparaStatement(q);
		try {	
			ResultSet res = p.executeQuery();
			
			// TODO: controllo 0 sessioni
				
			// prendi i risultati
			while (res.next())
				result.add(getSessioneFromResult(res));
		} catch (SQLException e) {
			throw new DatabaseException("Problemi con la base dati, riprovare!");
		}
			
		return result;
	}

	// Additional
	
	@Override
	public void start(SessioneDiVoto s) {
		String q = "update Sessione set status = 's' where nome = ?;";
		
		// prepara e gira la query
		PreparedStatement p = DBManager.getInstance().preparaStatement(q);
		try {	
			p.setString(1, s.getNome());
			p.execute();
		} catch (SQLException e) {
			throw new DatabaseException("Problemi con la base dati, riprovare!");
		}
		notifyObservers();
	}

	@Override
	public void stop(SessioneDiVoto s) {
		String q = "update Sessione set status = 'f' where nome = ?;";
		
		// prepara e gira la query
		PreparedStatement p = DBManager.getInstance().preparaStatement(q);
		try {	
			p.setString(1, s.getNome());
			p.execute();
		} catch (SQLException e) {
			throw new DatabaseException("Problemi con la base dati, riprovare!");
		}
		notifyObservers();
	}
	
	@Override
	public void save(SessioneDiVoto t) {
		// configurazione sessione
		String q = "insert into Sessione(nome, vincita, voto, status, candPart) values (?, ?, ?, ?, ?)";
		
		// prepara e gira la query
		PreparedStatement p = DBManager.getInstance().preparaStatement(q);
		try {	
			p.setString(1, t.getNome()); // nome
			p.setString(2, t.getStrategiaVincita()); // vincita
			p.setString(3, t.getStrategiaVoto()); //voto
			p.setString(4, t.getStatus());
			p.setString(5, t.getOrdinaleCategoricoType());
			p.execute();
		} catch (SQLException e) {
			throw new DatabaseException("Problemi con la base dati, riprovare!");
		}
			
		notifyObservers();
	}
	
	@Override
	public int getId(SessioneDiVoto s) {
		String q = "select id from Sessione where nome = ?;";
		
		// prepara e gira la query
		int result = 0;
		PreparedStatement p = DBManager.getInstance().preparaStatement(q);
		try {
			p.setString(1, s.getNome());
			ResultSet res = p.executeQuery();
				
			// prendi i risultati
			while (res.next())
				result = res.getInt(1);
		} catch (SQLException e) {
			throw new DatabaseException("Problemi con la base dati, riprovare!");
		}
			
		return result;
	}
	
	// TODO: move this
		@Override
		public List<Voto> getVoti(SessioneDiVoto s) {
			String q = "select ";
				
			//prepara e gira la query
			List<Voto> result = null;
			PreparedStatement p = DBManager.getInstance().preparaStatement(q);
			try {
				//p.setString(1, id);
						
				ResultSet res = p.executeQuery();
			} catch (SQLException e) {
				throw new DatabaseException("Problemi con la base dati, riprovare!");
			}
				
				// Count voti per candidato
				//SELECT
				// S.nome,
				// C.nome,
				// C.cognome,
				// count(C.id) AS NumeroVoti
			    //FROM
				// Sessione AS S
				// JOIN Voto AS V ON V.id_sessione = S.id
				// JOIN SceltePreferenza AS Sc ON Sc.id_voto = V.id
				// JOIN Partecipante AS P ON Sc.id_part = P.id
				// JOIN Candidato AS C ON P.id_cand = C.id
				//GROUP BY
				// S.id,
				// P.id
				
			return result;
		}

	@Override
	public void addObserver(IObserver o) {
		obs.add(o);
	}

	@Override
	public void removeObserver(IObserver o) {
		obs.remove(o);
	}

	@Override
	public void notifyObservers() {
		for (IObserver o : obs) {
			o.update();
		}
	}
	
	@Override
	public void update(SessioneDiVoto t, SessioneDiVoto u) {
		// non serve
	}

	@Override
	public void delete(SessioneDiVoto t) {
		// non serve
	}
}
