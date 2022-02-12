package svse.controllers.gestoremain;

import svse.controllers.Controller;
import svse.models.utente.Gestore;

public class ConfigurazioneSessioneController extends Controller {
	private Gestore gestore;
	@Override
	public void init(Object parameter) {
		gestore = (Gestore)parameter;
		
	}

}
