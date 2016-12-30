package controller;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import models.Loop;

public class LoopRow extends HBox {
	@FXML
	private Label nameLabel;
	@FXML
	private Button chooseFileButton;
	@FXML
	private Button recordButton;
	@FXML
	private Button soloButton;
	@FXML
	private Button muteButton;
	
	private Loop loop;
	
	public LoopRow(Loop loop) {
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/views/LoopRowView.fxml"));
		fxmlLoader.setRoot(this);
		fxmlLoader.setController(this);

		try {
			fxmlLoader.load();
		} catch (IOException exception) {
			throw new RuntimeException(exception);
		}
	}
	
	public Loop getLoop(){
		return loop;
	}
}
