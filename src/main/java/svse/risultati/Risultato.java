package svse.risultati;

import svse.models.sessione.SessioneDiVoto;

public abstract class Risultato {
	protected SessioneDiVoto sessione;
	public abstract String getVincitore();
	public abstract String getRisultatiInteri();
}
