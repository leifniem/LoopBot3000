package models;

import java.util.LinkedList;
import java.util.List;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class AudioPlayer {
	private List<MediaPlayer> mediaPlayers = new LinkedList<MediaPlayer>();
	
	public void playSound(String filename){
		Media media = new Media(filename);
		MediaPlayer mediaPlayer = new MediaPlayer(media);
		mediaPlayers.add(mediaPlayer);
		mediaPlayer.play();
		mediaPlayer.setOnEndOfMedia(createRemoveMediaPlayerRunnable(mediaPlayer));
		mediaPlayer.setOnStopped(createRemoveMediaPlayerRunnable(mediaPlayer));
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
