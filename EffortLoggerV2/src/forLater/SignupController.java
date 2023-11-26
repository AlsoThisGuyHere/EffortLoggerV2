package forLater;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

//import application.ReadWrite;
//import application.User;
//import application.Validator_Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

public class SignupController {
	
	//private User loggedInUser;
	private List<User> signedUpUsers = getUsersFromFile();
	Alert alert = new Alert(AlertType.ERROR);
	private ReadWrite readWrite = new ReadWrite("src\\sample.txt");
	
	@FXML
	private Stage primaryStage;
	@FXML
	private Scene scene;
	@FXML
	private Parent root;
	
	@FXML
	private TextField tfFirstName;
	@FXML
	private TextField tfLastName;
	@FXML
	private TextField tfRole;
	@FXML
	private TextField usernameField;
	@FXML
    private PasswordField passwordField;
	@FXML
	private PasswordField confirmPasswordField;
	@FXML
	private Button login_btn;
	@FXML
	private Button signup_btn;
	
	
	public void signup(ActionEvent event) {
		System.out.println("signing up");
		try {
			User user = new User(tfFirstName.getText(),
					tfLastName.getText(),
					tfRole.getText(),
					usernameField.getText(),
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
				//createSignUpPage(primaryStage, signedUpUsers);
			}
			else if (!(Validator.validate(user.getFirstName(), "Name") && Validator.validate(user.getLastName(), "Name"))) {
				alert.setTitle("Name Error");
				alert.setContentText("Not a valid name!");
				alert.show();
				//createSignUpPage(primaryStage, signedUpUsers);
			}
			else if (!Validator.validate(user.getRole(), "Name")) {
				alert.setTitle("Role Error");
				alert.setContentText("Not a valid role!");
				alert.show();
				//createSignUpPage(primaryStage, signedUpUsers);
			}
			else if (!Validator.validate(user.getPassword(), "Password")) {
				alert.setTitle("Password Error");
				alert.setContentText("Passwords need to have 8-32 characters, at least one letter, one number, and one special character.");
				alert.show();
				//createSignUpPage(primaryStage, signedUpUsers);
			}
			else if (!Validator.validate(user.getUsername(), "Username")) {
				alert.setTitle("Username Error");
				alert.setContentText("Username need to have 6-16 characters and only use letters and the following characters: .-\'");
				alert.show();
				//createSignUpPage(primaryStage, signedUpUsers);
			}
			else if (!user.getPassword().equals(confirmPasswordField.getText())) {
				alert.setTitle("Password Mismatch");
				alert.setContentText("Password and Confirm Password mismatch.");
				alert.show();
				//createSignUpPage(primaryStage, signedUpUsers);
			}
			else if (getUser(signedUpUsers, user.getUsername()).isPresent()) {
				alert.setTitle("Username Already Exists");
				alert.setContentText("Please choose a different username as it already exists.");
				alert.show();
				//createSignUpPage(primaryStage, signedUpUsers);
			}
			else {
				/*
				signedUpUsers.add(user);
				readWrite.append(user.getFirstName() + " " + user.getLastName() + " " + user.getRole() + " " + user.getUsername() + " " + user.getPassword(), true);
				//main.login(primaryStage, signedUpUsers);
				// back to login page
				root = FXMLLoader.load(getClass().getResource("Main.fxml"));    // add try/catch and finish tutorial
	    		primaryStage = (Stage)((Node)event.getSource()).getScene().getWindow();
	    		scene = new Scene(root);
	    		primaryStage.setScene(scene);
	    		primaryStage.show();
	    		*/
				securityQuestionPage(primaryStage, signedUpUsers, user);
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
    
	// security question thing needs to go here, can use this controller for both scenes
	
	
	
	public void switchToLogin(ActionEvent event) {
		//System.out.println("signing up");
    	try {
    		root = FXMLLoader.load(getClass().getResource("Main.fxml"));    // add try/catch and finish tutorial
    		primaryStage = (Stage)((Node)event.getSource()).getScene().getWindow();
    		scene = new Scene(root);
    		primaryStage.setScene(scene);
    		primaryStage.show();
    	}
    	catch (Exception except) {
    		except.printStackTrace();
    	}
    	
	}
    
	private Optional<User> getUser(final List<User> users, final String username) {
    	return users.stream()
    			.filter(user -> user.getUsername().equals(username))
    			.findFirst();
    }
    
    private List<User> getUsersFromFile() {
    	List<User> users = new ArrayList<User>();
    	ReadWrite readWrite = new ReadWrite("src\\sample.txt");
    	String readData = readWrite.read();
    	if (readData == null) return new ArrayList<User>();
    	String[] stringUsers = readData.split("\n");
    	for (int i = 0; i< stringUsers.length; i++) {
    		System.out.println(stringUsers[i]);
    		String[] splitUserDetails = stringUsers[i].split(" ");
    		if (splitUserDetails.length != 8) continue;
    		users.add(new User(splitUserDetails[0], splitUserDetails[1], splitUserDetails[2], splitUserDetails[3], splitUserDetails[4], splitUserDetails[5], splitUserDetails[6], splitUserDetails[7]));
    	}
    	return users;
    }
    
    private boolean checkTextInput(String value) {
    	return value == null || value.isBlank() || value.isEmpty();
    }
    
}
