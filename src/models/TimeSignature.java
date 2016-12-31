package models;

import javafx.beans.property.ReadOnlyIntegerProperty;
import javafx.beans.property.ReadOnlyIntegerWrapper;

public class TimeSignature {
	private final ReadOnlyIntegerWrapper numberOfBeats = new ReadOnlyIntegerWrapper();
	private final ReadOnlyIntegerWrapper noteValue = new ReadOnlyIntegerWrapper();
	private final ReadOnlyIntegerWrapper tempo = new ReadOnlyIntegerWrapper();
	
	public TimeSignature(int numberOfBeats, int noteValue, int tempo){
		this.numberOfBeats.set(numberOfBeats);
		this.noteValue.set(noteValue);
		this.tempo.set(tempo);
	}
	
	public ReadOnlyIntegerProperty numberOfBeatsProperty(){
		return numberOfBeats.getReadOnlyProperty();
	}
	
	public ReadOnlyIntegerProperty noteValueProperty(){
		return noteValue.getReadOnlyProperty();
	}
	
	public ReadOnlyIntegerProperty tempoProperty(){
		return tempo.getReadOnlyProperty();
	}
	
	public int getNumberOfBeats(){
		return numberOfBeats.get();
	}
	
	public int getNoteValue(){
		return noteValue.get();
	}
	
	public int getTempo(){
		return tempo.get();
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
	
	public int getAmountOfNotes(){
		return noteValue.get() * numberOfBeats.get();
	}
}
