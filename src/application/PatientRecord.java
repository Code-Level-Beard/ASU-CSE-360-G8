package application;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ComboBox;
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

	//Method to update DB used in Patient Controller
	static public void updateWith(String userId, TextField firstName,
			TextField lastName, TextField dob,
			TextField address, TextField phone,
			TextField insurance, TextField pharmacy,
			TextField history, TextField immunizations,
			TextField medications, TextField allergies) {

		TextField[] textFields = new TextField[] {
				firstName, lastName, dob, address, phone, insurance,
				pharmacy, history, immunizations, medications, allergies };
		int numOfTextFields = textFields.length;
		int numOfEmptyFields = 0;

		for (TextField textField: textFields) {
			if (textField.getText().trim().isEmpty()) {
				numOfEmptyFields ++;
			}
		}
		if (numOfEmptyFields == numOfTextFields) {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("Form Invalid");
			alert.setHeaderText(null);
			alert.setContentText(
					"Please enter text into the field you wish to update.");
			alert.showAndWait();
			// if all textFields are empty just bail early
			return;
		}

		Connection connect;
		try {
			connect = DriverManager.getConnection("jdbc:sqlite:./MainDatabase.sqlite");
			PreparedStatement dbCon = connect.prepareStatement(
					"update PatientRecord set first_name = ?, last_name = ?, DOB = ?, address = ?, phone_number = ?, ins_id = ?, pharmacy = ?, health_history = ?, immunizations = ?, medications = ?, allergies = ? where patient_id = ?");
			PreparedStatement dbResS = connect.prepareStatement ("SELECT first_name, last_name, DOB, address, phone_number, ins_id, pharmacy, health_history, immunizations, medications, allergies FROM PatientRecord WHERE patient_id = ?");
			dbResS.setString(1, userId);
			ResultSet dbRes = dbResS.executeQuery();
			for (int i = 0; i < textFields.length; i++) {

				if(i == 7 || i == 8 || i == 9 || i == 10) {
					//Append data in DB with new values if textField is not empty
					if(textFields[i].getText().trim().isEmpty() == false) {
						dbCon.setString(i + 1, dbRes.getString(i + 1) + ", " + textFields[i].getText().trim());
					} 
					//TextField is empty, save current data back into DB
					else { 
						dbCon.setString(i + 1, dbRes.getString(i + 1));
					}
				}
				//TextField does not requiring appending
				else {
					//Overwrite data in DB with new values if textField is not empty
					if(textFields[i].getText().trim().isEmpty() == false) {
						dbCon.setString(i + 1, textFields[i].getText().trim());
					} 
					//TextField is empty, save current data back into DB
					else { 
						dbCon.setString(i + 1, dbRes.getString(i + 1));
					}
				}
			}
			dbResS.close();
			dbRes.close();
			dbCon.setString(textFields.length + 1, userId);
			dbCon.execute();
			dbCon.close();
			connect.close();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
	}

	//Overloaded method to updateDB in NurseController
	static public void updateWithNurse(String userId, TextField firstName,
			TextField lastName, TextField dob,
			TextField address, TextField phone,
			TextField insurance, TextField pharmacy,
			TextField history, TextField immunizations,
			TextField medications, TextField allergies, ComboBox<String> assignedDoctor) {

		TextField[] textFields = new TextField[] {
				firstName, lastName, dob, address, phone, insurance,
				pharmacy, history, immunizations, medications, allergies };
		int numOfTextFields = textFields.length + 1;
		int numOfEmptyFields = 0;

		if (assignedDoctor.getValue() == null) {
			numOfEmptyFields ++;
		}

		//Checks whether each TextField is empty, if true, increments a value
		for (TextField textField: textFields) {
			if (textField.getText().trim().isEmpty()) {
				numOfEmptyFields ++;
			}
		}

		//If all of the fields are empty, quit the method
		if (numOfEmptyFields == numOfTextFields) {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("Form Invalid");
			alert.setHeaderText(null);
			alert.setContentText(
					"Please enter text into the field you wish to update.");
			alert.showAndWait();
			return;
		}

		//At least one of the fields is not empty
		Connection connect;
		try {
			System.out.println("Try: " + userId);
			connect = DriverManager.getConnection("jdbc:sqlite:./MainDatabase.sqlite");
			PreparedStatement dbCon = connect.prepareStatement(
					"update PatientRecord set first_name = ?, last_name = ?, DOB = ?, address = ?, phone_number = ?, ins_id = ?, pharmacy = ?," + 
					"health_history = ?, immunizations = ?, medications = ?, allergies = ?, assigned_doctor = ? where patient_id = ?");
			PreparedStatement dbResS = connect.prepareStatement ("SELECT first_name, last_name, DOB, address, phone_number, ins_id, pharmacy," + 
					"health_history, immunizations, medications, allergies, assigned_doctor FROM PatientRecord WHERE patient_id = ?");
			dbResS.setString(1, userId);
			ResultSet dbRes = dbResS.executeQuery();
			for (int i = 0; i < textFields.length + 1; i++) {
				System.out.println("Trying " + i + " " + dbRes.getString(i + 1));
				//Data should be appended, not overwritten
				if(i == 7 || i == 8 || i == 9 || i == 10) {
					//Append data in DB with new values if textField is not empty
					if(textFields[i].getText().trim().isEmpty() == false) {
						dbCon.setString(i + 1, dbRes.getString(i + 1) + ", " + textFields[i].getText().trim());
					} 
					//TextField is empty, save current data back into DB
					else { 
						dbCon.setString(i + 1, dbRes.getString(i + 1));
					}
				}
				//Check for ComboBox
				else if (i == 11) {
					//ComboBox has a value, write it to DB
					if (assignedDoctor.getValue() != null) {
						dbCon.setString(i + 1, assignedDoctor.getValue().substring(assignedDoctor.getValue().length() - 6, assignedDoctor.getValue().length()));
					}
					//ComboBox does not have a value, save current data back into DB
					else {
						dbCon.setString(i + 1, dbRes.getString(i + 1));
					}
				}
				//TextField does not requiring appending
				else {
					//Overwrite data in DB with new values if textField is not empty
					if(textFields[i].getText().trim().isEmpty() == false) {
						System.out.println("Found Text at " + i);
						dbCon.setString(i + 1, textFields[i].getText().trim());
					} 
					//TextField is empty, save current data back into DB
					else { 
						dbCon.setString(i + 1, dbRes.getString(i + 1));
					}
				}
			}
			dbResS.close();
			dbRes.close();
			dbCon.setString(textFields.length + 2, userId);
			dbCon.execute();
			dbCon.close();
			connect.close();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
	}

	/* Method currently not required
  private static boolean isEmpty(TextField[] textFields) {
    for (TextField f : textFields) {
      if (f.getText().isEmpty() || f.getText().isBlank()) {
        return true;
      }
    }
    return false;
  } 
	 */
}
