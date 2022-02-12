package svse;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import svse.data.DBManager;

public class App extends Application {
	private static Scene primaryScene;
	
	public static void main(String args[]) {
		launch();
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		// si controlla che la db sia aperta e abbia le tabelle necessarie
		DBManager.getInstance().ensureCreated();
		
		Parent root = FXMLLoader.load(App.class.getResource("views/Login.fxml"));
		Scene login = new Scene(root);
		primaryScene = login;
		primaryStage.setScene(login);
		primaryStage.show();
	}
	
	public static Scene getAppScene() {
		return primaryScene;
	}
}
