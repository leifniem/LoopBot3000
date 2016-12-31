package models;

import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Loop {
	private StringProperty name = new SimpleStringProperty();
	private String soundFilename;
	private BooleanProperty isMuted = new SimpleBooleanProperty();
	private BooleanProperty isSolo = new SimpleBooleanProperty();
	private TimeSignature timeSignature;
	
	private ObservableList<BooleanProperty> fields = FXCollections.<BooleanProperty> observableArrayList();

	public Loop(String name, TimeSignature timeSignature) {
		this.name.set(name);
		this.timeSignature = timeSignature;
		//Wie werden die Felder in der GUI generiert? Hier kommt sicher noch eine Aenderung!
		initFields();
		
		//test purposes...
		//soundFilename = "D:\\Programmierung\\MagefightColNew\\Eleminja\\Eleminja\\Assets\\ElemNinjaAssets\\Prefabs\\Bullets\\yaay.MP3";
	}

	private void initFields() {
		int amountOfNotes = timeSignature.getAmountOfNotes();
		for (int i = 0; i < amountOfNotes; i++) {
			fields.add(new SimpleBooleanProperty(false));
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

	public ObservableList<BooleanProperty> getFields() {
		return fields;
	}
	
	public TimeSignature getTimeSignature(){
		return timeSignature;
	}
}
