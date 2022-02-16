package svse.dao.totem;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import svse.data.DBManager;
import svse.exceptions.DatabaseException;
import svse.models.totem.Totem;

public class TotemJDBCDAO implements ITotemDAO {
	@Override
	public Totem get(String id) {
		return null;
	}

	@Override
	public List<Totem> getAll() {
		String q = "select * from Totem;";
		
		// prepara e gira la query
		List<Totem> result = new ArrayList<Totem>();
		PreparedStatement p = DBManager.getInstance().preparaStatement(q);
		try {
			ResultSet res = p.executeQuery();
			
			// TODO: controllo 0 sessioni
				
			// prendi i risultati
			while (res.next())
				result.add(getTotemFromResult(res));
		} catch (SQLException e) {
			throw new DatabaseException("Problemi con la base dati, riprovare!");
		}
			
		return result;
	}
	
	private Totem getTotemFromResult(ResultSet res) {
		Totem result = null;
		String ip;
		int porta;
		
		try {
			ip = res.getString(2);
			porta = res.getInt(3);
			
			result = new Totem(ip, porta);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return result;
	}

	@Override
	public void save(Totem t) {
		String q = "insert into Totem(ip, porta) values(?, ?);";
		
		PreparedStatement p = DBManager.getInstance().preparaStatement(q);
		try {
			p.setString(1, t.getIndirizzo());
			p.setInt(2, t.getPort());
			
			p.execute();
		} catch (SQLException e) {
			throw new DatabaseException("Problemi con la base dati, riprovare!");
		}
	}

	@Override
	public void update(Totem t, Totem u) {
		// non usato
	}
	

	@Override
	public void delete(Totem t) {
		String q = "delete from Totem where (ip = ? and porta = ?);";
		PreparedStatement p = DBManager.getInstance().preparaStatement(q);
		try {
			p.setString(1, t.getIndirizzo());
			p.setInt(2, t.getPort());
			
			p.executeUpdate();
		} catch (SQLException e) {
			throw new DatabaseException("Problemi con la base dati, riprovare!");
		}
	}
}
