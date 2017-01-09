package models;

import java.io.File;
import java.net.URI;
import java.util.LinkedList;
import java.util.List;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class AudioPlayer {
	private final static int SOUND_LIMIT = 16;
	
	private List<MediaPlayer> mediaPlayers = new LinkedList<MediaPlayer>();
	
	public void playShortSound(String filename){
		MediaPlayer mediaPlayer = getNewMediaPlayerFor(filename);
		mediaPlayer.play();
	}
	
	public void playLongSound(String filename){
		if(mediaPlayers.size() < SOUND_LIMIT){
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
