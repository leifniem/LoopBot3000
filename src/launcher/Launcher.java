package launcher;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Launcher extends Application {
	
	public static void main(String[] args){
        launch(args);
    }

	@Override
	public void start(Stage stage) throws Exception {
		Parent root = FXMLLoader.load(getClass().getResource("/views/MainView.fxml"));	
		
		//Alternative:
		//FXMLLoader loader = new FXMLLoader();
		//Parent root2 = loader.load(new FileInputStream(filepath + "/HelloWorldView.fxml"));

		Scene scene = new Scene(root, 1000, 500);
		stage.setTitle("Loop-Bot 3000");
		stage.setScene(scene);
		stage.show();
	}
}
