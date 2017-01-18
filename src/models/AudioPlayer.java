package models;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

public class AudioPlayer {
	private final static int LONG_SOUND_LIMIT = 24;
	private final static Duration MIN_LONG_SOUND_DURATION = new Duration(1000);

	private List<MediaPlayer> mediaPlayers = new LinkedList<MediaPlayer>();
	private static ThreadPoolExecutor pool = (ThreadPoolExecutor) Executors.newCachedThreadPool();
	
	public void playSound(Media media, float volume, float pitch){
		if(media.getDuration().greaterThan(MIN_LONG_SOUND_DURATION)){
			playLongSound(media, volume, pitch);
		} else {
			playShortSound(media, volume, pitch);
		}
	}

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

	public void playLongSound(Media media, float volume, float pitch) {
		if (mediaPlayers.size() < LONG_SOUND_LIMIT) {
			MediaPlayer mediaPlayer = new MediaPlayer(media);
			mediaPlayers.add(mediaPlayer);
			mediaPlayer.setOnEndOfMedia(() -> pool.execute(createRemoveMediaPlayerRunnable(mediaPlayer)));
			mediaPlayer.setOnStopped(() -> pool.execute(createRemoveMediaPlayerRunnable(mediaPlayer)));
			mediaPlayer.setVolume(volume);
			float javaFXPitch = calculatePitch(pitch);
			mediaPlayer.setRate(javaFXPitch);
			mediaPlayer.play();
		}
	}

	public synchronized void stopAll() {
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
