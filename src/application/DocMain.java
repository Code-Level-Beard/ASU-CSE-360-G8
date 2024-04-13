
package application;

import application.PhysicianController;
import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class DocMain extends Application {
  public static void main(String[] args) {
    launch(args);
  }

  @Override
  public void start(Stage stage) {
    String activeUser = "MD1234";
    try {
      FXMLLoader loader = new FXMLLoader(getClass().getResource("Doctor.fxml"));
      Parent root = loader.load();
      PhysicianController controller = loader.getController();
      controller.displayMessages(activeUser);
      controller.activeUser = activeUser;
      controller.genPLPatientComboBox();
      stage.setScene(new Scene(root));
      stage.show();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
