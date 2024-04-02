package application;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class Main extends Application {

	private static Scene scene;

	public static void setRoot(String fxmlFileName) {
		try {
			Main.scene.setRoot(
					FXMLLoader.load(Main.class.getClass().getResource(fxmlFileName)));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void start(Stage primaryStage) {
		try {
		BorderPane root = (BorderPane) FXMLLoader.load(getClass().getResource("Login.fxml"));
		scene = new Scene(root, 550, 400);
		scene.getStylesheets().add(
				getClass().getResource("application.css").toExternalForm());
		primaryStage.setScene(scene);
		primaryStage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		launch(args);
	}
}
