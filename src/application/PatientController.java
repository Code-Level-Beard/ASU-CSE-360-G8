package application;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

public class PatientController {
	String activeUser;

	String messageID;

	Integer messageNum;

	String patientName;

	@FXML
	private Button update;
	@FXML
	private TextArea composeMessage;
	// Patient Record Tab
	@FXML
	private TextArea currRecord;
	@FXML
	private TextField address;
	@FXML
	private TextField allergies;
	@FXML
	private TextField dob;
	@FXML
	private TextField firstName;
	@FXML
	private TextField hHistory;
	@FXML
	private TextField immuni;
	@FXML
	private TextField insID;
	@FXML
	private TextField lastName;
	@FXML
	private TextField med;
	@FXML
	private TextField pNum;
	@FXML
	private TextField pharm;
	@FXML
	private TextFlow messageText;
	// Previous Visits Tab
	@FXML
	private TextArea PvisitPnotes;
	@FXML
	private TextField PvisitPname;
	@FXML
	private TextField Pvisitdob;
	@FXML
	private TextField Pvisitaddress;
	@FXML
	private TextField PvisitPnumber;
	@FXML
	private TextField PvisitInsurance;
	@FXML
	private TextField PvisitPolnumber;
	@FXML
	private TextField Pvisitcitystatezip;
	@FXML
	private TextField PvisitPheight;
	@FXML
	private TextField PvisitPweight;
	@FXML
	private TextField PvisitPtemperature;
	@FXML
	private TextField PvisitPbloodpressure;
	@FXML
	private TextField PvisitPimmunization1;
	@FXML
	private TextField PvisitPAllergy1;
	@FXML
	private TextField PvisitPdateofvisit;
	@FXML
	private TextField PvisitPpharmacy;
	@FXML
	private TextField PvisitPimmunization2;
	@FXML
	private TextField PvisitPallergy2;
	@FXML
	private TextField PvisitPperscription1;
	@FXML
	private TextField PvisitPperscription2;
	@FXML
	private TextField PvisitPdiagnosis1;
	@FXML
	private TextField PvisitPdiagnosis2;

	public void pullPreviousVisit(String user) {
		activeUser = user;
		Connection connect;
		try {
			connect = DriverManager.getConnection("jdbc:sqlite:./MainDatabase.sqlite");

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@FXML
	public void sendMessage(javafx.event.ActionEvent e) {
		System.out.println(composeMessage.getText().trim()); // test output
		Connection connectMessage;
		Connection connectRecord;
		System.out.println(activeUser); // test output
		try {
			connectRecord = DriverManager.getConnection("jdbc:sqlite:./MainDatabase.sqlite");
			// Run a query to grab Patient's name and his/her Doctor + Nurse
			PreparedStatement retrieveDataStatement = connectRecord.prepareStatement(
					"SELECT first_name, last_name, assigned_doctor FROM PatientRecord WHERE patient_id = ?");
			retrieveDataStatement.setString(1, activeUser);
			ResultSet resultSet = retrieveDataStatement.executeQuery();
			patientName = resultSet.getString("first_name") + " " +
					resultSet.getString("last_name");
			resultSet.close();
			retrieveDataStatement.close();
			connectRecord.close();
			// Bottom connection + query to add the new message to the database
			connectMessage = DriverManager.getConnection("jdbc:sqlite:./MainDatabase.sqlite");
			PreparedStatement newMessageStatement = connectMessage.prepareStatement(
					"INSERT INTO Message (patient_id, message_id, sender, content) VALUES (?, ?, ?, ?)");
			if (!(composeMessage.getText().isBlank() &&
					composeMessage.getText().isEmpty())) {
				newMessageStatement.setString(1, activeUser); // insert patientID
				newMessageStatement.setString(2, genMessageID());
				System.out.println(patientName); // test output
				newMessageStatement.setString(3, patientName); // insert sender
				// insert recipient
				// insert header
				newMessageStatement.setString(4, composeMessage.getText().trim());

				newMessageStatement.executeUpdate();
				newMessageStatement.close();
				connectMessage.close();
				composeMessage.clear();
			} else {
				Alert alert = new Alert(AlertType.WARNING);
				alert.setTitle("No Message Entered");
				alert.setHeaderText(null);
				alert.setContentText("Enter a message before attempting to send");
				alert.showAndWait();
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
	}

	public String genMessageID() {
		// load previous message's ID and increment by 1
		Connection connection;
		messageNum = 0;
		try {
			connection = DriverManager.getConnection("jdbc:sqlite:./MainDatabase.sqlite");
			PreparedStatement statement = connection.prepareStatement(
					"SELECT message_id FROM Message WHERE patient_id = ?");
			statement.setString(1, activeUser);
			ResultSet resultSet = statement.executeQuery();
			while (resultSet.next()) {
				try {
					messageNum = Integer.valueOf(resultSet.getString("message_id"));
				} catch (NumberFormatException e) {
					messageID = "0";
				}
			}
			resultSet.close();
			statement.close();
			connection.close();
			if ("0".equals(messageID)) {
				System.out.println(messageID); // test output
				return messageID;
			} else {
				messageNum = messageNum + 1;
				messageID = String.valueOf(messageNum);
				System.out.println(messageID); // test output
				return messageID;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		System.out.println(messageID); // test output
		return messageID;
	}

	public void updateText(String user) {
		activeUser = user;
		Connection connect;
		try {
			connect = DriverManager.getConnection("jdbc:sqlite:./MainDatabase.sqlite");
			PreparedStatement statement = connect.prepareStatement(
					"SELECT patient_id, first_name, last_name, address, "
							+ "phone_number, ins_id, pharmacy, health_history,immunizations, "
							+
							"medications, allergies, assigned_doctor,DOB FROM PatientRecord WHERE patient_id = ?");
			statement.setString(1, user);
			ResultSet resultSet = statement.executeQuery();
			while (resultSet.next()) {
				currRecord.appendText("Name: " + resultSet.getString("first_name") +
						" " + resultSet.getString("last_name") + "\n\n");
				currRecord.appendText("Date of Birth: " + resultSet.getString("DOB") +
						"\n\n");
				currRecord.appendText("Address: " + resultSet.getString("address") +
						"\n\n");
				currRecord.appendText(
						"Phone Number: " + resultSet.getString("phone_number") + "\n\n");
				currRecord.appendText(
						"Insurance Provider: " + resultSet.getString("ins_id") + "\n\n");
				currRecord.appendText("Pharmacy: " + resultSet.getString("pharmacy") +
						"\n\n");
				currRecord.appendText("Health History: " +
						resultSet.getString("health_history") + "\n\n");
				currRecord.appendText(
						"Immunizations: " + resultSet.getString("immunizations") + "\n\n");
				currRecord.appendText(
						"Medications: " + resultSet.getString("medications") + "\n\n");
				currRecord.appendText("Allergies: " + resultSet.getString("allergies") +
						"\n\n");
				currRecord.appendText("Assigned Doctor: " +
						resultSet.getString("assigned_doctor") + "\n\n");
			}
			resultSet.close();
			statement.close();
			connect.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void displayMessages(String user) {
		Connection connect;
		// composeMessage.setText("test");
		try {
			connect = DriverManager.getConnection("jdbc:sqlite:./MainDatabase.sqlite");
			PreparedStatement statement = connect.prepareStatement(
					"SELECT message_id, sender, content FROM Message WHERE patient_id = ?");
			statement.setString(1, user);
			ResultSet resultSet = statement.executeQuery();

			while (resultSet.next()) {

				Text sender = new Text();
				Text content = new Text();
				sender.setText(resultSet.getString("sender") + "\n");
				sender.setFont(Font.font("Verdana", FontWeight.BOLD, 14));
				content.setText(resultSet.getString("content") + "\n\n\n");
				content.setFont(Font.font("Verdana", FontWeight.NORMAL, 12));
				messageText.getChildren().addAll(sender, content);
			
			}
					
			resultSet.close();
			statement.close();
			connect.close();
		} catch (SQLException e) {
			// TODO error message
			e.printStackTrace();
		}
	}

	@FXML
	public void updateDB(javafx.event.ActionEvent e) {
		Connection connect;
		try {
			connect = DriverManager.getConnection("jdbc:sqlite:./MainDatabase.sqlite");
			PreparedStatement userState = connect.prepareStatement(
					"Select patient_id FROM PatientRecord WHERE patient_id = ?");
			userState.setString(1, activeUser);
			ResultSet userSQL = userState.executeQuery();
			while (userSQL.next()) {
				activeUser = userSQL.getString("patient_id");
			}
			userSQL.close();
			userState.close();
			// if (!dob.getText().isBlank() && !dob.getText().isEmpty()) {
			if (!((dob.getText().isBlank() && dob.getText().isEmpty()) &&
					(address.getText().isBlank() && address.getText().isEmpty()) &&
					(pNum.getText().isBlank() && pNum.getText().isEmpty()) &&
					(insID.getText().isBlank() && insID.getText().isEmpty()) &&
					(immuni.getText().isBlank() && immuni.getText().isEmpty()) &&
					(hHistory.getText().isBlank() && hHistory.getText().isEmpty()) &&
					(med.getText().isBlank() && med.getText().isEmpty()) &&
					(allergies.getText().isBlank() && allergies.getText().isEmpty()) &&
					(firstName.getText().isBlank() && firstName.getText().isEmpty()) &&
					(lastName.getText().isBlank() && lastName.getText().isEmpty()))) {
				if (!dob.getText().isBlank() && !dob.getText().isEmpty()) {
					PreparedStatement setDOB = connect.prepareStatement(
							"UPDATE PatientRecord SET dob = ? WHERE patient_id = ?");
					setDOB.setString(1, dob.getText().trim());
					setDOB.setString(2, activeUser);
					setDOB.execute();
					setDOB.close();
				}
				if (!address.getText().isBlank() && !address.getText().isEmpty()) {
					PreparedStatement setADD = connect.prepareStatement(
							"UPDATE PatientRecord SET address = ? WHERE patient_id = ?");
					setADD.setString(1, address.getText().trim());
					setADD.setString(2, activeUser);
					setADD.execute();
					setADD.close();
				}

				if (!pNum.getText().isBlank() && !pNum.getText().isEmpty()) {
					PreparedStatement setPNUM = connect.prepareStatement(
							"UPDATE PatientRecord SET phone_number = ? WHERE patient_id = ?");
					setPNUM.setString(1, pNum.getText().trim());
					setPNUM.setString(2, activeUser);
					setPNUM.execute();
					setPNUM.close();
				}
				if (!insID.getText().isBlank() && !insID.getText().isEmpty()) {
					PreparedStatement setIID = connect.prepareStatement(
							"UPDATE PatientRecord SET ins_id = ? WHERE patient_id = ?");
					setIID.setString(1, insID.getText().trim());
					setIID.setString(2, activeUser);
					setIID.execute();
					setIID.close();
				}
				if (!immuni.getText().isBlank() && !immuni.getText().isEmpty()) {
					PreparedStatement setIMM = connect.prepareStatement(
							"UPDATE PatientRecord SET immunizations = ? WHERE patient_id = ?");
					PreparedStatement getIMM = connect.prepareStatement(
							"SELECT immunizations FROM PatientRecord where patient_id = ?");
					getIMM.setString(1, activeUser);
					ResultSet currIMM = getIMM.executeQuery();
					while (currIMM.next()) {
						if (currIMM.getString("immunizations") == "N/A") {
							setIMM.setString(1, immuni.getText().trim());
						} else {
							setIMM.setString(1, currIMM.getString("immunizations") + ", " +
									immuni.getText().trim());
						}
					}
					currIMM.close();
					getIMM.close();
					setIMM.setString(2, activeUser);
					setIMM.execute();
					setIMM.close();
				}
				if (!hHistory.getText().isBlank() && !hHistory.getText().isEmpty()) {
					PreparedStatement setHHST = connect.prepareStatement(
							"UPDATE PatientRecord SET health_history = ? WHERE patient_id = ?");
					PreparedStatement getHHST = connect.prepareStatement(
							"SELECT health_history FROM PatientRecord where patient_id = ?");
					getHHST.setString(1, activeUser);
					ResultSet currHHST = getHHST.executeQuery();
					while (currHHST.next()) {
						if (currHHST.getString("health_history") == "N/A") {
							setHHST.setString(1, hHistory.getText().trim());
						} else {
							setHHST.setString(1, currHHST.getString("health_history") + ", " +
									hHistory.getText().trim());
						}
					}
					currHHST.close();
					getHHST.close();
					setHHST.setString(2, activeUser);
					setHHST.execute();
					setHHST.close();
				}
				if (!pharm.getText().isBlank() && !pharm.getText().isEmpty()) {
					PreparedStatement setPHM = connect.prepareStatement(
							"UPDATE PatientRecord SET pharmacy = ? WHERE patient_id = ?");
					setPHM.setString(1, pharm.getText().trim());
					setPHM.setString(2, activeUser);
					setPHM.execute();
					setPHM.close();
				}
				if (!med.getText().isBlank() && !med.getText().isEmpty()) {
					PreparedStatement setMED = connect.prepareStatement(
							"UPDATE PatientRecord SET medications = ? WHERE patient_id = ?");
					PreparedStatement getMED = connect.prepareStatement(
							"SELECT medications FROM PatientRecord where patient_id = ?");
					getMED.setString(1, activeUser);
					ResultSet currMED = getMED.executeQuery();
					while (currMED.next()) {
						if (currMED.getString("medications") == "N/A") {
							setMED.setString(1, med.getText().trim());
						} else {
							setMED.setString(1, currMED.getString("medications") + ", " +
									med.getText().trim());
						}
					}
					currMED.close();
					getMED.close();
					setMED.setString(2, activeUser);
					setMED.execute();
					setMED.close();
				}
				if (!allergies.getText().isBlank() && !allergies.getText().isEmpty()) {
					PreparedStatement setALG = connect.prepareStatement(
							"UPDATE PatientRecord SET allergies = ? WHERE patient_id = ?");
					PreparedStatement getALG = connect.prepareStatement(
							"SELECT allergies FROM PatientRecord where patient_id = ?");
					getALG.setString(1, activeUser);
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
					setALG.setString(2, activeUser);
					setALG.execute();
					setALG.close();
				}
				if (!firstName.getText().isBlank() && !firstName.getText().isEmpty()) {
					PreparedStatement setFN = connect.prepareStatement(
							"UPDATE PatientRecord SET first_name = ? WHERE patient_id = ?");
					PreparedStatement setUFN = connect.prepareStatement(
							"UPDATE UserType SET first_name = ? WHERE user_id = ?");
					setUFN.setString(1, firstName.getText().trim());
					setUFN.setString(2, activeUser);
					setFN.setString(1, firstName.getText().trim());
					setFN.setString(2, activeUser);
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
					setULN.setString(2, activeUser);
					setLN.setString(1, lastName.getText().trim());
					setLN.setString(2, activeUser);
					setULN.execute();
					setLN.execute();
					setULN.close();
					setLN.close();
				}
				currRecord.clear();
				updateText(activeUser);
			} else {
				Alert alert = new Alert(AlertType.WARNING);
				alert.setTitle("No Data Entered");
				alert.setHeaderText(null);
				alert.setContentText(
						"Please enter text into the field you wish to update.");
				alert.showAndWait();
			}
			connect.close();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
}
