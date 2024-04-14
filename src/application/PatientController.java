package application;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;

public class PatientController {
	String activeUser;

	String messageID;

	Integer messageNum;

	String patientName;

	// Previous Visits Tab
	@FXML
	private TextArea PvisitPimmunizations, PvisitPAllergies, PvisitPperscriptions,
			PvisitPdiagnoses;
	@FXML
	private Tab PrevVisitTab;
	@FXML
	private TableView<String> PrevVisitsTable;
	// Button for log-out it will kick you back to the login screen
	@FXML
	private Button ptLogOutButton;
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
	private TextField PvisitPdateofvisit;
	@FXML
	private TextField PvisitPpharmacy;

	// Team #3 ********Previous Visit Tab Method*******
	public void pullPreviousVisit() {
		VisitRecord.readTo(activeUser, PvisitPdateofvisit, PvisitPweight,
				PvisitPheight, PvisitPpharmacy, PvisitPtemperature,
				PvisitPbloodpressure, PvisitPimmunizations,
				PvisitPperscriptions, PvisitPdiagnoses, PvisitPAllergies,
				PvisitPnotes, PrevVisitsTable);
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
					"INSERT INTO Message (patient_id, message_id, sender, header, content) VALUES (?, ?, ?, ?, ?)");
			if (!(composeMessage.getText().isBlank() &&
					composeMessage.getText().isEmpty())) {
				newMessageStatement.setString(1, activeUser); // insert patientID
				newMessageStatement.setString(2, genMessageID());
				System.out.println(patientName); // test output
				newMessageStatement.setString(3, patientName); // insert sender
				newMessageStatement.setString(4, "new"); // insert header
				newMessageStatement.setString(5, composeMessage.getText().trim());

				newMessageStatement.executeUpdate();
				newMessageStatement.close();
				connectMessage.close();
				composeMessage.clear();
				displayMessages(activeUser); // call display message to properly display
																			// the newly-sent text
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

	// Generates the text that shows the patient their current patient record
	public void updateText(String user) {
		PatientRecord.readTo(user, currRecord);
		PatientRecord.readTo(user, PvisitPname, Pvisitdob, Pvisitaddress,
				PvisitPnumber, PvisitInsurance, PvisitPpharmacy);
	}

	public void displayMessages(String user) {
		messageText.getChildren().clear();
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
			e.printStackTrace();
		}
	}

	// Allows the patient to update their DB entry of their Patient Record with
	// new information
	@FXML
	public void updateDB() {
		PatientRecord.updateWith(activeUser, firstName, lastName, dob, address,
				pNum, insID, pharm, hHistory, immuni, med,
				allergies);
		currRecord.clear();
		updateText(activeUser);
	}

	public void ptLogOutOnAction(javafx.event.ActionEvent e) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("Login.fxml"));
			Parent root = loader.load();

			Stage stage = new Stage();
			stage.setScene(new Scene(root));
			stage.show();

			// Close the current patient panel window
			Stage currentStage = (Stage) ptLogOutButton.getScene().getWindow();
			currentStage.close();
		} catch (IOException et) {
			et.printStackTrace();
		}
	}
}
