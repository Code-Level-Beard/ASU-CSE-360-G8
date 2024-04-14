package application;

import javafx.scene.control.TextField;

public class TextFieldController {
	
	public static void lockField (TextField textField) {
		textField.setText("Not Required");
		textField.setEditable(false);
	}
	
	public static void unlockField (TextField textField) {
		textField.clear();
		textField.setEditable(true);
	}

}
