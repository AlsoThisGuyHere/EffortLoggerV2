package logtester;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import effortloggerv2.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

public class ConsoleController implements Initializable{
	
	private User loggedInUser;
	Alert alert = new Alert(AlertType.ERROR);
	
	@FXML
	private Stage primaryStage;
	@FXML
	private Scene scene;
	@FXML
	private Parent root;
	@FXML
	private TextField projNameField;
	@FXML
	private Label taskName;
	@FXML
    private ChoiceBox<String> lifecycleStep;
	private String[] lifecycleSteps = {"Planning", "Information Gathering", "Information Understanding", "Verifying", "Outlining", "Drafting", "Finalizing", "Team Meeting", "Coach Meeting", "STakeholder MEeting", "Other"};
	@FXML
    private ChoiceBox<String> effortCategory;
	private String[] effortCategories = {"Plans", "Deliverables", "Interruptions", "Defects", "Others"};
	@FXML
	private Button startActivity;
	@FXML
	private Button stopActivity;
	
	// need to save whether the clock is running on the excel sheet (maybe if Stop field of most recent task is "", then clockIsRunning = true)
	private boolean clockIsRunning = false;
	
	
	// check if all required fields are filled in. If so, get local time and create a new log entry with selected fields and current time
	public void startTimer(ActionEvent event) {
		if (clockIsRunning) {
			alert.setTitle("Start Ignored");
			alert.setContentText("The clock is already running.");
			alert.show();
		} else {
			System.out.println("Start Time: " + java.time.LocalTime.now());
			clockIsRunning = true;
		}
	}
	// if timerRunning is true, edit last log entry and set the stop time to localTime
	public void stopTimer(ActionEvent event) {
		if (!clockIsRunning) {
			alert.setTitle("Stop Ignored");
			alert.setContentText("The clock is not running.");
			alert.show();
		} else {
			System.out.println("Stop Time: " + java.time.LocalTime.now());
			clockIsRunning = false;
		}
	}
	
	public void getTask(ActionEvent event) {
		String category = effortCategory.getValue();
		taskName.setText(category);
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		lifecycleStep.getItems().addAll(lifecycleSteps);
		effortCategory.getItems().addAll(effortCategories);
		effortCategory.setOnAction(this::getTask);
		
	}
}
