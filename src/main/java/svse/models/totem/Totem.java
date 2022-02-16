package svse.models.totem;

import java.net.InetSocketAddress;

public class Totem {
	private String indirizzo;
	private InetSocketAddress isa;
	
	public Totem(String indirizzo, int porta) {
		try {
			this.indirizzo = indirizzo;
			this.isa = new InetSocketAddress(this.indirizzo, porta);
		} catch (Exception e) {
			// TODO: log
			e.printStackTrace();
		}
	}
	
	public String getIndirizzo() {
		return this.indirizzo;
	}
	
	public InetSocketAddress getSocketAddress() {
		return this.isa;
	}
	
	public int getPort() {
		return this.isa.getPort();
	}
}
