package application;

import javafx.application.Application;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class Main extends Application {

    public static void main(String[] args) {
        // Launch the JavaFX application
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        // Set the stage title
        primaryStage.setTitle("Medical EMR");

        // Create a vertical box (VBox) as the root node
        VBox root = new VBox(20);
        root.setStyle("-fx-background-color: #f4f4f4;");
        root.setPadding(new Insets(20));

        // Create a label for the title and customize its style
        Label titleLabel = new Label("Login");
        titleLabel.setStyle("-fx-font-size: 24; -fx-font-weight: bold;");
        VBox.setMargin(titleLabel, new Insets(15, 100, 20, 100));

        // Create a grid pane for arranging login components
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);

        // Center all elements within the grid
        ColumnConstraints columnConstraints = new ColumnConstraints();
        columnConstraints.setHalignment(HPos.CENTER);
        grid.getColumnConstraints().addAll(columnConstraints, columnConstraints);

        RowConstraints rowConstraints = new RowConstraints();
        rowConstraints.setValignment(VPos.CENTER);
        grid.getRowConstraints().addAll(rowConstraints, rowConstraints, rowConstraints, rowConstraints);

        // Create text fields, combo box, and login button
        TextField usernameField = new TextField();
        usernameField.setPromptText("Username");
        usernameField.setStyle("-fx-prompt-text-fill: derive(-fx-control-inner-background, -30%);");

        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Password");
        passwordField.setStyle("-fx-prompt-text-fill: derive(-fx-control-inner-background, -30%);");

        ComboBox<String> roleComboBox = new ComboBox<>();
        roleComboBox.getItems().addAll("Doctor", "Nurse", "Patient");
        roleComboBox.setPromptText("Select Role");

        Button loginButton = new Button("Login");
        loginButton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white;");

        // Add components to the grid
        grid.add(new Label("Username:"), 0, 0);
        grid.add(usernameField, 1, 0);
        grid.add(new Label("Password:"), 0, 1);
        grid.add(passwordField, 1, 1);
        grid.add(new Label("Role:"), 0, 2);
        grid.add(roleComboBox, 1, 2);
        grid.add(loginButton, 1, 3);

        // Add grid and title label to the root VBox
        root.getChildren().addAll(titleLabel, grid);

        // Create the main scene
        Scene scene = new Scene(root, 400, 250);
        primaryStage.setScene(scene);

        // Show the stage
        primaryStage.show();

        // Event handler for the login button
        loginButton.setOnAction(event -> {
            // Get user input
            String username = usernameField.getText();
            String password = passwordField.getText();
            String selectedRole = roleComboBox.getValue();

            // Validate login
            if (validateLogin(username, password, selectedRole)) {
                // Open the respective dashboard based on the role
                openDashboard(selectedRole);
                primaryStage.close();
            } else {
                // Show invalid credentials warning
                showInvalidCredentialsWarning();
            }
        });
    }

    // Basic validation method
    private boolean validateLogin(String username, String password, String selectedRole) {
        return !username.isEmpty() && !password.isEmpty() && selectedRole != null;
    }

    // Method to show invalid credentials warning
    private void showInvalidCredentialsWarning() {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Invalid Credentials");
        alert.setHeaderText(null);
        alert.setContentText("Invalid username or password. Please try again.");
        alert.showAndWait();
    }

    // Method to open the dashboard based on the selected role
    private void openDashboard(String selectedRole) {
        if("doctor".equalsIgnoreCase(selectedRole)) {
            // Validates and opens the doctorDashboard when logged in
            docDashboard dDash = new docDashboard();
            dDash.start(new Stage());
        } else if("nurse".equalsIgnoreCase(selectedRole)){
            // Validates and opens the nurseDashboard when logged in
            nurseDashboard nDash = new nurseDashboard();
            nDash.start(new Stage());
        } else if("patient".equalsIgnoreCase(selectedRole)) {
            // Validates and opens the patient portal when logged in
            patientPortal pPortal = new patientPortal();
            pPortal.start(new Stage());
        }
    }
}
