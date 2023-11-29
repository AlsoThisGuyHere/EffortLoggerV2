package application;

import java.io.File;
import java.net.URL;
import java.time.Duration;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.function.UnaryOperator;

import FXDirectoryExcel.*;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;
import javafx.util.StringConverter;

public class EffortEditorController implements Initializable{
	
	private User user;
	private List<User> users;
	private boolean authenticationStatus;
	private List<Task> tasks;			// purely here for compatibility while I get this working
	
	// file stuff
	private File projFile;
	FileChooser fileChooser;
	
	Alert alert = new Alert(AlertType.ERROR);
	
	int maxNameLen = 25;
	
	@FXML
	private Stage primaryStage;
	@FXML
	private Scene scene;
	@FXML
	private Parent root;
	@FXML
	private Label effortNum;			// may not need
	@FXML
	private ComboBox<EffortLog> effortSelector;
	@FXML
	private TextField effortDate;
	@FXML
	private TextField effortStartTime;
	@FXML
	private TextField effortStopTime;
	@FXML
	private ChoiceBox<String> effortLifecycleStep;
	private String[] lifecycleSteps = {"Planning", "Information Gathering", "Information Understanding", "Verifying", "Outlining", "Drafting", 
			"Finalizing", "Team Meeting", "Coach Meeting", "Stakeholder Meeting", "Other"};
	@FXML
    private ChoiceBox<String> effortCategory;
	private String[] effortCategories = {"Not Specified", "Documentation", "Syntax", "Build, Package", "Assignment", "Interface",
			"Checking", "Data", "Function", "System", "Environment"};
	@FXML
	private TextField effortName;
	
	private List<DefectLog> defectInfo;
	private List<EffortLog> effortInfo;
	private EffortLog currEffort;
	
	
	
	// deprecated in-part; these keep track of the user currently logged in
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
	
	
	
	private UnaryOperator<TextFormatter.Change> modifyChange = c -> {	// triggers IndexOutOfBounds if pasting long string into empty box
		
		if (c.isContentChange()) {
			int inLength = c.getControlNewText().length();
			if (inLength > maxNameLen) {
				String head = c.getControlNewText().substring(0, maxNameLen-1);
				c.setText(head);
				int oldLength = c.getControlText().length();
				c.setRange(0, oldLength-1);
			}
			
		}
		return c;
	};
	
	
	/**
	 * Initializes the options in the choice boxes.
	 */
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		effortName.setTextFormatter(new TextFormatter<TextField> (modifyChange));
		effortLifecycleStep.getItems().addAll(lifecycleSteps);
		effortCategory.getItems().addAll(effortCategories);
		effortSelector.setConverter(converter);
		//effortSelector.getItems().addAll(effortInfo);
		effortSelector.setOnAction(this::populateEffortEditor);
	}
	
	// for entry selection
	private StringConverter<EffortLog> converter = new StringConverter<EffortLog>() {

		@Override
		public EffortLog fromString(String string) {
			//for (EffortLog log : effortInfo) {
			//	// go through list and find corresponding entry
			//	if (log.getName() == string) {
			//		return log;
			//	}
			//}
			return null;
		}

		@Override
		public String toString(EffortLog effortLog) {
			if (effortLog != null) {
				String name = effortLog.getDelInt();
				// shortens name if it is too long
				if (name.length() > maxNameLen) {
					effortLog.setDelInt(name.substring(0, maxNameLen-1));
				}
				return effortLog.toString();
			}
			return "-no entry selected-";
		}
	};
	
	/**
	 * Updates the user interface to display properties of the selected entry.
	 * @param event		The event triggering this method (in this case, the selection of an entry from the effortSelector ComboBox).
	 */
	public void populateEffortEditor(ActionEvent event) {		// setOnAction activates EVERY TIME a choice is selected
		if (effortSelector.getValue() == null) {
			System.out.println("Something changed");
		} else {
			System.out.println(effortSelector.getValue().toString());
			currEffort = effortSelector.getValue();
			System.out.println("Current Effort Entry: " + currEffort.toString());
			effortDate.setText(currEffort.getDate());
			effortStartTime.setText(currEffort.getStart());
			effortStopTime.setText(currEffort.getStop());
			effortLifecycleStep.setValue(currEffort.getLifeCycleStep());
			effortCategory.setValue(currEffort.getCategory());
			effortName.setText(currEffort.getDelInt());
			
		}
		
	}
	
	
	
	/**
	 * Imports an existing project from an Excel workbook and gets the list of effort logs.
	 * @param event		The event triggering this method (in this case, pressing the "import project" button).
	 */
	public void importProject (ActionEvent event) {		
		// get list of effort logs from Excel and pass it to populateDefectSelector()
		fileChooser = new FileChooser();
		fileChooser.getExtensionFilters().addAll(
				new ExtensionFilter("Worksheets", "*.xlsx")
				);
		projFile = fileChooser.showOpenDialog(primaryStage);
		if (projFile == null) {
			System.out.println("No file was chosen by user.");
		} else {
			defectInfo = ExcelController.readDefectLogs(projFile);
			effortInfo = ExcelController.readEffortLogs(projFile);
			effortSelector.setItems(FXCollections.observableList(effortInfo));
		}
	}
	
	/****
	 * Updates the selected effort entry with the values in the editable fields, then saves it to the log.
	 * @param event 	The event triggering this method (in this case, pressing the "update this entry" button).
	 */
	public void updateEffort(ActionEvent event) {
		double timeElapsed = Duration.between(LocalTime.parse(currEffort.getStart()), LocalTime.parse(currEffort.getStop())).getSeconds() / 60.0;
		
		if (currEffort == null) {
			errorAlert(alert, "No effort log chosen", "You must first select an effort entry!");
		} else if (timeElapsed <= 0) {
			errorAlert(alert, "Invalid start/stop pair", "The stop time must be later than the start time!");
		} else {
			currEffort.setDate(effortDate.getText());
			currEffort.setStart(effortStartTime.getText());
			currEffort.setStop(effortStopTime.getText());
			currEffort.setTimeElapsed(timeElapsed);
			currEffort.setLifeCycleStep(effortLifecycleStep.getValue());
			currEffort.setCategory(effortCategory.getValue());
			currEffort.setDelInt(effortName.getText());
			
			
			for (EffortLog e : effortInfo) {			// just to prove it works
				System.out.println(e);
			}
			ExcelController.write(projFile, (ArrayList<EffortLog>)effortInfo, (ArrayList<DefectLog>)defectInfo);
			//defectInfo = ExcelController.readDefectLogs(projFile);
			effortSelector.setItems(FXCollections.observableList(effortInfo));// resets the selection model but still keeps the defect selected???
			effortSelector.getSelectionModel().select(currEffort);		// did absolutely nothing
		}
		
	}
	
	/**
	 * Removes the selected effort entry from the effort list.
	 * @param event		The event triggering this method (in this case, pressing the "delete this entry" button).
	 */
	public void deleteEffort(ActionEvent event) {
		// ensure that the selected defect is not null before trying to delete anything, and set the selected defect to null after
		if (currEffort == null) {
			errorAlert(alert, "No effort entry chosen", "You must first select an entry!");
		} else {
			
			effortInfo.remove(currEffort);
			currEffort = null;
			
			ExcelController.write(projFile, (ArrayList<EffortLog>)effortInfo, (ArrayList<DefectLog>)defectInfo);
			effortInfo = ExcelController.readEffortLogs(projFile);
			effortSelector.setItems(FXCollections.observableList(effortInfo));
			System.out.println("Selected entry is no more");
		}
	}
	
	/**
	 * Removes all effort entries from the list after receiving user confirmation.
	 * @param event		The event triggering this method (in this case, pressing the "clear this effort log" button).
	 */
	public void clearEffortLog(ActionEvent event) {
		if (projFile == null) {
			errorAlert(alert, "No project chosen", "You must first import a project!");
		} else {
			alert.setTitle("Delete all");
			alert.setAlertType(AlertType.WARNING);
			alert.setContentText("Are you sure you want to delete ALL effort log entries (this cannot be undone)?");
			alert.showAndWait().ifPresent(response -> {
				if (response == ButtonType.OK) {
					effortInfo.clear();
					currEffort = null;
					effortSelector.setItems(FXCollections.observableList(effortInfo));
					ExcelController.write(projFile, (ArrayList<EffortLog>)effortInfo, (ArrayList<DefectLog>)defectInfo);
				}
			});
			alert.setAlertType(AlertType.ERROR);
		}
	}
	

	
	/**
	 * Sets the active scene to the Effort Console.
	 * Must run keepUser to pass current logged-in user to other scenes.
	 * @param event		The event triggering this method (in this case, pressing the "return to effort console" button).
	 */
	public void openLogConsole(ActionEvent event) {
		try {																					
			
			FXMLLoader loader = new FXMLLoader(getClass().getResource("EffortConsole.fxml"));
			Parent root = loader.load();
			EffortConsoleController consoleController = loader.getController();
			consoleController.keepUser(primaryStage, true, users, user, tasks);
			Scene scene = new Scene(root,1280,720);
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) {
			System.out.println("Could not load scene Console.fxml");
			e.printStackTrace();
		}
	}
	
	
	private void errorAlert(Alert alert, String title, String text) {
		alert.setTitle(title);
		alert.setContentText(text);
		alert.show();
	}
}