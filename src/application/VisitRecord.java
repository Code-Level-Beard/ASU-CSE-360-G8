package application;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class VisitRecord {

  public static void newRecord(String patientId, String doctorId,
      TextField date, TextField height,
      TextField weight, TextField temperature,
      TextField bloodPressure, TextArea immunizations,
      TextArea allergies, TextArea notes) {

    try {
      Connection connect = DriverManager.getConnection("jdbc:sqlite:./MainDatabase.sqlite");
      // Prepare a SQL INSERT statement for Visit table
      PreparedStatement insertVisit = connect.prepareStatement(
          "INSERT INTO Visit (patient_id, doctor_id, date, height, weight, temperature, blood_pressure, immunization, allergies, notes,completed, prescription, visit_diag) "
              + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");

      // Set parameters for the prepared statement
      insertVisit.setString(1, patientId); // Using the current patient ID
      insertVisit.setString(2, doctorId); // Using the current doctor ID
      insertVisit.setString(3, date.getText().trim());
      insertVisit.setString(4, height.getText().trim());
      insertVisit.setString(5, weight.getText().trim());
      insertVisit.setString(6, temperature.getText().trim());
      insertVisit.setString(7, bloodPressure.getText().trim());
      insertVisit.setString(8, immunizations.getText().trim());
      insertVisit.setString(9, allergies.getText().trim());
      insertVisit.setString(10, notes.getText().trim());
      insertVisit.setString(11, "NC");
      insertVisit.setString(12, "UNK");
      insertVisit.setString(13, "UNK");

      // Execute the INSERT statement into DB
      insertVisit.executeUpdate();

      // Closes out insert statements / closes DB connection
      insertVisit.close();
      connect.close();

    } catch (SQLException ex) {
      ex.printStackTrace(); // Handle exceptions appropriately
    }
  }
}
