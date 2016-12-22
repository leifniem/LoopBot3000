package models;

import java.io.File;
import java.util.LinkedList;
import java.util.List;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class AudioPlayer {
	private List<MediaPlayer> mediaPlayers = new LinkedList<MediaPlayer>();
	
	public void playSound(String filename){
		MediaPlayer mediaPlayer = getNewMediaPlayerFor(filename);
		mediaPlayers.add(mediaPlayer);
		mediaPlayer.play();
		mediaPlayer.setOnEndOfMedia(createRemoveMediaPlayerRunnable(mediaPlayer));
		mediaPlayer.setOnStopped(createRemoveMediaPlayerRunnable(mediaPlayer));
	}

	private MediaPlayer getNewMediaPlayerFor(String filename) {
		String asciiFilename = convertToAsciiFilename(filename);
		Media media = new Media(asciiFilename);
		MediaPlayer mediaPlayer = new MediaPlayer(media);
		return mediaPlayer;
	}

	private String convertToAsciiFilename(String filename) {
		File file = new File(filename);
		String asciiFilename = file.toURI().toASCIIString();
		return asciiFilename;
	}
	
	public void stopAll(){
		for(MediaPlayer mp : mediaPlayers){
			mp.stop();
		}
	}
	
	private Runnable createRemoveMediaPlayerRunnable(MediaPlayer mediaPlayer){
		Runnable result = new Runnable(){
			@Override
			public void run() {
				mediaPlayers.remove(mediaPlayer);	
			}
		};
		
		return result;
	}
}
