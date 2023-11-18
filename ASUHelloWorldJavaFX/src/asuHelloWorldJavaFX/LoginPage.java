package asuHelloWorldJavaFX;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
 
public class LoginPage extends Application {
	
	private User loggedInUser;
	
	private SignupPage signupPage;
	
	private EffortLoggerPage taskPage;

	public static void main(String[] args) {
        launch(args);
    }
	
	public void start(Stage primaryStage) {
		login(primaryStage, getUsersFromFile(), new ArrayList<Task>());
	}
    
    public void login(Stage primaryStage, List<User> signedUpUsers, List<Task> tasks) {
    	System.out.println(signedUpUsers.size());
    	System.out.println(tasks.size());
    	signupPage = new SignupPage();
    	taskPage = new EffortLoggerPage();
        primaryStage.setTitle("Effort Logger");
        
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
        
        Button btn = new Button("Login");
        btn.setOnAction(new EventHandler<>() {
            public void handle(ActionEvent event) {
                System.out.println("User data: " + tfUsername.getText() + " " + passwordField.getText());
                User user = getUser(signedUpUsers, tfUsername.getText(), passwordField.getText()).orElse(null);
				if (user == null) {
					alert.setTitle("Login Error");
					alert.setContentText("User with the above username and password is not registered.");
					alert.show();
					start(primaryStage);
				}
				else {
					loggedInUser = user;
					loggedInUser.setIsLoggedIn(Boolean.TRUE);
					taskPage.createEffortLoggerPage(primaryStage, true, signedUpUsers, user, tasks);
				}
            }
        });
        
        Button signupBtn = new Button("Signup");
        signupBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				System.out.println("Hello");
				signupPage.createSignUpPage(primaryStage, signedUpUsers);
			}
		});
        
        Button directAccessButton = new Button("Direct Access");
        directAccessButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent actionEvent) {
				taskPage.createEffortLoggerPage(primaryStage, false, signedUpUsers, null, tasks);
			}
		});
        
        GridPane root = new GridPane();
        root.addRow(0, username, tfUsername);
        root.addRow(1, password, passwordField);
        root.addRow(2, btn, signupBtn);
//        root.addRow(3, directAccessButton);
        root.setAlignment(Pos.CENTER);
        
        primaryStage.setScene(new Scene(root, 400, 400));
        primaryStage.show();
    }
    
    private Optional<User> getUser(final List<User> users, final String username, final String password) {
    	return users.stream()
    			.filter(user -> user.getUsername().equals(username) && user.getPassword().equals(password))
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
}