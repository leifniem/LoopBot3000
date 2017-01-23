package models;

import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class LoopProject {
	private final static String DEFAULT_LOOP_NAME = "Empty";
	private final static int MAX_AMOUNT_OF_LOOPS = 10;
	
	private StringProperty name = new SimpleStringProperty();
	private ObservableList<Loop> loops = FXCollections.<Loop> observableArrayList();
	private TimeSignature timeSignature;

	public LoopProject(int numberOfBeats, int noteValue, int tempo) {
		this.timeSignature = new TimeSignature(numberOfBeats, noteValue, tempo);
	}
	
	public void addEmptyLoop() {
		Loop loop = new Loop(DEFAULT_LOOP_NAME, this);
		loops.add(loop);
	}

	public StringProperty nameProperty() {
		return name;
	}

	public ObservableList<Loop> getLoops() {
		return loops;
	}

	public void setSoloLoop(int number) {
		disableSoloProperties();
		Loop loop = loops.get(number);
		loop.isSoloProperty().set(true);
	}

	private void disableSoloProperties() {
		for (Loop loop : loops) {
			loop.isSoloProperty().set(false);
		}
	}

	public int getSoloLoop() {
		int soloLoop = loops.filtered(x -> x.isSoloProperty().get()).size();
		return soloLoop;
	}
	
	public TimeSignature getTimeSignature(){
		return timeSignature;
	}

	public void addLoop(Loop loop) {
		if(loops.size() < MAX_AMOUNT_OF_LOOPS)
			loops.add(loop);
	}
	
	public void removeLoop(Loop loop){
		loops.remove(loop);
	}
}
