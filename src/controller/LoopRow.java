package controller;

import java.io.File;
import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import models.AudioRecorder;
import models.FileManager;
import models.Loop;
import models.TimeSignature;

public class LoopRow extends HBox {
	private final static String RECT_BUTTON_STYLE_CLASS = "rect-button";
	private final static String RECT_BUTTON_ACTIVE_STYLE_CLASS = "rect-on";
	private final static String MUTE_BUTTON_ACTIVE_STYLE_CLASS = "mute-on";
	private final static String SOLO_BUTTON_ACTIVE_STYLE_CLASS = "solo-on";
	
	private final AudioRecorder rec = new AudioRecorder();
	
	@FXML
	private TextField nameText;
	@FXML
	private Button chooseFileButton;
	@FXML
	private Button recordButton;
	@FXML
	private HBox noteStatusContainer;
	@FXML
	private Slider volumeSlider;
	@FXML
	private Button soloButton;
	@FXML
	private Button muteButton;
	@FXML
	private Button removeButton;

	private Loop loop;

	public LoopRow(Loop loop) {
		this.loop = loop;

		loadFxml();		
		chooseFileButton.setOnAction(e -> loadSample());
		addEventsToRecordButton();
		volumeSlider.setMax(1);
		volumeSlider.valueProperty().bindBidirectional(loop.volumeProperty());
		muteButton.setOnAction(e -> switchMute());
		soloButton.setOnAction(e -> switchSolo());
		removeButton.setOnAction(e -> loop.remove());
		nameText.textProperty().bindBidirectional(loop.nameProperty());
		generateNoteStatusButtonsForTimeSignature();
	}

	private void switchMute() {
		loop.isMutedProperty().set(!loop.isMutedProperty().get());
		StyleHelper.applyStyleClass(loop.isMutedProperty().get(), muteButton, MUTE_BUTTON_ACTIVE_STYLE_CLASS);
	}
	
	private void switchSolo() {
		loop.isSoloProperty().set(!loop.isSoloProperty().get());
		StyleHelper.applyStyleClass(loop.isSoloProperty().get(), soloButton, SOLO_BUTTON_ACTIVE_STYLE_CLASS);
	}

	private void loadFxml() {
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/views/LoopRowView.fxml"));
		fxmlLoader.setRoot(this);
		fxmlLoader.setController(this);

		try {
			fxmlLoader.load();
		} catch (IOException exception) {
			throw new RuntimeException(exception);
		}
	}

	private void addEventsToRecordButton() {
		recordButton.setOnMousePressed(e -> rec.startRecording());
		recordButton.setOnMouseReleased(e -> stopRecordingAndApplySoundFile(loop));
	}
	
	private void stopRecordingAndApplySoundFile(Loop loop) {
		rec.stopRecording();
		loop.setSoundFile(rec.getNewestRecording());
	}

	private void generateNoteStatusButtonsForTimeSignature() {
		TimeSignature timeSignature = loop.getTimeSignature();
		
		for (int currentBeat = 0; currentBeat < timeSignature.getNumberOfBeats(); currentBeat++) {
			Button button = null;
			for (int currentNoteInBeat = 0; currentNoteInBeat < timeSignature.getNoteValue(); currentNoteInBeat++) {
				int currentNote = currentBeat * timeSignature.getNoteValue() + currentNoteInBeat;
				boolean active = loop.getNoteStatus().get(currentNote).get();
				button = createRectButton("" + currentNote, active);
				noteStatusContainer.getChildren().add(button);
			}

			button.getStyleClass().add("space");
		}
	}

	private Button createRectButton(String id, boolean active) {
		Button button = new Button();
		button.setId(id);
		button.getStyleClass().add(RECT_BUTTON_STYLE_CLASS);
		
		if(active){
			button.getStyleClass().add(RECT_BUTTON_ACTIVE_STYLE_CLASS);
		}

		button.setOnAction(new EventHandler<ActionEvent>(){
			@Override
			public void handle(ActionEvent e) {
				Button button = (Button)e.getSource();
				int buttonId = Integer.parseInt(button.getId());
				boolean buttonIsActive = loop.getNoteStatus().get(buttonId).get();
				StyleHelper.applyStyleClass(!buttonIsActive, button, RECT_BUTTON_ACTIVE_STYLE_CLASS);
				
				loop.getNoteStatus().get(buttonId).set(!buttonIsActive);
			}
		});
		
		return button;
	}
	
	public void loadSample(){
		File audio = FileManager.askUserToLoadAudioFile();
		if(audio != null){
			this.loop.setSoundFile(audio.getAbsolutePath());
			this.loop.nameProperty().set(audio.getName());
		}
	}

	public Loop getLoop() {
		return loop;
	}
}
