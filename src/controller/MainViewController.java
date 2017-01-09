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
		initLoopProjectView();
		initPlaybar();
		addLoopsForTestPurposes();
	}

	private void initLoopProjectView() {
		LoopProject loopProject = loopManager.getLoopProject();
		loopProjectViewController.setLoopProject(loopProject);
	}

	private void addLoopsForTestPurposes() {
		loopManager.getLoopProject().addDefaultLoop();
		loopManager.getLoopProject().addDefaultLoop();
		loopManager.getLoopProject().addDefaultLoop();
	}

	private void initPlaybar() {
		LoopProject loopProject = loopManager.getLoopProject();
		TimeSignature timeSignature = loopProject.getTimeSignature();
		playbarController.init(loopManager);
	}
}
