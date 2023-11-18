package asuHelloWorldJavaFX;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class SignupPage {
	
	private LoginPage main = new LoginPage();
	
	private ReadWrite readWrite = new ReadWrite("src\\sample.txt");
	
	private String selectedQuestion1;
	private String selectedQuestion2;
	private String selectedQuestion3;
	
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
    	
    	Button nextButton = new Button("Next");
    	nextButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				System.out.println("Signup page");
				User user = new User(tfFirstName.getText(),
						tfLastName.getText(),
						tfRole.getText(),
						tfUsername.getText(),
						passwordField.getText(), null, null, null);
				
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
				else if (!Validator.validate(passwordField.getText(), "Password")) {
					alert.setTitle("Password Error");
					alert.setContentText("Password doesn't match the regex pattern");
					alert.show();
					createSignUpPage(primaryStage, users);
				}
				else if (!Validator.validate(tfUsername.getText(), "Username")) {
					alert.setTitle("Username Error");
					alert.setContentText("Username doesn't match the regex pattern");
					alert.show();
					createSignUpPage(primaryStage, users);
				}
				else if (!Validator.validate(tfFirstName.getText(), "Name") || !Validator.validate(tfLastName.getText(), "Name")) {
					alert.setTitle("Name Error");
					alert.setContentText("First name or last name doesn't match the regex pattern");
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
					securityQuestionPage(primaryStage, users, user);
				}
			}
		});
    	
    	Button loginButton = new Button("Login");
    	loginButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent actionEvent) {
				main.login(primaryStage, users, new ArrayList<Task>());
			}
		});
    	
    	gridPane.addRow(0, firstName, tfFirstName);
    	gridPane.addRow(1, lastName, tfLastName);
    	gridPane.addRow(2, role, tfRole);
    	gridPane.addRow(3, username, tfUsername);
    	gridPane.addRow(4, password, passwordField);
    	gridPane.addRow(5, confirmPassword, confirmPasswordField);
    	gridPane.addRow(6, nextButton, loginButton);
    	gridPane.setAlignment(Pos.CENTER);
    	
    	primaryStage.setScene(new Scene(gridPane, 400, 400));
    	primaryStage.show();
    }
	
	private boolean checkTextInput(String value) {
    	return value == null || value.isBlank() || value.isEmpty();
    }
	
	private Optional<User> getUser(final List<User> users, final String username) {
    	return users.stream()
    			.filter(user -> user.getUsername().equals(username))
    			.findFirst();
    }
	
	private void securityQuestionPage(Stage primaryStage, List<User> users, User user) {
		
		List<String> questions = new ArrayList<String>();
		questions.add("1. What is your favorite number?");
		questions.add("2. What is the name of a college you applied to but never attended?");
		questions.add("3. What was the make and model of your first car?");
		questions.add("4. In what city or town did your parents first meet?");
		questions.add("5. What is your oldest siblings middle name?");
		questions.add("6. What is the middle name of your youngest child?");
		questions.add("7. What is the title of the first book you read?");
		questions.add("8. What is the name of your least favorite teacher?");
		questions.add("9. What was the name of your childhood pet?");
		questions.add("10. What was the first concert you attended?");
		
		GridPane gridPane = new GridPane();
		
		Label question1Label = new Label("Please Select question 1");
		Label question2Label = new Label("Please Select question 2");
		Label question3Label = new Label("Please Select question 3");
		
		TextField tfAnswer1 = new TextField();
		TextField tfAnswer2 = new TextField();
		TextField tfAnswer3 = new TextField();
		
		System.out.println("Handle all user");
		ComboBox<String> question1 = new ComboBox<String>(FXCollections.observableArrayList(questions));
		question1.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent actionEvent) {
				System.out.println(question1.getValue());
				selectedQuestion1 = question1.getValue();
			}
		});
		ComboBox<String> question2 = new ComboBox<String>(FXCollections.observableArrayList(questions));
		question2.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent actionEvent) {
				selectedQuestion2 = question2.getValue();
			}
		});
		ComboBox<String> question3 = new ComboBox<String>(FXCollections.observableArrayList(questions));
		question3.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent actionEvent) {
				selectedQuestion3 = question3.getValue();
			}
		});
		
		Alert alert = new Alert(AlertType.ERROR);
		
		Button signUpButton = new Button("Signup");
		signUpButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent actionEvent) {
				System.out.println("Sinnn: " + selectedQuestion1 + selectedQuestion2 + selectedQuestion3);
				if (checkTextInput(tfAnswer1.getText()) || checkTextInput(tfAnswer2.getText()) || checkTextInput(tfAnswer3.getText())) {
					alert.setTitle("Missing answers");
					alert.setContentText("Please add all the answers");
					alert.show();
					securityQuestionPage(primaryStage, users, user);
				}
				else if (checkTextInput(selectedQuestion1) || checkTextInput(selectedQuestion2) || checkTextInput(selectedQuestion3)) {
					alert.setTitle("Missing Questions");
					alert.setContentText("Please select all the questions");
					alert.show();
					securityQuestionPage(primaryStage, users, user);
				}
				else if (selectedQuestion1.equals(selectedQuestion2) || selectedQuestion2.equals(selectedQuestion3) || selectedQuestion3.equals(selectedQuestion1)) {
					alert.setTitle("Same Question selected");
					alert.setContentText("Please select different questions");
					alert.show();
					securityQuestionPage(primaryStage, users, user);
				}
				else {
					user.setAnswer1(tfAnswer1.getText());
					user.setAnswer2(tfAnswer2.getText());
					user.setAnswer3(tfAnswer3.getText());
					users.add(user);
					readWrite.append(user.getFirstName() + " " + user.getLastName() + " " + user.getRole() + " " + user.getUsername() + " " + user.getPassword() + " " + user.getAnswer1() + " " + user.getAnswer2() + " " + user.getAnswer3(), true);
					main.login(primaryStage, users, new ArrayList<Task>());
				}
			}
		});
		
		
		Button loginButton = new Button("Login");
		loginButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent actionEvent) {
				main.login(primaryStage, users, new ArrayList<Task>());
			}
		});
		
		gridPane.addRow(0, question1Label);
		gridPane.addRow(1, question1, new Button());
		gridPane.addRow(2, tfAnswer1);
		gridPane.addRow(3, question2Label);
		gridPane.addRow(4, question2);
		gridPane.addRow(5, tfAnswer2);
		gridPane.addRow(6, question3Label);
		gridPane.addRow(7, question3);
		gridPane.addRow(8, tfAnswer3);
		gridPane.addRow(9, signUpButton);
		gridPane.addRow(10, loginButton);
		gridPane.setAlignment(Pos.CENTER);
		
		primaryStage.setScene(new Scene(gridPane, 400, 400));
    	primaryStage.show();
	}
}
