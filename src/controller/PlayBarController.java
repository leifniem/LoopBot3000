package controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import models.LoopManager;
import models.LoopProject;
import models.TimeSignature;

public class PlayBarController {
	@FXML
	private Button loadButton;
	@FXML
	private Button saveButton;
	@FXML
	private Button playButton;
	@FXML
	private HBox noteStatusContainer;

	private LoopManager loopManager;

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

	public void init(LoopManager loopManager) {
		this.loopManager = loopManager;
		LoopProject loopProject = loopManager.getLoopProject();
		TimeSignature timeSignature = loopProject.getTimeSignature();
		generateNoteStatusButtonsForTimeSignature(timeSignature.getNumberOfBeats(), timeSignature.getNoteValue());

		playButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				if (loopManager.isPlayling()) {
					loopManager.stop();
				} else {
					loopManager.play();
				}
			}
		});
	}
}
