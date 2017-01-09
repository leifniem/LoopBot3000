package controller;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import models.LoopManager;
import models.LoopProject;
import models.LoopProjectExporter;
import models.TimeSignature;

public class PlayBarController {
	private final static String RECT_BUTTON_ACTIVE_STYLE_CLASS = "rect-on";
	
	@FXML
	private Button playButton;
	@FXML
	private HBox noteStatusContainer;

	private LoopManager loopManager;

	public void generateNoteStatusButtonsForTimeSignature(int numberOfBeats, int noteValue) {
		noteStatusContainer.getChildren().clear();
		TimeSignature timeSignature = loopManager.getLoopProject().getTimeSignature();
		
		for (int currentBeat = 0; currentBeat < numberOfBeats; currentBeat++) {
			for (int currentNoteInBeat = 0; currentNoteInBeat < noteValue; currentNoteInBeat++) {
				int currentNote = currentBeat * timeSignature.getNoteValue() + currentNoteInBeat;
				Button button = createRectButton("" + currentNote);
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
		setPlayButtonAction(loopManager);
		addCurrentNoteDisplayListener(loopManager);
	}

	private void setPlayButtonAction(LoopManager loopManager) {
		playButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				if (loopManager.isPlaying()) {
					loopManager.stop();
				} else {
					loopManager.play();
				}
			}
		});
	}

	private void addCurrentNoteDisplayListener(LoopManager loopManager) {
		loopManager.currentNoteProperty().addListener(new ChangeListener<Number>(){
			@Override
			public void changed(ObservableValue<? extends Number> observable, Number previousNote, Number nextNote) {
				for(Node node : noteStatusContainer.getChildren()){
					if(node instanceof Button){
						if(Integer.parseInt(node.getId()) == (int)previousNote){
							node.getStyleClass().remove(RECT_BUTTON_ACTIVE_STYLE_CLASS);
						}
						if(loopManager.isPlaying() && Integer.parseInt(node.getId()) == (int)nextNote){
							node.getStyleClass().add(RECT_BUTTON_ACTIVE_STYLE_CLASS);
						}
					}
				}
			}
		});
	}
}
