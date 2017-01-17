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
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import models.LoopProjectExporter;

public class Launcher extends Application {
	private MainViewController mainViewController;

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
		addKeyEventsToScene(scene, stage);

		stage.show();
	}

	private void addFocusListenerToStage(Stage stage, GridPane root) {
		stage.focusedProperty().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				Platform.runLater(new Runnable() {
					@Override
					public void run() {
						if (!newValue) {
							root.getStyleClass().add("dimmed");
						} else {
							root.getStyleClass().remove("dimmed");
						}
					}
				});
			}
		});
	}

	private void addOnCloseRequestHandlerToPrimaryStage(Stage stage) {
		stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
			@Override
			public void handle(WindowEvent e) {
				System.exit(0);
			}
		});
	}
	
	private void addKeyEventsToScene(Scene scene, Stage stage){
		scene.setOnKeyPressed(new EventHandler<KeyEvent>(){
			@Override
			public void handle(KeyEvent event) {
				if(event.isMetaDown() || event.isControlDown()){
					if (event.getCode() == KeyCode.S){
						LoopProjectExporter.askUserToExportLoopProject(mainViewController.getLoopProject(), stage);
					}else if(event.getCode() == KeyCode.O){
						mainViewController.importLoopProject();
					} else if(event.getCode() == KeyCode.N){
						mainViewController.openCreateProjectDialog();
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
	}
}
