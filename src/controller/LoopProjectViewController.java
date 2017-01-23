package controller;

import java.io.File;

import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.binding.DoubleBinding;
import javafx.beans.binding.IntegerBinding;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.*;
import models.Loop;
import models.LoopProject;

public class LoopProjectViewController {
	private final static String DRAG_ENTERED_PROJECT_STYLE_CLASS = "drag_entered_row";

	@FXML
	private VBox loopRowContainer;
	@FXML
	private HBox emptyProjectView;

	private LoopProject loopProject;
	private ObservableList<LoopRow> loopRows = FXCollections.<LoopRow> observableArrayList();

	public LoopProjectViewController() {

	}

	public void initLoopProjectContainer(LoopProject loopProject) {
		this.loopProject = loopProject;

		loopRowContainer.getChildren().clear();
		for (Loop loop : loopProject.getLoops()) {
			LoopRow loopRow = new LoopRow(loop);
			loopRows.add(loopRow);
			loopRowContainer.getChildren().add(loopRow);
		}

		addChangeListenerToLoopProject();

		BooleanBinding noLoops = Bindings.createBooleanBinding(() -> loopProject.getLoops().size() == 0,
				loopProject.getLoops());
		emptyProjectView.visibleProperty().bind(noLoops);
		emptyProjectView.managedProperty().bind(noLoops);

		addDragEventsToEmptyProjectView();
	}

	private void addChangeListenerToLoopProject() {
		loopProject.getLoops().addListener(new ListChangeListener<Loop>() {
			@Override
			public void onChanged(ListChangeListener.Change<? extends Loop> change) {
				while (change.next()) {
					if (change.wasAdded()) {
						for (Loop addedLoop : change.getAddedSubList()) {
							LoopRow loopRow = new LoopRow(addedLoop);
							loopRows.add(loopRow);
							loopRowContainer.getChildren().add(loopRow);
						}
					}
					if (change.wasRemoved()) {
						for (Loop removedLoop : change.getRemoved()) {
							LoopRow loopRow = loopRows.filtered(x -> x.getLoop() == removedLoop).get(0);
							loopRows.remove(loopRow);
							loopRowContainer.getChildren().remove(loopRow);
						}
					}
				}
			}
		});
	}

	private void addDragEventsToEmptyProjectView() {
		emptyProjectView.setOnDragOver(new EventHandler<DragEvent>() {
			@Override
			public void handle(DragEvent event) {
				Dragboard db = event.getDragboard();
				boolean success = isValidDragboardContent(db);

				if (success) {
					event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
				}

				event.consume();
			}
		});

		emptyProjectView.setOnDragEntered(new EventHandler<DragEvent>() {
			@Override
			public void handle(DragEvent event) {
				Dragboard db = event.getDragboard();
				boolean success = isValidDragboardContent(db);

				if (success) {
					emptyProjectView.getStyleClass().add(DRAG_ENTERED_PROJECT_STYLE_CLASS);
				}

				event.consume();
			}
		});

		emptyProjectView.setOnDragExited(new EventHandler<DragEvent>() {
			@Override
			public void handle(DragEvent event) {
				if (emptyProjectView.getStyleClass().contains(DRAG_ENTERED_PROJECT_STYLE_CLASS))
					emptyProjectView.getStyleClass().remove(DRAG_ENTERED_PROJECT_STYLE_CLASS);

				event.consume();
			}
		});

		emptyProjectView.setOnDragDropped(new EventHandler<DragEvent>() {
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
		});
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
}
