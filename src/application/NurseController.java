package application;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class NurseController {
	String activeUser;

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
	private Button newPatient;
	
	@FXML
	private ComboBox<String> selDoctor;
	
	@FXML
	public void newPatient(javafx.event.ActionEvent e) {
		Connection connect;
		try {
			connect = DriverManager.getConnection("jdbc:sqlite:./MainDatabase.sqlite");
			PreparedStatement newPatientStatement = connect.prepareStatement("INSERT INTO PatientRecord (first_name, last_name, address," +
					"phone_number, ins_id, pharmacy, health_history,immunizations,"
					+" medications, allergies, assigned_doctor,DOB) VALUES (?,?,?,?,?,?,?,?,?,?,?,?)");
			newPatientStatement.setString(1, "Test");
			newPatientStatement.setString(2, "Test");
			newPatientStatement.setString(3, "Test");
			newPatientStatement.setString(4, "Test");
			newPatientStatement.setString(5, "Test");
			newPatientStatement.setString(6, "Test");
			newPatientStatement.setString(7, "Test");
			newPatientStatement.setString(8, "Test");
			newPatientStatement.setString(9, "Test");
			newPatientStatement.setString(10, "Test");
			newPatientStatement.setString(11, "Test");
			newPatientStatement.setString(12, "Test");
			
			newPatientStatement.executeUpdate();
			newPatientStatement.close();
			connect.close();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	
}
