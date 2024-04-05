package application;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.fxml.FXMLLoader;
import java.io.IOException;


public class PatientPanel extends Application {
    @Override
    public void start(Stage primaryStage) {
        try {
        	Parent root = FXMLLoader.load(getClass().getResource("/application/Patient.fxml"));            
        	Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch(IOException error) {
            error.printStackTrace();
        }
    }
    
	public static void main(String[] args) {
		launch(args);
	}
}