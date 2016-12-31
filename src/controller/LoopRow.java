package controller;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import models.Loop;
import models.TimeSignature;

public class LoopRow extends HBox {
	@FXML
	private Label nameLabel;
	@FXML
	private Button chooseFileButton;
	@FXML
	private Button recordButton;
	@FXML
	private HBox soundFieldContainer;
	@FXML
	private Button soloButton;
	@FXML
	private Button muteButton;

	private Loop loop;

	public LoopRow(Loop loop) {
		this.loop = loop;

		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/views/LoopRowView.fxml"));
		fxmlLoader.setRoot(this);
		fxmlLoader.setController(this);

		try {
			fxmlLoader.load();
		} catch (IOException exception) {
			throw new RuntimeException(exception);
		}

		generateSoundFieldsForTimeSignature();
	}

	private void generateSoundFieldsForTimeSignature() {
		TimeSignature timeSignature = loop.getTimeSignature();
		
		for (int currentBeat = 0; currentBeat < timeSignature.getNumberOfBeats(); currentBeat++) {
			for (int currentNote = 0; currentNote < timeSignature.getNoteValue(); currentNote++) {
				Button button = createRectButton("" + currentBeat * currentNote);
				soundFieldContainer.getChildren().add(button);
			}

			HBox spacer = new HBox();
			spacer.getStyleClass().add("spacer");
			soundFieldContainer.getChildren().add(spacer);
		}
	}

	private Button createRectButton(String id) {
		Button button = new Button();
		button.setId(id);
		button.getStyleClass().add("rect-button");
		return button;
	}

	public Loop getLoop() {
		return loop;
	}
}
