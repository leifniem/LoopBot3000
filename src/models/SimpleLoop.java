package models;

import java.io.Serializable;
import java.util.ArrayList;
import javafx.beans.property.BooleanProperty;

public class SimpleLoop implements Serializable{
	private String name;
	private String soundFilename;
	private boolean isMuted;
	private boolean isSolo;
	private ArrayList<Boolean> noteStatus = new ArrayList<Boolean>();

	public SimpleLoop(){
	}
	
	public SimpleLoop(Loop loop){
		name = loop.nameProperty().get();
		soundFilename = loop.getSoundFilename();
		isMuted = loop.isMutedProperty().get();
		isSolo = loop.isSoloProperty().get();
		
		for(BooleanProperty active : loop.getNoteStatus()){
			noteStatus.add(active.get());
		}
	}
}
