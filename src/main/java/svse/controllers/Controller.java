package svse.controllers;

import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import svse.App;

public abstract class Controller  {
	// inizializza la schermata
	public abstract void init(Object parameter);
	
	// metodo per cambiare la schermata
	/***
	 * Cambia la schermata corrente.
	 * @param view La vista a cui si vuole passare.
	 * @param parameter Parametri da passare alla schermata {@code view}. 
	 */
	public void changeView(String view, Object parameters) {
		try {
			FXMLLoader loader = new FXMLLoader(App.class.getResource(view));
			Parent root = loader.load();
			Controller c = loader.getController();
			c.init(parameters);
			App.getAppScene().setRoot(root);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/***
	 * Cambia la schermata corrente senza passare parametri.
	 * @param view la vista a cui si vuole passare.
	 */
	public void changeView(String view) {
		changeView(view, null);
	}
}
