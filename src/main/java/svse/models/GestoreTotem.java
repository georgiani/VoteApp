package svse.models;

import java.net.DatagramPacket;
import java.net.DatagramSocket;

import svse.controllers.common.LoginController;

public class GestoreTotem extends Thread {
	private LoginController c;
	private DatagramSocket ds;
	
	public GestoreTotem(LoginController lc, DatagramSocket dtsc) {
		c = lc;
		ds = dtsc;
	}
	
	public void run() {
		int dim_buffer = 1000;
		byte[] buffer = new byte[dim_buffer];
		
		DatagramPacket dp = new DatagramPacket(buffer, dim_buffer);
		try {
			System.out.println("Prima di receive");
			ds.receive(dp);
			String risp = new String(buffer, 0, buffer.length);
			int id = Integer.parseInt(risp.trim());
			c.passaInVoto(id);
		} catch (Exception e) {
			System.out.println("");
		}
		
	}
}
