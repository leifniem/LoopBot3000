package controller;

import javafx.beans.binding.Bindings;
import javafx.beans.binding.StringBinding;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.SingleSelectionModel;
import javafx.scene.control.Slider;
import javafx.stage.Stage;

/**
 * @author Micha Lanvers, Leif Niemczik
 */
public class CreateProjectViewController {
	private static final int MIN_TEMPO = 60;
	private static final int MAX_TEMPO = 270;
	private static final int DEFAULT_TEMPO = 120;
	private static final Integer DEFAULT_NUMBER_OF_BEATS = 4;
	private static final Integer DEFAULT_NOTE_VALUE = 4;
	
	private ObservableList<Integer> numberOfBeatsValues = FXCollections.<Integer> observableArrayList();
	private ObjectProperty<ObservableList<Integer>> numberOfBeatsItems = new SimpleObjectProperty<ObservableList<Integer>>();
	private ObservableList<Integer> noteValueValues = FXCollections.<Integer> observableArrayList();
	private ObjectProperty<ObservableList<Integer>> noteValueItems = new SimpleObjectProperty<ObservableList<Integer>>();
	private ObjectProperty<SingleSelectionModel<Integer>> selectedNumberOfBeats = new SimpleObjectProperty<SingleSelectionModel<Integer>>();
	private ObjectProperty<SingleSelectionModel<Integer>> selectedNoteValue = new SimpleObjectProperty<SingleSelectionModel<Integer>>();
	private boolean wasSubmitted;

	@FXML
	private ComboBox<Integer> numberOfBeatsInput;
	@FXML
	private ComboBox<Integer> noteValueInput;
	@FXML
	private Slider tempoSlider;
	@FXML
	private Label tempoLabel;
	@FXML
	private Button submitButton;

	@FXML
	private void initialize() {
		initNumberOfBeatsInput();
		initNoteValueInput();
		initBpmSlider();
		initBpmValue();
		submitButton.setOnAction(e -> close());
	}

	private void close() {
		wasSubmitted = true;
		((Stage) tempoSlider.getScene().getWindow()).close();
	}

	private void initBpmValue() {
		StringBinding tempoBinding = Bindings.createStringBinding(() -> (int) tempoSlider.getValue() + "",
				tempoSlider.valueProperty());
		tempoLabel.textProperty().bind(tempoBinding);
	}

	private void initBpmSlider() {
		tempoSlider.setMin(MIN_TEMPO);
		tempoSlider.setMax(MAX_TEMPO);
		tempoSlider.setValue(DEFAULT_TEMPO);
	}

	private void initNumberOfBeatsInput() {
		numberOfBeatsValues.addAll(2, 3, 4, 5);
		numberOfBeatsItems.set(numberOfBeatsValues);
		numberOfBeatsInput.itemsProperty().bind(numberOfBeatsItems);
		selectedNumberOfBeats.bind(numberOfBeatsInput.selectionModelProperty());
		numberOfBeatsInput.getSelectionModel().select(DEFAULT_NUMBER_OF_BEATS);
	}

	private void initNoteValueInput() {
		noteValueValues.addAll(2, 3, 4, 5, 6, 7, 8);
		noteValueItems.set(noteValueValues);
		noteValueInput.itemsProperty().bind(noteValueItems);
		selectedNoteValue.bind(noteValueInput.selectionModelProperty());
		noteValueInput.getSelectionModel().select(DEFAULT_NOTE_VALUE);
	}

	public int getNumberOfBeats() {
		return selectedNumberOfBeats.getValue().getSelectedItem();
	}

	public int getNoteValue() {
		return selectedNoteValue.getValue().getSelectedItem();
	}

	public int getTempo() {
		return (int) tempoSlider.getValue();
	}

	public boolean getWasSubmitted() {
		return wasSubmitted;
	}
}
