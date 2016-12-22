package models;

import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Loop {
	private StringProperty name = new SimpleStringProperty();
	private String soundFilename;
	private BooleanProperty isMuted = new SimpleBooleanProperty();
	private BooleanProperty isSolo = new SimpleBooleanProperty();
	
	private ObservableList<Boolean> fields = FXCollections.<Boolean> observableArrayList();

	public Loop(String name, int numberOfBeats, int noteValue) {
		this.name.set(name);
		//Wie werden die Felder in der GUI generiert? Hier kommt sicher noch eine Aenderung!
		initFields(numberOfBeats * noteValue);
	}

	private void initFields(int dimension) {
		for (int i = 0; i < dimension; i++) {
			fields.add(false);
		}
	}
	
	public String getSoundFilename(){
		return soundFilename;
	}

	public StringProperty nameProperty() {
		return name;
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
