package models;

import java.io.File;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;

public class FileManager {
	private final static String INITIAL_FOLTER_PATH = ".";
	
	public static File askUserToLoadAudioFile(Stage parentStage) {
		ExtensionFilter extensionFilter = new ExtensionFilter("Sound", "*.mp3", "*.wav", "*.ogg", "*.wma");
		return chooseFileToLoad(parentStage, extensionFilter);
	}
	
	public static File askUserToLoadAudioFile() {
		return askUserToLoadAudioFile(new Stage());
	}
	
	public static File askUserToLoadXMLFile(Stage parentStage) {
		ExtensionFilter extensionFilter = new ExtensionFilter("Loop Project", "*.lp");
		return chooseFileToLoad(parentStage, extensionFilter);
	}
	
	public static File askUserToLoadXMLFile(){
		return askUserToLoadXMLFile(new Stage());
	}
	
	private static File chooseFileToLoad(Stage parentStage, ExtensionFilter extensionFilter){
		if(parentStage == null){
			throw new IllegalArgumentException("No stage was given!");
		}
		
		FileChooser fc = createFileChooser(extensionFilter);
		File file = fc.showOpenDialog(parentStage);
		
		return file;
	}
	
	public static File askUserToSaveXMLFile(Stage parentStage){
		ExtensionFilter extensionFilter = new ExtensionFilter("Loop Project", "*.lp");
		return chooseFileToSave(parentStage, extensionFilter);
	}
	
	public static File askUserToSaveXMLFile(){
		return askUserToSaveXMLFile(new Stage());
	}
	
	private static File chooseFileToSave(Stage parentStage, ExtensionFilter extensionFilter){
		if(parentStage == null){
			throw new IllegalArgumentException("No stage was given!");
		}
		
		FileChooser fc = createFileChooser(extensionFilter);
		File file = fc.showSaveDialog(parentStage);
		
		return file;
	}

	private static FileChooser createFileChooser(ExtensionFilter extensionFilter) {
		FileChooser fc = new FileChooser();
		fc.getExtensionFilters().add(extensionFilter);
		fc.setInitialDirectory(new File(INITIAL_FOLTER_PATH));
		return fc;
	}
}