package application;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class PatientRecord {

  /**
   * Reads PatientRecord from db writes to GUI TextArea
   *
   * @param userId   id req for db fetch
   * @param textArea area to write to.
   */
  static public void readTo(String userId, TextArea textArea) {
    Connection connect;
    try {
      connect = DriverManager.getConnection("jdbc:sqlite:./MainDatabase.sqlite");
      PreparedStatement statement = connect.prepareStatement(
          "SELECT patient_id, first_name, last_name, address, "
              + "phone_number, ins_id, pharmacy, health_history,immunizations, "
              +
              "medications, allergies, assigned_doctor,DOB FROM PatientRecord WHERE patient_id = ?");
      statement.setString(1, userId);
      ResultSet dbRes = statement.executeQuery();
      while (dbRes.next()) {
        textArea.appendText("Name: " + dbRes.getString("first_name") + " " +
            dbRes.getString("last_name") + "\n\n");
        textArea.appendText("Date of Birth: " + dbRes.getString("DOB") +
            "\n\n");
        textArea.appendText("Address: " + dbRes.getString("address") + "\n\n");
        textArea.appendText("Phone Number: " + dbRes.getString("phone_number") +
            "\n\n");
        textArea.appendText("Insurance Provider: " + dbRes.getString("ins_id") +
            "\n\n");
        textArea.appendText("Pharmacy: " + dbRes.getString("pharmacy") +
            "\n\n");
        textArea.appendText(
            "Health History: " + dbRes.getString("health_history") + "\n\n");
        textArea.appendText(
            "Immunizations: " + dbRes.getString("immunizations") + "\n\n");
        textArea.appendText("Medications: " + dbRes.getString("medications") +
            "\n\n");
        textArea.appendText("Allergies: " + dbRes.getString("allergies") +
            "\n\n");
        textArea.appendText(
            "Assigned Doctor: " + dbRes.getString("assigned_doctor") + "\n\n");
      }
      dbRes.close();
      statement.close();
      connect.close();
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  /**
   * Reads PatientRecord from db writes to GUI TextFields
   *
   * @param userId    user id req for db fetch
   * @param name
   * @param DOB
   * @param address
   * @param phone
   * @param insurance
   * @param pharmacy
   */
  static public void readTo(String userId, TextField name, TextField DOB,
      TextField address, TextField phone,
      TextField insurance, TextField pharmacy) {
    Connection connect;
    try {
      connect = DriverManager.getConnection("jdbc:sqlite:./MainDatabase.sqlite");
      PreparedStatement statement = connect.prepareStatement(
          "SELECT * FROM PatientRecord WHERE patient_id = ?");
      statement.setString(1, userId);
      ResultSet dbRes = statement.executeQuery();
      name.setText(dbRes.getString("first_name") + " " +
          dbRes.getString("last_name"));
      DOB.setText(dbRes.getString("DOB"));
      address.setText(dbRes.getString("address"));
      phone.setText(dbRes.getString("phone_number"));
      insurance.setText(dbRes.getString("ins_id"));
      pharmacy.setText(dbRes.getString("pharmacy"));
      dbRes.close();
      statement.close();
      connect.close();
    } catch (SQLException e) {
      e.printStackTrace();
    }
  };
}
