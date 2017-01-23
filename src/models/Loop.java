package models;

import java.io.File;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.media.Media;

public class Loop {
	private StringProperty name = new SimpleStringProperty();
	private String soundFilename;
	private Media soundMedia;
	private FloatProperty volume = new SimpleFloatProperty();
	private FloatProperty pitch = new SimpleFloatProperty();
	private BooleanProperty isMuted = new SimpleBooleanProperty();
	private BooleanProperty isSolo = new SimpleBooleanProperty();
	private LoopProject loopProject;
	private ObservableList<BooleanProperty> noteStatus = FXCollections.<BooleanProperty> observableArrayList();

	public Loop(String name, LoopProject loopProject) {
		this.name.set(name);
		this.volume.set(1f);
		this.loopProject = loopProject;
		initNoteStatus();
	}

	private void initNoteStatus() {
		int amountOfNotes = loopProject.getTimeSignature().getAmountOfNotes();
		for (int i = 0; i < amountOfNotes; i++) {
			noteStatus.add(new SimpleBooleanProperty(false));
		}
	}
	
	public String getSoundFilename(){
		return soundFilename;
	}
	
	public void setSoundFile(String soundFilename){
		this.soundFilename = soundFilename;
		if(soundFilename != null && !soundFilename.isEmpty()){
			String asciiFilename = convertToAsciiFilename(soundFilename);
			soundMedia = new Media(asciiFilename);
		}
	}
	
	private String convertToAsciiFilename(String filename){
		File file = new File(filename);
		String result = file.toURI().toASCIIString();
		return result;
	}

	public StringProperty nameProperty() {
		return name;
	}
	
	public FloatProperty volumeProperty(){
		return volume;
	}
	
	public FloatProperty pitchProperty(){
		return pitch;
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
		return loopProject.getTimeSignature();
	}
	
	public void remove(){
		loopProject.removeLoop(this);			
	}	
	
	public Media getSoundMedia(){
		return soundMedia;
	}
	
	public float getVolume(){
		return volume.get(); 
	}
	
	public float getPitch(){
		return pitch.get();
	}
}
