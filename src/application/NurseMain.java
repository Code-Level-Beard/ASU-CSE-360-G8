
package application;

import application.NurseController;
import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class NurseMain extends Application {
  public static void main(String[] args) {
    launch(args);
  }

  @Override
  public void start(Stage stage) {
    try {
      FXMLLoader loader = new FXMLLoader(getClass().getResource("Nurse.fxml"));
      Parent root = loader.load();
      NurseController controller = loader.getController();
      controller.genNPComboBox();
      controller.genPLComboBox();
      controller.displayMessages("JD0119");
      controller.messageSelect(controller.activeUser);
      stage.setScene(new Scene(root));
      stage.show();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
