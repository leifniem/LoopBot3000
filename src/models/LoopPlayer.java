package models;

import java.util.Timer;
import java.util.TimerTask;

import javafx.beans.property.*;

public class LoopPlayer {
	private AudioPlayer audioPlayer;
	private Timer playTimer;
	private LoopProject loopProject;
	
	private IntegerProperty currentNote = new SimpleIntegerProperty();
	private BooleanProperty isPlaying = new SimpleBooleanProperty();

	public LoopPlayer(LoopProject loopProject) {
		audioPlayer = new AudioPlayer();
		playTimer = new Timer();
		this.loopProject = loopProject;
	}

	public BooleanProperty isPlayingProperty() {
		return isPlaying;
	}
	
	public boolean isPlaying(){
		return isPlaying.get();
	}
	
	public IntegerProperty currentNoteProperty(){
		return currentNote;
	}
	
	public int getCurrentNote(){
		return currentNote.get();
	}

	public void play() {
		if (loopProject != null && !isPlaying.get()) {
			TimeSignature timeSignature = loopProject.getTimeSignature();
			float durationOfSingleSoundInMS = timeSignature.getDurationOfSingleSoundInMS();
			int startingDelay = 0;
			playTimer = new Timer();
			playTimer.scheduleAtFixedRate(createPlayLoopSoundsTask(), startingDelay, (long) durationOfSingleSoundInMS);
			isPlaying.set(true);
		}
	}

	private TimerTask createPlayLoopSoundsTask() {
		int amountOfNotes = loopProject.getTimeSignature().getAmountOfNotes();
		
		return new TimerTask() {
			@Override
			public void run() {
				for (Loop loop : loopProject.getLoops()) {
					playLoopIfNecessary(loop);
				}
				
				int nextNote = (currentNote.get() + 1) % amountOfNotes;
				currentNote.set(nextNote);
			}

			private void playLoopIfNecessary(Loop loop) {
				boolean shouldPlay = !loop.isMutedProperty().get() && loop.getNoteStatus().get(currentNote.get()).get();

				if (shouldPlay) {
					String soundFilename = loop.getSoundFilename();

					if (soundFilename != null && !soundFilename.isEmpty()) {
						audioPlayer.playSound(soundFilename);
					} else {
						System.out.println("Kein Sound hinterlegt!");
					}
				}
			}
		};
	}

	public void stop() {
		if (isPlaying.get()) {
			playTimer.cancel();
			audioPlayer.stopAll();
			isPlaying.set(false);
			currentNote.set(0);
		}
	}
}