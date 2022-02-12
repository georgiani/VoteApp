package svse.controllers.gestoremain;

import svse.controllers.Controller;
import svse.models.utente.Gestore;

public class VisualizzaRisultatiController extends Controller {
	// questo controller deve andare a fare getVoti sul dao della
	// sessione su cui si e cliccato e poi passare la lista di voti attraverso la strategia
	// della sessione
	private Gestore gestore;
	@Override
	public void init(Object parameter) {
		gestore = (Gestore)parameter;
		
	}
}
