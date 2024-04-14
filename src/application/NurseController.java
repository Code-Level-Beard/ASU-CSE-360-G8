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
import java.util.Random;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Hyperlink;
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

public class NurseController {
	String activeUser, selectedPatient, selectedDoctor, userId, messageID,
	nurseName;
	Integer messageNum;
	@FXML
	private Button newPatient, selectPatient, sendButton;
	@FXML
	private ComboBox<String> assignedDoctorUpdate, selDoctor, selPLDoctor,
	selPLPatient;
	@FXML
	private Tab messageTab, newPatientTab, patientListTab, patientRecordTab,
	prevVisitTab;
	@FXML
	private TextArea currRecord, selectedPatientRecord, composeMessage;
	@FXML
	private TextField address, addressUpdate, allergies, allergiesUpdate, dob,
	dobUpdate, firstName, firstNameUpdate, hHistory, hHistoryUpdate, immuni,
	immuniUpdate, insID, insIDUpdate, lastName, lastNameUpdate, med,
	medUpdate, pNum, pNumUpdate, pharm, pharmUpdate,
	pharmUpdateprevVisitNameTxtField, prevVisitDobTxtField,
	prevVisitPtAddTxtField, prevVisitPtPhTxtField, prevVisitInsIdTxtField,
	prevVisitPtPharmTxtField;

	@FXML
	private TextFlow messageText, messageThreadArea;
	// ****Team #3 Fx:id for New Visit tab of the nurseController Panel******
	@FXML
	private Tab newVisitTab;
	// Team #3 fx:id thats getting the data from the SQL database and setting
	// it in the text Field on New Visit tab
	@FXML
	private TextField newVisitNameTxtField, newVisitDobTxtField,
	newVisitPtAddTxtField, newVisitPtPhTxtField, newVisitInsIdTxtField,
	newVisitPtPharmTxtField;

	// Team #3 fx:id TextFields that are writing to the visit table in the SQL
	// database
	@FXML
	private Button newSaveVisitButtonOnAction;

	@FXML
	TextField newVisitBpTxtField, newVisitDovTxtField, newVisitHeightTxtField,
	newVisitWeightTxtField, newVisitTempTxtField;

	@FXML
	private TextArea newVisitMedNotesTxtArea, newVisitImmTxtArea,
	newVisitAlrgTxtArea;
	// Log out Button for the log out functionality
	@FXML
	private Button nurLogOutButton;

	// Previous Visits Tab
	@FXML
	private TextField PvisitPdateofvisit, PvisitPheight, PvisitPweight,
	PvisitPtemperature, PvisitPbloodpressure, PvisitPname, Pvisitdob,
	Pvisitaddress, PvisitPnumber, PvisitInsurance, PvisitPpharmacy;
	@FXML
	private TextArea PvisitPimmunizations, PvisitPAllergies, PvisitPperscriptions,
	PvisitPdiagnoses, PvisitPnotes;
	@FXML
	private TableView<String> PrevVisitsTable;

	@FXML
	public void newPatient(javafx.event.ActionEvent e) {
		PatientRecord.newRecord(firstName, lastName, dob, address, pNum, insID,
				pharm, hHistory, immuni, med, allergies, selDoctor);
	}

	public void genNPComboBox() {
		Connection connect;
		try {
			connect = DriverManager.getConnection("jdbc:sqlite:./MainDatabase.sqlite");
			PreparedStatement doctors = connect.prepareStatement(
					"SELECT first_name, last_name,user_id FROM UserType WHERE user_type = ?");
			doctors.setString(1, "Physician");
			ResultSet doctorList = doctors.executeQuery();
			while (doctorList.next()) {
				selDoctor.getItems().add(doctorList.getString("first_name") + " " +
						doctorList.getString("last_name") +
						" ID: " + doctorList.getString("user_id"));
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
			PreparedStatement doctors = connect.prepareStatement(
					"SELECT first_name, last_name,user_id FROM UserType WHERE user_type = ?");
			doctors.setString(1, "Physician");
			ResultSet doctorList = doctors.executeQuery();
			while (doctorList.next()) {
				selPLDoctor.getItems().add(doctorList.getString("first_name") + " " +
						doctorList.getString("last_name") +
						" ID: " + doctorList.getString("user_id"));
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
		selectedDoctor = selPLDoctor.getValue().substring(
				selPLDoctor.getValue().length() - 6, selPLDoctor.getValue().length());
		genPLPatientComboBox();
	}

	@FXML
	public void getSelectedPatient(javafx.event.ActionEvent e) throws SQLException {
		selectedPatient = selPLPatient.getValue().substring(
				selPLPatient.getValue().length() - 6, selPLPatient.getValue().length());
		patientRecordSelected();
		if (PatientRecord.patientUnderAge(selectedPatient) == true) {
			TextFieldController.lockField(newVisitBpTxtField);
		} else {
			TextFieldController.unlockField(newVisitBpTxtField);
		}
	}

	public void genPLPatientComboBox() {
		selPLPatient.getItems().clear();
		Connection connect;
		try {
			connect = DriverManager.getConnection("jdbc:sqlite:./MainDatabase.sqlite");
			PreparedStatement patients = connect.prepareStatement(
					"SELECT first_name, last_name, patient_id FROM PatientRecord WHERE assigned_doctor = ?");
			patients.setString(1, selectedDoctor);
			ResultSet patientList = patients.executeQuery();

			while (patientList.next()) {
				selPLPatient.getItems().add(
						patientList.getString("first_name") + " " +
								patientList.getString("last_name") +
								" Patient ID: " + patientList.getString("patient_id"));
			}
			patientList.close();
			patients.close();
			connect.close();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

	// Generates the textArea of the current patient record for the selected
	// patient also generates a combo box that allows an assigned doctor to be
	// changed.
	public void patientRecordSelected() {
		updatePatientRecordText();
		genADUComboBox();
	}

	public void updatePatientRecordText() {
		currRecord.clear();
		PatientRecord.readTo(selectedPatient, currRecord);
		// PatientRecord.readTo(selectedPatient, prevVisitNameTxtField,
		// prevVisitDobTxtField, prevVisitPtAddTxtField,
		// prevVisitPtPhTxtField, prevVisitInsIdTxtField,
		// prevVisitPtPharmTxtField);
		// PatientRecord.readTo(selectedPatient, newVisitNameTxtField,
		// newVisitDobTxtField, newVisitPtAddTxtField,
		// newVisitPtPhTxtField, newVisitInsIdTxtField,
		// newVisitPtPharmTxtField);
	}

	@FXML
	public void sendMessage(String user, String patient) {
		Connection connectNurse;
		try {
			connectNurse = DriverManager.getConnection("jdbc:sqlite:./MainDatabase.sqlite");
			PreparedStatement nurseStatement = connectNurse.prepareStatement(
					"SELECT first_name, last_name FROM UserType where user_id = ?");
			nurseStatement.setString(1, user);
			ResultSet resultSet = nurseStatement.executeQuery();
			nurseName = resultSet.getString("first_name") + " " +
					resultSet.getString("last_name");
			resultSet.close();
			nurseStatement.close();
			connectNurse.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		EventHandler<ActionEvent> send = new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent actionEvent) {
				Connection connectMessage;
				try {
					// Bottom connection + query to add the new message to the database
					connectMessage = DriverManager.getConnection("jdbc:sqlite:./MainDatabase.sqlite");
					PreparedStatement newMessageStatement = connectMessage.prepareStatement(
							"INSERT INTO Message (patient_id, message_id, sender, header, content) VALUES (?, ?, ?, ?, ?)");
					if (!(composeMessage.getText().isBlank() &&
							composeMessage.getText().isEmpty())) {
						newMessageStatement.setString(1, patient); // insert patientID
						newMessageStatement.setString(2, genMessageID(patient));
						newMessageStatement.setString(3, nurseName); // insert sender
						newMessageStatement.setString(4, "read"); // insert header
						newMessageStatement.setString(5, composeMessage.getText().trim());
						newMessageStatement.executeUpdate();
						newMessageStatement.close();
						connectMessage.close();
						composeMessage.clear();
						messageText.getChildren().clear();
						displayMessages(patient); // call display message to properly
						// display the newly-sent text
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
		};
		sendButton.setOnAction(send);
	}

	public String genMessageID(String patient) {
		// load previous message's ID and increment by 1
		Connection connection;
		messageNum = 0;
		try {
			connection = DriverManager.getConnection("jdbc:sqlite:./MainDatabase.sqlite");
			PreparedStatement statement = connection.prepareStatement(
					"SELECT message_id FROM Message WHERE patient_id = ?");
			statement.setString(1, patient);
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

	public void displayMessages(String user) {
		messageText.getChildren().clear();
		Connection connect;
		Connection connectUpdate;

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

			connectUpdate = DriverManager.getConnection("jdbc:sqlite:./MainDatabase.sqlite");
			PreparedStatement updateRead = connectUpdate.prepareStatement(
					"UPDATE Message SET header = ? WHERE patient_id = ?");
			updateRead.setString(1, "read");
			updateRead.setString(2, user);
			updateRead.execute();
			updateRead.close();

			resultSet.close();
			statement.close();
			connect.close();
		} catch (SQLException e) {
			// TODO error message
			e.printStackTrace();
		}
		messageSelect(activeUser);
	}

	public void messageSelect(String activeUser) {
		messageThreadArea.getChildren().clear();
		Connection connectMessage;
		Connection connectRecord;

		try {

			connectMessage = DriverManager.getConnection("jdbc:sqlite:./MainDatabase.sqlite");
			connectRecord = DriverManager.getConnection("jdbc:sqlite:./MainDatabase.sqlite");

			PreparedStatement statementMessage = connectMessage.prepareStatement(
					"SELECT patient_id, MAX(message_id), sender, header FROM Message GROUP BY patient_id");
			ResultSet resultSetMessage = statementMessage.executeQuery();

			while (resultSetMessage.next()) {

				PreparedStatement statementRecord = connectRecord.prepareStatement(
						"SELECT first_name, last_name FROM PatientRecord WHERE patient_id = ?");
				statementRecord.setString(1,  resultSetMessage.getString("patient_id"));
				ResultSet resultSetRecord = statementRecord.executeQuery();

				Hyperlink sender = new Hyperlink();
				Hyperlink unread = new Hyperlink();
				Text spacer = new Text("\n");
				String patient = resultSetMessage.getString("patient_id");
				sender.setText(resultSetRecord.getString("first_name") + " " + resultSetRecord.getString("last_name") + "\n\n\n");
				sender.setFont(Font.font("Verdana", FontWeight.BOLD, 14));
				sender.setFocusTraversable(false);
				sender.setOnAction(e -> {
					displayMessages(patient);
					sendMessage(activeUser, patient);
				});
				if (resultSetMessage.getString("header").equals("new")) {
					unread.setText("NEW");
					unread.setFont(Font.font("Verdana", FontWeight.BOLD, 12));
					unread.setFocusTraversable(false);
					messageThreadArea.getChildren().addAll(unread, sender, spacer);
				} else {
					messageThreadArea.getChildren().addAll(sender, spacer);
				}
				resultSetRecord.close();
				statementRecord.close();

			}

			resultSetMessage.close();
			statementMessage.close();
			connectRecord.close();
			connectMessage.close();
		} catch (SQLException e) {
			// TODO error message
			e.printStackTrace();
		}
	}

	// Updates the SelectedPatients Patient Record in the DB
	@FXML
	public void updateDB(javafx.event.ActionEvent e) {
		PatientRecord.updateWithNurse(
				selectedPatient, firstNameUpdate, lastNameUpdate, dobUpdate,
				addressUpdate, pNumUpdate, insIDUpdate, pharmUpdate, hHistoryUpdate,
				immuniUpdate, medUpdate, allergiesUpdate, assignedDoctorUpdate);
		currRecord.clear();
		PatientRecord.readTo(selectedPatient, currRecord);
	}

	// Generates the combo box that allows the nurse to assign a diff doctor to
	// the selected patient
	public void genADUComboBox() {
		Connection connect;
		assignedDoctorUpdate.getItems().clear();
		try {
			connect = DriverManager.getConnection("jdbc:sqlite:./MainDatabase.sqlite");
			PreparedStatement doctors = connect.prepareStatement(
					"SELECT first_name, last_name,user_id FROM UserType WHERE user_type = ?");
			doctors.setString(1, "Physician");
			ResultSet doctorList = doctors.executeQuery();
			while (doctorList.next()) {
				assignedDoctorUpdate.getItems().add(
						doctorList.getString("first_name") + " " +
								doctorList.getString("last_name") +
								" ID: " + doctorList.getString("user_id"));
			}
			doctorList.close();
			doctors.close();
			connect.close();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

	// Team #3 ********New Visit Tab Method*******
	public void newVisitTabListener() {
		Connection connect;
		try {
			connect = DriverManager.getConnection("jdbc:sqlite:./MainDatabase.sqlite");
			PreparedStatement statement = connect.prepareStatement(
					"SELECT patient_id, first_name, last_name, address, "
							+ "phone_number, ins_id, pharmacy, health_history,immunizations, "
							+
					"medications, allergies, assigned_doctor,DOB FROM PatientRecord WHERE patient_id = ?");
			statement.setString(1, selectedPatient);
			ResultSet rs = statement.executeQuery();

			newVisitNameTxtField.clear();
			newVisitDobTxtField.clear();
			newVisitPtAddTxtField.clear();
			newVisitPtPhTxtField.clear();
			newVisitInsIdTxtField.clear();
			newVisitPtPharmTxtField.clear();

			while (rs.next()) {
				newVisitNameTxtField.appendText(rs.getString("first_name") + " " +
						rs.getString("last_name"));
				newVisitDobTxtField.appendText(rs.getString("DOB"));
				newVisitPtAddTxtField.appendText(rs.getString("address"));
				newVisitPtPhTxtField.appendText(rs.getString("phone_number"));
				newVisitInsIdTxtField.appendText(rs.getString("ins_id"));
				newVisitPtPharmTxtField.appendText(rs.getString("pharmacy"));
			}

			rs.close();
			statement.close();
			connect.close();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

	// **** Team #3 New Visit Button to Save visit into SQL DB / Visit Table
	// ******
	@FXML
	public void newSaveVisitButtonOnAction(javafx.event.ActionEvent e) {

		try {
			// Retrieve data from the text fields
			String bloodPressure = newVisitBpTxtField.getText().trim();
			String immunization = newVisitImmTxtArea.getText().trim();
			String allergies = newVisitAlrgTxtArea.getText().trim();
			String notes = newVisitMedNotesTxtArea.getText().trim();
			String dateOfVisit = newVisitDovTxtField.getText().trim();
			String height = newVisitHeightTxtField.getText().trim();
			String weight = newVisitWeightTxtField.getText().trim();
			String temperature = newVisitTempTxtField.getText().trim();

			// Establish a connection to the database
			Connection connect = DriverManager.getConnection("jdbc:sqlite:./MainDatabase.sqlite");

			// Prepare a SQL INSERT statement for Visit table
			PreparedStatement insertVisit = connect.prepareStatement(
					"INSERT INTO Visit (patient_id, doctor_id, date, height, weight, temperature, blood_pressure, immunization, allergies, notes,completed, prescription, visit_diag) "
							+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");

			//Grab the patients age and check against age req. Under 12 no BP taken

			// Set parameters for the prepared statement
			insertVisit.setString(1, selectedPatient); // Using the current patient ID
			insertVisit.setString(2, selectedDoctor); // Using the current doctor ID
			insertVisit.setString(3, dateOfVisit);
			insertVisit.setString(4, height);
			insertVisit.setString(5, weight);
			insertVisit.setString(6, temperature);
			insertVisit.setString(7, bloodPressure);
			insertVisit.setString(8, immunization);
			insertVisit.setString(9, allergies);
			insertVisit.setString(10, notes);
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

	// Team #3 ********Previous Visit Tab Method*******
	public void pullPreviousVisit() {
		Connection connect;
		PvisitPname.clear();
		Pvisitdob.clear();
		Pvisitaddress.clear();
		PvisitPnumber.clear();
		PvisitInsurance.clear();
		PvisitPpharmacy.clear();
		if (selectedPatient != null) {
			PatientRecord.readTo(selectedPatient, PvisitPname, Pvisitdob,
					Pvisitaddress, PvisitPnumber, PvisitInsurance,
					PvisitPpharmacy);
			try {
				connect = DriverManager.getConnection("jdbc:sqlite:./MainDatabase.sqlite");
				// sqlite statement

				PreparedStatement PreviousVisitstatement = connect.prepareStatement(
						"SELECT * FROM Visit WHERE patient_id = ? AND completed IS NOT NULL");
				PreviousVisitstatement.setString(1, selectedPatient);
				ResultSet rs = PreviousVisitstatement.executeQuery();
				// Clears Table data and columns
				PrevVisitsTable.getColumns().clear();
				PrevVisitsTable.getItems().clear();
				// List to hold each row from query, visit data
				List<Map<String, String>> visits = new ArrayList<>();

				// creates Table column and adds it to Table
				TableColumn<String, String> visitDateCol = new TableColumn<>("Visit Date");
				visitDateCol.setCellValueFactory(
						cellData -> new SimpleStringProperty(cellData.getValue()));
				ObservableList<String> visitDates = FXCollections.observableArrayList();
				PrevVisitsTable.getColumns().add(visitDateCol);
				// Clears data from our Lists
				PvisitPdateofvisit.clear();
				PvisitPheight.clear();
				PvisitPweight.clear();
				PvisitPtemperature.clear();
				PvisitPbloodpressure.clear();
				PvisitPimmunizations.clear();
				PvisitPAllergies.clear();
				PvisitPnotes.clear();
				PvisitPperscriptions.clear();
				PvisitPdiagnoses.clear();
				visitDates.clear();
				visits.clear();
				// Process the result set and parse into a new Hash Map for each entry
				// of visits Array List
				while (rs.next()) {
					// Map to hold data for one visit
					Map<String, String> visitData = new HashMap<>();

					// Retrieve data from the result set for each visit
					visitData.put("date", rs.getString("date"));
					visitData.put("height", rs.getString("height"));
					visitData.put("weight", rs.getString("weight"));
					visitData.put("temperature", rs.getString("temperature"));
					visitData.put("blood_pressure", rs.getString("blood_pressure"));
					visitData.put("immunization", rs.getString("immunization"));
					visitData.put("allergies", rs.getString("allergies"));
					visitData.put("notes", rs.getString("notes"));
					visitData.put("prescription", rs.getString("prescription"));
					visitData.put("visit_diag", rs.getString("visit_diag"));

					// adds date into table
					visitDates.add(visitData.get("date"));
					PrevVisitsTable.setItems(visitDates);
					// Add data for the current visit to the list
					visits.add(visitData);
				}
				// Showing first visit in the table

				if (visits.isEmpty()) {
					visitDates.add("No Previous Visits to Display");
					PrevVisitsTable.setItems(visitDates);
				} else {

					Map<String, String> firstVisitData = visits.get(0);
					PvisitPdateofvisit.clear();
					PvisitPheight.clear();
					PvisitPweight.clear();
					PvisitPtemperature.clear();
					PvisitPbloodpressure.clear();
					PvisitPimmunizations.clear();
					PvisitPAllergies.clear();
					PvisitPnotes.clear();
					PvisitPperscriptions.clear();
					PvisitPdiagnoses.clear();

					PvisitPdateofvisit.appendText(firstVisitData.get("date"));
					PvisitPheight.appendText(firstVisitData.get("height"));
					PvisitPweight.appendText(firstVisitData.get("weight"));
					PvisitPtemperature.appendText(firstVisitData.get("temperature"));
					PvisitPbloodpressure.appendText(firstVisitData.get("blood_pressure"));
					PvisitPimmunizations.appendText(firstVisitData.get("immunization"));
					PvisitPAllergies.appendText(firstVisitData.get("allergies"));
					PvisitPnotes.appendText(firstVisitData.get("notes"));
					PvisitPperscriptions.appendText(firstVisitData.get("prescription"));
					PvisitPdiagnoses.appendText(firstVisitData.get("visit_diag"));

					// Listener for each entry in the table. when one is selected, newVal
					// equals that date
					PrevVisitsTable.getSelectionModel()
					.selectedItemProperty()
					.addListener((obs, oldVal, newVal) -> {
						if (newVal != null) {
							String selectedDate = newVal; // Date selected in table

							// Find the index of the visit with the selected date in the
							// ArrayList
							int selectedIndex = -1;
							for (int i = 0; i < visits.size(); i++) {
								String visitDate = visits.get(i).get("date");
								if (visitDate.equals(selectedDate)) {
									selectedIndex = i;
									break;
								}
							}
							// If a visit with the selected date was found, populate the
							// text fields with its information
							if (selectedIndex != -1) {
								Map<String, String> selectedVisitData = visits.get(selectedIndex);
								// Populate text fields with selectedVisitData
								PvisitPdateofvisit.clear();
								PvisitPheight.clear();
								PvisitPweight.clear();
								PvisitPtemperature.clear();
								PvisitPbloodpressure.clear();
								PvisitPimmunizations.clear();
								PvisitPAllergies.clear();
								PvisitPnotes.clear();
								PvisitPperscriptions.clear();
								PvisitPdiagnoses.clear();

								PvisitPdateofvisit.appendText(
										selectedVisitData.get("date"));
								PvisitPheight.appendText(selectedVisitData.get("height"));
								PvisitPweight.appendText(selectedVisitData.get("weight"));
								PvisitPtemperature.appendText(
										selectedVisitData.get("temperature"));
								PvisitPbloodpressure.appendText(
										selectedVisitData.get("blood_pressure"));
								PvisitPimmunizations.appendText(
										selectedVisitData.get("immunization"));
								PvisitPAllergies.appendText(
										selectedVisitData.get("allergies"));
								PvisitPnotes.appendText(selectedVisitData.get("notes"));
								PvisitPperscriptions.appendText(
										selectedVisitData.get("prescription"));
								PvisitPdiagnoses.appendText(
										selectedVisitData.get("visit_diag"));
							}
						}
					});
				}
				rs.close();
				PreviousVisitstatement.close();
				connect.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	// Log out button method / action listener that kicks the user out back to
	// login screen

	public void nurLogOutOnAction(javafx.event.ActionEvent e) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("Login.fxml"));
			Parent root = loader.load();

			Stage stage = new Stage();
			stage.setScene(new Scene(root));
			stage.show();

			// Close the current patient panel window
			Stage currentStage = (Stage) nurLogOutButton.getScene().getWindow();
			currentStage.close();
		} catch (IOException et) {
			et.printStackTrace();
		}
	}
}
