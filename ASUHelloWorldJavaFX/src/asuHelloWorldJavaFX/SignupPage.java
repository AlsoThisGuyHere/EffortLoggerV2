package asuHelloWorldJavaFX;

import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class SignupPage {
	
	private ASUHelloWorldJavaFX main = new ASUHelloWorldJavaFX();
	
	public void createSignUpPage(Stage primaryStage, List<User> users) {
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
					createSignUpPage(primaryStage, users);
				}
				else if (!regexChecker("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$", user.getPassword())) {
					alert.setTitle("Password Error");
					alert.setContentText("Passwords need to have minimum eight characters, at least one uppercase letter, one lowercase letter, one number and one special character.");
					alert.show();
					createSignUpPage(primaryStage, users);
				}
				else if (!regexChecker("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{6,}$", user.getUsername())) {
					alert.setTitle("Username Error");
					alert.setContentText("Username need to have minimum six characters, at least one uppercase letter, one lowercase letter, one number and one special character.");
					alert.show();
					createSignUpPage(primaryStage, users);
				}
				else if (!user.getPassword().equals(confirmPasswordField.getText())) {
					alert.setTitle("Password Mismatch");
					alert.setContentText("Password and Confirm Password mismatch.");
					alert.show();
					createSignUpPage(primaryStage, users);
				}
				else if (getUser(users, user.getUsername()).isPresent()) {
					alert.setTitle("Username Already Exists");
					alert.setContentText("Please choose a different username as it already exists.");
					alert.show();
					createSignUpPage(primaryStage, users);
				}
				else {
					users.add(user);
					printUser(users);
					main.login(primaryStage, users);
				}
			}
		});
    	
    	Button loginButton = new Button("Login");
    	loginButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent actionEvent) {
				main.login(primaryStage, users);
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
	
	private boolean checkTextInput(String value) {
    	return value == null || value.isBlank() || value.isEmpty();
    }
	
	private void printUser(List<User> users) {
    	users.stream().forEach(user -> {
    		System.out.println(user.getFirstName() + " " + user.getLastName() + " " + user.getRole() + " " + user.getUsername() + " " + user.getPassword());
    	});
    }
	
	private boolean regexChecker(String regex, String value) {
		System.out.println("Some: " + Pattern.matches(regex, value));
		return Pattern.matches(regex, value);
	}
	
	private Optional<User> getUser(final List<User> users, final String username) {
    	return users.stream()
    			.filter(user -> user.getUsername().equals(username))
    			.findFirst();
    }
}
