package controller;

import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import models.LoopProjectExporter;

public class InputController {
	public InputController(Scene scene, MainViewController mainViewController, Stage parentStage) {
		scene.setOnKeyPressed(new EventHandler<KeyEvent>(){
			@Override
			public void handle(KeyEvent event) {
				if(event.isMetaDown() || event.isControlDown()){
					if (event.getCode() == KeyCode.S){
						LoopProjectExporter.askUserToExportLoopProject(mainViewController.getLoopProject(), parentStage);
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
