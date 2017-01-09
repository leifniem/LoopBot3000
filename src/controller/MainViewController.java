package controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import models.LoopPlayer;
import models.LoopProject;
import models.LoopProjectExporter;
import models.TimeSignature;

public class MainViewController {
	private final static int DEFAULT_NUMBER_OF_BEATS = 4;
	private final static int DEFAULT_NOTE_VALUE = 4;
	private final static int DEFAULT_TEMP = 120;
	
	@FXML
	private LoopProjectViewController loopProjectViewController;
	@FXML
	private PlayBarController playbarController;
	@FXML
	private Button loadButton;
	@FXML
	private Button saveButton;

	private LoopProject loopProject;

	public MainViewController() {
		createDefaultLoopProject();
	}
	
	public void createDefaultLoopProject() {
		loopProject = new LoopProject(DEFAULT_NUMBER_OF_BEATS, DEFAULT_NOTE_VALUE, DEFAULT_TEMP);
	}
	
	public void setLoopProject(LoopProject loopProject){
		this.loopProject = loopProject;
	}

	@FXML
	private void initialize() {
		initLoopProjectView();
		initPlaybar();
		
		saveButton.setOnAction(new EventHandler<ActionEvent>(){
			@Override
			public void handle(ActionEvent arg0) {
				LoopProjectExporter.exportLoopProject(loopProject);
			}
		});
		
		loadButton.setOnAction(new EventHandler<ActionEvent>(){
			@Override
			public void handle(ActionEvent arg0) {
				loopProject = LoopProjectExporter.importLoopProject();
				initLoopProjectView();
				initPlaybar();
			}
		});
		
		addLoopsForTestPurposes();
	}

	private void initLoopProjectView() {
		loopProjectViewController.initLoopProjectContainer(loopProject);
	}

	private void addLoopsForTestPurposes() {
		loopProject.addDefaultLoop();
		loopProject.addDefaultLoop();
		loopProject.addDefaultLoop();
	}

	private void initPlaybar() {
		playbarController.init(loopProject);
	}
}
