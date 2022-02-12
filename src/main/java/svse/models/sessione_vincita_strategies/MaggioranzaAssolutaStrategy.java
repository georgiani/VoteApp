package svse.models.sessione_vincita_strategies;

import java.util.List;

import svse.models.risultati.Risultato;
import svse.models.sessione.SessioneDiVoto;
import svse.models.sessione.Voto;

public class MaggioranzaAssolutaStrategy extends VincitaStrategy {

	@Override
	public Risultato getVincitore(List<Voto> v) {
		return null;
	}

}
