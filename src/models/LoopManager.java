package models;

import java.util.Timer;
import java.util.TimerTask;

import javafx.beans.property.*;

public class LoopManager {
	private AudioPlayer audioPlayer;
	private LoopProject loopProject;
	private Timer playTimer;
	private int currentNote;

	private BooleanProperty isPlaying = new SimpleBooleanProperty();

	public LoopManager() {
		audioPlayer = new AudioPlayer();
		playTimer = new Timer();
		createDefaultLoopProject();
	}

	public void createDefaultLoopProject() {
		loopProject = new LoopProject(4, 4, 120);
	}

	public BooleanProperty isPlayingProperty() {
		return isPlaying;
	}

	public void play() {
		if (loopProject != null && !isPlaying.get()) {
			float durationOfSingleSoundInMS = loopProject.getDurationOfSingleSoundInMS();
			playTimer.scheduleAtFixedRate(createPlayLoopSoundsTask(), 0, (long) durationOfSingleSoundInMS);
			isPlaying.set(true);
		}
	}

	private TimerTask createPlayLoopSoundsTask() {
		return new TimerTask() {
			@Override
			public void run() {
				for (Loop loop : loopProject.getLoops()) {
					playLoopIfNecessary(loop);
				}

				int amountOfNotes = loopProject.numberOfBeatsProperty().get() * loopProject.noteValueProperty().get();
				currentNote = (currentNote + 1) % amountOfNotes;
			}

			private void playLoopIfNecessary(Loop loop) {
				boolean shouldPlay = loop.getFields().get(currentNote);

				if (shouldPlay) {
					String soundFilename = loop.getSoundFilename();

					if (soundFilename != null && !soundFilename.isEmpty()) {
						audioPlayer.playSound(loop.getSoundFilename());
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
			currentNote = 0;
		}
	}
}