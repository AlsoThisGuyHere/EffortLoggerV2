package effortloggerv2;

import FXDirectoryExcel.*;
import java.net.URL;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
//import java.util.Optional;
import java.util.ResourceBundle;

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
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
//import javafx.stage.DirectoryChooser;			// no longer needed
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;

import org.apache.poi.ss.usermodel.Cell;		// not sure if I will need ALL of these (or ANY)
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ConsoleController implements Initializable{
	
	// user info
	private User user;
	private List<User> users;
	private boolean authenticationStatus;
	private List<Task> tasks;			// purely here for compatibility while I get this working
	
	// file stuff
	private File projFile;
	// DirectoryChooser dirChooser;			// no longer needed
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
	private TextField projNameField;
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
	
	//private List<String> projInfo;
	private Workbook workbook;
	private Sheet sheet;
	private Row row;
	private int cellNum = 0;
	private Cell cell;
	
	// need to save whether the clock is running on the excel sheet (maybe if Stop field of most recent task is "", then clockIsRunning = true)
	private boolean clockIsRunning = false;
	
	
	// these keep track of the user currently logged in
	public void keepUser(Stage primaryStage, boolean authenticationCheck, List<User> users, User loggedInUser) { // do I even need this one?
		user = loggedInUser;
		authenticationStatus = authenticationCheck;
		this.users = users;
		tasks = new ArrayList<Task>();			// purely here for compatibility
		this.primaryStage = primaryStage;
	}
	public void keepUser(Stage primaryStage, boolean authenticationCheck, List<User> users, User loggedInUser, List<Task> tasks) {
		user = loggedInUser;
		authenticationStatus = authenticationCheck;
		this.users = users;
		this.tasks = tasks;			// purely here for compatibility
		this.primaryStage = primaryStage;
	}
	
	
	
	// check if all required fields are filled in. If so, get local time and create a new log entry with selected fields and current time
	// iterate through each row's Number field until one returns null. Put the new entry in that row
	public void startTimer(ActionEvent event) {
		if (projFile == null) {
			alert.setTitle("No file chosen");
			alert.setContentText("No file chosen.");
			alert.show();
		} else if (clockIsRunning) {
			alert.setTitle("Start Ignored");
			alert.setContentText("The clock is already running.");
			alert.show();
		} else {
			System.out.println("Start Time: " + java.time.LocalTime.now());
			
			List<String> projInfo = new ArrayList<String>();
			projInfo.add(java.time.LocalTime.now().toString());
			projInfo.add(lifecycleStep.getValue());
			projInfo.add(effortCategory.getValue());
			projInfo.add(projNameField.getText());
			System.out.println(projInfo.toString());
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
	
	public void openDefectConsole(ActionEvent event) {
		try {																						// just for now
			
			FXMLLoader loader = new FXMLLoader(getClass().getResource("DefectConsole.fxml"));
			Parent root = loader.load();
			DefectConsoleController consoleController = loader.getController();
			consoleController.keepUser(primaryStage, true, users, user, tasks);
			Scene scene = new Scene(root,1280,720);
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	// log out current user
	public void logout(ActionEvent event) {
		System.out.println("logging out");
		user = null;
		/*try {																		
    		root = FXMLLoader.load(getClass().getResource("MainLoginPage.fxml"));			// for when we switch to only FXML files
    		primaryStage = (Stage)((Node)event.getSource()).getScene().getWindow();
    		scene = new Scene(root);
    		primaryStage.setScene(scene);
    		primaryStage.show();
    	}
    	catch (Exception except) {
    		except.printStackTrace();
    	}*/
		main.login(primaryStage, users, tasks);
	}
	
	
	
	
	
	// make an Excel workbook for a new project
	public void createProject (ActionEvent event) {
		fileChooser = new FileChooser();
		fileChooser.getExtensionFilters().addAll(
  		         new ExtensionFilter("Worksheets", "*.xlsx", "*.xlsm", "*.xlsb", "*.xls")
  		         );
       	fileChooser.setInitialFileName("Book1.xlsx");
		projFile = fileChooser.showSaveDialog(primaryStage);
		if (projFile == null) {
			System.out.println("No file was chosen by user.");
		} else {ExcelCreator.createExcel(projFile);}
	}
	
	// import an existing project in an Excel workbook
	public void importProject (ActionEvent event) {		// needs to get relevant fields from the chosen file
		fileChooser = new FileChooser();
		fileChooser.getExtensionFilters().addAll(
				new ExtensionFilter("Worksheets", "*.xlsx", "*.xlsm", "*.xlsb", "*.xls")
				);
		projFile = fileChooser.showOpenDialog(primaryStage);
	}
	
	
	
	
	
	// sets the text above the Task box based on what effort category the user chooses
	public void getTask(ActionEvent event) {
		String category = effortCategory.getValue();
		taskName.setText(category);
	}
	
	// initializes the options in the choice boxes
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		lifecycleStep.getItems().addAll(lifecycleSteps);
		effortCategory.getItems().addAll(effortCategories);
		effortCategory.setOnAction(this::getTask);
	}
}
