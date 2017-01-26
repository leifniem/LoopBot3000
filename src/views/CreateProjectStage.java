package views;

import java.io.IOException;

import controller.CreateProjectViewController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import launcher.Launcher;

/**
 * @author Micha Lanvers, Leif Niemczik
 */
public class CreateProjectStage extends Stage{
	private CreateProjectViewController controller;
	
	public CreateProjectStage() throws IOException{	
		getIcons().add(new Image(Launcher.class.getResourceAsStream("/files/icon.png")));
		initOwner(new Stage());
		initModality(Modality.APPLICATION_MODAL); 
		
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/CreateProjectView.fxml"));
		VBox root = loader.load();
		controller = loader.getController();
		
		Scene scene = new Scene(root, 500, 500);

		setTitle("New Project");
		setScene(scene);
	}
	
	public CreateProjectViewController getController(){
		return controller;
	}
}
