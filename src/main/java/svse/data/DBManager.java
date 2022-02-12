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
	
	/**
	 * Si apre la basi dati e si creano le tabelle neccessarie nel
	 * caso non esistano.
	 * @return true se tutto e andato a buon fine, false altrimenti.
	 */
	public boolean ensureCreated() {
		if (!open()) {
			return false;
		}
		
		try {
			// si verifica se tutte le tabelle neccessarie per l'app esistono
			// se non esistono, si creano ora
			String createUserTable = "create table if not exists Utente(" + 
									 "id INTEGER NOT NULL PRIMARY KEY AUTO_INCREMENT," + 
									 "nome VARCHAR(50) NOT NULL," + 
									 "cognome VARCHAR(50) NOT NULL," + 
									 "cf VARCHAR(50) NOT NULL," + 
									 "ruolo ENUM('e', 'g') DEFAULT 'e'," +
									 "password VARCHAR(50) NOT NULL," +
									 "comune VARCHAR(50) NOT NULL);";
			
			// creazione sessione
			// create table Sessione 
			// (id int not null auto_increment primary key, 
			// nome varchar(100), 
			// vincita varchar(2), 
			// voto varchar(2), 
			// tipo varchar(2), 
			// status char(1));
			
			// creazione Partito
			// create table Partito 
			// (id int not null auto_increment primary key, 
			// nome varchar(100));
			
			// creazione lista
			// create table Lista 
			// (id int not null auto_increment primary key, 
			// id_sessione int, 
			// id_partito int, 
			// foreign key (id_sessione) references Sessione(id) on delete cascade, 
			// foreign key (id_partito) references Partito(id) on delete cascade);
			
			// creazione candidato
			// create table Candidato 
			// (id int not null auto_increment primary key, 
			// nome varchar(100), 
			// cognome varchar(100), 
			// id_lista int, 
			// id_partito int, 
			// foreign key (id_lista) references Lista(id) on delete set null, 
			// foreign key (id_partito) references Partito(id) on delete cascade);
			
			// creazione info
			// create table Info 
			// (id int not null auto_increment primary key, 
			// risposta varchar(20), 
			// id_cand int, 
			// id_lista int, 
			// foreign key (id_cand) references Candidato(id) on delete set null, 
			// foreign key (id_lista) references Lista(id) on delete set null);
			
			// creazione scelta
			// create table SceltaPreferenza 
			// (id int not null auto_increment primary key, 
			// id_info int, 
			// id_cand int, 
			// foreign key (id_info) references Info(id) on delete cascade, 
			// foreign key (id_cand) references Candidato(id) on delete cascade);
			
			// creazione voto
			// create table Voto (id int not null auto_increment primary key, 
			// id_sessione int, 
			// id_info int, 
			// foreign key (id_sessione) references Sessione(id) on delete cascade, 
			// foreign key (id_info) references Info(id) on delete cascade);
			PreparedStatement p = con.prepareStatement(createUserTable);
			ResultSet rs = p.executeQuery();
			System.out.println(rs.toString());
			return true;
		} catch (SQLException e) {
			return false;
		}
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
	
	/***
	 * Restituisce la connessione che puo essere usata dalle DAO per fare query.
	 * @return la connessione.
	 */
	public Connection getCon() {
		return this.con;
	}
	
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
