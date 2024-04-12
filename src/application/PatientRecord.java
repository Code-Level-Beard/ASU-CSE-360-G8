package application;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
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
   * @param userId user id req for db fetch
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
  }

  static public void writeWith(String userId, TextField firstName,
      TextField lastName, TextField dob,
      TextField address, TextField phone,
      TextField insurance, TextField pharmacy,
      TextField history, TextField immunizations,
      TextField medications, TextField allergies) {
    Connection connect;
    try {
      connect = DriverManager.getConnection("jdbc:sqlite:./MainDatabase.sqlite");
      PreparedStatement userState = connect.prepareStatement(
          "Select patient_id FROM PatientRecord WHERE patient_id = ?");
      userState.setString(1, userId);
      ResultSet userSQL = userState.executeQuery();
      while (userSQL.next()) {
        userId = userSQL.getString("patient_id");
      }
      userSQL.close();
      userState.close();

      boolean isEmpty = isEmpty(new TextField[] {
          firstName, lastName, dob, address, phone, insurance, pharmacy,
          history, immunizations, medications, allergies });

      if (!isEmpty) {
        if (!dob.getText().isBlank() && !dob.getText().isEmpty()) {
          PreparedStatement setDOB = connect.prepareStatement(
              "UPDATE PatientRecord SET dob = ? WHERE patient_id = ?");
          setDOB.setString(1, dob.getText().trim());
          setDOB.setString(2, userId);
          setDOB.execute();
          setDOB.close();
        }
        if (!address.getText().isBlank() && !address.getText().isEmpty()) {
          PreparedStatement setADD = connect.prepareStatement(
              "UPDATE PatientRecord SET address = ? WHERE patient_id = ?");
          setADD.setString(1, address.getText().trim());
          setADD.setString(2, userId);
          setADD.execute();
          setADD.close();
        }

        if (!phone.getText().isBlank() && !phone.getText().isEmpty()) {
          PreparedStatement setPNUM = connect.prepareStatement(
              "UPDATE PatientRecord SET phone_number = ? WHERE patient_id = ?");
          setPNUM.setString(1, phone.getText().trim());
          setPNUM.setString(2, userId);
          setPNUM.execute();
          setPNUM.close();
        }
        if (!insurance.getText().isBlank() && !insurance.getText().isEmpty()) {
          PreparedStatement setIID = connect.prepareStatement(
              "UPDATE PatientRecord SET ins_id = ? WHERE patient_id = ?");
          setIID.setString(1, insurance.getText().trim());
          setIID.setString(2, userId);
          setIID.execute();
          setIID.close();
        }
        if (!immunizations.getText().isBlank() &&
            !immunizations.getText().isEmpty()) {
          PreparedStatement setIMM = connect.prepareStatement(
              "UPDATE PatientRecord SET immunizations = ? WHERE patient_id = ?");
          PreparedStatement getIMM = connect.prepareStatement(
              "SELECT immunizations FROM PatientRecord where patient_id = ?");
          getIMM.setString(1, userId);
          ResultSet currIMM = getIMM.executeQuery();
          while (currIMM.next()) {
            if (currIMM.getString("immunizations") == "N/A") {
              setIMM.setString(1, immunizations.getText().trim());
            } else {
              setIMM.setString(1, currIMM.getString("immunizations") + ", " +
                  immunizations.getText().trim());
            }
          }
          currIMM.close();
          getIMM.close();
          setIMM.setString(2, userId);
          setIMM.execute();
          setIMM.close();
        }
        if (!history.getText().isBlank() && !history.getText().isEmpty()) {
          PreparedStatement setHHST = connect.prepareStatement(
              "UPDATE PatientRecord SET health_history = ? WHERE patient_id = ?");
          PreparedStatement getHHST = connect.prepareStatement(
              "SELECT health_history FROM PatientRecord where patient_id = ?");
          getHHST.setString(1, userId);
          ResultSet currHHST = getHHST.executeQuery();
          while (currHHST.next()) {
            if (currHHST.getString("health_history") == "N/A") {
              setHHST.setString(1, history.getText().trim());
            } else {
              setHHST.setString(1, currHHST.getString("health_history") + ", " +
                  history.getText().trim());
            }
          }
          currHHST.close();
          getHHST.close();
          setHHST.setString(2, userId);
          setHHST.execute();
          setHHST.close();
        }
        if (!pharmacy.getText().isBlank() && !pharmacy.getText().isEmpty()) {
          PreparedStatement setPHM = connect.prepareStatement(
              "UPDATE PatientRecord SET pharmacy = ? WHERE patient_id = ?");
          setPHM.setString(1, pharmacy.getText().trim());
          setPHM.setString(2, userId);
          setPHM.execute();
          setPHM.close();
        }
        if (!medications.getText().isBlank() &&
            !medications.getText().isEmpty()) {
          PreparedStatement setMED = connect.prepareStatement(
              "UPDATE PatientRecord SET medications = ? WHERE patient_id = ?");
          PreparedStatement getMED = connect.prepareStatement(
              "SELECT medications FROM PatientRecord where patient_id = ?");
          getMED.setString(1, userId);
          ResultSet currMED = getMED.executeQuery();
          while (currMED.next()) {
            if (currMED.getString("medications") == "N/A") {
              setMED.setString(1, medications.getText().trim());
            } else {
              setMED.setString(1, currMED.getString("medications") + ", " +
                  medications.getText().trim());
            }
          }
          currMED.close();
          getMED.close();
          setMED.setString(2, userId);
          setMED.execute();
          setMED.close();
        }
        if (!allergies.getText().isBlank() && !allergies.getText().isEmpty()) {
          PreparedStatement setALG = connect.prepareStatement(
              "UPDATE PatientRecord SET allergies = ? WHERE patient_id = ?");
          PreparedStatement getALG = connect.prepareStatement(
              "SELECT allergies FROM PatientRecord where patient_id = ?");
          getALG.setString(1, userId);
          ResultSet currALG = getALG.executeQuery();
          while (currALG.next()) {
            if (currALG.getString("allergies") == "N/A") {
              setALG.setString(1, allergies.getText().trim());
            } else {
              setALG.setString(1, currALG.getString("allergies") + ", " +
                  allergies.getText().trim());
            }
          }
          currALG.close();
          getALG.close();
          setALG.setString(2, userId);
          setALG.execute();
          setALG.close();
        }
        if (!firstName.getText().isBlank() && !firstName.getText().isEmpty()) {
          PreparedStatement setFN = connect.prepareStatement(
              "UPDATE PatientRecord SET first_name = ? WHERE patient_id = ?");
          PreparedStatement setUFN = connect.prepareStatement(
              "UPDATE UserType SET first_name = ? WHERE user_id = ?");
          setUFN.setString(1, firstName.getText().trim());
          setUFN.setString(2, userId);
          setFN.setString(1, firstName.getText().trim());
          setFN.setString(2, userId);
          setFN.execute();
          setUFN.execute();
          setUFN.close();
          setFN.close();
        }
        if (!lastName.getText().isBlank() && !lastName.getText().isEmpty()) {
          PreparedStatement setLN = connect.prepareStatement(
              "UPDATE PatientRecord SET last_name = ? WHERE patient_id = ?");
          PreparedStatement setULN = connect.prepareStatement(
              "UPDATE UserType SET last_name = ? WHERE user_id = ?");
          setULN.setString(1, firstName.getText().trim());
          setULN.setString(2, userId);
          setLN.setString(1, lastName.getText().trim());
          setLN.setString(2, userId);
          setULN.execute();
          setLN.execute();
          setULN.close();
          setLN.close();
        }
      } else {
        Alert alert = new Alert(AlertType.WARNING);
        alert.setTitle("Form Invalid");
        alert.setHeaderText(null);
        alert.setContentText(
            "Please enter text into the field you wish to update.");
        alert.showAndWait();
      }
      connect.close();
    } catch (SQLException e1) {
      e1.printStackTrace();
    }
  }

  private static boolean isEmpty(TextField[] textFields) {
    for (TextField f : textFields) {
      if (f.getText().isEmpty() || f.getText().isBlank()) {
        return true;
      }
    }
    return false;
  }
}
