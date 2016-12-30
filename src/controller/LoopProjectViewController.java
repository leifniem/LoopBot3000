package controller;

import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.layout.*;
import models.Loop;
import models.LoopProject;

public class LoopProjectViewController {
	@FXML
	private VBox loopRowContainer;

	private LoopProject loopProject;
	private ObservableList<LoopRow> loopRows = FXCollections.<LoopRow> observableArrayList();

	public LoopProjectViewController() {

	}

	@FXML
	private void initialize() {
		//createDummyLoopRows();	
	}

	private void createDummyLoopRows() {
		loopRowContainer.getChildren().add(new LoopRow(new Loop("test1", 4, 4)));
		loopRowContainer.getChildren().add(new LoopRow(new Loop("test1", 4, 4)));
		loopRowContainer.getChildren().add(new LoopRow(new Loop("test1", 4, 4)));
		loopRowContainer.getChildren().add(new LoopRow(new Loop("test1", 4, 4)));
	}

	public void setLoopProject(LoopProject loopProject) {
		this.loopProject = loopProject;
	
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
}
