package controller;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import models.LoopPlayer;
import models.LoopProject;
import models.TimeSignature;

public class PlayBarController {
	private final static String RECT_BUTTON_ACTIVE_STYLE_CLASS = "rect-on";
	private final static String PLAY_BUTTON_ACTIVE_STYLE_CLASS = "play-on";

	@FXML
	private Button playButton;
	@FXML
	private HBox noteStatusContainer;

	protected LoopPlayer loopPlayer;
	private TimeSignature timeSignature;

	public void generateNoteStatusButtonsForTimeSignature(int numberOfBeats, int noteValue) {
		noteStatusContainer.getChildren().clear();

		for (int currentBeat = 0; currentBeat < timeSignature.getNumberOfBeats(); currentBeat++) {
			Button button = null;
			for (int currentNoteInBeat = 0; currentNoteInBeat < timeSignature.getNoteValue(); currentNoteInBeat++) {
				int currentNote = currentBeat * timeSignature.getNoteValue() + currentNoteInBeat;
				button = createRectButton("" + currentNote);
				noteStatusContainer.getChildren().add(button);
			}

			button.getStyleClass().add("space");
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

	public void init(LoopProject loopProject) {
		loopPlayer = new LoopPlayer(loopProject);
		timeSignature = loopProject.getTimeSignature();
		generateNoteStatusButtonsForTimeSignature(timeSignature.getNumberOfBeats(), timeSignature.getNoteValue());
		setPlayButtonAction(loopPlayer);
		addCurrentNoteDisplayListener(loopPlayer);
	}

	private void setPlayButtonAction(LoopPlayer loopPlayer) {
		playButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				switchPlaying();
			}
		});
	}

	private void addCurrentNoteDisplayListener(LoopPlayer loopManager) {
		loopManager.currentNoteProperty().addListener(new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> observable, Number previousNote, Number nextNote) {
				for (Node node : noteStatusContainer.getChildren()) {
					if (node instanceof Button) {
						boolean isCurrentButton = Integer.parseInt(node.getId()) == (int) previousNote;
						Platform.runLater(new Runnable() {
							@Override
							public void run() {
								StyleHelper.applyStyleClass(isCurrentButton, node, RECT_BUTTON_ACTIVE_STYLE_CLASS);
							}
						});
					}
				}
			}
		});
	}

	public void switchPlaying(){
		if (loopPlayer.isPlaying()) {
			loopPlayer.stop();
		} else {
			loopPlayer.play();
		}
		StyleHelper.applyStyleClass(loopPlayer.isPlaying(), playButton, PLAY_BUTTON_ACTIVE_STYLE_CLASS);
	}
}
