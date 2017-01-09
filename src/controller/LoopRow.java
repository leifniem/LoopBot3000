package controller;

import java.io.File;
import java.io.IOException;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import models.AudioFileLoader;
import models.AudioRecorder;
import models.Loop;
import models.TimeSignature;

public class LoopRow extends HBox {
	private final static String RECT_BUTTON_STYLE_CLASS = "rect-button";
	private final static String RECT_BUTTON_ACTIVE_STYLE_CLASS = "rect-on";
	private final AudioRecorder rec = new AudioRecorder();
	
	@FXML
	private Label nameLabel;
	@FXML
	private Button chooseFileButton;
	@FXML
	private Button recordButton;
	@FXML
	private HBox noteStatusContainer;
	@FXML
	private Button soloButton;
	@FXML
	private Button muteButton;

	private Loop loop;

	public LoopRow(Loop loop) {
		this.loop = loop;

		loadFxml();		
		addActionToChooseFileButton();
		addEventsToRecordButton(loop);
		nameLabel.textProperty().bind(loop.nameProperty());
		generateNoteStatusButtonsForTimeSignature();
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

	private void addActionToChooseFileButton() {
		chooseFileButton.setOnAction(new EventHandler<ActionEvent>(){

			@Override
			public void handle(ActionEvent event) {
				loadSample();
			}
			
		});
	}

	private void addEventsToRecordButton(Loop loop) {
		recordButton.setOnMousePressed(new EventHandler<MouseEvent>(){
			@Override
			public void handle(MouseEvent event) {
				rec.startRecording();
			}
			
		});
		recordButton.setOnMouseReleased(new EventHandler<MouseEvent>(){
			@Override
			public void handle(MouseEvent event) {
				rec.stopRecording();
				loop.setSoundFile(rec.getNewestRecording());
			}
		});
	}

	private void generateNoteStatusButtonsForTimeSignature() {
		TimeSignature timeSignature = loop.getTimeSignature();
		
		for (int currentBeat = 0; currentBeat < timeSignature.getNumberOfBeats(); currentBeat++) {
			for (int currentNoteInBeat = 0; currentNoteInBeat < timeSignature.getNoteValue(); currentNoteInBeat++) {
				int currentNote = currentBeat * timeSignature.getNoteValue() + currentNoteInBeat;
				boolean active = loop.getNoteStatus().get(currentNote).get();
				Button button = createRectButton("" + currentNote, active);
				noteStatusContainer.getChildren().add(button);
			}

			HBox spacer = new HBox();
			spacer.getStyleClass().add("spacer");
			noteStatusContainer.getChildren().add(spacer);
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
				ObservableList<String> styleClass = button.getStyleClass();
				
				if(buttonIsActive){
					styleClass.remove(RECT_BUTTON_ACTIVE_STYLE_CLASS);
				} else {
					styleClass.add(RECT_BUTTON_ACTIVE_STYLE_CLASS);
				}
				loop.getNoteStatus().get(buttonId).set(!buttonIsActive);
			}
		});
		
		return button;
	}
	
	public void loadSample(){
		File audio = AudioFileLoader.askUserToLoadAudioFile();
		if(audio != null){
			this.loop.setSoundFile(audio.getAbsolutePath());
			this.loop.nameProperty().set(audio.getName());
		}
	}

	public Loop getLoop() {
		return loop;
	}
}
