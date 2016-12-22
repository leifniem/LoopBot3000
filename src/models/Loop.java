package models;

import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Loop {
	private StringProperty name = new SimpleStringProperty();
	private StringProperty soundFilename = new SimpleStringProperty();
	private BooleanProperty isMuted = new SimpleBooleanProperty();
	private BooleanProperty isSolo = new SimpleBooleanProperty();
	private ObservableList<Boolean> fields = FXCollections.<Boolean> observableArrayList();

	public Loop(String name, int dimension) {
		this.name.set(name);
		initFields(dimension);
	}

	private void initFields(int dimension) {
		for (int i = 0; i < dimension; i++) {
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

	public BooleanProperty isSoloProperty() {
		return isSolo;
	}

	public ObservableList<Boolean> getFields() {
		return fields;
	}
}
