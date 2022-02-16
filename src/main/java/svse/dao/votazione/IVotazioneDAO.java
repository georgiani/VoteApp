package svse.dao.votazione;

import svse.dao.IDAO;
import svse.models.sessione.SessioneDiVoto;
import svse.models.utente.Elettore;
import svse.models.voto.Voto;

public interface IVotazioneDAO extends IDAO<Voto> {
	public void confermaVotazione(Elettore e, SessioneDiVoto s);
}
