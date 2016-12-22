package models;

import java.io.File;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;

public class AudioFileLoader {
	public static File askUserToLoadAudioFile(Stage parentStage) {
		if(parentStage == null){
			throw new IllegalArgumentException("No stage was given!");
		}
		
		FileChooser fc = new FileChooser();
		ExtensionFilter extensionFilter = new ExtensionFilter("Sound", "*.mp3", "*.wav", "*.ogg", "*.wma");
		fc.getExtensionFilters().add(extensionFilter);
		File file = fc.showOpenDialog(parentStage);
		
		return file;
	}
	
	public static File askUserToLoadAudioFile() {
		return askUserToLoadAudioFile(new Stage());
	}
}
