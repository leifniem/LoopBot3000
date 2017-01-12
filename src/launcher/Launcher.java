package launcher;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class Launcher extends Application {

	public static void main(String[] args) {
		endProgramIfAlreadyStarted();

		launch(args);
	}

	private static void endProgramIfAlreadyStarted() {
		try {
			java.net.ServerSocket ss = new java.net.ServerSocket(666);
		} catch (java.net.BindException ex) {
			System.out.println("Programm läuft bereits.");
			System.exit(1);
		} catch (java.io.IOException ex) {
			ex.printStackTrace();
			System.exit(1);
		}
	}

	@Override
	public void start(Stage stage) throws Exception {
		VBox root = FXMLLoader.load(getClass().getResource("/views/MainView.fxml"));

		Scene scene = new Scene(root, 1000, 500);
		// hier k�nnte der InputManager initialisiert werden!

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
