package application;

import java.util.ArrayList;
import java.util.List;

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
import javafx.stage.Stage;

public class ForgotPasswordScreen {
	
	private UtilityHelper utilityHelper;
	
	private MainLoginPage loginPage;
	
	public void forgotPasswordScreen(Stage primaryStage) {
		loginPage = new MainLoginPage();
		utilityHelper = new UtilityHelper();
		
		GridPane gridPane = new GridPane();
		
		gridPane.setHgap(0.1);
    	gridPane.setVgap(0.1);
    	gridPane.setAlignment(Pos.CENTER);
    	
    	Label usernameLabel = new Label("Enter username");
    	Label passwordLabel = new Label("Enter password");
    	Label confirmPasswordLabel = new Label("Confirm Password");
    	
    	TextField tfUsername = new TextField();
    	PasswordField tfPassword = new PasswordField();
    	PasswordField tfConfirmPassword = new PasswordField();
    	
    	Button updatePasswordButton = new Button("Update Password");
    	
    	Alert alert = new Alert(AlertType.INFORMATION);
    	
    	updatePasswordButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent actionEvent) {
				if (utilityHelper.validateForgotPassword(tfUsername.getText(), tfPassword.getText(), tfConfirmPassword.getText())) {
					alert.setAlertType(AlertType.ERROR);
					alert.setContentText("All fields are required");
					alert.setTitle("Missing Fields");
					alert.show();
				}
				else if (!Validator.validate(tfPassword.getText(), "Password")) {
					alert.setAlertType(AlertType.ERROR);
					alert.setContentText("Password doesn't macth the regex");
					alert.setTitle("Passwords Incorrect");
					alert.show();
				}
				else if (!utilityHelper.validatePasswordAndConfirmPassword(tfPassword.getText(), tfConfirmPassword.getText())) {
					alert.setAlertType(AlertType.ERROR);
					alert.setContentText("Password Mismatch");
					alert.setTitle("Passwords Incorrect");
					alert.show();
				}
				else if (utilityHelper.findUser(tfUsername.getText())) {
					List<User> updatedUsers= utilityHelper.updateUser(tfUsername.getText(), tfPassword.getText());
					utilityHelper.saveUsers(updatedUsers);
					alert.setAlertType(AlertType.INFORMATION);
					alert.setContentText("Password Updated Successfully");
					alert.setTitle("Update Password");
					alert.show();
				}
				else {
					alert.setAlertType(AlertType.ERROR);
					alert.setContentText("User not found");
					alert.setTitle("Update Password");
					alert.show();
				}
			}
		});
    	
    	Button backToLoginButton = new Button("Back To Login");
    	backToLoginButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent actionEvent) {
				loginPage.login(primaryStage, UtilityHelper.getUsersFromFile("src\\sample.txt"), new ArrayList<Task>());
			}
		});
    	
    	gridPane.addRow(0, usernameLabel);
    	gridPane.addRow(1, tfUsername);
    	gridPane.addRow(2, passwordLabel);
    	gridPane.addRow(3, tfPassword);
    	gridPane.addRow(4, confirmPasswordLabel);
    	gridPane.addRow(5, tfConfirmPassword);
    	gridPane.addRow(6, updatePasswordButton);
    	gridPane.addRow(7, backToLoginButton);
    	
    	primaryStage.setScene(new Scene(gridPane, 400, 400));
    	primaryStage.show();
	}
}