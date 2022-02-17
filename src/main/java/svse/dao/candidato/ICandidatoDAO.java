package svse.dao.candidato;

import java.util.List;

import svse.dao.IDAO;
import svse.models.sessione.Candidato;
import svse.models.sessione.Lista;
import svse.models.sessione.Partito;

public interface ICandidatoDAO extends IDAO<Candidato> {
	public List<Candidato> getCandidati(Partito partito);
	public void save(Candidato c, Lista l);
	public int getId(Candidato c); 
	public int getIdLista(Candidato c);
}
