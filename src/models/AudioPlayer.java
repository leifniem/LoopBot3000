package models;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class AudioPlayer {
	private final static int SOUND_LIMIT = 16;

	private List<MediaPlayer> mediaPlayers = new LinkedList<MediaPlayer>();

	public void playShortSound(String filename){
		/*
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					Clip clip = AudioSystem.getClip();
					//InputStream inputStream = new FileInputStream(filename);
					File file = new File(filename);
					AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(file);
					clip.open(audioInputStream);
					clip.start();
				} catch (LineUnavailableException e) {
					e.printStackTrace();
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} catch (UnsupportedAudioFileException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}).start();
		*/

		MediaPlayer mediaPlayer = getNewMediaPlayerFor(filename);
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
				mediaPlayers.remove(mediaPlayer);
			}
		};

		return result;
	}
}
