package controller;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import models.LoopPlayer;
import models.LoopProject;
import models.TimeSignature;

/**
 * @author Micha Lanvers, Leif Niemczik
 */
public class PlayBarController {
	private final static String PLAY_POSITION_BUTTON_STYLE_CLASS = "play-position-button";
	private final static String PLAY_BUTTON_ACTIVE_STYLE_CLASS = "play-on";

	@FXML
	private Button playButton;
	@FXML
	private HBox playPositionButtonContainer;

	private LoopPlayer loopPlayer;
	private TimeSignature timeSignature;
	
	public void init(LoopProject loopProject) {
		loopPlayer = new LoopPlayer(loopProject);
		timeSignature = loopProject.getTimeSignature();
		generateNoteStatusButtonsForTimeSignature(timeSignature.getNumberOfBeats(), timeSignature.getNoteValue());
		setPlayButtonAction();
		addCurrentPlayPositionListener();
	}

	public void generateNoteStatusButtonsForTimeSignature(int numberOfBeats, int noteValue) {
		playPositionButtonContainer.getChildren().clear();

		for (int currentBeat = 0; currentBeat < timeSignature.getNumberOfBeats(); currentBeat++) {
			Button button = null;
			for (int currentNoteInBeat = 0; currentNoteInBeat < timeSignature.getNoteValue(); currentNoteInBeat++) {
				int currentNote = currentBeat * timeSignature.getNoteValue() + currentNoteInBeat;
				button = createRectButton("" + currentNote);
				
				button.setOnAction(new EventHandler<ActionEvent>(){
					@Override
					public void handle(ActionEvent event) {
						loopPlayer.playNote(currentNote);
					}
				});
				
				playPositionButtonContainer.getChildren().add(button);
			}

			button.getStyleClass().add("space");
		}

		HBox spacer = new HBox();
		spacer.getStyleClass().add("pb-spacer");
		playPositionButtonContainer.getChildren().add(spacer);
	}

	private Button createRectButton(String id) {
		Button button = new Button();
		button.setId(id);
		button.getStyleClass().add("rect-button");
		return button;
	}

	private void setPlayButtonAction() {
		playButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				switchPlaying();
				StyleHelper.applyStyleClass(loopPlayer.isPlaying(), playButton, PLAY_BUTTON_ACTIVE_STYLE_CLASS);
			}
		});
	}

	private void addCurrentPlayPositionListener() {
		loopPlayer.currentNoteProperty().addListener(new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> observable, Number previousNote, Number currentNote) {
				Button previousButton = (Button)playPositionButtonContainer.getChildren().get((int) previousNote);
				Platform.runLater(new Runnable() {
					@Override
					public void run() {
						StyleHelper.applyStyleClass(false, previousButton, PLAY_POSITION_BUTTON_STYLE_CLASS);
					}
				});
				
				Button currentButton = (Button)playPositionButtonContainer.getChildren().get((int) currentNote);
				Platform.runLater(new Runnable() {
					@Override
					public void run() {
						StyleHelper.applyStyleClass(true, currentButton, PLAY_POSITION_BUTTON_STYLE_CLASS);
					}
				});
				
				if(!loopPlayer.isPlaying()){
					Platform.runLater(new Runnable() {
						@Override
						public void run() {
							StyleHelper.applyStyleClass(false, currentButton, PLAY_POSITION_BUTTON_STYLE_CLASS);
						}
					});
				}
			}
		});
	}

	public void switchPlaying() {
		loopPlayer.switchPlaying();
	}

	public void stopPlaying() {
		loopPlayer.stop();
	}
}
