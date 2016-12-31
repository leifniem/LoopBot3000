package controller;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;

public class PlayBarController {
	@FXML
	private Button loadButton;
	@FXML 
	private Button saveButton;
	@FXML
	private Button playButton;
	@FXML
	private HBox noteStatusContainer;
	
	public PlayBarController() {
		
	}
	
	public void generateNoteStatusButtonsForTimeSignature(int numberOfBeats, int noteValue) {
		for (int currentBeat = 0; currentBeat < numberOfBeats; currentBeat++) {
			for (int currentNote = 0; currentNote < noteValue; currentNote++) {
				Button button = createRectButton("" + currentBeat * currentNote);
				noteStatusContainer.getChildren().add(button);
			}

			HBox spacer = new HBox();
			spacer.getStyleClass().add("spacer");
			noteStatusContainer.getChildren().add(spacer);
		}
		

		HBox spacer = new HBox();
		spacer.getStyleClass().add("pb-spacer");
		noteStatusContainer.getChildren().add(spacer);
	}
	
	private Button createRectButton(String id) {
		Button button = new Button();
		button.setId(id);
		button.getStyleClass().add("rect-button");
		return button;
	}
}
