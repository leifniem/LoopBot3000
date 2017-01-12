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
	private Label bpmValue;
	@FXML
	private Button submitButton;


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
		
		// Slider
		bpmSlider.setMin(60);
		bpmSlider.setMax(270);
		
		StringBinding bpmAsString = Bindings.createStringBinding(() -> (int)bpmSlider.getValue() + "", bpmSlider.valueProperty());
		
		bpmValue.textProperty().bind(bpmAsString);
	}
	
	public int getNumberOfBeats(){
		return selectedNumberOfBeats.getValue().getSelectedItem();
	}
	
	public int getNoteValue(){
		return selectedNoteValue.getValue().getSelectedItem();
	}
}
