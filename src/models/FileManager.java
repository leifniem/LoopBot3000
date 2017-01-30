package models;

import java.io.File;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;

/**
 * @author Micha Lanvers, Leif Niemczik
 */
public class FileManager {
	private final static String INITIAL_FOLDER_PATH = ".";
	
	public static File askUserToLoadAudioFile(Stage parentStage) {
		
		ExtensionFilter extensionFilter = new ExtensionFilter("Sound", "*.mp3", "*.wav", "*.ogg", "*.wma");
		return chooseFileToLoad(parentStage, extensionFilter);
	}
	
	public static File askUserToLoadXMLFile(Stage parentStage) {
		ExtensionFilter extensionFilter = new ExtensionFilter("Loop Project", "*.lp");
		return chooseFileToLoad(parentStage, extensionFilter);
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
		fc.setInitialDirectory(new File(INITIAL_FOLDER_PATH));
		return fc;
	}
}
