package application;

import java.sql.*;

public class SqliteConnection {

	
    // Establishes a connection to the SQLite database/loads SQLite JDBC driver
    public static Connection Connector() {
        try {
            Class.forName("org.sqlite.JDBC");
            Connection connect = DriverManager.getConnection("jdbc:sqlite:C:\\Users\\jaron\\OneDrive\\Desktop\\CSE_360_IntroSoftware_Engineering\\Program_Files\\GroupProject_SceneBuilder_LoginPanel_SQLiteDB\\SQLite_Database\\MainDatabase.sqlite");
            createTables(connect); // Create all tables
            insertDataIntoLoginTable(connect); // Insert initial data into the Login table
            return connect;
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e);
            return null; // or handle the exception according to your application's requirements
        }
    }

    // Method to create all tables
    private static void createTables(Connection connection) throws SQLException {
        createTable(connection, "Login", "user_id STRING PRIMARY KEY, password STRING NOT NULL, user_type STRING NOT NULL");
        createTable(connection, "UserType", "user_id STRING PRIMARY KEY, user_type STRING, first_name STRING, last_name STRING");
        createTable(connection, "PatientRecord", "patient_id STRING PRIMARY KEY, first_name STRING, last_name STRING, address STRING, "
        						+ "phone_number STRING, ins_id STRING, pharmacy STRING, health_history STRING, immunizations STRING, "
        						+ "medications STRING, allergies STRING");
        createTable(connection, "Visit", "patient_id STRING, doctor_id STRING, date STRING, height STRING, "
        						+ "weight STRING, temperature STRING, blood_pressure STRING, immunization STRING, allergies STRING,"
        						+ " notes STRING, prescription STRING, visit_diag STRING, completed STRING");
        createTable(connection, "Message", "patient_id STRING, message_id STRING, sender STRING, recipients STRING, header STRING, content STRING");
    }

    // Method to create a table
    private static void createTable(Connection connection, String tableName, String columns) throws SQLException {
        String sql = "CREATE TABLE IF NOT EXISTS " + tableName + " (" + columns + ")";
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(sql);
        }
    }

    // Method to insert initial data into the Login table
    private static void insertDataIntoLoginTable(Connection connection) throws SQLException {
        String sql = "INSERT INTO Login (user_id, password, user_type) VALUES (?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            // Insert record for MD1234
            preparedStatement.setString(1, "MD1234");
            preparedStatement.setString(2, "MD1234");
            preparedStatement.setString(3, "Physician");
            preparedStatement.executeUpdate();

            preparedStatement.clearParameters();

            // Insert record for Nurse1234
            preparedStatement.setString(1, "NR1234");
            preparedStatement.setString(2, "NR1234");
            preparedStatement.setString(3, "Nurse");
            preparedStatement.executeUpdate();

            // Reset parameters
            preparedStatement.clearParameters();

            // Insert record for PT1234
            preparedStatement.setString(1, "PT1234");
            preparedStatement.setString(2, "PT1234");
            preparedStatement.setString(3, "Patient");
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            // Check if the error is due to duplicate key violation
            if (e.getMessage().contains("UNIQUE constraint failed: Login.user_id")) {
                // Ignores duplicate key errors 
            } else {
                // Re-throw other SQL exceptions
                throw e;
            }
        }
    }
}