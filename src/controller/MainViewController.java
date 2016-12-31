package controller;

import javafx.fxml.FXML;
import javafx.scene.layout.*;
import models.LoopManager;
import models.LoopProject;
import models.TimeSignature;

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
		TimeSignature timeSignature = loopProject.getTimeSignature();
		playbarController.generateSoundFieldsForTimeSignature(timeSignature.getNumberOfBeats(),
				timeSignature.getNoteValue());
		loopProjectViewController.setLoopProject(loopProject);
		loopManager.getLoopProject().addLoop("Test");
		loopManager.getLoopProject().addDefaultLoop();
	}
}
