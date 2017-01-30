package controller;

import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import models.Loop;
import models.LoopProject;
import models.LoopProjectExporter;
import views.CreateProjectStage;

/**
 * @author Micha Lanvers, Leif Niemczik
 */
public class MainViewController {
	private final static int DEFAULT_NUMBER_OF_BEATS = 4;
	private final static int DEFAULT_NOTE_VALUE = 4;
	private final static int DEFAULT_TEMP = 120;
	private final static int MAX_LOOPS = 9;
	private final static int MAX_NOTES_FOR_DEFAULT_CSS = 20;
	private final static String DRAG_ENTERED_PROJECT_STYLE_CLASS = "drag_entered_row";

	@FXML
	private LoopProjectViewController loopProjectViewController;
	@FXML
	private PlayBarViewController playbarController;
	@FXML
	private Button loadButton;
	@FXML
	private Button saveButton;
	@FXML
	private Button addLoopButton;
	@FXML
	private Button newProjectButton;
	@FXML
	private HBox emptyProjectView;
	@FXML
	private VBox contentVBox;

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
		initLoopProjectComponents();

		saveButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				exportLoopProject();
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

		newProjectButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				openCreateProjectDialog();
			}
		});
	}

	private void initLoopProjectComponents() {
		initLoopProjectView();
		initPlaybar();
		initEmptyProjectBinding();
		addDragEventsToNode(emptyProjectView);
		addDragEventsToNode(addLoopButton);
	}

	private void initEmptyProjectBinding() {
		BooleanBinding noLoops = Bindings.createBooleanBinding(() -> loopProject.getLoops().size() == 0,
				loopProject.getLoops());
		emptyProjectView.visibleProperty().bind(noLoops);
		emptyProjectView.managedProperty().bind(noLoops);
	}

	private Stage getParentStage() {
		Stage stage = (Stage) loadButton.getScene().getWindow();
		return stage;
	}

	public void openCreateProjectDialog() {
		try {
			playbarController.stopPlaying();
			CreateProjectStage dialog = new CreateProjectStage();
			dialog.showAndWait();

			if (dialog.getController().getWasSubmitted()) {
				int numberOfBeats = dialog.getController().getNumberOfBeats();
				int noteValue = dialog.getController().getNoteValue();
				int tempo = dialog.getController().getTempo();

				loopProject = new LoopProject(numberOfBeats, noteValue, tempo);
				initLoopProjectComponents();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void importLoopProject() {
		playbarController.stopPlaying();
		LoopProject importedLoopProject = LoopProjectExporter.askUserToImportLoopProject(getParentStage());
		if (importedLoopProject != null) {
			loopProject = importedLoopProject;
			initLoopProjectComponents();
		}
	}

	public void exportLoopProject() {
		playbarController.stopPlaying();
		LoopProjectExporter.askUserToExportLoopProject(loopProject, getParentStage());
	}

	public void determineSize() {
		String css = getSmallCSSURIAsString();

		if (contentVBox != null) {
			if (loopProject.getTimeSignature().getAmountOfNotes() > MAX_NOTES_FOR_DEFAULT_CSS) {
				if (!contentVBox.getStylesheets().contains(css))
					contentVBox.getStylesheets().add(css);
			} else {
				if (contentVBox.getStylesheets().contains(css))
					contentVBox.getStylesheets().remove(css);
			}
		}
	}

	private String getSmallCSSURIAsString() {
		URL url = getClass().getResource("/stylesheets/small.css");
		String result = "";

		if (url != null) {
			try {
				result = url.toURI().toString();
			} catch (URISyntaxException e) {
				e.printStackTrace();
			}
		}

		return result;
	}

	private void addDragEventsToNode(Node node) {
		node.setOnDragOver(new AddLoopDragOverEventHandler());
		node.setOnDragEntered(new AddLoopDragEnteredEventHandler(node));
		node.setOnDragExited(new AddLoopDragExitedEventHandler(node));
		node.setOnDragDropped(new AddLoopDragDroppedEventHandler());
	}

	private class AddLoopDragOverEventHandler implements EventHandler<DragEvent> {
		@Override
		public void handle(DragEvent event) {
			Dragboard db = event.getDragboard();
			boolean success = isValidDragboardContent(db);

			if (success) {
				event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
			}

			event.consume();
		}
	}

	private class AddLoopDragEnteredEventHandler implements EventHandler<DragEvent> {
		private Node node;

		public AddLoopDragEnteredEventHandler(Node node) {
			this.node = node;
		}

		@Override
		public void handle(DragEvent event) {
			Dragboard db = event.getDragboard();
			boolean success = isValidDragboardContent(db);

			if (success) {
				node.getStyleClass().add(DRAG_ENTERED_PROJECT_STYLE_CLASS);
			}

			event.consume();
		}
	}

	private class AddLoopDragExitedEventHandler implements EventHandler<DragEvent> {
		private Node node;

		public AddLoopDragExitedEventHandler(Node node) {
			this.node = node;
		}

		@Override
		public void handle(DragEvent event) {
			if (node.getStyleClass().contains(DRAG_ENTERED_PROJECT_STYLE_CLASS))
				node.getStyleClass().remove(DRAG_ENTERED_PROJECT_STYLE_CLASS);

			event.consume();
		}
	}

	private class AddLoopDragDroppedEventHandler implements EventHandler<DragEvent> {
		@Override
		public void handle(DragEvent event) {
			Dragboard db = event.getDragboard();
			boolean success = isValidDragboardContent(db);

			if (success) {
				for (File file : db.getFiles()) {
					if (file.isFile() && (file.getName().endsWith(".mp3") || file.getName().endsWith(".wav"))) {
						Loop loop = new Loop(file.getName(), loopProject);
						loop.setSoundFile(file.getAbsolutePath());
						loopProject.addLoop(loop);
					}
				}
			}

			event.setDropCompleted(success);
			event.consume();
		}
	}

	private boolean isValidDragboardContent(Dragboard db) {
		boolean success = true;

		if (db.getFiles().isEmpty()) {
			success = false;
		} else {
			for (File file : db.getFiles()) {
				if (!file.isFile() || (!file.getName().endsWith(".mp3") && !file.getName().endsWith(".wav"))) {
					success = false;
				}
			}
		}

		return success;
	}

	private void initLoopProjectView() {
		loopProjectViewController.initLoopProjectContainer(loopProject);
		determineSize();
	}

	private void initPlaybar() {
		playbarController.init(loopProject);
	}

	public LoopProject getLoopProject() {
		return loopProject;
	}

	public PlayBarViewController getPlayBarController() {
		return playbarController;
	}
}
