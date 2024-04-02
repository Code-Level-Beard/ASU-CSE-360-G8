package application;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
//import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;
import javafx.stage.Stage;

public class LoginController {
	
		@FXML
		private Button exitButton;
		
		@FXML
		private Label loginMessageLabel;
		
		@FXML
		private TextField usernameTextField;
		
		@FXML
		private PasswordField passwordTextField;
		
	
		
		
		
		
		public void exitButtonOnAction(ActionEvent e) {
			Stage stage = (Stage) exitButton.getScene().getWindow();
			stage.close();
		
		}
		
	
	
		
		
	
		
	}



