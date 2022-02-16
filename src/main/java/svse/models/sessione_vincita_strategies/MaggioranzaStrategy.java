package svse.models.sessione_vincita_strategies;

import java.util.List;

import svse.models.risultati.Risultato;
import svse.models.sessione.SessioneDiVoto;
import svse.models.voto.Voto;

public class MaggioranzaStrategy extends VincitaStrategy {

	@Override
	public Risultato getVincitore(List<Voto> v) {
		return null;
	}

}
