package controller;

import javafx.collections.ObservableList;
import javafx.scene.Node;

public class StyleHelper {
	public static void applyStyleClass(boolean check, Node node, String styleClassName) {
		ObservableList<String> styleClass = node.getStyleClass();
		if(!check){
			if(styleClass.contains(styleClassName))
				styleClass.remove(styleClassName);
		} else {
			styleClass.add(styleClassName);
		}
	}
}
