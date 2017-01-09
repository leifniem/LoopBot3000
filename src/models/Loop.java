package models;

import java.io.File;
import java.net.URI;

import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Loop {
	private StringProperty name = new SimpleStringProperty();
	private String soundFilename;
	private String asciiFilename;
	private BooleanProperty isMuted = new SimpleBooleanProperty();
	private BooleanProperty isSolo = new SimpleBooleanProperty();
	private TimeSignature timeSignature;
	private ObservableList<BooleanProperty> noteStatus = FXCollections.<BooleanProperty> observableArrayList();

	public Loop(String name, TimeSignature timeSignature) {
		this.name.set(name);
		this.timeSignature = timeSignature;
		initNoteStatus();
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
	
	public String getAsciiFilename(){
		return asciiFilename;
	}
	
	public void setSoundFile(String soundFilename){
		this.soundFilename = soundFilename;
		asciiFilename = convertToAsciiFilename(soundFilename);
	}
	
	private String convertToAsciiFilename(String filename){
		File file = new File(filename);
		String result = file.toURI().toASCIIString();
		return result;
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
