package application;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Random;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Tab;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;

public class NurseController {
	String activeUser;
	
	String selectedPatient;
	
	String selectedDoctor;
	
	String newPatientID;

	@FXML
	private TextArea currRecord;

	@FXML
	private TextField firstName;

	@FXML
	private TextField lastName;

	@FXML
	private TextField dob;

	@FXML
	private TextField address;

	@FXML
	private TextField pNum;

	@FXML
	private TextField insID;

	@FXML
	private TextField pharm;

	@FXML
	private TextField hHistory;

	@FXML
	private TextField immuni;

	@FXML
	private TextField med;

	@FXML
	private TextField allergies;
	@FXML
	private TextArea selectedPatientRecord;

	@FXML 
	private Button newPatient;
	
	@FXML
	private ComboBox<String> selDoctor;
	
	@FXML
	private ComboBox<String> selPLDoctor;
	
	@FXML
	private ComboBox<String> selPLPatient;
	
	@FXML
	private Tab newPatientTab;
	@FXML
	private Tab patientListTab;
	@FXML
	private Tab messageTab;
	@FXML
	private Tab patientRecordTab;
	@FXML
	private Tab prevVisitTab;
	@FXML
	private Tab newVisitTab;
	@FXML
	private Button selectPatient;
	
	
	@FXML
	public void newPatient(javafx.event.ActionEvent e) {
		Connection connect;
		try {
			connect = DriverManager.getConnection("jdbc:sqlite:./MainDatabase.sqlite");
			PreparedStatement newPatientStatement = connect.prepareStatement("INSERT INTO PatientRecord (first_name, last_name, address," +
					"phone_number, ins_id, pharmacy, health_history,immunizations,"
					+" medications, allergies, assigned_doctor,DOB,patient_id) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?)");
			if (!((dob.getText().isBlank() && dob.getText().isEmpty()) && (address.getText().isBlank() && address.getText().isEmpty()) && (pNum.getText().isBlank() && pNum.getText().isEmpty())
					&& (insID.getText().isBlank() && insID.getText().isEmpty()) && (immuni.getText().isBlank() && immuni.getText().isEmpty()) && (hHistory.getText().isBlank() && hHistory.getText().isEmpty())
					&& (med.getText().isBlank() && med.getText().isEmpty()) && (allergies.getText().isBlank() && allergies.getText().isEmpty()) && (firstName.getText().isBlank() && firstName.getText().isEmpty())
					&& (lastName.getText().isBlank() && lastName.getText().isEmpty()))) {
				newPatientStatement.setString(1, firstName.getText().trim());
				newPatientStatement.setString(2, lastName.getText().trim());
				newPatientStatement.setString(3, address.getText().trim());
				newPatientStatement.setString(4, pNum.getText().trim());
				newPatientStatement.setString(5, insID.getText().trim());
				newPatientStatement.setString(6, pharm.getText().trim());
				newPatientStatement.setString(7, hHistory.getText().trim());
				newPatientStatement.setString(8, immuni.getText().trim());
				newPatientStatement.setString(9, med.getText().trim());
				newPatientStatement.setString(10, allergies.getText().trim() );
				newPatientStatement.setString(11, selDoctor.getValue());
				newPatientStatement.setString(12, dob.getText().trim());
				newPatientStatement.setString(13, genPatientID(firstName.getText().trim(),lastName.getText().trim()));
				
				PreparedStatement newPatientUser = connect.prepareStatement("INSERT INTO UserType (user_id, user_type, first_name, last_name) VALUES (?,?,?,?)");
				newPatientUser.setString(1, newPatientID);
				newPatientUser.setString(2, "Patient");
				newPatientUser.setString(3, firstName.getText().trim());
				newPatientUser.setString(4, lastName.getText().trim());
				
				PreparedStatement newPatientLogin = connect.prepareStatement("INSERT INTO Login (user_id, user_type, password) VALUES (?,?,?)");
				newPatientLogin.setString(1, newPatientID);
				newPatientLogin.setString(2, "Patient");
				newPatientLogin.setString(3, newPatientID);
				
				Alert alert = new Alert(AlertType.WARNING);
				alert.setTitle("Share Username and Password with Patient");
				alert.setHeaderText(null);
				alert.setContentText("Please inform the patient of their username and password\nUsername: " + newPatientID + "\nUsername: " + newPatientID);
				alert.showAndWait();
				
				newPatientUser.executeUpdate();
				newPatientLogin.executeUpdate();
				newPatientStatement.executeUpdate();
				newPatientLogin.close();
				newPatientUser.close();
				newPatientStatement.close();
				connect.close();
				firstName.clear();
				lastName.clear();
				address.clear();
				pNum.clear();
				insID.clear();
				pharm.clear();
				hHistory.clear();
				immuni.clear();
				med.clear();
				allergies.clear();
				dob.clear();
			} else {
				Alert alert = new Alert(AlertType.WARNING);
				alert.setTitle("No Data Entered");
				alert.setHeaderText(null);
				alert.setContentText("Please enter text into each field. If there is nothing to enter, enter N/A");
				alert.showAndWait();
			}
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	
	public String genPatientID(String fName, String lName) {
		Random ranInt = new Random();
		int randomInt = ranInt.nextInt(9000);
		randomInt += 1000;
		
		newPatientID = (fName.substring(0, 1) + lName.substring(0, 1) + randomInt);
		
		return newPatientID;
	}
	
	public void genNPComboBox() {
		Connection connect;
		try {
			connect = DriverManager.getConnection("jdbc:sqlite:./MainDatabase.sqlite");
			PreparedStatement doctors = connect.prepareStatement("SELECT first_name, last_name FROM UserType WHERE user_type = ?");
			doctors.setString(1, "Physician");
			ResultSet doctorList = doctors.executeQuery();
			while (doctorList.next()) {
				selDoctor.getItems().add(doctorList.getString("first_name") + " " +doctorList.getString("last_name"));
			}
			doctorList.close();
			doctors.close();
			connect.close();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	
	public void genPLComboBox() {
		Connection connect;
		try {
			connect = DriverManager.getConnection("jdbc:sqlite:./MainDatabase.sqlite");
			PreparedStatement doctors = connect.prepareStatement("SELECT first_name, last_name FROM UserType WHERE user_type = ?");
			doctors.setString(1, "Physician");
			ResultSet doctorList = doctors.executeQuery();
			while (doctorList.next()) {
				selPLDoctor.getItems().add(doctorList.getString("first_name") + " " +doctorList.getString("last_name"));
			}
			doctorList.close();
			doctors.close();
			connect.close();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	
	@FXML
	public void getDoctor(javafx.event.ActionEvent e) {
		selectedDoctor = selPLDoctor.getValue();
		genPLPatientComboBox();
	}
	
	@FXML
	public void getSelectedPatient(javafx.event.ActionEvent e) {
		selectedPatient = selPLPatient.getValue().substring(selPLPatient.getValue().length()-6, selPLPatient.getValue().length());
	}
	
	public void genPLPatientComboBox() {
		Connection connect;
		try {
			connect = DriverManager.getConnection("jdbc:sqlite:./MainDatabase.sqlite");
			PreparedStatement patients = connect.prepareStatement("SELECT first_name, last_name, patient_id FROM PatientRecord WHERE assigned_doctor = ?");
			patients.setString(1, selectedDoctor);
			ResultSet patientList = patients.executeQuery();
			while (patientList.next()) {
				selPLPatient.getItems().add(patientList.getString("first_name") + " " + patientList.getString("last_name") + " Patient ID: " + patientList.getString("patient_id"));
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
	public void patientRecordSelected(javafx.event.ActionEvent e) {
		updatePatientRecordText();
	}
	public void updatePatientRecordText() {
		Connection connect;
		try {
			connect = DriverManager.getConnection("jdbc:sqlite:./MainDatabase.sqlite");
			PreparedStatement statement = connect.prepareStatement("SELECT patient_id, first_name, last_name, address, "
					+ "phone_number, ins_id, pharmacy, health_history,immunizations, "
					+ "medications, allergies, assigned_doctor,DOB FROM PatientRecord WHERE patient_id = ?");
			statement.setString(1, selectedPatient);
			ResultSet resultSet = statement.executeQuery();
			while (resultSet.next()) {
				currRecord.appendText("Name: " + resultSet.getString("first_name") + " " + resultSet.getString("last_name")+"\n\n");
				currRecord.appendText("Date of Birth: " + resultSet.getString("DOB") + "\n\n");
				currRecord.appendText("Address: " + resultSet.getString("address") + "\n\n");
				currRecord.appendText("Phone Number: " + resultSet.getString("phone_number") + "\n\n");
				currRecord.appendText("Insurance Provider: " + resultSet.getString("ins_id") + "\n\n");
				currRecord.appendText("Pharmacy: " + resultSet.getString("pharmacy") + "\n\n");
				currRecord.appendText("Health History: " + resultSet.getString("health_history") + "\n\n");
				currRecord.appendText("Immunizations: " + resultSet.getString("immunizations") + "\n\n");
				currRecord.appendText("Medications: " + resultSet.getString("medications") + "\n\n");
				currRecord.appendText("Allergies: " + resultSet.getString("allergies") + "\n\n");
				currRecord.appendText("Assigned Doctor: " + resultSet.getString("assigned_doctor") + "\n\n");
			}
			resultSet.close();
			statement.close();
			connect.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
