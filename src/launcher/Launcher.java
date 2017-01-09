package launcher;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class Launcher extends Application {
	
	public static void main(String[] args){
        launch(args);
    }

	@Override
	public void start(Stage stage) throws Exception {
		VBox root = FXMLLoader.load(getClass().getResource("/views/MainView.fxml"));	

		Scene scene = new Scene(root, 1000, 500);
		addOnCloseRequestHandlerToPrimaryStage(stage);
		stage.setTitle("Loop-Bot 3000");
		stage.setScene(scene);
		stage.show();	
	}
	
	private void addOnCloseRequestHandlerToPrimaryStage(Stage primaryStage) {
		primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
			@Override
			public void handle(WindowEvent e) {
				System.exit(0);
			}
		});
	}
}
