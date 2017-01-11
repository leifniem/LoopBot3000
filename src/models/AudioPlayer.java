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

	public void playShortSound(Media media) {
		MediaPlayer mediaPlayer = new MediaPlayer(media);
		mediaPlayer.setOnEndOfMedia(() -> pool.execute(mediaPlayer::dispose));
		mediaPlayer.play();
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
