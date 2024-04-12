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
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Tab;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

public class NurseController {
	String activeUser;
	
	String selectedPatient;
	
	String selectedDoctor;
	
	String newPatientID;

	@FXML private Button newPatient;
	@FXML private Button selectPatient;
	@FXML private ComboBox<String> selDoctor;
	@FXML private ComboBox<String> selPLDoctor;
	@FXML private ComboBox<String> selPLPatient;
	@FXML private Tab messageTab;
	@FXML private Tab newPatientTab;
	@FXML private Tab newVisitTab;
	@FXML private Tab patientListTab;
	@FXML private Tab patientRecordTab;
	@FXML private Tab prevVisitTab;
	@FXML private TextArea currRecord;
	@FXML private TextArea selectedPatientRecord;
	@FXML private TextField address;
	@FXML private TextField addressUpdate;
	@FXML private TextField allergies;
	@FXML private TextField allergiesUpdate;
	@FXML private TextField dob;
	@FXML private TextField dobUpdate;
	@FXML private TextField firstName;
	@FXML private TextField firstNameUpdate;
	@FXML private TextField hHistory;
	@FXML private TextField hHistoryUpdate;
	@FXML private TextField immuni;
	@FXML private TextField immuniUpdate;
	@FXML private TextField insID;
	@FXML private TextField insIDUpdate;
	@FXML private TextField lastName;
	@FXML private TextField lastNameUpdate;
	@FXML private TextField med;
	@FXML private TextField medUpdate;
	@FXML private TextField pNum;
	@FXML private TextField pNumUpdate;
	@FXML private TextField pharm;
	@FXML private TextField pharmUpdate;
	@FXML private TextFlow messageText;
	@FXML private TextFlow messageThreadArea;
	
	@FXML private ComboBox<String> assignedDoctorUpdate;
	
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
				newPatientStatement.setString(11, selDoctor.getValue().substring(selDoctor.getValue().length()-6,selDoctor.getValue().length()));
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
			PreparedStatement doctors = connect.prepareStatement("SELECT first_name, last_name,user_id FROM UserType WHERE user_type = ?");
			doctors.setString(1, "Physician");
			ResultSet doctorList = doctors.executeQuery();
			while (doctorList.next()) {
				selDoctor.getItems().add(doctorList.getString("first_name") + " " +doctorList.getString("last_name") + " ID: " + doctorList.getString("user_id"));
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
			PreparedStatement doctors = connect.prepareStatement("SELECT first_name, last_name,user_id FROM UserType WHERE user_type = ?");
			doctors.setString(1, "Physician");
			ResultSet doctorList = doctors.executeQuery();
			while (doctorList.next()) {
				selPLDoctor.getItems().add(doctorList.getString("first_name") + " " +doctorList.getString("last_name") + " ID: " + doctorList.getString("user_id"));
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
		selectedDoctor = selPLDoctor.getValue().substring(selPLDoctor.getValue().length() - 6, selPLDoctor.getValue().length());
		genPLPatientComboBox();
	}
	
	@FXML
	public void getSelectedPatient(javafx.event.ActionEvent e) {
		selectedPatient = selPLPatient.getValue().substring(selPLPatient.getValue().length()-6, selPLPatient.getValue().length());
	}
	
	public void genPLPatientComboBox() {
		selPLPatient.getItems().clear();
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
	
	public void patientRecordSelected() {
		updatePatientRecordText();
		genADUComboBox();
	}
	
	public void updatePatientRecordText() {
		currRecord.clear();
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
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	
	public void displayMessages(String user) {
		messageText.getChildren().clear();
		Connection connect;
		//composeMessage.setText("test");
		try {
			connect = DriverManager.getConnection("jdbc:sqlite:./MainDatabase.sqlite");
			PreparedStatement statement = connect.prepareStatement("SELECT message_id, sender, content FROM Message WHERE patient_id = ?");
			statement.setString(1, user);
			ResultSet resultSet = statement.executeQuery();
			
			while(resultSet.next()) {
				Text sender = new Text();
				Text content = new Text();
				sender.setText(resultSet.getString("sender") + "\n");
				sender.setFont(Font.font("Verdana", FontWeight.BOLD, 14));
				content.setText(resultSet.getString("content") + "\n\n\n");
				content.setFont(Font.font("Verdana", FontWeight.NORMAL, 12));
				messageText.getChildren().addAll(sender, content);
				//messageText.getChildren().addAll(new Text(resultSet.getString("sender") + "\n" + "\n" + resultSet.getString("content") + "\n" + "\n" + "\n" + "\n"));
				
			}
			resultSet.close();
			statement.close();
			connect.close();
		} catch(SQLException e) {
			// TODO error message
			e.printStackTrace();
		}
	} 
	
public void messageSelect() {
	Connection connect;
	//composeMessage.setText("test");
	try {
		
		connect = DriverManager.getConnection("jdbc:sqlite:./MainDatabase.sqlite");
		PreparedStatement statement = connect.prepareStatement("SELECT patient_id, MAX(message_id), sender, header FROM Message GROUP BY patient_id");
		ResultSet resultSet = statement.executeQuery();
		
		while(resultSet.next()) {
			
			Hyperlink sender = new Hyperlink();
			Hyperlink unread = new Hyperlink();
			Text	  spacer = new Text("\n");
			String    patient = resultSet.getString("patient_id");
			sender.setText(resultSet.getString("sender") + "\n\n\n");
			sender.setFont(Font.font("Verdana", FontWeight.BOLD, 14));
			sender.setFocusTraversable(false);
			sender.setOnAction(e -> {
					displayMessages(patient);
			});
			if(resultSet.getString("header").equals("new")) {
				unread.setText("NEW");
				unread.setFont(Font.font("Verdana", FontWeight.BOLD, 12));
				unread.setFocusTraversable(false);
				messageThreadArea.getChildren().addAll(unread, sender, spacer);
			}
			else {
			messageThreadArea.getChildren().addAll(sender, spacer);
			}
			//messageText.getChildren().addAll(new Text(resultSet.getString("sender") + "\n" + "\n" + resultSet.getString("content") + "\n" + "\n" + "\n" + "\n"));
			
		}
		resultSet.close();
		statement.close();
		connect.close();
	} catch(SQLException e) {
		// TODO error message
		e.printStackTrace();
	}
}
	@FXML
	public void updateDB(javafx.event.ActionEvent e) {
		Connection connect;
		try {
			connect = DriverManager.getConnection("jdbc:sqlite:./MainDatabase.sqlite");
			if (!((dobUpdate.getText().isBlank() && dobUpdate.getText().isEmpty()) && (addressUpdate.getText().isBlank() && addressUpdate.getText().isEmpty()) && (pNumUpdate.getText().isBlank() && pNumUpdate.getText().isEmpty())
					&& (insIDUpdate.getText().isBlank() && insIDUpdate.getText().isEmpty()) && (immuniUpdate.getText().isBlank() && immuniUpdate.getText().isEmpty()) && (hHistoryUpdate.getText().isBlank() && hHistoryUpdate.getText().isEmpty())
					&& (medUpdate.getText().isBlank() && medUpdate.getText().isEmpty()) && (allergiesUpdate.getText().isBlank() && allergiesUpdate.getText().isEmpty()) && (firstNameUpdate.getText().isBlank() && firstNameUpdate.getText().isEmpty())
					&& (lastNameUpdate.getText().isBlank() && lastNameUpdate.getText().isEmpty()) && (assignedDoctorUpdate.getValue() == null))) {
				if (!dobUpdate.getText().isBlank() && !dobUpdate.getText().isEmpty()) {
					PreparedStatement setDOB = connect.prepareStatement("UPDATE PatientRecord SET dob = ? WHERE patient_id = ?");
					setDOB.setString(1, dobUpdate.getText().trim());
					setDOB.setString(2, selectedPatient);
					setDOB.execute();
					setDOB.close();
				}
				if (!addressUpdate.getText().isBlank() && !addressUpdate.getText().isEmpty()) {
					PreparedStatement setADD = connect.prepareStatement("UPDATE PatientRecord SET address = ? WHERE patient_id = ?");
					setADD.setString(1, addressUpdate.getText().trim());
					setADD.setString(2, selectedPatient);
					setADD.execute();
					setADD.close();
				}

				if (!pNumUpdate.getText().isBlank() && !pNumUpdate.getText().isEmpty()) {
					PreparedStatement setPNUM = connect.prepareStatement("UPDATE PatientRecord SET phone_number = ? WHERE patient_id = ?");
					setPNUM.setString(1, pNumUpdate.getText().trim());
					setPNUM.setString(2, selectedPatient);
					setPNUM.execute();
					setPNUM.close();
				}
				if (!insIDUpdate.getText().isBlank() && !insIDUpdate.getText().isEmpty()) {
					PreparedStatement setIID = connect.prepareStatement("UPDATE PatientRecord SET ins_id = ? WHERE patient_id = ?");
					setIID.setString(1, insIDUpdate.getText().trim());
					setIID.setString(2, selectedPatient);
					setIID.execute();
					setIID.close();
				} 
				if (!immuniUpdate.getText().isBlank() && !immuniUpdate.getText().isEmpty()) {
					PreparedStatement setIMM = connect.prepareStatement("UPDATE PatientRecord SET immunizations = ? WHERE patient_id = ?");
					PreparedStatement getIMM = connect.prepareStatement("SELECT immunizations FROM PatientRecord WHERE patient_id = ?");
					getIMM.setString(1, selectedPatient);
					ResultSet currIMM = getIMM.executeQuery();
					while(currIMM.next()) {
						if(currIMM.getString("immunizations") == "N/A") {
							setIMM.setString(1, immuniUpdate.getText().trim());
						} else {
							setIMM.setString(1, currIMM.getString("immunizations") + ", " + immuniUpdate.getText().trim());
						}
					}
					currIMM.close();
					getIMM.close();
					setIMM.setString(2, selectedPatient);
					setIMM.execute();
					setIMM.close();
				} 
				if (!hHistoryUpdate.getText().isBlank() && !hHistoryUpdate.getText().isEmpty()) {
					PreparedStatement setHHST = connect.prepareStatement("UPDATE PatientRecord SET health_history = ? WHERE patient_id = ?");
					PreparedStatement getHHST = connect.prepareStatement("SELECT health_history FROM PatientRecord where patient_id = ?");
					getHHST.setString(1, selectedPatient);
					ResultSet currHHST = getHHST.executeQuery();
					while (currHHST.next()) {
						if (currHHST.getString("health_history") == "N/A") {
							setHHST.setString(1, hHistoryUpdate.getText().trim());
						} else {
							setHHST.setString(1, currHHST.getString("health_history") + ", " + hHistoryUpdate.getText().trim());
						}
					}
					currHHST.close();
					getHHST.close();
					setHHST.setString(2, selectedPatient);
					setHHST.execute();
					setHHST.close();
				}
				if (!pharmUpdate.getText().isBlank() && !pharmUpdate.getText().isEmpty()) {
					PreparedStatement setPHM = connect.prepareStatement("UPDATE PatientRecord SET pharmacy = ? WHERE patient_id = ?");
					setPHM.setString(1, pharmUpdate.getText().trim());
					setPHM.setString(2, selectedPatient);
					setPHM.execute();
					setPHM.close();
				}
				if (!medUpdate.getText().isBlank() && !medUpdate.getText().isEmpty()) {
					PreparedStatement setMED = connect.prepareStatement("UPDATE PatientRecord SET medications = ? WHERE patient_id = ?");
					PreparedStatement getMED = connect.prepareStatement("SELECT medications FROM PatientRecord where patient_id = ?");
					getMED.setString(1, selectedPatient);
					ResultSet currMED = getMED.executeQuery();
					while (currMED.next()) {
						if (currMED.getString("medications") == "N/A") {
							setMED.setString(1, medUpdate.getText().trim());
						} else {
							setMED.setString(1, currMED.getString("medications") +", " + medUpdate.getText().trim());
						}
					}
					currMED.close();
					getMED.close();
					setMED.setString(2, selectedPatient);
					setMED.execute();
					setMED.close();
				}
				if (!allergiesUpdate.getText().isBlank() && !allergiesUpdate.getText().isEmpty()) {
					PreparedStatement setALG = connect.prepareStatement("UPDATE PatientRecord SET allergies = ? WHERE patient_id = ?");
					PreparedStatement getALG = connect.prepareStatement("SELECT allergies FROM PatientRecord where patient_id = ?");
					getALG.setString(1, selectedPatient);
					ResultSet currALG = getALG.executeQuery();
					while (currALG.next()) {
						if (currALG.getString("allergies") == "N/A") {
							setALG.setString(1, allergiesUpdate.getText().trim());
						} else {
							setALG.setString(1, currALG.getString("allergies") + ", " + allergiesUpdate.getText().trim());
						}
					}
					currALG.close();
					getALG.close();
					setALG.setString(2, selectedPatient);
					setALG.execute();
					setALG.close();
				}
				if (!firstNameUpdate.getText().isBlank() && !firstNameUpdate.getText().isEmpty()) {
					PreparedStatement setFN = connect.prepareStatement("UPDATE PatientRecord SET first_name = ? WHERE patient_id = ?");
					PreparedStatement setUFN = connect.prepareStatement("UPDATE UserType SET first_name = ? WHERE user_id = ?");
					setUFN.setString(1, firstNameUpdate.getText().trim());
					setUFN.setString(2, selectedPatient);
					setFN.setString(1, firstNameUpdate.getText().trim());
					setFN.setString(2, selectedPatient);
					setFN.execute();
					setUFN.execute();
					setUFN.close();
					setFN.close();
				}
				if (!lastNameUpdate.getText().isBlank() && !lastNameUpdate.getText().isEmpty()) {
					PreparedStatement setLN = connect.prepareStatement("UPDATE PatientRecord SET last_name = ? WHERE patient_id = ?");
					PreparedStatement setULN = connect.prepareStatement("UPDATE UserType SET last_name = ? WHERE user_id = ?");
					setULN.setString(1, firstNameUpdate.getText().trim());
					setULN.setString(2, selectedPatient);
					setLN.setString(1, lastNameUpdate.getText().trim());
					setLN.setString(2, selectedPatient);
					setULN.execute();
					setLN.execute();
					setULN.close();
					setLN.close();
				}
				if ((assignedDoctorUpdate.getValue() != null)) {
					PreparedStatement updateDoctorStatement = connect.prepareStatement("UPDATE PatientRecord SET assigned_doctor = ? WHERE patient_id = ?");
					updateDoctorStatement.setString(1, assignedDoctorUpdate.getValue().substring(assignedDoctorUpdate.getValue().length() - 6, assignedDoctorUpdate.getValue().length()));
					updateDoctorStatement.setString(2, selectedPatient);
					updateDoctorStatement.execute();
					updateDoctorStatement.close();
				}
				currRecord.clear();
				updatePatientRecordText();
			} else {
				Alert alert = new Alert(AlertType.WARNING);
				alert.setTitle("No Data Entered");
				alert.setHeaderText(null);
				alert.setContentText("Please enter text into the field you wish to update.");
				alert.showAndWait();
			}
			connect.close();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	
	public void genADUComboBox() {
		Connection connect;
		try {
			connect = DriverManager.getConnection("jdbc:sqlite:./MainDatabase.sqlite");
			PreparedStatement doctors = connect.prepareStatement("SELECT first_name, last_name,user_id FROM UserType WHERE user_type = ?");
			doctors.setString(1, "Physician");
			ResultSet doctorList = doctors.executeQuery();
			while (doctorList.next()) {
				assignedDoctorUpdate.getItems().add(doctorList.getString("first_name") + " " +doctorList.getString("last_name") + " ID: " + doctorList.getString("user_id"));
			}
			doctorList.close();
			doctors.close();
			connect.close();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
}
