package models;

import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Loop {
	StringProperty name = new SimpleStringProperty();
	StringProperty soundFilename = new SimpleStringProperty();
	BooleanProperty isMuted = new SimpleBooleanProperty();
	ObservableList<Boolean> fields = FXCollections.<Boolean>observableArrayList();
	
	public Loop(String name, int dimension){
		this.name.set(name);
		initFields(dimension);
	}

	private void initFields(int dimension) {
		for(int i = 0; i < dimension; i++){
			fields.add(false);
		}
	}

	public StringProperty nameProperty() {
		return name;
	}

	public StringProperty soundFilenameProperty() {
		return soundFilename;
	}

	public BooleanProperty isMutedProperty() {
		return isMuted;
	}
}
