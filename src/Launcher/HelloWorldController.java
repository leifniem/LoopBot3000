package Launcher;

import javafx.beans.binding.Bindings;
import javafx.beans.binding.StringBinding;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class HelloWorldController {
	@FXML
	private Label helloWorldLabel;
	@FXML
	private Button doSomethingButton;
	@FXML
	private Label codeBindedLabel;

	public HelloWorldController() {
	}

	@FXML
	private void initialize() {
		bindCodeBindedLabel();
	}

	private void bindCodeBindedLabel() {
		StringProperty codeBindedStringProperty = new SimpleStringProperty();
		codeBindedLabel.textProperty().bind(codeBindedStringProperty);
		codeBindedStringProperty.set("Neuer Wert!");

		//Ausführliches String Binding -> Wenn sich der Wert von codeBindedStringProperty ändert, dann gebe dessen Wert in UpperCase zurück
		StringBinding codeBindedStringBinding = Bindings
				.createStringBinding(() -> codeBindedStringProperty.get().toUpperCase(), codeBindedStringProperty);
		//Dieses Binding kann dann genau so wie eine Property gebinded werden!
		//codeBindedLabel.textProperty().bind(codeBindedStringBinding);
	}

	public void doSomethingFunny() {
		System.out.println("Something funny");
	}
}