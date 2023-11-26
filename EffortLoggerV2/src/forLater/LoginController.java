package forLater;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import effortloggerv2.User;
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

public class LoginController {

	private User loggedInUser;
	Alert alert = new Alert(AlertType.ERROR);
	
	
	@FXML
	private Stage primaryStage;
	@FXML
	private Scene scene;
	@FXML
	private Parent root;
	@FXML
	private TextField usernameField;
	@FXML
    private PasswordField passwordField;
	@FXML
	private Button login_btn;
	@FXML
	private Button signup_btn;
	
    public void login(ActionEvent event) {
        //System.out.println("Hasan Shahid: yo!");
        System.out.println("User data: " + usernameField.getText() + " " + passwordField.getText());
        User user = getUser(getUsersFromFile(), usernameField.getText(), passwordField.getText()).orElse(null);
		if (user == null) {
			alert.setTitle("Login Error");
			alert.setContentText("User with the above username and password combination is not registered.");
			alert.show();
			//Main.start(primaryStage);
		}
		else {
			loggedInUser = user;
			loggedInUser.setIsLoggedIn(Boolean.TRUE);
			//taskPage.createPlanningPokerPage(primaryStage, true, signedUpUsers, user);    will deal with later
			try {
	    		root = FXMLLoader.load(getClass().getResource("TaskPage.fxml"));    // add try/catch and finish tutorial
	    		primaryStage = (Stage)((Node)event.getSource()).getScene().getWindow();
	    		scene = new Scene(root);
	    		primaryStage.setScene(scene);
	    		primaryStage.show();
	    	}
	    	catch (Exception except) {
	    		except.printStackTrace();
	    	}
		}
    }
    /*public void signup(ActionEvent e) {
		System.out.println("signing up");
	}*/
    
    public void switchToSignup(ActionEvent event) {
		System.out.println("signing up");
    	try {
    		root = FXMLLoader.load(getClass().getResource("SignupPage.fxml"));    // add try/catch and finish tutorial
    		primaryStage = (Stage)((Node)event.getSource()).getScene().getWindow();
    		scene = new Scene(root);
    		primaryStage.setScene(scene);
    		primaryStage.show();
    	}
    	catch (Exception except) {
    		except.printStackTrace();
    	}
    	
	}
    /*public void switchToLogin(ActionEvent event) {
		//System.out.println("signing up");
    	try {
    		root = FXMLLoader.load(getClass().getResource("Main.fxml"));
    		primaryStage = (Stage)((Node)event.getSource()).getScene().getWindow();
    		scene = new Scene(root);
    		primaryStage.setScene(scene);
    		primaryStage.show();
    	}
    	catch (Exception except) {
    		except.printStackTrace();
    	}
    	
	}*/
	
	
	
	/*@FXML
	public void login(ActionEvent e) {
		System.out.println("logged in");
	}
	public void signup(ActionEvent e) {
		System.out.println("signing up");
	}*/
	
	private Optional<User> getUser(final List<User> users, final String username, final String password) {
    	return users.stream()
    			.filter(user -> user.getUsername().equals(username) && user.getPassword().equals(password))
    			.findFirst();
    }
	
	private List<User> getUsersFromFile() {
    	List<User> users = new ArrayList<User>();
    	ReadWrite readWrite = new ReadWrite("src\\sample.txt");
    	String readData = readWrite.read();
    	String[] stringUsers = readData.split("\n");
    	for (int i = 0; i< stringUsers.length; i++) {
    		System.out.println(stringUsers[i]);
    		String[] splitUserDetails = stringUsers[i].split(" ");
    		if (splitUserDetails.length != 5) continue;
    		users.add(new User(splitUserDetails[0], splitUserDetails[1], splitUserDetails[2], splitUserDetails[3], splitUserDetails[4], splitUserDetails[5], splitUserDetails[6], splitUserDetails[7]));
    	}
//    	return readWrite.read();
    	return users;
    }
	
}