package application;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class patientPortal extends Application {

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Patient Portal");

        BorderPane root = new BorderPane();

        // Create a menu bar
        MenuBar menuBar = new MenuBar();

        // Create menus
        Menu fileMenu = createMenu("File");
        Menu homeMenu = createMenu("Home");
        Menu searchMenu = createMenu("Search");

        // Create menu items for each menu
        MenuItem openItem = createMenuItem("Open");
        MenuItem saveItem = createMenuItem("Save");
        MenuItem signOutItem = createMenuItem("Sign Out");
        
        //Event handler to exitItem
        signOutItem.setOnAction(new EventHandler<ActionEvent>() {
        	public void handle(ActionEvent event) {
        		primaryStage.close();
        		Main returnLogin = new Main();
        		returnLogin.start(new Stage());
        	}
        });

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

        Tab patientVisitsTab = createTab("Visits");
        Tab messageTab = createTab("Message");


        // Create content for each tab
        VBox patientContent = new VBox();
        patientContent.getChildren().add(createButton("Visits"));

        VBox messageContent = new VBox();
        messageContent.getChildren().add(createButton("Message Center"));



        patientVisitsTab.setContent(patientContent);
        messageTab.setContent(messageContent);


        // Add tabs to the tab pane
        tabPane.getTabs().addAll(patientVisitsTab, messageTab);

        root.setTop(menuBar);
        root.setCenter(tabPane);

        Scene scene = new Scene(root, 600, 400);
        primaryStage.setScene(scene);

        primaryStage.show();
    }

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

    public static void main(String[] args) {
        launch(args);
    }
}

