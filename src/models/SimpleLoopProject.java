package models;

import java.io.Serializable;
import java.util.ArrayList;

public class SimpleLoopProject implements Serializable{
	private String name;
	private int numberOfBeats;
	private int noteValue;
	private int tempo;
	private ArrayList<SimpleLoop> loops = new ArrayList<SimpleLoop>();
	
	public SimpleLoopProject(){
	}
	
	public SimpleLoopProject(LoopProject loopProject){
		name = loopProject.nameProperty().get();
		TimeSignature timeSignature = loopProject.getTimeSignature();
		numberOfBeats = timeSignature.getNumberOfBeats();
		noteValue = timeSignature.getNoteValue();
		tempo = timeSignature.getTempo();
		
		for(Loop loop : loopProject.getLoops()){
			SimpleLoop simpleLoop = new SimpleLoop(loop);
			loops.add(simpleLoop);
		}
	}
}
