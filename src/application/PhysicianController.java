package application;


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
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

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
	
	// Previous Visits Tab
	  @FXML
	  private TextField PvisitPdateofvisit, PvisitPheight, PvisitPweight, PvisitPtemperature, PvisitPbloodpressure, PvisitPname, Pvisitdob,
	  Pvisitaddress, PvisitPnumber, PvisitInsurance, PvisitPpharmacy;
	  @FXML
	  private TextArea PvisitPimmunizations, PvisitPAllergies, PvisitPperscriptions, PvisitPdiagnoses, PvisitPnotes;
	  @FXML
	  private TableView<String> PrevVisitsTable;
	  
	//Team #3 ********Previous Visit Tab Method*******
			public void pullPreviousVisit() {
				Connection connect;
				if(selectedPatient != null) {
					updatePreviousVisitInfo();
				try {
					connect = DriverManager.getConnection("jdbc:sqlite:./MainDatabase.sqlite");
					//sqlite statement
					
					PreparedStatement PreviousVisitstatement = connect.prepareStatement("SELECT * FROM Visit WHERE patient_id = ? AND completed IS NOT NULL");
					PreviousVisitstatement.setString(1, selectedPatient);
					ResultSet rs = PreviousVisitstatement.executeQuery();
					//Clears Table data and columns
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
						PrevVisitsTable.getColumns().clear();
						PrevVisitsTable.getItems().clear();
						// List to hold each row from query, visit data
						List<Map<String, String>> visits = new ArrayList<>();
						
					    //creates Table column and adds it to Table
						TableColumn<String, String> visitDateCol = new TableColumn<>("Visit Date");
					    visitDateCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue()));
					    ObservableList<String> visitDates = FXCollections.observableArrayList();
					    PrevVisitsTable.getColumns().add(visitDateCol);
					    //Clears data from our Lists
					    visitDates.clear();
					    visits.clear();
						// Process the result set and parse into a new Hash Map for each entry of visits Array List
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
						    
						    //adds date into table
						    visitDates.add(visitData.get("date"));
						    PrevVisitsTable.setItems(visitDates);
						    // Add data for the current visit to the list
						    visits.add(visitData);
						    
						}
						//Showing first visit in the table
						
						if(visits.isEmpty()) {
							System.out.println("Empty visits List");
							visitDates.add("No Previous Visits to Display");
						    PrevVisitsTable.setItems(visitDates);
						}
						else {
							
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
						
						//Listener for each entry in the table. when one is selected, newVal equals that date
						PrevVisitsTable.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
				            if (newVal != null) {
				            	String selectedDate = newVal; //Date selected in table

				                // Find the index of the visit with the selected date in the ArrayList
				                int selectedIndex = -1;
				                for (int i = 0; i < visits.size(); i++) {
				                    String visitDate = visits.get(i).get("date"); 
				                    if (visitDate.equals(selectedDate)) {
				                        selectedIndex = i;
				                        break;
				                    }
				                }
				                // If a visit with the selected date was found, populate the text fields with its information
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
				    				
				    				PvisitPdateofvisit.appendText(selectedVisitData.get("date"));
				    				PvisitPheight.appendText(selectedVisitData.get("height"));
				    				PvisitPweight.appendText(selectedVisitData.get("weight"));
				    				PvisitPtemperature.appendText(selectedVisitData.get("temperature"));
				    				PvisitPbloodpressure.appendText(selectedVisitData.get("blood_pressure"));
				    				PvisitPimmunizations.appendText(selectedVisitData.get("immunization"));
				    				PvisitPAllergies.appendText(selectedVisitData.get("allergies"));
				    				PvisitPnotes.appendText(selectedVisitData.get("notes"));
				    				PvisitPperscriptions.appendText(selectedVisitData.get("prescription"));
				    				PvisitPdiagnoses.appendText(selectedVisitData.get("visit_diag"));
				                }
				             
				            }
				        });
						}
					
					rs.close();
					PreviousVisitstatement.close();
					connect.close();
				}
					catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					}
				}
			}
			public void updatePreviousVisitInfo() {
			    PatientRecord.readTo(selectedPatient, PvisitPname, Pvisitdob, Pvisitaddress,
			        PvisitPnumber, PvisitInsurance, PvisitPpharmacy);
			  }
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
}

