package controller;

import javafx.event.EventHandler;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import models.LoopPlayer;
import models.LoopProjectExporter;

public class InputController {
	public InputController() {
		/*controller.getScene().setOnKeyTyped(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent event) {
				if (event.isControlDown()) {
					if (event.getCode() == KeyCode.S) {
						LoopProjectExporter.askUserToExportLoopProject(controller.getLoopProject());
					} else if(event.getCode() == KeyCode.O){
						controller.importLoopProject();
					}
				} else {
					if(event.getCode() == KeyCode.SPACE){
						playBarController.getCurrentLoopPlayer().play();
					}
				}
			}
		});
		*/
	}
}
