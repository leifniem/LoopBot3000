package launcher;

import controller.InputController;
import controller.MainViewController;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class Launcher extends Application {
	private MainViewController mainViewController;
	private InputController inputController;

	public static void main(String[] args) {
		endProgramIfAlreadyStarted();

		launch(args);
	}

	private static void endProgramIfAlreadyStarted() {
		try {
			java.net.ServerSocket ss = new java.net.ServerSocket(1359);
		} catch (java.net.BindException ex) {
			System.out.println("Programm l�uft bereits.");
			System.exit(1);
		} catch (java.io.IOException ex) {
			ex.printStackTrace();
			System.exit(1);
		}
	}

	@Override
	public void start(Stage stage) throws Exception {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/MainView.fxml"));
		GridPane root = loader.load();
		mainViewController = loader.getController();

		Scene scene = new Scene(root, 1280, 600);

		mainViewController.determineSize();
		
		addOnCloseRequestHandlerToPrimaryStage(stage);
		stage.setTitle("Loop-Bot 3000");
		stage.getIcons().add(new Image(Launcher.class.getResourceAsStream("/files/icon.png")));
		stage.setResizable(false);
		
		stage.setScene(scene);
		
		
		addFocusListenerToStage(stage, root);
		inputController = new InputController(scene, mainViewController, stage);
		
		stage.show();
	}

	private void addFocusListenerToStage(Stage stage, GridPane root) {
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
