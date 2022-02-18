package svse.dao.utente;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.sql.*;
import java.util.List;
import logger.ProjectLogger;
import java.util.ArrayList;
import svse.data.DBManager;
import svse.exceptions.*;
import svse.models.utente.*;

// un prodotto della famiglia UtenteDAO
public class UtenteJDBCDAO implements IUtenteDAO {

	public UtenteJDBCDAO() {}
	
	public Utente login(String cf, String password) {		
		String hashedPassword = sha1Hashing(password);
	
		// query
		String q = "select * from Utente where cf = ? and password = ?;";
		
		// prepara e gira la query
		Utente result = null;
		PreparedStatement p = DBManager.getInstance().preparaStatement(q);
		try {
			p.setString(1, cf);
			p.setString(2, hashedPassword);
			
			ResultSet res = p.executeQuery();
			
			// nessun risultato
			if (!res.isBeforeFirst()) {
				throw new UtenteNotFoundException("Utente con le credenziali inserite non trovato!");
			}
			
			// prendi i risultati
			res.next();
			result = getUtente(res);
		} catch (SQLException e) {
			throw new DatabaseException("Problemi con la base dati, riprovare! Context: login");
		}
		
		return result;
	}

	public boolean registraElettore(Utente t, String pass) {
		boolean result;
		String q = "insert into Utente(nome, cognome, cf, ruolo, password) values(?, ?, ?, ?, ?)";
		PreparedStatement p = DBManager.getInstance().preparaStatement(q);
		try {
			p.setString(1, t.getNome());
			p.setString(2, t.getCognome());
			p.setString(3, t.getCF());
			p.setString(4, t.isGestore() ? "g" : "e");
			p.setString(5, sha1Hashing(pass));
			
			result = p.execute();
		} catch (SQLException e) {
			throw new DatabaseException("Problemi con la base dati, riprovare! Context: registraElettore");
		}
		
		return result;
	}

	public Utente get(String cf) {
		String q = "select * from Utente where cf = ?;";
		
		// prepara e gira la query
		Utente result = null;
		PreparedStatement p = DBManager.getInstance().preparaStatement(q);
		try {
			p.setString(1, cf);
			
			ResultSet res = p.executeQuery();
			
			// nessun risultato
			if (!res.isBeforeFirst()) {
				throw new UtenteNotFoundException("Utente con questo codice fiscale non trovato!");
			}
			
			// prendi i risultati
			res.next();
			result = getUtente(res);
		} catch (SQLException e) {
			throw new DatabaseException("Problemi con la base dati, riprovare! Context: get");
		}
		
		return result;
	}
	
	public List<Utente> getAll() {
		List<Utente> results = new ArrayList<Utente>();
		String q = "select * from Utente;";
		PreparedStatement preparedStatement = DBManager.getInstance().preparaStatement(q);
		try {
			ResultSet res = preparedStatement.executeQuery();
			// prendi i risultati
			while(res.next())
				results.add(getUtente(res));
		} catch (SQLException e) {
			throw new DatabaseException("Problemi con la base dati, riprovare! Context: getAll");
		}
		
		return results;
	}
	
	public void save(Utente t) {
		// usiamo registraElettore per facilita
		return;
	}

	public void update(Utente t, Utente u) {
		// non serve in questo caso
		return;
	}
	
	private String sha1Hashing(String password) {
		// applica un hash alla password per compararla con quella nel db
		String result = "";
		try {
			MessageDigest digest = MessageDigest.getInstance("SHA-1");
		    digest.reset();
		    digest.update(password.getBytes("utf8"));
		    result = String.format("%040x", new BigInteger(1, digest.digest()));
		} catch (Exception e) {
			ProjectLogger.getInstance().log("e", e.getMessage());
		}
		
		return result;
	}

	private Utente getUtente(ResultSet res) {
		Utente result = null;
		String n, c, cf, r;
		
		try {
			n = res.getString(2);
			c = res.getString(3);
			cf = res.getString(4);
			r = res.getString(5);
			// vedi il tipo d'utente
			if (r.equals("e"))
				result = new Elettore(n, c, cf);
			else
				result = new Gestore(n, c, cf);
		} catch (SQLException e) {
			throw new DatabaseException("Problemi con la base dati, riprovare! Context: getUtente");
		}
		
		return result;
	}
	
	@Override
	public int getId(Utente u) {
		String q = "select id from Utente where cf = ?;";
		
		// prepara e gira la query
		int result = 0;
		PreparedStatement p = DBManager.getInstance().preparaStatement(q);
		try {
			p.setString(1, u.getCF());
			
			ResultSet res = p.executeQuery();
			
			if (!res.isBeforeFirst())
				throw new UtenteNotFoundException("Utente non esiste!");
			
			// prendi i risultati
			res.next();
			result = res.getInt(1);
		} catch (SQLException e) {
			throw new DatabaseException("Problemi con la base dati, riprovare! Context: getId");
		}
		
		return result;
	}
	
	public void delete(Utente t) {
		// non usato
	}
}
