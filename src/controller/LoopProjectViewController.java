package controller;

import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.binding.IntegerBinding;
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
		
		BooleanBinding noLoops = Bindings.createBooleanBinding(() -> loopProject.getLoops().size() == 0, loopProject.getLoops());
		emptyProjectView.visibleProperty().bind(noLoops);
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
}
