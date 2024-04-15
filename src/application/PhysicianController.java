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
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;

public class PhysicianController {
  String activeUser;

  String selectedPatient;

  String docID, docName, messageID;

  Integer messageNum;

  @FXML
  private Button sendButton;

  @FXML
  private TextArea composeMessage;

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
  // Team #3 fx:id textFields and buttons

  // Team #3 fx:id textFields that get / hold the current visit data
  @FXML
  private TextField currVisitPtName, currVisitPtDOB, currVisitPtAdd,
      currVisitPtPhNum, currVisitInsID, currVisitPtPharm;

  // Team #3 fx:id buttons and textFields for save visit / complete visit
  @FXML
  private TextField currVisitDateOfVisit, currVisitPtHeight, currVisitPtWeight,
      currVisitPtTemp, currVisitPtBP;

  @FXML
  private TextArea currVisitPtMedNotes, currVisitPtImm, currVisitPtAlrg,
      currVisitPtPresc, currVisitPtDiag;

  @FXML
  private Button currVisitCompVisitOnAction, currVisitSaveOnAction;

  // Log out button for physician controller kicks you back to the login
  // screen
  @FXML
  private Button docLogOutButton;

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
  private ScrollPane messageTextSP;

  // Team #3 ********Previous Visit Tab Method*******
  public void pullPreviousVisit() {
    Connection connect;
    if (selectedPatient != null) {
      updatePreviousVisitInfo();
      try {
        connect = DriverManager.getConnection("jdbc:sqlite:./MainDatabase.sqlite");
        // sqlite statement

        PreparedStatement PreviousVisitstatement = connect.prepareStatement(
            "SELECT * FROM Visit WHERE patient_id = ? AND completed = ?");
        PreviousVisitstatement.setString(1, selectedPatient);
        PreviousVisitstatement.setString(2, "C");
        ResultSet rs = PreviousVisitstatement.executeQuery();
        // Clears Table data and columns
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

        // creates Table column and adds it to Table
        TableColumn<String, String> visitDateCol = new TableColumn<>("Visit Date");
        visitDateCol.setCellValueFactory(
            cellData -> new SimpleStringProperty(cellData.getValue()));
        ObservableList<String> visitDates = FXCollections.observableArrayList();
        PrevVisitsTable.getColumns().add(visitDateCol);
        // Clears data from our Lists
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

  public void updatePreviousVisitInfo() {
    PatientRecord.readTo(selectedPatient, PvisitPname, Pvisitdob, Pvisitaddress,
        PvisitPnumber, PvisitInsurance, PvisitPpharmacy);
  }

  public void updateText() {
    patientRecord.clear();
    PatientRecord.readTo(selectedPatient, patientRecord);
  }

  @FXML
  public void sendMessage(String patient) {
    Connection connectDoc;
    Connection connectDocID;
    try {
      connectDoc = DriverManager.getConnection("jdbc:sqlite:./MainDatabase.sqlite");
      PreparedStatement docStatement = connectDoc.prepareStatement(
          "SELECT assigned_doctor FROM PatientRecord where patient_id = ?");
      docStatement.setString(1, patient);
      ResultSet resultSet = docStatement.executeQuery();
      docID = resultSet.getString("assigned_doctor");
      resultSet.close();
      docStatement.close();
      connectDoc.close();
      connectDocID = DriverManager.getConnection("jdbc:sqlite:./MainDatabase.sqlite");
      PreparedStatement docIDStatement = connectDocID.prepareStatement(
          "SELECT first_name, last_name FROM UserType where user_id = ?");
      docIDStatement.setString(1, docID);
      ResultSet resultSet2 = docIDStatement.executeQuery();
      docName = resultSet2.getString("first_name") + " " +
          resultSet2.getString("last_name");
      resultSet2.close();
      docIDStatement.close();
      connectDocID.close();
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
            newMessageStatement.setString(3, docName); // insert sender
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
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    messageSelect(activeUser);
    messageTextSP.layout();
    messageTextSP.setVvalue(1.0f);
    
  }

  public void messageSelect(String user) {
    messageThreadArea.getChildren().clear();
    Connection connectRecord;
    Connection connectMessage;

    try {
      connectRecord = DriverManager.getConnection("jdbc:sqlite:./MainDatabase.sqlite");
      connectMessage = DriverManager.getConnection("jdbc:sqlite:./MainDatabase.sqlite");

      PreparedStatement statementRecord = connectRecord.prepareStatement(
          "SELECT patient_id, first_name, last_name  FROM PatientRecord WHERE assigned_doctor = ?");
      statementRecord.setString(1, user);
      ResultSet resultSetRecord = statementRecord.executeQuery();

      while (resultSetRecord.next()) {

        PreparedStatement statementMessage = connectMessage.prepareStatement(
            "SELECT MAX(message_id), sender, header, content FROM Message WHERE patient_id = ?");
        statementMessage.setString(1, resultSetRecord.getString("patient_id"));
        ResultSet resultSetMessage = statementMessage.executeQuery();
        String patientID = resultSetRecord.getString("patient_id");
        String patientFirst = resultSetRecord.getString("first_name");
        String patientLast = resultSetRecord.getString("last_name");

        while (resultSetMessage.next()) {

          Hyperlink sender = new Hyperlink();
          Hyperlink unread = new Hyperlink();
          Text spacer = new Text("\n");

          sender.setText(patientFirst + " " + patientLast + "\n\n\n");
          sender.setFont(Font.font("Verdana", FontWeight.BOLD, 14));
          sender.setFocusTraversable(false);
          sender.setOnAction(e -> {
            displayMessages(patientID);
            sendMessage(patientID);
          });
          if ("new".equals(resultSetMessage.getString("header"))) {
            unread.setText("NEW");
            unread.setFont(Font.font("Verdana", FontWeight.BOLD, 12));
            unread.setFocusTraversable(false);
            messageThreadArea.getChildren().addAll(unread, sender, spacer);
          } else {
            messageThreadArea.getChildren().addAll(sender, spacer);
          }
        }

        resultSetMessage.close();
        statementMessage.close();
      }

      resultSetRecord.close();
      statementRecord.close();
      connectRecord.close();
      connectMessage.close();

    } catch (SQLException e) {
      // TODO error message
      e.printStackTrace();
    }
  }

  public void genPLPatientComboBox() {
    pLComboBox.getItems().clear();
    Connection connect;
    try {
      connect = DriverManager.getConnection("jdbc:sqlite:./MainDatabase.sqlite");
      PreparedStatement patients = connect.prepareStatement(
          "SELECT first_name, last_name, patient_id FROM PatientRecord WHERE assigned_doctor = ?");
      patients.setString(1, activeUser);
      ResultSet patientList = patients.executeQuery();
      while (patientList.next()) {
        pLComboBox.getItems().add(
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

  @FXML
  public void getSelectedPatient(javafx.event.ActionEvent e) {
    selectedPatient = pLComboBox.getValue().substring(
        pLComboBox.getValue().length() - 6, pLComboBox.getValue().length());
  }

  // Team #3 Current Visit tab
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
        currVisitPtName.setText(patientResultSet.getString("first_name") + " " +
            patientResultSet.getString("last_name"));
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
        if (connect != null)
          connect.close();
      } catch (SQLException e) {
        e.printStackTrace();
      }
    }
  }

  // Team #3 Save Visit Button / not completed visit
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
          "Update Visit SET doctor_id = ?, date = ?, height = ?, weight = ?, temperature = ?, blood_pressure = ?, immunization = ?, "
              +
              "allergies = ?, notes = ?, prescription = ?, visit_diag = ?, completed = ? WHERE patient_id = ?");
      insertStatement.setString(1, doctorId);
      insertStatement.setString(2, currVisitDateOfVisit.getText());
      insertStatement.setString(3, currVisitPtHeight.getText());
      insertStatement.setString(4, currVisitPtWeight.getText());
      insertStatement.setString(5, currVisitPtTemp.getText());
      insertStatement.setString(6, currVisitPtBP.getText());
      insertStatement.setString(7, currVisitPtImm.getText());
      insertStatement.setString(8, currVisitPtAlrg.getText());
      insertStatement.setString(9, currVisitPtMedNotes.getText());
      insertStatement.setString(10, currVisitPtPresc.getText());
      insertStatement.setString(11, currVisitPtDiag.getText());
      insertStatement.setString(12, "NC");
      insertStatement.setString(13, selectedPatient);
      int rowsAffected = insertStatement.executeUpdate();
      System.out.println("Rows affected: " + rowsAffected);
      System.out.println("SQL String:");
      System.out.println(insertStatement.toString());

      insertStatement.close();
    } catch (SQLException ex) {
      // Handle SQL exceptions
      ex.printStackTrace();
    } finally {
      // Close the connection in the finally block
      try {
        if (connect != null)
          connect.close();
      } catch (SQLException ex) {
        ex.printStackTrace();
      }
    }
  }

  @FXML
  public void currVisitCompVisitOnAction(javafx.event.ActionEvent e) {
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
    		  "Update Visit SET doctor_id = ?, date = ?, height = ?, weight = ?, temperature = ?, blood_pressure = ?, immunization = ?, "
    	              +
    	              "allergies = ?, notes = ?, prescription = ?, visit_diag = ?, completed = ? WHERE patient_id = ? AND completed = ?");
    	      insertStatement.setString(1, doctorId);
    	      insertStatement.setString(2, currVisitDateOfVisit.getText());
    	      insertStatement.setString(3, currVisitPtHeight.getText());
    	      insertStatement.setString(4, currVisitPtWeight.getText());
    	      insertStatement.setString(5, currVisitPtTemp.getText());
    	      insertStatement.setString(6, currVisitPtBP.getText());
    	      insertStatement.setString(7, currVisitPtImm.getText());
    	      insertStatement.setString(8, currVisitPtAlrg.getText());
    	      insertStatement.setString(9, currVisitPtMedNotes.getText());
    	      insertStatement.setString(10, currVisitPtPresc.getText());
    	      insertStatement.setString(11, currVisitPtDiag.getText());
    	      insertStatement.setString(12, "C");
    	      insertStatement.setString(13, selectedPatient);
    	      insertStatement.setString(14, "NC");

      int rowsAffected = insertStatement.executeUpdate();
      System.out.println("Rows affected: " + rowsAffected);

      currVisitDateOfVisit.clear();
      currVisitPtHeight.clear();
      currVisitPtWeight.clear();
      currVisitPtTemp.clear();
      currVisitPtBP.clear();
      currVisitPtImm.clear();
      currVisitPtAlrg.clear();
      currVisitPtMedNotes.clear();
      currVisitPtPresc.clear();
      currVisitPtDiag.clear();

      insertStatement.close();
    } catch (SQLException ex) {
      // Handle SQL exceptions
      ex.printStackTrace();
    } finally {
      // Close the connection in the finally block
      try {
        if (connect != null)
          connect.close();
      } catch (SQLException ex) {
        ex.printStackTrace();
      }
    }
  }

  // Doctor Log out button
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

  // Team #3 Current visit, this displays a warning box when no
  // patient is selected / selectedPatient = null;
  private void showWarningDialog(String title, String content) {
    Platform.runLater(() -> {
      Alert alert = new Alert(AlertType.WARNING);
      alert.setTitle(title);
      alert.setHeaderText(null);
      alert.setContentText(content);
      alert.showAndWait();
    });
  }
}
