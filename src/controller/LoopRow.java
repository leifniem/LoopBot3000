package controller;

import java.io.File;
import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import models.AudioRecorder;
import models.FileManager;
import models.Loop;
import models.TimeSignature;
import views.CircularSlider;

/**
 * @author Micha Lanvers, Leif Niemczik
 */
public class LoopRow extends HBox {
	private final static String RECT_BUTTON_STYLE_CLASS = "rect-button";
	private final static String RECT_BUTTON_ACTIVE_STYLE_CLASS = "rect-on";
	private final static String MUTE_BUTTON_ACTIVE_STYLE_CLASS = "mute-on";
	private final static String SOLO_BUTTON_ACTIVE_STYLE_CLASS = "solo-on";
	private final static String DRAG_ENTERED_ROW_STYLE_CLASS = "drag_entered_row";

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
	private CircularSlider volumeKnob;
	@FXML
	private CircularSlider pitchKnob;
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
		initVolumeKnob(loop);
		initPitchKnob(loop);
		muteButton.setOnAction(e -> switchMute());
		soloButton.setOnAction(e -> switchSolo());
		removeButton.setOnAction(e -> loop.remove());
		nameText.textProperty().bindBidirectional(loop.nameProperty());
		addDragEvents();
		generateNoteStatusButtonsForTimeSignature();
	}

	private void addDragEvents() {
		setOnDragOver(new EventHandler<DragEvent>() {
			@Override
			public void handle(DragEvent event) {
				Dragboard db = event.getDragboard();
				boolean success = isValidDragboardContent(db);

				if(success){
					event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
				}
				
				event.consume();
			}
		});

		setOnDragEntered(new EventHandler<DragEvent>() {
			@Override
			public void handle(DragEvent event) {
				Dragboard db = event.getDragboard();
				boolean success = isValidDragboardContent(db);

				if(success){
					getStyleClass().add(DRAG_ENTERED_ROW_STYLE_CLASS);
				}

				event.consume();
			}
		});

		setOnDragExited(new EventHandler<DragEvent>() {
			@Override
			public void handle(DragEvent event) {
				if(getStyleClass().contains(DRAG_ENTERED_ROW_STYLE_CLASS))
					getStyleClass().remove(DRAG_ENTERED_ROW_STYLE_CLASS);

				event.consume();
			}
		});

		setOnDragDropped(new EventHandler<DragEvent>() {
			@Override
			public void handle(DragEvent event) {
				Dragboard db = event.getDragboard();
				boolean success = isValidDragboardContent(db);

				if(success){
					File file = db.getFiles().get(0);
					
					if (file.isFile() && (file.getName().endsWith(".mp3") || file.getName().endsWith(".wav"))) {
						loop.setSoundFile(file.getAbsolutePath());
						loop.nameProperty().set(file.getName());
					}
				}

				event.setDropCompleted(success);
				event.consume();
			}
		});
	}
	
	private boolean isValidDragboardContent(Dragboard db){
		boolean success = true;
		
		if (db.getFiles().isEmpty()) {
			success = false;
		} else {
			for (File file : db.getFiles()) {
				if (!file.isFile() || (!file.getName().endsWith(".mp3") && !file.getName().endsWith(".wav"))) {
					success = false;
				}
			}
		}

		return success;
	}

	private void initVolumeKnob(Loop loop) {
		volumeKnob.setMax(1);
		volumeKnob.setMin(0);
		volumeKnob.valueProperty().bindBidirectional(loop.volumeProperty());
	}

	private void initPitchKnob(Loop loop) {
		pitchKnob.setMax(1);
		pitchKnob.setMin(-1);
		pitchKnob.valueProperty().bindBidirectional(loop.pitchProperty());
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

		if (active) {
			button.getStyleClass().add(RECT_BUTTON_ACTIVE_STYLE_CLASS);
		}

		button.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				Button button = (Button) e.getSource();
				int buttonId = Integer.parseInt(button.getId());
				boolean buttonIsActive = loop.getNoteStatus().get(buttonId).get();
				StyleHelper.applyStyleClass(!buttonIsActive, button, RECT_BUTTON_ACTIVE_STYLE_CLASS);

				loop.getNoteStatus().get(buttonId).set(!buttonIsActive);
			}
		});

		return button;
	}

	public void loadSample() {
		Stage stage = (Stage) nameText.getScene().getWindow();
		File audio = FileManager.askUserToLoadAudioFile(stage);
		if (audio != null) {
			this.loop.setSoundFile(audio.getAbsolutePath());
			this.loop.nameProperty().set(audio.getName());
		}
	}

	public Loop getLoop() {
		return loop;
	}
}
