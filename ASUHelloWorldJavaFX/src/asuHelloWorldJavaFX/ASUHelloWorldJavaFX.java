package asuHelloWorldJavaFX;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.MultipleSelectionModel;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
 
public class ASUHelloWorldJavaFX extends Application {
	
	private List<User> users = new ArrayList<User>();
	
	private List<String> tasks = new ArrayList<String>();
	
	private User loggedInUser;
	
	private String task;
	
    public static void main(String[] args) {
        launch(args);
    }
    
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Planning Poker");
        
        Label username = new Label();
        username.setText("Username");
        username.setUnderline(true);
        
        Label password = new Label();
        password.setText("Password");
        password.setUnderline(true);
        password.setTextAlignment(TextAlignment.CENTER);
        
        TextField tfUsername = new TextField();
        PasswordField passwordField = new PasswordField();
        
        Alert alert = new Alert(AlertType.ERROR);
        
        Button btn = createButton("Login");
        btn.setOnAction(new EventHandler<>() {
            public void handle(ActionEvent event) {
                System.out.println("Hasan Shahid: yo!");
                System.out.println("User data: " + tfUsername.getText() + " " + passwordField.getText());
                User user = getUser(tfUsername.getText(), passwordField.getText()).orElse(null);
				if (user == null) {
					alert.setTitle("Login Error");
					alert.setContentText("User with the above username and password is not registered.");
					alert.show();
					start(primaryStage);
				}
				else {
					loggedInUser = user;
					loggedInUser.setIsLoggedIn(Boolean.TRUE);
					createPlanningPokerPage(primaryStage);
				}
            }
        });
        
        Button signupBtn = createButton("Signup");
        signupBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				System.out.println("Hello");
				createSignUpPage(primaryStage);
			}
		});
        
        Button directAccessButton = new Button("Direct Access");
        directAccessButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent actionEvent) {
				createPlanningPokerPage(primaryStage);
			}
		});
        
        GridPane root = new GridPane();
        root.addRow(0, username, tfUsername);
        root.addRow(1, password, passwordField);
        root.addRow(2, btn, signupBtn);
        root.addRow(3, directAccessButton);
        root.setAlignment(Pos.CENTER);
        
        primaryStage.setScene(new Scene(root, 400, 400));
        primaryStage.show();
    }
    
    private Button createButton(String text) {
    	return new Button(text);
    }
    
    private void createSignUpPage(Stage primaryStage) {
    	GridPane gridPane = new GridPane();
    	
    	Label firstName = new Label("First Name");
    	Label lastName = new Label("Last Name");
    	Label role = new Label("Role");
    	Label username = new Label("Username");
    	Label password = new Label("Password");
    	Label confirmPassword = new Label("Confirm Password");
    	
    	TextField tfFirstName = new TextField();
    	TextField tfLastName = new TextField();
    	TextField tfRole = new TextField();
    	TextField tfUsername = new TextField();
    	PasswordField passwordField = new PasswordField();
    	PasswordField confirmPasswordField = new PasswordField();
    	
    	Alert alert = new Alert(AlertType.ERROR);
    	
    	Button signupButton = new Button("Signup");
    	signupButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				System.out.println("Signup page");
				User user = new User(tfFirstName.getText(),
						tfLastName.getText(),
						tfRole.getText(),
						tfUsername.getText(),
						passwordField.getText());
				
				if (checkTextInput(user.getFirstName()) ||
						checkTextInput(user.getLastName()) ||
						checkTextInput(user.getRole())  ||
						checkTextInput(user.getUsername())  ||
						checkTextInput(user.getPassword())  ||
						checkTextInput(confirmPasswordField.getText())) {
					alert.setTitle("Missing field");
					alert.setContentText("All the fields are required.");
					alert.show();
					createSignUpPage(primaryStage);
				}
				else if (!user.getPassword().equals(confirmPasswordField.getText())) {
					alert.setTitle("Password Mismatch");
					alert.setContentText("Password and Confirm Password mismatch.");
					alert.show();
					createSignUpPage(primaryStage);
				}
				else {
					users.add(user);
					printUser();
					start(primaryStage);
				}
			}
		});
    	
    	Button loginButton = new Button("Login");
    	loginButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent actionEvent) {
				start(primaryStage);
			}
		});
    	
    	gridPane.addRow(0, firstName, tfFirstName);
    	gridPane.addRow(1, lastName, tfLastName);
    	gridPane.addRow(2, role, tfRole);
    	gridPane.addRow(3, username, tfUsername);
    	gridPane.addRow(4, password, passwordField);
    	gridPane.addRow(5, confirmPassword, confirmPasswordField);
    	gridPane.addRow(6, signupButton, loginButton);
    	gridPane.setAlignment(Pos.CENTER);
    	
    	primaryStage.setScene(new Scene(gridPane, 400, 400));
    	primaryStage.show();
    }
    
    private void createPlanningPokerPage(Stage primaryStage) {
    	GridPane gridPane = new GridPane();
    	
    	Alert alert = new Alert(AlertType.ERROR);

    	if (loggedInUser == null || !loggedInUser.getIsLoggedIn()) {
    		alert.setTitle("Invalid Access");
    		alert.setContentText("User is not logged in");
    		alert.show();
    		start(primaryStage);
    	}
    	else {
    		Rectangle rectangle = new Rectangle();
        	rectangle.setX(0);
        	rectangle.setY(0);
        	rectangle.setWidth(350);
        	rectangle.setHeight(350);
        	rectangle.setFill(Color.ALICEBLUE);
        	
        	Label username = new Label();
            username.setText("Username");
            username.setUnderline(true);
            
            Text text = new Text();
            text.setText("Sample");
            
            Button deleteButton = new Button("Delete Task");
            Button deleteButtonWithoutAuthorizarion = new Button("Delete Task without Authorization");
            Button addButton = new Button("Add Task");
            Button addTaskWithoutAuthorization = new Button("Add Task without Authorization");

            ListView<String> listView = new ListView<String>();
            ObservableList<String> items = FXCollections.observableArrayList(tasks);
            System.out.println("Items: " + items.size());
            listView.setItems(items);
            
            MultipleSelectionModel<String> lvSelModel = listView.getSelectionModel();
            lvSelModel.selectedItemProperty().addListener(new ChangeListener<String>() {
    			@Override
    			public void changed(ObservableValue<? extends String> changed, String oldVal, String newVal) {
    				task = newVal;
    			}
    		});
            
            deleteButton.setOnAction(new EventHandler<ActionEvent>() {
    			@Override
    			public void handle(ActionEvent actionEvent) {
    				if (tasks.isEmpty()) {
    					alert.setTitle("No Tasks");
    					alert.setContentText("There aren't any task present.");
    					alert.show();
    					createPlanningPokerPage(primaryStage);
    				}
    				else if (task == null) {
    					alert.setTitle("Task Not Selected");
    					alert.setContentText("There aren't any task selected for deletion");
    					alert.show();
    					createPlanningPokerPage(primaryStage);
    				}
    				else {
    					tasks.remove(task);
    					createPlanningPokerPage(primaryStage);
    				}
    			}
    		});
            
            addButton.setOnAction(new EventHandler<ActionEvent>() {
    			
    			@Override
    			public void handle(ActionEvent actionEvent) {
    				addButtonPage(primaryStage);
    			}
    		});
        	
        	gridPane.setHgap(0.1);
        	gridPane.setVgap(0.1);
        	gridPane.setAlignment(Pos.CENTER);
        	
        	gridPane.addRow(0, username, text);
        	gridPane.addRow(1, listView);
        	gridPane.addRow(2, deleteButtonWithoutAuthorizarion);
        	gridPane.addRow(3, addButton, deleteButton);
        	gridPane.addRow(4, addTaskWithoutAuthorization);
//        	gridPane.addRow(1, rectangle);
        	
        	primaryStage.setScene(new Scene(gridPane, 400, 400));
        	primaryStage.show();
    	}
    }
    
    private void addButtonPage(Stage primaryStage) {
    	GridPane gridPane = new GridPane();
    	
    	Label taskLabel = new Label("Please add a task");
    	
    	TextField tfTask = new TextField();
    	
    	Alert alert = new Alert(AlertType.ERROR);
    	
    	Button taskButton = new Button("Add Task");
    	taskButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent actionEvent) {
				if (checkTextInput(tfTask.getText())) {
					alert.setTitle("Missing Task");
					alert.setContentText("Please add the task name.");
					alert.show();
					addButtonPage(primaryStage);
				}
				else {
					tasks.add(tfTask.getText());
					createPlanningPokerPage(primaryStage);
				}
			}
		});
    	
    	gridPane.setHgap(0.1);
    	gridPane.setVgap(0.1);
    	gridPane.setAlignment(Pos.CENTER);
    	
    	gridPane.addRow(0, taskLabel);
    	gridPane.addRow(1, tfTask);
    	gridPane.addRow(2, taskButton);
    	
    	primaryStage.setScene(new Scene(gridPane, 400, 400));
    	primaryStage.show();
    }
    
    private void printUser() {
    	users.stream().forEach(user -> {
    		System.out.println(user.getFirstName() + " " + user.getLastName() + " " + user.getRole() + " " + user.getUsername() + " " + user.getPassword());
    	});
    }
    
    private boolean checkTextInput(String value) {
    	return value == null || value.isBlank() || value.isEmpty();
    }
    
    private Optional<User> getUser(final String username, final String password) {
    	return users.stream()
    			.filter(user -> user.getUsername().equals(username) && user.getPassword().equals(password))
    			.findFirst();
    }
}