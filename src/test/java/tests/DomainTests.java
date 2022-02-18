package tests;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import svse.models.sessione.SessioneDiVoto;

public class DomainTests {

	@Test
	void testStartSessione() {
		SessioneDiVoto s = new SessioneDiVoto("Test", "m", "c", "n", "c", null);
		s.start();
		assertTrue(s.getStatus().equals("s"));
	}
	
	@Test
	void testStopSessione() {
		SessioneDiVoto s = new SessioneDiVoto("Test", "m", "c", "s", "c", null);
		s.stop();
		assertTrue(s.getStatus().equals("f"));
	}
}
