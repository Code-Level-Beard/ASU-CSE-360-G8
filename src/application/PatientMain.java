
package application;

import application.PatientController;
import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class PatientMain extends Application {
  @Override
  public void start(Stage stage) {
    String activeUser = "JD0119";
    try {
      FXMLLoader loader = new FXMLLoader(getClass().getResource("Patient.fxml"));
      Parent root = loader.load();
      PatientController controller = loader.getController();
      controller.updateText(activeUser);
      controller.displayMessages(activeUser);
      controller.activeUser = activeUser;
      stage.setScene(new Scene(root));
      stage.show();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public static void main(String[] args) {
    launch(args);
  }
}
