package svse.dao.sessione;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import ps.IObserver;
import svse.dao.factory.DAOFactory;
import svse.data.DBManager;
import svse.exceptions.*;
import svse.models.risultati.*;
import svse.models.sessione.*;
import svse.models.utente.Elettore;

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
			
			if (!res.isBeforeFirst())
				throw new SessioneNotFoundException("Sessione non esiste!");
			
			// prendi i risultati
			while (res.next())
				result = getSessioneFromResult(res);
		} catch (SQLException e) {
			throw new DatabaseException("Problemi con la base dati, riprovare! Context: get");
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
			while (res.next())
				result.add(getSessioneFromResult(res));
		} catch (SQLException sqe) {
			throw new DatabaseException("Problemi con la base dati, riprovare! Context: getAll");
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
			
			if (!res.isBeforeFirst())
				throw new SessioneNotFoundException("Sessione non trovata");
			
			while (res.next())
				result = getSessioneFromResult(res);
		} catch (SQLException e) {
			throw new DatabaseException("Problemi con la base dati, riprovare! Context: getById");
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
			throw new DatabaseException("Problemi con la base dati, riprovare! Context: getSessioneFromResult");
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
				
			// prendi i risultati
			while (res.next())
				result.add(getSessioneFromResult(res));
		} catch (SQLException e) {
			throw new DatabaseException("Problemi con la base dati, riprovare! Context: getAll");
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
			throw new DatabaseException("Problemi con la base dati, riprovare! Context: start");
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
			throw new DatabaseException("Problemi con la base dati, riprovare! Context: stop");
		}
		notifyObservers();
	}
	
	@Override
	public void save(SessioneDiVoto t) {
		// configurazione sessione
		String q = "insert into Sessione(nome, vincita, voto, status, candPart, domandaReferendum) values (?, ?, ?, ?, ?, ?)";
		
		// prepara e gira la query
		PreparedStatement p = DBManager.getInstance().preparaStatement(q);
		try {	
			p.setString(1, t.getNome()); // nome
			p.setString(2, t.getStrategiaVincita()); // vincita
			p.setString(3, t.getStrategiaVoto()); //voto
			p.setString(4, t.getStatus());
			p.setString(5, t.getOrdinaleCategoricoType());
			p.setString(6, t.getDomandaReferendum());
			p.execute();
		} catch (SQLException e) {
			throw new DatabaseException("Problemi con la base dati, riprovare! Context: save Sessione");
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
			
			if (!res.isBeforeFirst())
				throw new SessioneNotFoundException("Sessione non esiste!");
			
			while (res.next())
				result = res.getInt(1);
		} catch (SQLException e) {
			throw new DatabaseException("Problemi con la base dati, riprovare! Context: getId");
		}
			
		return result;
	}
	
	@Override
	public Risultato getRisultato(SessioneDiVoto s) {
		int idSessione = getId(s);
		Risultato result = null;
		
		if (s.getStrategiaVoto().equals("c")) {
			if (s.getOrdinaleCategoricoType().equals("p")) {
				String q = "select L.partito, count(V.id) from Voto as V join Lista as L on V.id_lista = L.id where V.id_sessione = ? group by L.partito;";
				PreparedStatement p = DBManager.getInstance().preparaStatement(q);
				
				try {
					p.setInt(1, idSessione);
					ResultSet res = p.executeQuery();
					
					Map<Partito, Integer> m = new HashMap<Partito, Integer>();
					
					while (res.next())
						m.put(new Partito(res.getString(1)), res.getInt(2));
					
					result = new RisultatoPartito(m);
				} catch (SQLException e) {
					throw new DatabaseException("Problemi con la base dati, riprovare! Context: getRisultato");
				}
			} else {
				String q = "select C.nome, C.cognome, count(V.id) from Voto as V join Candidato as C on V.id_cand = C.id where V.id_sessione = ? group by (C.nome, C.cognome);";
				PreparedStatement p = DBManager.getInstance().preparaStatement(q);
				
				try {
					p.setInt(1, idSessione);
					ResultSet res = p.executeQuery();
					
					Map<Candidato, Integer> m = new HashMap<Candidato, Integer>();
					
					while (res.next())
						m.put(new Candidato(res.getString(2), res.getString(3)), res.getInt(3));
					
					result = new RisultatoCandidato(m);
				} catch (SQLException e) {
					throw new DatabaseException("Problemi con la base dati, riprovare! Context: getRisultato");
				}
			}
		} else if (s.getStrategiaVoto().equals("o")) {
			if (s.getOrdinaleCategoricoType().equals("p")) {
				String q = "select L.partito, count(V.id) from Voto as V join Lista as L on V.id_lista = L.id where (V.id_sessione = ? and V.ordine = 1) group by L.partito;";
				PreparedStatement p = DBManager.getInstance().preparaStatement(q);
				
				try {
					p.setInt(1, idSessione);
					ResultSet res = p.executeQuery();
					
					Map<Partito, Integer> m = new HashMap<Partito, Integer>();
					
					while (res.next())
						m.put(new Partito(res.getString(1)), res.getInt(2));
					
					result = new RisultatoPartito(m);
				} catch (SQLException e) {
					throw new DatabaseException("Problemi con la base dati, riprovare! Context: getRisultato");
				}
			} else {
				String q = "select C.nome, C.cognome, count(V.id) from Voto as V join Candidato as C on V.id_cand = C.id where (V.id_sessione = ? and V.ordine = 1) group by (C.nome, C.cognome);";
				PreparedStatement p = DBManager.getInstance().preparaStatement(q);
				
				try {
					p.setInt(1, idSessione);
					ResultSet res = p.executeQuery();
					
					Map<Candidato, Integer> m = new HashMap<Candidato, Integer>();
					
					while (res.next())
						m.put(new Candidato(res.getString(2), res.getString(3)), res.getInt(3));
					
					result = new RisultatoCandidato(m);
				} catch (SQLException e) {
					throw new DatabaseException("Problemi con la base dati, riprovare! Context: getRisultato");
				}
			}
		} else if (s.getStrategiaVoto().equals("p")) {
			String q = "select C.nome, C.cognome, count(V.id) from Voto as V join Candidato as C on V.id_cand = C.id where V.id_sessione = ? group by (C.nome, C.cognome);";
			PreparedStatement p = DBManager.getInstance().preparaStatement(q);
			
			try {
				p.setInt(1, idSessione);
				ResultSet res = p.executeQuery();
				
				Map<Candidato, Integer> m = new HashMap<Candidato, Integer>();
				
				while (res.next())
					m.put(new Candidato(res.getString(2), res.getString(3)), res.getInt(3));
				
				result = new RisultatoCandidato(m);
			} catch (SQLException e) {
				throw new DatabaseException("Problemi con la base dati, riprovare! Context: getRisultato");
			}
		} else {
			String fav = "select count(V.id) from Voto as V where V.id_sessione = ? and V.risposta_referendum = 1;";
			String nonFav = "select count(V.id) from Voto as V where V.id_sessione = ? and V.risposta_referendum = 0;";
			PreparedStatement favP = DBManager.getInstance().preparaStatement(fav);
			PreparedStatement nonFavP = DBManager.getInstance().preparaStatement(nonFav);
			
			try {
				favP.setInt(1, idSessione);
				nonFavP.setInt(1, idSessione);
				
				ResultSet f = favP.executeQuery();
				ResultSet n = nonFavP.executeQuery();
				
				int countFv = 0;
				int countNFv = 0;
				while (f.next())
					countFv = f.getInt(1);
				while (n.next())
					countNFv = n.getInt(1);
				
				result = new RisultatoReferendum(countFv, countNFv);
			} catch (SQLException e) {
				throw new DatabaseException("Problemi con la base dati, riprovare! Context: getRisultato");
			}
		}
		
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
