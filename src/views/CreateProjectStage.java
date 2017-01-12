package views;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class CreateProjectStage extends Stage{
	public CreateProjectStage(){	
		initOwner(new Stage());
		initModality(Modality.APPLICATION_MODAL); 
		
		//FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/CreateProjectView.fxml"));
		//VBox root = loader.load();
		//irgendeinController = loader.getController();
		
		//Scene scene = new Scene(root, 1000, 500);

		setTitle("Loop-Bot 3000");
		//setScene(scene);
	}
}
