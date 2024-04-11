package application;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Tab;
import javafx.scene.control.TextArea;

public class PhysicianController {
	String activeUser;
	
	String selectedPatient;
	
	@FXML
	ComboBox<String> pLComboBox;
	
	@FXML
	Button selPatient;
	
	@FXML
	TextArea patientRecord;
	
	@FXML
	private Tab patientListTab;
	
	@FXML
	private Tab messageTab;
	
	@FXML
	private Tab patientRecordTab;
	
	@FXML
	private Tab prevVisitTab;
	
	@FXML
	private Tab currVisitTab;
	
	public void updateText() {
		Connection connect;
		try {
			connect = DriverManager.getConnection("jdbc:sqlite:./MainDatabase.sqlite");
			PreparedStatement statement = connect.prepareStatement("SELECT patient_id, first_name, last_name, address, "
					+ "phone_number, ins_id, pharmacy, health_history,immunizations, "
					+ "medications, allergies, assigned_doctor,DOB FROM PatientRecord WHERE patient_id = ?");
			statement.setString(1, selectedPatient);
			ResultSet resultSet = statement.executeQuery();
			while (resultSet.next()) {
				patientRecord.appendText("Name: " + resultSet.getString("first_name") + " " + resultSet.getString("last_name")+"\n\n");
				patientRecord.appendText("Date of Birth: " + resultSet.getString("DOB") + "\n\n");
				patientRecord.appendText("Address: " + resultSet.getString("address") + "\n\n");
				patientRecord.appendText("Phone Number: " + resultSet.getString("phone_number") + "\n\n");
				patientRecord.appendText("Insurance Provider: " + resultSet.getString("ins_id") + "\n\n");
				patientRecord.appendText("Pharmacy: " + resultSet.getString("pharmacy") + "\n\n");
				patientRecord.appendText("Health History: " + resultSet.getString("health_history") + "\n\n");
				patientRecord.appendText("Immunizations: " + resultSet.getString("immunizations") + "\n\n");
				patientRecord.appendText("Medications: " + resultSet.getString("medications") + "\n\n");
				patientRecord.appendText("Allergies: " + resultSet.getString("allergies") + "\n\n");
				patientRecord.appendText("Assigned Doctor: " + resultSet.getString("assigned_doctor") + "\n\n");
			}
			resultSet.close();
			statement.close();
			connect.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void genPLPatientComboBox() {
		pLComboBox.getItems().clear();
		Connection connect;
		try {
			connect = DriverManager.getConnection("jdbc:sqlite:./MainDatabase.sqlite");
			PreparedStatement patients = connect.prepareStatement("SELECT first_name, last_name, patient_id FROM PatientRecord WHERE assigned_doctor = ?");
			patients.setString(1, activeUser);
			ResultSet patientList = patients.executeQuery();
			while (patientList.next()) {
				pLComboBox.getItems().add(patientList.getString("first_name") + " " + patientList.getString("last_name") + " Patient ID: " + patientList.getString("patient_id"));
			}
			patientList.close();
			patients.close();
			connect.close();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	
	@FXML
	public void getSelectedPatient(javafx.event.ActionEvent e) {
		selectedPatient = pLComboBox.getValue().substring(pLComboBox.getValue().length()-6, pLComboBox.getValue().length());
	}
}

