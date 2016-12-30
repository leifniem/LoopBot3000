package controller;

import javafx.fxml.FXML;
import javafx.scene.layout.*;
import models.LoopManager;

public class MainViewController {
	@FXML
	private VBox loopProjectView; 
	@FXML
	private LoopProjectViewController loopProjectViewController;
	
	private LoopManager loopManager;
	
	public MainViewController(){
		loopManager = new LoopManager();
	}
	
	@FXML
	private void initialize(){
		loopProjectViewController.setLoopProject(loopManager.getLoopProject());
		loopManager.getLoopProject().addLoop("Test", 4, 4);
		loopManager.getLoopProject().addLoop("Test2", 4, 4);
	}
}
