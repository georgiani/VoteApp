package tests;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import svse.dao.factory.DAOFactory;
import svse.data.DBManager;
import svse.models.sessione.SessioneDiVoto;

class DBSessione {
	
	@BeforeAll
	public static void setup() {
		DBManager.getInstance().ensureCreated();	
	}
	
	@Test
	void testLogin() {
		assertNotNull(DAOFactory.getFactory().getUtenteDAOInstance().login("TEST", "1234"));
	}
	
	@Test
	void testStartSessione() {
		// dato fake
		SessioneDiVoto s = new SessioneDiVoto("Test", "m", "c", "n", "c", null);
		DAOFactory.getFactory().getSessioneDAOInstance().save(s);
		
		// start sessione
		DAOFactory.getFactory().getSessioneDAOInstance().start(s);
		
		// recovery
		s = DAOFactory.getFactory().getSessioneDAOInstance().get(s.getNome());
		
		// delete dato fake
		DAOFactory.getFactory().getSessioneDAOInstance().delete(s);
		
		assertTrue(s.getStatus().equals("s"));
	}
	
	@Test
	void testStop() {
		// dato fake
		SessioneDiVoto s = new SessioneDiVoto("Test", "m", "c", "s", "c", null);
		DAOFactory.getFactory().getSessioneDAOInstance().save(s);
		
		// start sessione
		DAOFactory.getFactory().getSessioneDAOInstance().stop(s);
		
		// recovery
		s = DAOFactory.getFactory().getSessioneDAOInstance().get(s.getNome());
		
		// delete dato fake
		DAOFactory.getFactory().getSessioneDAOInstance().delete(s);
		
		assertTrue(s.getStatus().equals("f"));
	}

}
