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
import javafx.scene.control.ListView;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
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
  private TextField PvisitPdateofvisit, PvisitPheight, PvisitPweight, PvisitPtemperature, PvisitPbloodpressure, PvisitPname, Pvisitdob,
  Pvisitaddress, PvisitPnumber, PvisitInsurance, PvisitPpharmacy;
  @FXML
  private TextArea PvisitPimmunizations, PvisitPAllergies, PvisitPperscriptions, PvisitPdiagnoses, PvisitPnotes;
  @FXML
	private Tab PrevVisitTab;
  @FXML
  private TableView<String> PrevVisitsTable;
  
//Team #3 ********Previous Visit Tab Method*******
	public void pullPreviousVisit() {
		Connection connect;
		
		try {
			connect = DriverManager.getConnection("jdbc:sqlite:./MainDatabase.sqlite");
			//sqlite statement
			PreparedStatement PreviousVisitstatement = connect.prepareStatement("SELECT * FROM Visit WHERE patient_id = ? AND completed IS NOT NULL");
			PreviousVisitstatement.setString(1, activeUser);
			ResultSet rs = PreviousVisitstatement.executeQuery();
			//Clears Table data and columns
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
				Map<String, String> firstVisitData = visits.get(0);
				if(firstVisitData == null) {
					System.out.println("Empty visits List");
				}
				else {
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
    PatientRecord.readTo(user, currRecord);
    PatientRecord.readTo(user, PvisitPname, Pvisitdob, Pvisitaddress,
        PvisitPnumber, PvisitInsurance, PvisitPpharmacy);
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
        // messageText.getChildren().addAll(new
        // Text(resultSet.getString("sender") + "\n" + "\n" +
        // resultSet.getString("content") + "\n" + "\n" + "\n" + "\n"));
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
  public void updateDB() {
    PatientRecord.updateWith(activeUser, firstName, lastName, dob, address,
        pNum, insID, pharm, hHistory, immuni, med,
        allergies);
    currRecord.clear();
    updateText(activeUser);
  }
}
