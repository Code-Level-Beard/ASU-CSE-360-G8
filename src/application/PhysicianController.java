package application;


import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Tab;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;
import javafx.fxml.FXML;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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
	
	@FXML
	private TextFlow messageText;
	@FXML
	private TextFlow messageThreadArea;
//	Team #3 fx:id textFields and buttons
	
//	Team #3 fx:id textFields that get / hold the current visit data
	@FXML
	private TextField currVisitPtName,currVisitPtDOB,currVisitPtAdd,currVisitPtPhNum,
					  currVisitInsID, currVisitPtPharm;
	
//	Team #3 fx:id buttons and textFields for save visit / complete visit
	@FXML
	private TextField  currVisitDateOfVisit,currVisitPtHeight,currVisitPtWeight,currVisitPtTemp,
						currVisitPtBP;
	
	@FXML
	private TextArea	currVisitPtMedNotes,currVisitPtImm,currVisitPtAlrg,currVisitPtPresc,currVisitPtDiag;
	
	@FXML
	private Button currVisitCompVisitOnAction,currVisitSaveOnAction;
	
//	Log out button for physician controller kicks you back to the login screen
	@FXML
	private Button docLogOutButton;
	
	
	public void displayMessages(String user) {
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
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
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
	
/*	public void messageSelect() {
		Connection connect;
		//composeMessage.setText("test");
		try {
			connect = DriverManager.getConnection("jdbc:sqlite:./MainDatabase.sqlite");
			PreparedStatement statement = connect.prepareStatement("SELECT message_id, sender, header, content FROM Message WHERE patient_id = ?");
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
	} */
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
	
	
//	Team #3 Current Visit tab 
	public void currVisitTabListener() {
	    Connection connect = null;
	    try {
	        connect = DriverManager.getConnection("jdbc:sqlite:./MainDatabase.sqlite");
	        
	        // Fetching data from PatientRecord table
	        PreparedStatement patientStatement = connect.prepareStatement(
	                "SELECT first_name, last_name, address, phone_number, ins_id, pharmacy, DOB FROM PatientRecord WHERE patient_id = ?");
	        patientStatement.setString(1, selectedPatient);
	        ResultSet patientResultSet = patientStatement.executeQuery();

	        // Populating PatientRecord fields
	        if (patientResultSet.next()) { // Assuming only one record is expected
	            currVisitPtName.setText(patientResultSet.getString("first_name") + " " + patientResultSet.getString("last_name"));
	            currVisitPtDOB.setText(patientResultSet.getString("DOB"));
	            currVisitPtAdd.setText(patientResultSet.getString("address"));
	            currVisitPtPhNum.setText(patientResultSet.getString("phone_number"));
	            currVisitInsID.setText(patientResultSet.getString("ins_id"));
	            currVisitPtPharm.setText(patientResultSet.getString("pharmacy"));
	        }

	        patientResultSet.close();
	        patientStatement.close();

	        // Fetching data from Visit table
	        PreparedStatement visitStatement = connect.prepareStatement(
	                "SELECT date, height, weight, temperature, blood_pressure, immunization, allergies, notes, prescription, visit_diag FROM Visit WHERE patient_id = ? ORDER BY date DESC LIMIT 1");
	        visitStatement.setString(1, selectedPatient);
	        ResultSet visitResultSet = visitStatement.executeQuery();

	        // Populating Visit fields
	        if (visitResultSet.next()) { // Assuming only one record is expected
	            currVisitDateOfVisit.setText(visitResultSet.getString("date"));
	            currVisitPtHeight.setText(visitResultSet.getString("height"));
	            currVisitPtWeight.setText(visitResultSet.getString("weight"));
	            currVisitPtTemp.setText(visitResultSet.getString("temperature"));
	            currVisitPtBP.setText(visitResultSet.getString("blood_pressure"));
	            currVisitPtImm.setText(visitResultSet.getString("immunization"));
	            currVisitPtAlrg.setText(visitResultSet.getString("allergies"));
	            currVisitPtMedNotes.setText(visitResultSet.getString("notes"));
	            currVisitPtPresc.setText(visitResultSet.getString("prescription"));
	            currVisitPtDiag.setText(visitResultSet.getString("visit_diag"));
	        }

	        visitResultSet.close();
	        visitStatement.close();
	    } catch (SQLException e) {
	        // Handle SQL exceptions
	        e.printStackTrace();
	    } finally {
	        // Close the connection in the finally block
	        try {
	            if (connect != null) connect.close();
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }
	}
	
//	Team #3 Save Visit Button / not completed visit
	@FXML
	public void currVisitSaveOnAction(javafx.event.ActionEvent e) {
	    // Assuming you have access to doctor_id in your controller
	    String doctorId = activeUser;

	    if (selectedPatient == null) {
	        // Show a warning dialog
	        showWarningDialog("Warning", "No patient selected.");
	        return;
	    }
	    Connection connect = null;
	    try {
	        connect = DriverManager.getConnection("jdbc:sqlite:./MainDatabase.sqlite");

	        // Insert new visit record with completion status 'NC'
	        PreparedStatement insertStatement = connect.prepareStatement(
	                "INSERT INTO Visit (patient_id, doctor_id, date, height, weight, temperature, blood_pressure, immunization, allergies, notes, prescription, visit_diag, completed) " +
	                        "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, 'NC')");
	        insertStatement.setString(1, selectedPatient);
	        insertStatement.setString(2, doctorId);
	        insertStatement.setString(3, currVisitDateOfVisit.getText());
	        insertStatement.setString(4, currVisitPtHeight.getText());
	        insertStatement.setString(5, currVisitPtWeight.getText());
	        insertStatement.setString(6, currVisitPtTemp.getText());
	        insertStatement.setString(7, currVisitPtBP.getText());
	        insertStatement.setString(8, currVisitPtImm.getText());
	        insertStatement.setString(9, currVisitPtAlrg.getText());
	        insertStatement.setString(10, currVisitPtMedNotes.getText());
	        insertStatement.setString(11, currVisitPtPresc.getText());
	        insertStatement.setString(12, currVisitPtDiag.getText());

	        int rowsAffected = insertStatement.executeUpdate();
	        System.out.println("Rows affected: " + rowsAffected);

	        insertStatement.close();
	    } catch (SQLException ex) {
	        // Handle SQL exceptions
	        ex.printStackTrace();
	    } finally {
	        // Close the connection in the finally block
	        try {
	            if (connect != null) connect.close();
	        } catch (SQLException ex) {
	            ex.printStackTrace();
	        }
	    }
	}

	@FXML
	public void currVisitCompVisitOnAction (javafx.event.ActionEvent e) {
	    // Assuming you have access to doctor_id in your controller
		String doctorId = activeUser;

	    if (selectedPatient == null) {
	        // Show a warning dialog
	        showWarningDialog("Warning", "No patient selected.");
	        return;
	    }
	    Connection connect = null;
	    try {
	        connect = DriverManager.getConnection("jdbc:sqlite:./MainDatabase.sqlite");

	        // Insert new visit record with completion status 'C'
	        PreparedStatement insertStatement = connect.prepareStatement(
	                "INSERT INTO Visit (patient_id, doctor_id, date, height, weight, temperature, blood_pressure, immunization, allergies, notes, prescription, visit_diag, completed) " +
	                        "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, 'C')");
	        insertStatement.setString(1, selectedPatient);
	        insertStatement.setString(2, doctorId);
	        insertStatement.setString(3, currVisitDateOfVisit.getText());
	        insertStatement.setString(4, currVisitPtHeight.getText());
	        insertStatement.setString(5, currVisitPtWeight.getText());
	        insertStatement.setString(6, currVisitPtTemp.getText());
	        insertStatement.setString(7, currVisitPtBP.getText());
	        insertStatement.setString(8, currVisitPtImm.getText());
	        insertStatement.setString(9, currVisitPtAlrg.getText());
	        insertStatement.setString(10, currVisitPtMedNotes.getText());
	        insertStatement.setString(11, currVisitPtPresc.getText());
	        insertStatement.setString(12, currVisitPtDiag.getText());

	        int rowsAffected = insertStatement.executeUpdate();
	        System.out.println("Rows affected: " + rowsAffected);

	        insertStatement.close();
	    } catch (SQLException ex) {
	        // Handle SQL exceptions
	        ex.printStackTrace();
	    } finally {
	        // Close the connection in the finally block
	        try {
	            if (connect != null) connect.close();
	        } catch (SQLException ex) {
	            ex.printStackTrace();
	        }
	    }
	}
	
//	Team #3 Current visit, this displays a warning box when no 
//	patient is selected / selectedPatient = null;
	private void showWarningDialog(String title, String content) {
	    Platform.runLater(() -> {
	        Alert alert = new Alert(AlertType.WARNING);
	        alert.setTitle(title);
	        alert.setHeaderText(null);
	        alert.setContentText(content);
	        alert.showAndWait();
	    });
	}
	
//	Doctor Log out button
	public void docLogOutButtonOnAction(javafx.event.ActionEvent e) {
		  try {
		        FXMLLoader loader = new FXMLLoader(getClass().getResource("Login.fxml"));
		        Parent root = loader.load();
		        
		        Stage stage = new Stage();
		        stage.setScene(new Scene(root));
		        stage.show();

		        // Close the current patient panel window
		        Stage currentStage = (Stage) docLogOutButton.getScene().getWindow();
		        currentStage.close();
		    } catch (IOException et) {
		        et.printStackTrace();
		    }
		}
}
	


