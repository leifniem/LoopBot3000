package controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import models.LoopManager;
import models.LoopProject;
import models.LoopProjectExporter;
import models.TimeSignature;

public class MainViewController {
	@FXML
	private LoopProjectViewController loopProjectViewController;
	@FXML
	private PlayBarController playbarController;
	@FXML
	private Button loadButton;
	@FXML
	private Button saveButton;

	private LoopManager loopManager;

	public MainViewController() {
		loopManager = new LoopManager();
	}

	@FXML
	private void initialize() {
		initLoopProjectView();
		initPlaybar();
		
		saveButton.setOnAction(new EventHandler<ActionEvent>(){
			@Override
			public void handle(ActionEvent arg0) {
				LoopProjectExporter.exportLoopProject(loopManager.getLoopProject());
			}
		});
		
		loadButton.setOnAction(new EventHandler<ActionEvent>(){
			@Override
			public void handle(ActionEvent arg0) {
				LoopProject loopProject = LoopProjectExporter.importLoopProject();
				loopManager.setLoopProject(loopProject);
				initLoopProjectView();
				initPlaybar();
			}
		});
		
		addLoopsForTestPurposes();
	}

	private void initLoopProjectView() {
		LoopProject loopProject = loopManager.getLoopProject();
		loopProjectViewController.initLoopProjectContainer(loopProject);
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
