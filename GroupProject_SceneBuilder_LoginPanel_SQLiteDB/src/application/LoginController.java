package application;

import javafx.fxml.FXML;
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
            String username = usernameTextField.getText();
            String password = passwordTextField.getText();

            if (username.startsWith("MD")) {
                openPhysicianPanel();
            } else if (username.startsWith("NR")) {
                openNursePanel();
            } else if (username.startsWith("PT")) {
                openPatientPanel();
            } else {
                // Handle invalid username format or unrecognized user type
                Alert alert = new Alert(AlertType.WARNING);
                alert.setTitle("Invalid Login");
                alert.setHeaderText(null);
                alert.setContentText("Incorrect username or password");
                alert.showAndWait();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            
        }
    }

    // Method to open PhysicianPanel
    private void openPhysicianPanel() {
        PhysicianPanel physicianPanel = new PhysicianPanel();
        Stage stage = new Stage();
        try {
            physicianPanel.start(stage);
            // Close the current login window
            Stage currentStage = (Stage) loginButton.getScene().getWindow();
            currentStage.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private void openNursePanel() {
        NursePanel nursePanel = new NursePanel();
        Stage stage = new Stage();
        try {
            nursePanel.start(stage);
            // Close the current login window
            Stage currentStage = (Stage) loginButton.getScene().getWindow();
            currentStage.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private void openPatientPanel() {
        PatientPanel  patientPanel = new PatientPanel();
        Stage stage = new Stage();
        try {
            patientPanel.start(stage);
            // Close the current login window
            Stage currentStage = (Stage) loginButton.getScene().getWindow();
            currentStage.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
