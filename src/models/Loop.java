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
	private ObservableList<BooleanProperty> noteStatus = FXCollections.<BooleanProperty> observableArrayList();

	public Loop(String name, TimeSignature timeSignature) {
		this.name.set(name);
		this.timeSignature = timeSignature;
		initNoteStatus();
		
		//test purposes...
		soundFilename = "bin/files/Bass-Drum-1.wav";
	}

	private void initNoteStatus() {
		int amountOfNotes = timeSignature.getAmountOfNotes();
		for (int i = 0; i < amountOfNotes; i++) {
			noteStatus.add(new SimpleBooleanProperty(false));
		}
	}
	
	public String getSoundFilename(){
		return soundFilename;
	}
	
	public void setSoundFile(String soundPath){
		this.soundFilename = soundPath;
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

	public ObservableList<BooleanProperty> getNoteStatus() {
		return noteStatus;
	}
	
	public TimeSignature getTimeSignature(){
		return timeSignature;
	}
}
