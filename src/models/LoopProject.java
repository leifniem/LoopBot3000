package models;

import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class LoopProject {
	private StringProperty name = new SimpleStringProperty();
	private ObservableList<Loop> loops = FXCollections.<Loop>observableArrayList();
	
	public LoopProject(){
		
	}
	
	public StringProperty nameProperty(){
		return name;
	}
	
	public ObservableList<Loop> getLoops(){
		return loops;
	}
	
	public void setSoloLoop(int number){
		disableSoloProperties();
		Loop loop = loops.get(number);
		loop.isSoloProperty().set(true);
	}

	private void disableSoloProperties() {
		for(Loop loop : loops){
			loop.isSoloProperty().set(false);
		}
	}
	
	private Loop getSoloLoop(){
		Loop soloLoop = loops.filtered(x -> x.isSoloProperty().get()).get(0);
		return soloLoop;
	}
}
