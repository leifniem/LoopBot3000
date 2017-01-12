package controller;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.SingleSelectionModel;
import javafx.scene.control.Slider;

public class CreateProjectViewController {
	private ObservableList<Integer> numberOfBeatsValues = FXCollections.<Integer> observableArrayList();
	private ObjectProperty<ObservableList<Integer>> numberOfBeatsItems = new SimpleObjectProperty<ObservableList<Integer>>();
	
	
	private ObservableList<Integer> noteValueValues = FXCollections.<Integer> observableArrayList();
	private ObjectProperty<ObservableList<Integer>> noteValueItems = new SimpleObjectProperty<ObservableList<Integer>>();

	private ObjectProperty<SingleSelectionModel<Integer>> selectedNumberOfBeats = new SimpleObjectProperty<SingleSelectionModel<Integer>>();
	private ObjectProperty<SingleSelectionModel<Integer>> selectedNoteValue = new SimpleObjectProperty<SingleSelectionModel<Integer>>();
	
	@FXML
	private ComboBox<Integer> numberOfBeatsInput;
	@FXML
	private ComboBox<Integer> noteValueInput;
	@FXML
	private Slider bpmSlider;
	@FXML
	private Button submitButton;

	public CreateProjectViewController() {

	}

	@FXML
	private void initialize() {
		numberOfBeatsValues.add(4);
		numberOfBeatsItems.set(numberOfBeatsValues);
		numberOfBeatsInput.itemsProperty().bind(numberOfBeatsItems);
		selectedNumberOfBeats.bind(numberOfBeatsInput.selectionModelProperty());
		numberOfBeatsInput.getSelectionModel().selectFirst();
		
		noteValueValues.add(4);
		noteValueItems.set(noteValueValues);
		noteValueInput.itemsProperty().bind(noteValueItems);
		selectedNoteValue.bind(noteValueInput.selectionModelProperty());
		noteValueInput.getSelectionModel().selectFirst();
	}
	
	public int getNumberOfBeats(){
		return selectedNumberOfBeats.getValue().getSelectedItem();
	}
	
	public int getNoteValue(){
		return selectedNoteValue.getValue().getSelectedItem();
	}
}
