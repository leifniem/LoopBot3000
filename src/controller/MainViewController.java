package controller;

import javafx.fxml.FXML;
import javafx.scene.layout.*;
import models.LoopManager;
import models.LoopProject;

public class MainViewController {
	@FXML
	private LoopProjectViewController loopProjectViewController;
	@FXML
	private PlayBarController playbarController;

	private LoopManager loopManager;

	public MainViewController() {
		loopManager = new LoopManager();
	}

	@FXML
	private void initialize() {
		LoopProject loopProject = loopManager.getLoopProject();
		playbarController.generateSoundFieldsForTimeSignature(loopProject.numberOfBeatsProperty().get(),
				loopProject.noteValueProperty().get());
		loopProjectViewController.setLoopProject(loopProject);
		loopManager.getLoopProject().addLoop("Test");
		loopManager.getLoopProject().addLoop("Test2");
	}
}
