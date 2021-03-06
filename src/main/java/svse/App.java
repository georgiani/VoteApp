package svse;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import svse.controllers.common.LoginController;
import svse.data.DBManager;

public class App extends Application {
	private static Scene primaryScene;
	private static Stage stg;
	private static LoginController loginController;
	
	public static void main(String args[]) {
		launch();
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		DBManager.getInstance().open();
		
		FXMLLoader loader = new FXMLLoader(App.class.getResource("views/Login.fxml"));
		Parent root = loader.load();
		Scene login = new Scene(root);
		
		loginController = loader.getController();
		loginController.accendiTotem();
		primaryScene = login;
		stg = primaryStage;
		
		primaryStage.setScene(login);
		primaryStage.sizeToScene();
		primaryStage.show();
	}
	
	@Override
	public void stop() {
		loginController.chiudiTotem();
		DBManager.getInstance().close();
		System.exit(0);
	}
	
	public static void resize() {
		stg.sizeToScene();
		stg.show();
	}
	
	public static Scene getAppScene() {
		return primaryScene;
	}
}
