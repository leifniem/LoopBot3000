package controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import models.LoopProject;
import models.LoopProjectExporter;

public class MainViewController {
	private final static int DEFAULT_NUMBER_OF_BEATS = 4;
	private final static int DEFAULT_NOTE_VALUE = 4;
	private final static int DEFAULT_TEMP = 120;
	private final static int MAX_LOOPS = 9;

	@FXML
	private LoopProjectViewController loopProjectViewController;
	@FXML
	private PlayBarController playbarController;
	@FXML
	private Button loadButton;
	@FXML
	private Button saveButton;
	@FXML
	private Button addLoopButton;

	private LoopProject loopProject;

	public MainViewController() {
		createDefaultLoopProject();
	}

	public void createDefaultLoopProject() {
		loopProject = new LoopProject(DEFAULT_NUMBER_OF_BEATS, DEFAULT_NOTE_VALUE, DEFAULT_TEMP);
	}

	public void setLoopProject(LoopProject loopProject) {
		this.loopProject = loopProject;
	}

	@FXML
	private void initialize() {
		initLoopProjectView();
		initPlaybar();

		saveButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				LoopProjectExporter.askUserToExportLoopProject(loopProject);
			}
		});

		loadButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				importLoopProject();
			}
		});

		addLoopButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				if (loopProject.getLoops().size() < MAX_LOOPS) {
					loopProject.addEmptyLoop();
				}
			}
		});

		addLoopsForTestPurposes();
	}

	public void importLoopProject() {
		LoopProject importedLoopProject = LoopProjectExporter.askUserToImportLoopProject();
		if (importedLoopProject != null) {
			loopProject = importedLoopProject;
			initLoopProjectView();
			initPlaybar();
		}
	}

	private void initLoopProjectView() {
		loopProjectViewController.initLoopProjectContainer(loopProject);
	}

	private void addLoopsForTestPurposes() {
		loopProject.addDefaultLoop();
	}

	private void initPlaybar() {
		playbarController.init(loopProject);
	}

	public LoopProject getLoopProject() {
		return loopProject;
	}
}
