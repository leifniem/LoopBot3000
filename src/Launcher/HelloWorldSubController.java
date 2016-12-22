package Launcher;

import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class HelloWorldSubController {
	@FXML
	private Button subViewButton;
	
	public HelloWorldSubController(){
		
	}
	
	public void doSomethingSub(){
		System.out.println("Do something sub");
	}
}
