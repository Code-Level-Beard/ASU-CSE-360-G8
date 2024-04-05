package application;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;


public class LoginController {
	public LoginModel loginModel = new LoginModel();

	@FXML
	private Button exitButton;

	@FXML
	private Label loginMessageLabel;

	@FXML
	private TextField usernameTextField;

	@FXML
	private PasswordField passwordTextField;

	@FXML
	private Button loginButton;

	public void exitButtonOnAction(javafx.event.ActionEvent e) {
		Stage stage = (Stage) exitButton.getScene().getWindow();
		stage.close();
	}

	@SuppressWarnings("unused")
	@FXML
	public void loginButtonAction(javafx.event.ActionEvent e) {
		try {
			Connection connect = DriverManager.getConnection("jdbc:sqlite:./MainDatabase.sqlite");
			PreparedStatement statement = connect.prepareStatement("SELECT user_id, password, user_type FROM Login WHERE user_id = ?");
			String username = usernameTextField.getText();
			String password = passwordTextField.getText();
			
			statement.setString(1, username);
			
			ResultSet resultSet = statement.executeQuery();

			if (resultSet.next()) {
				
				String sqlPWD = resultSet.getString("password");
				if (password.equals(sqlPWD)) {
					String sqlType = resultSet.getString("user_type");
					if (sqlType.equals("Physician")) {
						openPhysicianPanel();
					} else if (sqlType.equals("Nurse")) {
						openNursePanel();
					} else if (sqlType.equals("Patient")) {
						openPatientPanel();
					}
				} else {
					// Handle invalid username format or unrecognized user type
					Alert alert = new Alert(AlertType.WARNING);
					alert.setTitle("Invalid Login");
					alert.setHeaderText(null);
					alert.setContentText("Incorrect username or password");
					alert.showAndWait();
				}
			} else {
				// Handle invalid username format or unrecognized user type
				Alert alert = new Alert(AlertType.WARNING);
				alert.setTitle("Invalid Login");
				alert.setHeaderText(null);
				alert.setContentText("Incorrect username or password");
				alert.showAndWait();
			}
			resultSet.close();
			statement.close();
			connect.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	// Method to open PhysicianPanel
	private void openPhysicianPanel() {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("Doctor.fxml"));
			Parent root = loader.load();

			Stage stage = new Stage();
			stage.setScene(new Scene(root));
			stage.show();

			// Close the current login window
			Stage currentStage = (Stage) loginButton.getScene().getWindow();
			currentStage.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void openNursePanel() {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("Nurse.fxml"));
			Parent root = loader.load();

			Stage stage = new Stage();
			stage.setScene(new Scene(root));
			stage.show();

			// Close the current login window
			Stage currentStage = (Stage) loginButton.getScene().getWindow();
			currentStage.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void openPatientPanel() {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("Patient.fxml"));
			Parent root = loader.load();

			Stage stage = new Stage();
			stage.setScene(new Scene(root));
			stage.show();

			// Close the current login window
			Stage currentStage = (Stage) loginButton.getScene().getWindow();
			currentStage.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
