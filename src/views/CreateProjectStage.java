package views;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class CreateProjectStage extends Stage{
	public CreateProjectStage() throws IOException{	
		initOwner(new Stage());
		initModality(Modality.APPLICATION_MODAL); 
		
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/CreateProjectView.fxml"));
		VBox root = loader.load();
		// irgendeinController = loader.getController();
		
		Scene scene = new Scene(root, 500, 500);

		setTitle("New Project");
		setScene(scene);
	}
}
