package views;

import javafx.stage.Modality;
import javafx.stage.Stage;

public class CreateProjectStage extends Stage{
	public CreateProjectStage(){	
		initOwner(new Stage());
		initModality(Modality.APPLICATION_MODAL); 
	}
}
