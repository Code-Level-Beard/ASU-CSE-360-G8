package application;

import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.fxml.FXML;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class PatientController {
	String activeUser;
	
	@FXML
	private TextArea currRecord;
	
	@FXML
	private TextField nField;
	
	@FXML 
	private Button update;
	
	
	
	public void updateText(String user) {
		activeUser = user;
		Connection connect;
		try {
			connect = DriverManager.getConnection("jdbc:sqlite:./MainDatabase.sqlite");
			PreparedStatement statement = connect.prepareStatement("SELECT patient_id,first_name, last_name FROM PatientRecord WHERE patient_id = ?");
			statement.setString(1, user);
			ResultSet resultSet = statement.executeQuery();
			while (resultSet.next()) {
				nField.setText(resultSet.getString("first_name") + " " + resultSet.getString("last_name"));
				currRecord.appendText("Name: " + resultSet.getString("first_name") + " " + resultSet.getString("last_name")+"\n");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}