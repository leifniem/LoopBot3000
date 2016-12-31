package models;

import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class LoopProject {
	private StringProperty name = new SimpleStringProperty();
	private ObservableList<Loop> loops = FXCollections.<Loop> observableArrayList();
	private TimeSignature timeSignature;

	public LoopProject(int numberOfBeats, int noteValue, int tempo) {
		this.timeSignature = new TimeSignature(numberOfBeats, noteValue, tempo);
	}

	private void addInitialLoop() {
		Loop loop = new Loop("Default", timeSignature);
		int numberOfBeats = timeSignature.getNumberOfBeats();
		int noteValue = timeSignature.getNoteValue();
		
		// setzt jede letzte Note eines beats auf true
		for (int i = noteValue - 1; i < numberOfBeats * noteValue; i += noteValue) {
			loop.getFields().set(i, true);
		}
		
		loops.add(loop);
	}

	public void addLoop(String name) {
		Loop loop = new Loop(name, timeSignature);
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

	private Loop getSoloLoop() {
		Loop soloLoop = loops.filtered(x -> x.isSoloProperty().get()).get(0);
		return soloLoop;
	}
	
	public TimeSignature getTimeSignature(){
		return timeSignature;
	}
}
