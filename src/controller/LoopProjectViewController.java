package controller;

import javafx.fxml.FXML;
import javafx.scene.layout.*;

public class LoopProjectViewController {
	@FXML
	private VBox loopRowContainer;
	
	public LoopProjectViewController(){

	}
	
	@FXML
	private void initialize(){
		createDummyLoopRows();
	}

	private void createDummyLoopRows() {
		loopRowContainer.getChildren().add(new LoopRow());
		loopRowContainer.getChildren().add(new LoopRow());
		loopRowContainer.getChildren().add(new LoopRow());
		loopRowContainer.getChildren().add(new LoopRow());
	}
}
