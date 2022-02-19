package svse.data;
import java.sql.*;

public class DBManager {
	private static DBManager instance;
	private static String url = "jdbc:mysql://localhost/sistemavoto";
	private static String username = "root";
	private static String password = "rootroot";
	private Connection con;
	
	private DBManager() {}
	
	public static DBManager getInstance() {
		if (instance == null) {
			instance = new DBManager();
		}
		return instance;
	}
	
	/***
	 * Apre la connessione con la base dati.
	 * @return true se l'apertura e avvenuta con successo, false altrimenti.
	 */
	public boolean open() {
		try {
			con = DriverManager.getConnection(url,username,password);
			return true;
		} catch(SQLException e) {
			return false;
		}
	}
	
	/***
	 * Chiude la connessione con la base dati.
	 * @return true se la chiusura e avvenuta con successo, false altrimenti.
	 */
	public boolean close() {
		try {
			con.close();
			return true;
		} catch (SQLException e) {
			return false;
		}
	}

	// metodo utile
	public PreparedStatement preparaStatement(String q) {
		PreparedStatement result = null;
		try {
			result = con.prepareStatement(q);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return result;
	}
}
