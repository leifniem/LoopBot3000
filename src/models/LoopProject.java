package models;

import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class LoopProject {
	private StringProperty name = new SimpleStringProperty();
	private ObservableList<Loop> loops = FXCollections.<Loop> observableArrayList();
	private IntegerProperty numberOfBeats = new SimpleIntegerProperty();
	private IntegerProperty noteValue = new SimpleIntegerProperty();
	private IntegerProperty tempo = new SimpleIntegerProperty();

	public LoopProject(int numberOfBeats, int noteValue, int tempo) {
		this.numberOfBeats.set(numberOfBeats);
		this.noteValue.set(noteValue);
		this.tempo.set(tempo);
	}

	private void addInitialLoop(int numberOfBeats, int noteValue) {
		Loop loop = new Loop("Default", numberOfBeats, noteValue);
		// setzt jede letzte Note eines beats auf true
		for (int i = noteValue - 1; i < numberOfBeats * noteValue; i += noteValue) {
			loop.getFields().set(i, true);
		}
		
		loops.add(loop);
	}

	public void addLoop(String name) {
		Loop loop = new Loop(name, numberOfBeats.get(), noteValue.get());
		loops.add(loop);
	}

	public StringProperty nameProperty() {
		return name;
	}

	public IntegerProperty numberOfBeatsProperty() {
		return numberOfBeats;
	}

	public IntegerProperty noteValueProperty() {
		return noteValue;
	}

	public IntegerProperty tempoProperty() {
		return tempo;
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

	/**
	 * Calculates the duration of a single sound in milliseconds duration =
	 * tempo of the loop divided by 60 (seconds of a minute) and that divided by
	 * the note value
	 */
	public float getDurationOfSingleSoundInMS() {
		final float milliseconds = 1000f;
		float resultInSeconds =  60f / tempo.get() / noteValue.get();
		return resultInSeconds * milliseconds;
	}
}
