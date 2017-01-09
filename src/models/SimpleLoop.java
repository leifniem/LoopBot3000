package models;

import java.io.Serializable;
import java.util.ArrayList;
import javafx.beans.property.BooleanProperty;

public class SimpleLoop implements Serializable{
	private static final long serialVersionUID = -5833733316394246363L;
	
	private String name;
	private String soundFilename;
	private boolean isMuted;
	private boolean isSolo;
	private ArrayList<Boolean> noteStatus = new ArrayList<Boolean>();

	public SimpleLoop(){
	}
	
	public SimpleLoop(Loop loop){
		setName(loop.nameProperty().get());
		setSoundFilename(loop.getSoundFilename());
		setMuted(loop.isMutedProperty().get());
		setSolo(loop.isSoloProperty().get());
		
		for(BooleanProperty active : loop.getNoteStatus()){
			noteStatus.add(active.get());
		}
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSoundFilename() {
		return soundFilename;
	}

	public void setSoundFilename(String soundFilename) {
		this.soundFilename = soundFilename;
	}

	public boolean isMuted() {
		return isMuted;
	}

	public void setMuted(boolean isMuted) {
		this.isMuted = isMuted;
	}

	public boolean isSolo() {
		return isSolo;
	}

	public void setSolo(boolean isSolo) {
		this.isSolo = isSolo;
	}
}
