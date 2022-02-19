package svse.models.sessione;

public class SessioneDiVoto {
	//@ invariant (strategiaVoto.equals("o") || strategiaVoto.equals("c")) -> tipo != null;
	//@ invariant strategiaVoto.equals("r") -> domandaReferendum != null;
	
	private String /*@ non_null; spec_public @*/ nomeSessione;
	private String /*@ non_null; spec_public @*/ strategiaVincita;
	private String /*@ non_null; spec_public @*/ strategiaVoto;
	private String /*@ non_null @*/ stato;
	private String tipo = null;
	private String /*@ spec_public @*/ domandaReferendum;
	
	public SessioneDiVoto(String n, String vincita, String voto, String status, String t, String q) {
		nomeSessione = n;
		strategiaVincita = vincita;
		strategiaVoto = voto;
		stato = status;
		tipo = t;
		domandaReferendum = q;
	}
	
	public String getNome() {
		return nomeSessione;
	}
	
	public boolean isStarted() {
		return stato.equals("s");
	}
	
	public boolean isNew() {
		return stato.equals("n");
	}
	
	public boolean isFinished() {
		return stato.equals("f");
	}
	
	public String getStatus() {
		return stato;
	}
	
	public String getPrettyStatus() {
		if (stato.equals("n"))
			return "Non iniziata";
		
		if (stato.equals("s"))
			return "In corso";
		
		return "Finita";
	}
	
	public String getStrategiaVoto() {
		return strategiaVoto;
	}
	
	public String getStrategiaVincita() {
		return strategiaVincita;
	}
	
	public String getOrdinaleCategoricoType() {
		return (strategiaVoto.equals("o") || strategiaVoto.equals("c")) ? tipo : null;
	}
	
	public String getDomandaReferendum() {
		return this.domandaReferendum;
	}
	
	
	public void start() {
		this.stato = "s";
	}
	
	public void stop() {
		this.stato = "f";
	}
}
