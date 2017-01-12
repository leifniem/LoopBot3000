package launcher;

import controller.MainViewController;
import controller.PlayBarController;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import models.LoopProjectExporter;

public class Launcher extends Application {
	private MainViewController mainViewController;

	public static void main(String[] args) {
		// endProgramIfAlreadyStarted();

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
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/MainView.fxml"));
		VBox root = loader.load();
		mainViewController = loader.getController();

		Scene scene = new Scene(root, 1000, 500);
		// hier k�nnte der InputManager initialisiert werden!

		addOnCloseRequestHandlerToPrimaryStage(stage);
		stage.setTitle("Loop-Bot 3000");
		stage.setScene(scene);
		stage.focusedProperty().addListener(new ChangeListener<Boolean>(){

			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				if(!newValue){
					Platform.runLater(new Runnable(){

						@Override
						public void run() {
							root.getStyleClass().add("dimmed");
						}
						
					});
				}else{
					Platform.runLater(new Runnable(){

						@Override
						public void run() {
							root.getStyleClass().remove("dimmed");
						}
						
					});
				}
			}
			
		});
		scene.setOnKeyPressed(new EventHandler<KeyEvent>(){

			@Override
			public void handle(KeyEvent event) {
				if(event.isMetaDown()){
					if (event.getCode() == KeyCode.S){
						LoopProjectExporter.askUserToExportLoopProject(mainViewController.getLoopProject());
					}else if(event.getCode() == KeyCode.O){
						mainViewController.importLoopProject();
					}
				}
			}
			
		});
		
		scene.addEventFilter(KeyEvent.KEY_PRESSED, event->{
            if (event.getCode() == KeyCode.SPACE) {
            	PlayBarController pbc = mainViewController.getPlayBarController();
				pbc.switchPlaying();
				event.consume();
            }
        });
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
