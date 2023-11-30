package application;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

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
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;
import javafx.util.StringConverter;

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
	private Label defectNum;
	@FXML
	private TextField defectName;
	@FXML
	private TextField defectDetail;
	@FXML
	private Label defectStatus;
	@FXML
    private ComboBox<DefectLog> defectSelector;
	@FXML
	private ComboBox<DefectLog> defectFix;				// TODO: ensure there is an option to select no defect
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
	
	private List<DefectLog> defectInfo;
	private List<EffortLog> effortInfo;
	private DefectLog currDefect;
	
	
	
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
	
	
	
	
	/**
	 * Initializes the options in the choice boxes.
	 */
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		stepInjected.getItems().addAll(lifecycleSteps);
		stepRemoved.getItems().addAll(lifecycleSteps);
		defectCategory.getItems().addAll(defectCategories);
		defectSelector.setConverter(converter);
		defectFix.setConverter(converter);
		//defectSelector.getItems().addAll(defectInfo);
		defectSelector.setOnAction(this::populateDefectConsole);
	}
	
	// for defect selection
	private StringConverter<DefectLog> converter = new StringConverter<DefectLog>() {

		@Override
		public DefectLog fromString(String string) {
			//for (DefectLog log : defectInfo) {
			//	// go through list and find corresponding entry
			//	if (log.getName() == string) {
			//		return log;
			//	}
			//}
			return null;
		}

		@Override
		public String toString(DefectLog defectLog) {
			if (defectLog != null)
				return defectLog.getName();
			return "-no defect selected-";
		}
	};
	
	/**
	 * Updates the user interface to display properties of the selected defect.
	 * @param event		The event triggering this method (in this case, the selection of a defect from the defectSelector ComboBox).
	 */
	public void populateDefectConsole(ActionEvent event) {		// setOnAction activates EVERY TIME a choice is selected
		if (defectSelector.getValue() == null) {
			System.out.println("Something changed");
		} else {
			System.out.println(defectSelector.getValue().toString());
			currDefect = defectSelector.getValue();
			System.out.println("Current Defect: " + currDefect.toString());
			defectNum.setText(Integer.toString(currDefect.getNumber()));
			defectName.setText(currDefect.getName());
			defectDetail.setText(currDefect.getDetail());
			stepInjected.setValue(currDefect.getInjected());
			stepRemoved.setValue(currDefect.getRemoved());
			defectCategory.setValue(currDefect.getCategory());
			defectStatus.setText(currDefect.getStatus());
			if (currDefect.getFix() < 2 || currDefect.getFix() > defectInfo.size() + 1) {
				defectFix.setValue(null);
			} else {
				defectFix.setValue(defectInfo.get(currDefect.getFix() - 2));		// might be able to change to defectFix.getSelectionModel().select(currDefect.getFix()-2);
			}
		}
		
	}
	
	
	
	/**
	 * Imports an existing project from an Excel workbook and gets the list of defect logs.
	 * @param event		The event triggering this method (in this case, pressing the "import project" button).
	 */
	public void importProject (ActionEvent event) {		
		// get list of defects from Excel and pass it to populateDefectSelector()
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
			defectSelector.setItems(FXCollections.observableList(defectInfo));
			defectFix.setItems(FXCollections.observableList(defectInfo));
		}
	}
	
	/**
	 * Creates a new defect and appends it to the defect list.
	 * @param event		The event triggering this method (in this case, pressing the "log a new defect" button).
	 */
	public void addDefect(ActionEvent event) {
		if (defectInfo == null) {
			errorAlert(alert, "No project chosen", "You must first import a project!");
		} else {
			currDefect = new DefectLog(defectInfo.size() + 1, "-new defect-", "", "", "", "", "Open", -1);
			defectInfo.add(currDefect);
			defectSelector.setItems(FXCollections.observableList(defectInfo));
			defectFix.setItems(FXCollections.observableList(defectInfo));
			defectSelector.getSelectionModel().select(currDefect);	//TODO: check if other fields were filled in after this
		}
	}
	
	/****
	 * Updates the selected defect with the values in the editable fields, then saves it to the log.
	 * @param event 	The event triggering this method (in this case, pressing the "update defect" button).
	 */
	public void updateDefect(ActionEvent event) {
		
		if (currDefect == null) {
			errorAlert(alert, "No defect chosen", "You must first select a defect!");
		} else {
			currDefect.setName(defectName.getText());
			currDefect.setDetail(defectDetail.getText());
			currDefect.setInjected(stepInjected.getValue());
			currDefect.setRemoved(stepRemoved.getValue());
			currDefect.setCategory(defectCategory.getValue());
			currDefect.setStatus(defectStatus.getText());
			if (defectFix.getValue() == null) {
				currDefect.setFix(-1);
			} else {
				currDefect.setFix(defectFix.getValue().getNumber() + 1);
			}
			
			for (DefectLog d : defectInfo) {			// just to prove it works
				System.out.println(d);
			}
			ExcelController.write(projFile, (ArrayList<EffortLog>)effortInfo, (ArrayList<DefectLog>)defectInfo);
			//defectInfo = ExcelController.readDefectLogs(projFile);
			defectSelector.setItems(FXCollections.observableList(defectInfo));// resets the selection model but still keeps the defect selected???
			defectSelector.getSelectionModel().select(currDefect);		// did absolutely nothing
			defectFix.setItems(FXCollections.observableList(defectInfo));	
			defectFix.getSelectionModel().select(currDefect.getFix()-2);
		}
		
	}
	
	/**
	 * Changes the status of a defect.
	 * @param event		The event triggering this method (in this case, pressing the "change defect status" button).
	 */
	public void changeStatus(ActionEvent event) {
		if (currDefect == null) {
			errorAlert(alert, "No defect chosen", "You must first select a defect!");
		}
		else if (defectStatus.getText().equals("Closed"))
			defectStatus.setText("Open");
		else
			defectStatus.setText("Closed");
	}
	
	/**
	 * Removes the selected defect from the defect list.
	 * @param event		The event triggering this method (in this case, pressing the "delete the current defect" button).
	 */
	public void deleteDefect(ActionEvent event) {
		// ensure that the selected defect is not null before trying to delete anything, and set the selected defect to null after
		if (currDefect == null) {
			errorAlert(alert, "No defect chosen", "You must first select a defect!");
		} else {
			
			defectInfo.remove(currDefect);
			currDefect = null;
			
			ExcelController.write(projFile, (ArrayList<EffortLog>)effortInfo, (ArrayList<DefectLog>)defectInfo);
			defectInfo = ExcelController.readDefectLogs(projFile);
			defectSelector.setItems(FXCollections.observableList(defectInfo));
			System.out.println("Selected defect is no more");
		}
	}
	
	public void clearDefectLog(ActionEvent event) {
		if (projFile == null) {
			errorAlert(alert, "No project chosen", "You must first import a project!");
		} else {
			alert.setTitle("Delete all");
			alert.setAlertType(AlertType.WARNING);
			alert.setContentText("Are you sure you want to delete ALL defect log entries (this cannot be undone)?");
			alert.showAndWait().ifPresent(response -> {
				if (response == ButtonType.OK) {
					defectInfo.clear();
					currDefect = null;
					defectSelector.setItems(FXCollections.observableList(defectInfo));
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
