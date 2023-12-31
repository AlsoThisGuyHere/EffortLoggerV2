package effortloggerv2;

import FXDirectoryExcel.*;

import java.net.URL;
import java.time.Duration;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
//import java.util.Optional;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
//import javafx.stage.DirectoryChooser;			// no longer needed
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;


public class EffortConsoleController implements Initializable{
	
	
	// user info
	private User user;
	private List<User> users;
	private boolean authenticationStatus;
	
	// file stuff
	private File projFile;
	FileChooser fileChooser;
	
	Alert alert = new Alert(AlertType.ERROR);
	
	private MainLoginPage main = new MainLoginPage();	// this WILL become obsolete
	
	// FXML fields
	@FXML
	private Stage primaryStage;
	@FXML
	private Scene scene;
	@FXML
	private Parent root;
	@FXML
	private TextField taskNameField;
	@FXML
	private Label taskName;
	@FXML
    private ChoiceBox<String> lifecycleStep;
	private String[] lifecycleSteps = {"Planning", "Information Gathering", "Information Understanding", "Verifying", "Outlining", "Drafting", "Finalizing", "Team Meeting", "Coach Meeting", "Stakeholder Meeting", "Other"};
	@FXML
    private ChoiceBox<String> effortCategory;
	private String[] effortCategories = {"Plans", "Deliverables", "Interruptions", "Defects", "Others"};
	@FXML
	private Button startActivity;
	@FXML
	private Button stopActivity;
	
	// stores all log entries and the current entry
	private List<EffortLog> effortInfo;
	private List<DefectLog> defectInfo;
	private EffortLog currLog;
	
	// need to save whether the clock is running on the excel sheet (maybe if Stop field of most recent task is "", then clockIsRunning = true)
	private boolean clockIsRunning = false;
	
	
	// these keep track of the user currently logged in
	public void keepUser(Stage primaryStage, boolean authenticationCheck, List<User> users, User loggedInUser) { // do I even need this one?
		user = loggedInUser;
		authenticationStatus = authenticationCheck;
		this.users = users;
		this.primaryStage = primaryStage;
	}
	
	
	/**********
	 * 
	 * Checks if all required fields are filled in, then creates a new log entry with default values.
	 * 
	 * @param event		This event refers to the activation of the "Start Activity" button.
	 */
	public void startTimer(ActionEvent event) {
		if (projFile == null) {
			alert.setTitle("No file chosen");
			alert.setContentText("No file chosen.");
			alert.show();
		} else if (clockIsRunning) {
			alert.setTitle("Start Ignored");
			alert.setContentText("The clock is already running.");
			alert.show();
		} else if (lifecycleStep.getValue() == null || effortCategory.getValue() == null || taskNameField.getText() == null) {
			alert.setTitle("Options left blank");
			alert.setContentText("Log entry cannot contain blank fields.");
			alert.show();
		} else {
			System.out.println("Start Time: " + java.time.LocalTime.now());
			currLog = new EffortLog(effortInfo.size() + 1, java.time.LocalDate.now().toString(), java.time.LocalTime.now().format(DateTimeFormatter.ofPattern("kk:mm:ss")).toString(),
					"", -1, lifecycleStep.getValue(), effortCategory.getValue(), taskNameField.getText());  // try "" instead of null
			effortInfo.add(currLog);
			ExcelController.write(projFile, (ArrayList<EffortLog>)effortInfo, (ArrayList<DefectLog>)defectInfo);
			clockIsRunning = true;
		}
	}
	
	/*******
	 * 
	 * Sets the stop time of the latest log entry to localTime if timerRunning is true.
	 * If the clock was started and stopped on different days, the most recent entry will record its stop time as midnight,
	 * and a new log entry for the current day will be created.
	 * 
	 * @param event		This event reference is the activation of the "Stop Activity" button.
	 */
	public void stopTimer(ActionEvent event) {
		if (!clockIsRunning) {
			alert.setTitle("Stop Ignored");
			alert.setContentText("The clock is not running.");
			alert.show();
		} else {
			System.out.println("Stop Time: " + java.time.LocalTime.now());
			if (!currLog.getDate().equals(java.time.LocalDate.now().toString())) {
				currLog.setStop("23:59:59");
				currLog.setTimeElapsed(Duration.between(LocalTime.parse(currLog.getStart()), LocalTime.parse(currLog.getStop())).getSeconds() / 60.0);
				currLog = new EffortLog(effortInfo.size() + 1, java.time.LocalDate.now().toString(), "00:00:00",
						java.time.LocalTime.now().format(DateTimeFormatter.ofPattern("kk:mm:ss")).toString(), 
						Duration.between(LocalTime.parse("00:00:00"), java.time.LocalTime.now()).getSeconds() / 60.0, 
						currLog.getLifeCycleStep(), currLog.getCategory(), currLog.getDelInt());
				effortInfo.add(currLog);
			} else {
				currLog.setStop(java.time.LocalTime.now().format(DateTimeFormatter.ofPattern("kk:mm:ss")).toString());
				currLog.setTimeElapsed(Duration.between(LocalTime.parse(currLog.getStart()), LocalTime.parse(currLog.getStop())).getSeconds() / 60.0);
			}
			System.out.printf("Time elapsed: %.2f\n", currLog.getTimeElapsed());
			//System.out.println("Time elapsed: " + Duration.between(LocalTime.parse(currLog.getStart()), LocalTime.parse(currLog.getStop())).getSeconds() / 60.0);
			ExcelController.write(projFile, (ArrayList<EffortLog>)effortInfo, (ArrayList<DefectLog>)defectInfo);
			clockIsRunning = false;
		}
	}
	
	/****
	 * Switches the active scene to the effort editor.
	 * 
	 * @param event		This event references the activation of the "Switch to Effort Editor" button.
	 */
	public void openEffortEditor(ActionEvent event) {
		try {														// do this any time you want to switch scenes
			
			FXMLLoader loader = new FXMLLoader(getClass().getResource("EffortEditor.fxml"));
			Parent root = loader.load();
			EffortEditorController editorController = loader.getController();
			editorController.keepUser(primaryStage, true, users, user);		// calls a method to carry over important data
			Scene scene = new Scene(root,1280,720);
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	/****
	 * Switches the active scene to the defect console.
	 * 
	 * @param event		This event references the activation of the "Switch to Defect Console" button.
	 */
	public void openDefectConsole(ActionEvent event) {
		try {														// do this any time you want to switch scenes
			
			FXMLLoader loader = new FXMLLoader(getClass().getResource("DefectConsole.fxml"));
			Parent root = loader.load();
			DefectConsoleController consoleController = loader.getController();
			consoleController.keepUser(primaryStage, true, users, user);		// calls a method to carry over important data
			Scene scene = new Scene(root,1280,720);
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	/****
	 * Switches the active scene to the planning poker tool.
	 * 
	 * @param event		This event references the activation of the "Switch to Planning Poker" button.
	 */
	public void openPlanningPoker(ActionEvent event) {
		System.out.println("Planning");
		PlanningPokerPage planningPokerPage = new PlanningPokerPage();
		planningPokerPage.createPlanningPokerPage(primaryStage, user, users, authenticationStatus);
//		HistoricalData historicalData = new HistoricalData();
//		historicalData.ProjectDataPage(primaryStage, users, tasks, authenticationStatus, user);
	}
	
	// log out current user
	/**
	 * Log out current user and return to login page.
	 * @param event		This event references the activation of the "Log Out" button.
	 */
	public void logout(ActionEvent event) {
		System.out.println("logging out");
		user = null;
		/*try {																		
    		root = FXMLLoader.load(getClass().getResource("MainLoginPage.fxml"));			// if we switch to only FXML files
    		primaryStage = (Stage)((Node)event.getSource()).getScene().getWindow();
    		scene = new Scene(root);
    		primaryStage.setScene(scene);
    		primaryStage.show();
    	}
    	catch (Exception except) {
    		except.printStackTrace();
    	}*/
		main.login(primaryStage, users);
	}
	
	
	
	
	
	/**
	 * Make an Excel workbook for a new project and fill in the heading.
	 * @param event		This event references the activation of the "Create New Project" button.
	 */
	public void createProject (ActionEvent event) {
		fileChooser = new FileChooser();
		fileChooser.getExtensionFilters().addAll(
  		         new ExtensionFilter("Worksheets", "*.xlsx", "*.xlsm", "*.xlsb", "*.xls")
  		         );
       	fileChooser.setInitialFileName("Project1.xlsx");
		projFile = fileChooser.showSaveDialog(primaryStage);
		if (projFile == null) {
			System.out.println("No file was chosen by user.");
		} else {
			ExcelController.write(projFile, new ArrayList<EffortLog>(), new ArrayList<DefectLog>());
			currLog = new EffortLog();	// redundant
			clockIsRunning = false;
			effortInfo = ExcelController.readEffortLogs(projFile);
			defectInfo = ExcelController.readDefectLogs(projFile);
		}
	}
	
	/***
	 * Imports an existing project from an Excel workbook.
	 * 
	 * @param event
	 */
	public void importProject (ActionEvent event) {		
		fileChooser = new FileChooser();
		fileChooser.getExtensionFilters().addAll(
				new ExtensionFilter("Worksheets", "*.xlsx")
				);
		projFile = fileChooser.showOpenDialog(primaryStage);
		if (projFile == null) {
			System.out.println("No file was chosen by user.");
		} else {
			effortInfo = ExcelController.readEffortLogs(projFile);
			defectInfo = ExcelController.readDefectLogs(projFile);
			if (effortInfo.size() <= 0 || effortInfo.get(effortInfo.size() - 1).getStop() != "") {	// needs to check if logged day is today
				clockIsRunning = false;
				currLog = new EffortLog();		// redundant
			} else {
				clockIsRunning = true;
				currLog = effortInfo.get(effortInfo.size() - 1);
			}
			System.out.println("Clock is running: " + clockIsRunning);
		}
	}
	
	
	
	
	
	/****
	 * Private method to set the text above the Task box based on what effort category the user chooses.
	 * 
	 * @param event
	 */
	private void getTask(ActionEvent event) {
		String category = effortCategory.getValue();
		taskName.setText(category);
	}
	
	/**
	 * Initializes the options in the choice boxes
	 * 
	 */
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		lifecycleStep.getItems().addAll(lifecycleSteps);
		effortCategory.getItems().addAll(effortCategories);
		effortCategory.setOnAction(this::getTask);
	}
}
