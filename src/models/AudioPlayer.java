package models;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class AudioPlayer {
	private final static int SOUND_LIMIT = 24;

	private List<MediaPlayer> mediaPlayers = new LinkedList<MediaPlayer>();
	private static ThreadPoolExecutor pool = (ThreadPoolExecutor) Executors.newCachedThreadPool();

	public void playShortSound(Media media, float volume, float pitch) {
		MediaPlayer mediaPlayer = new MediaPlayer(media);
		mediaPlayer.setOnEndOfMedia(() -> pool.execute(mediaPlayer::dispose));
		mediaPlayer.setVolume(volume);
		float javaFXPitch = calculatePitch(pitch);
		mediaPlayer.setRate(javaFXPitch);
		mediaPlayer.play();
	}

	/**
	 * Converts pitch between -1 and 1 into pitch between 0 and 8
	 */
	private float calculatePitch(float pitch) {
		float rightPitch = pitch;

		if(rightPitch > 0){
			rightPitch *= 0.875;
			rightPitch = 1 / (1-rightPitch);
		} else {
			rightPitch *= (-1);
			rightPitch = 1-rightPitch;
		}
		
		return rightPitch;
	}

	public void playLongSound(String filename) {
		if (mediaPlayers.size() < SOUND_LIMIT) {
			MediaPlayer mediaPlayer = getNewMediaPlayerFor(filename);
			mediaPlayers.add(mediaPlayer);
			mediaPlayer.play();
			mediaPlayer.setOnEndOfMedia(createRemoveMediaPlayerRunnable(mediaPlayer));
			mediaPlayer.setOnStopped(createRemoveMediaPlayerRunnable(mediaPlayer));
		}
	}

	private MediaPlayer getNewMediaPlayerFor(String filename) {
		Media media = new Media(filename);
		MediaPlayer mediaPlayer = new MediaPlayer(media);
		return mediaPlayer;
	}

	public void stopAll() {
		for (MediaPlayer mp : mediaPlayers) {
			mp.stop();
		}
	}

	private Runnable createRemoveMediaPlayerRunnable(MediaPlayer mediaPlayer) {
		Runnable result = new Runnable() {
			@Override
			public void run() {
				mediaPlayer.dispose();
				mediaPlayers.remove(mediaPlayer);
			}
		};

		return result;
	}
}
