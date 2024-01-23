package application;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class docDashboard extends Application {

    @Override
    public void start(Stage primaryStage) {
        // Set the title of the application window
        primaryStage.setTitle("Doctor Dashboard");

        // Create the root layout for the application using BorderPane
        BorderPane root = new BorderPane();

        // Create a menu bar to hold different menus
        MenuBar menuBar = new MenuBar();

        // Create individual menus
        Menu fileMenu = createMenu("File");
        Menu homeMenu = createMenu("Home");
        Menu searchMenu = createMenu("Search");

        // Create menu items for each menu
        MenuItem openItem = createMenuItem("Open");
        MenuItem saveItem = createMenuItem("Save");
        MenuItem signOutItem = createMenuItem("Sign Out");
        
        // Set an event handler to handle sign out action
        signOutItem.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                // Close the current window
                primaryStage.close();
                // Start a new instance of the login screen (assuming Main is the login class)
                Main returnLogin = new Main();
                returnLogin.start(new Stage());
            }
        });

       //Menu Items for home and search " they have no functionality as of right now "
        MenuItem homeItem1 = createMenuItem("Option 1");
        MenuItem homeItem2 = createMenuItem("Option 2");

        MenuItem searchItem1 = createMenuItem("Search 1");
        MenuItem searchItem2 = createMenuItem("Search 2");

        // Add menu items to the corresponding menus
        fileMenu.getItems().addAll(openItem, saveItem, new SeparatorMenuItem(), signOutItem);
        homeMenu.getItems().addAll(homeItem1, homeItem2);
        searchMenu.getItems().addAll(searchItem1, searchItem2);

        // Add menus to the menu bar
        menuBar.getMenus().addAll(fileMenu, homeMenu, searchMenu);

        // Create a tab-style menu bar
        TabPane tabPane = new TabPane();
        tabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);

        // Create tabs
        Tab patientTab = createTab("Patient");
        Tab messageTab = createTab("Message");
        Tab prescriptionsTab = createTab("Prescriptions");

        // Create content for each tab
        VBox patientContent = new VBox();
        patientContent.getChildren().add(createButton("Patient Records"));

        VBox messageContent = new VBox();
        messageContent.getChildren().add(createButton("Message Center"));

        VBox prescriptionsContent = new VBox();
        prescriptionsContent.getChildren().add(createButton("Prescriptions & Orders"));

        // Set content for each tab
        patientTab.setContent(patientContent);
        messageTab.setContent(messageContent);
        prescriptionsTab.setContent(prescriptionsContent);

        // Add tabs to the tab pane
        tabPane.getTabs().addAll(patientTab, messageTab, prescriptionsTab);

        // Set the layout for the root BorderPane
        root.setTop(menuBar);
        root.setCenter(tabPane);

        // Create the main scene with the root layout
        Scene scene = new Scene(root, 600, 400);
        primaryStage.setScene(scene);

        // Show the primary stage
        primaryStage.show();
    }

    // Helper methods to create Menu, MenuItem, Tab, and Button
    private Menu createMenu(String text) {
        Menu menu = new Menu(text);
        return menu;
    }

    private MenuItem createMenuItem(String text) {
        MenuItem menuItem = new MenuItem(text);
        return menuItem;
    }

    private Tab createTab(String text) {
        Tab tab = new Tab(text);
        return tab;
    }

    private Button createButton(String text) {
        Button button = new Button(text);
        button.setPrefWidth(Double.MAX_VALUE);
        button.setStyle("-fx-background-color: #555; -fx-text-fill: white;");
        return button;
    }

    // Entry point for the JavaFX application
    public static void main(String[] args) {
        launch(args);
    }
}
