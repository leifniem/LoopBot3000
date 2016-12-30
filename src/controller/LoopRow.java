package controller;

import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.*;

public class LoopRow extends HBox{
	public LoopRow(){
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/views/LoopRowView.fxml"));
		fxmlLoader.setRoot(this);
		fxmlLoader.setController(this);
		
		try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
	}
}
