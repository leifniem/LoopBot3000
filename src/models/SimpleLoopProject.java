package models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class SimpleLoopProject implements Serializable{
	private static final long serialVersionUID = 7248543067960648347L;
	
	private String name;
	private int numberOfBeats;
	private int noteValue;
	private int tempo;
	private List<SimpleLoop> loops;
	
	public SimpleLoopProject(){
		loops = new ArrayList<SimpleLoop>();
	}
	
	public SimpleLoopProject(LoopProject loopProject){
		loops = new ArrayList<SimpleLoop>();
		setName(loopProject.nameProperty().get());
		TimeSignature timeSignature = loopProject.getTimeSignature();
		setNumberOfBeats(timeSignature.getNumberOfBeats());
		setNoteValue(timeSignature.getNoteValue());
		setTempo(timeSignature.getTempo());
		
		for(Loop loop : loopProject.getLoops()){
			SimpleLoop simpleLoop = new SimpleLoop(loop);
			loops.add(simpleLoop);
		}
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getNumberOfBeats() {
		return numberOfBeats;
	}

	public void setNumberOfBeats(int numberOfBeats) {
		this.numberOfBeats = numberOfBeats;
	}

	public int getNoteValue() {
		return noteValue;
	}

	public void setNoteValue(int noteValue) {
		this.noteValue = noteValue;
	}

	public int getTempo() {
		return tempo;
	}

	public void setTempo(int tempo) {
		this.tempo = tempo;
	}
	
	public List<SimpleLoop> getLoops(){
		return loops;
	}
	
	public void setLoops(List<SimpleLoop> loops){
		this.loops = loops;
	}
}
