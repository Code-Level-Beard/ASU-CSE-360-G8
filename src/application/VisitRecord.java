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
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class VisitRecord {

  public static void newRecord(String patientId, String doctorId,
      TextField date, TextField height,
      TextField weight, TextField temperature,
      TextField bloodPressure, TextArea immunizations,
      TextArea allergies, TextArea notes) {

    try {
      Connection connect = DriverManager.getConnection("jdbc:sqlite:./MainDatabase.sqlite");
      // Prepare a SQL INSERT statement for Visit table
      PreparedStatement insertVisit = connect.prepareStatement(
          "INSERT INTO Visit (patient_id, doctor_id, date, height, weight, temperature, blood_pressure, immunization, allergies, notes,completed, prescription, visit_diag) "
              + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");

      // Set parameters for the prepared statement
      insertVisit.setString(1, patientId); // Using the current patient ID
      insertVisit.setString(2, doctorId); // Using the current doctor ID
      insertVisit.setString(3, date.getText().trim());
      insertVisit.setString(4, height.getText().trim());
      insertVisit.setString(5, weight.getText().trim());
      insertVisit.setString(6, temperature.getText().trim());
      insertVisit.setString(7, bloodPressure.getText().trim());
      insertVisit.setString(8, immunizations.getText().trim());
      insertVisit.setString(9, allergies.getText().trim());
      insertVisit.setString(10, notes.getText().trim());
      insertVisit.setString(11, "NC");
      insertVisit.setString(12, "UNK");
      insertVisit.setString(13, "UNK");

      date.clear();
      height.clear();
      weight.clear();
      temperature.clear();
      bloodPressure.clear();
      immunizations.clear();
      allergies.clear();
      notes.clear();

      // Execute the INSERT statement into DB
      insertVisit.executeUpdate();

      // Closes out insert statements / closes DB connection
      insertVisit.close();
      connect.close();

    } catch (SQLException ex) {
      ex.printStackTrace(); // Handle exceptions appropriately
    }
  }

  public static void readTo(String patientId, TextField date, TextField weight,
      TextField height, TextField pharmacy,
      TextField temperature, TextField bloodPressure,
      TextArea immunizations, TextArea prescriptions,
      TextArea diagnoses, TextArea allergies,
      TextArea notes, TableView<String> visitList) {
    Connection connect;
    try {
      connect = DriverManager.getConnection("jdbc:sqlite:./MainDatabase.sqlite");
      // sqlite statement

      PreparedStatement sqlStatement = connect.prepareStatement(
          "SELECT * FROM Visit WHERE patient_id = ? AND completed = ?");
      sqlStatement.setString(1, patientId);
      sqlStatement.setString(2, "C");
      ResultSet rs = sqlStatement.executeQuery();
      // Clears Table data and columns
      visitList.getColumns().clear();
      visitList.getItems().clear();
      // List to hold each row from query, visit data
      List<Map<String, String>> visits = new ArrayList<>();

      // creates Table column and adds it to Table
      TableColumn<String, String> visitDateCol = new TableColumn<>("Visit Date");
      visitDateCol.setCellValueFactory(
          cellData -> new SimpleStringProperty(cellData.getValue()));
      ObservableList<String> visitDates = FXCollections.observableArrayList();
      visitList.getColumns().add(visitDateCol);
      // Clears data from our Lists
      date.clear();
      height.clear();
      weight.clear();
      temperature.clear();
      bloodPressure.clear();
      immunizations.clear();
      allergies.clear();
      notes.clear();
      prescriptions.clear();
      diagnoses.clear();
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
        visitList.setItems(visitDates);
        // Add data for the current visit to the list
        visits.add(visitData);
      }
      // Showing first visit in the table

      if (visits.isEmpty()) {
        visitDates.add("No Previous Visits to Display");
        visitList.setItems(visitDates);
      } else {

        Map<String, String> firstVisitData = visits.get(0);
        date.clear();
        height.clear();
        weight.clear();
        temperature.clear();
        bloodPressure.clear();
        immunizations.clear();
        allergies.clear();
        notes.clear();
        prescriptions.clear();
        diagnoses.clear();

        date.appendText(firstVisitData.get("date"));
        height.appendText(firstVisitData.get("height"));
        weight.appendText(firstVisitData.get("weight"));
        temperature.appendText(firstVisitData.get("temperature"));
        bloodPressure.appendText(firstVisitData.get("blood_pressure"));
        immunizations.appendText(firstVisitData.get("immunization"));
        allergies.appendText(firstVisitData.get("allergies"));
        notes.appendText(firstVisitData.get("notes"));
        prescriptions.appendText(firstVisitData.get("prescription"));
        diagnoses.appendText(firstVisitData.get("visit_diag"));

        // Listener for each entry in the table. when one is selected, newVal
        // equals that date
        visitList.getSelectionModel().selectedItemProperty().addListener(
            (obs, oldVal, newVal) -> {
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
                  date.clear();
                  height.clear();
                  weight.clear();
                  temperature.clear();
                  bloodPressure.clear();
                  immunizations.clear();
                  allergies.clear();
                  notes.clear();
                  prescriptions.clear();
                  diagnoses.clear();

                  date.appendText(selectedVisitData.get("date"));
                  height.appendText(selectedVisitData.get("height"));
                  weight.appendText(selectedVisitData.get("weight"));
                  temperature.appendText(selectedVisitData.get("temperature"));
                  bloodPressure.appendText(
                      selectedVisitData.get("blood_pressure"));
                  immunizations.appendText(
                      selectedVisitData.get("immunization"));
                  allergies.appendText(selectedVisitData.get("allergies"));
                  notes.appendText(selectedVisitData.get("notes"));
                  prescriptions.appendText(
                      selectedVisitData.get("prescription"));
                  diagnoses.appendText(selectedVisitData.get("visit_diag"));
                }
              }
            });
      }
      rs.close();
      sqlStatement.close();
      connect.close();
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }
}
