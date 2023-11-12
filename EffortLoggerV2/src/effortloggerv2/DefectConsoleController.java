package effortloggerv2;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.FileChooser.ExtensionFilter;

public class DefectConsoleController implements Initializable{
	
	private User user;
	private List<User> users;
	private boolean authenticationStatus;
	private List<Task> tasks;			// purely here for compatibility while I get this working
	
	// file stuff
	private File projFile;
	FileChooser fileChooser;
	
	Alert alert = new Alert(AlertType.ERROR);
	
	@FXML
	private Stage primaryStage;
	@FXML
	private Scene scene;
	@FXML
	private Parent root;
	@FXML
	private Label defectStatus;
	@FXML
	private Label num;
	@FXML
	private Label defectName;
	@FXML
	private TextField description;
	@FXML
    private ChoiceBox<String> defectSelector;
	@FXML
	private ChoiceBox<String> defectFix;
	private String[] defects = {"-no defect selected-"};// this will get defects from the imported file
	@FXML
	private ChoiceBox<String> stepInjected;
	@FXML
	private ChoiceBox<String> stepRemoved;
	private String[] lifecycleSteps = {"Planning", "Information Gathering", "Information Understanding", "Verifying", "Outlining", "Drafting", 
			"Finalizing", "Team Meeting", "Coach Meeting", "Stakeholder Meeting", "Other"};
	@FXML
    private ChoiceBox<String> defectCategory;
	private String[] defectCategories = {"Not Specified", "Documentation", "Syntax", "Build, Package", "Assignment", "Interface",
			"Checking", "Data", "Function", "System", "Environment"};
	
	
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
	
	// import an existing project in an Excel workbook
	public void importProject (ActionEvent event) {		// needs to get relevant fields from the chosen file
		fileChooser = new FileChooser();
		fileChooser.getExtensionFilters().addAll(
				new ExtensionFilter("Worksheets", "*.xlsx", "*.xlsm", "*.xlsb", "*.xls")
				);
		projFile = fileChooser.showOpenDialog(primaryStage);
		// get list of defects from Excel and pass it to populateDefectSelector()
	}
	
	
	public void updateDefect(ActionEvent event) {
		
		List<String> defectInfo = new ArrayList<String>();
		defectInfo.add(num.getText());
		defectInfo.add(defectName.getText());
		defectInfo.add(description.getText());
		defectInfo.add(stepInjected.getValue());
		defectInfo.add(stepRemoved.getValue());
		defectInfo.add(defectCategory.getValue());
		defectInfo.add(defectStatus.getText());
		
		for (String i : defectInfo) {			// just to prove it works
			System.out.print(i +" | ");
		}
		System.out.println();
		
		// now need to call updateDefect in the Excel creator and pass it defectInfo to insert into Excel
		// get list of defects from Excel and pass it to populateDefectSelector()
		
	}
	
	public void deleteDefect(ActionEvent event) {
		// ensure that the selected defect is not null before trying to delete anything, and set the selected defect to null after
		System.out.println("Selected defect is no more");
		// get list of defects from Excel and pass it to populateDefectSelector()
	}
	
	// go back to EffortLogger Console
	public void openLogConsole(ActionEvent event) {
		try {																						// just for now
			
			FXMLLoader loader = new FXMLLoader(getClass().getResource("Console.fxml"));
			Parent root = loader.load();
			ConsoleController consoleController = loader.getController();
			consoleController.keepUser(primaryStage, true, users, user, tasks);
			Scene scene = new Scene(root,1280,720);
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void populateDefectSelector(ActionEvent event) {
		
	}
	
	public void changeStatus(ActionEvent event) {
		
		if (defectStatus.getText().equals("Closed"))
			defectStatus.setText("Open");
		else
			defectStatus.setText("Closed");
	}
	
	// initializes the options in the choice boxes
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		defectSelector.getItems().addAll(defects);
		defectFix.getItems().addAll(defects);
		stepInjected.getItems().addAll(lifecycleSteps);
		stepRemoved.getItems().addAll(lifecycleSteps);
		defectCategory.getItems().addAll(defectCategories);
		// defectStatus.setOnAction(changeStatus);
	}
}
