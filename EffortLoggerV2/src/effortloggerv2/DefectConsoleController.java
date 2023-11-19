package effortloggerv2;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import FXDirectoryExcel.DefectLog;
import FXDirectoryExcel.EffortLog;
import FXDirectoryExcel.ExcelController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import javafx.util.StringConverter;
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
	private Label defectNum;
	@FXML
	private Label defectName;
	@FXML
	private TextField defectDetail;
	@FXML
	private Label defectStatus;
	@FXML
    private ChoiceBox<DefectLog> defectSelector;
	@FXML
	private ChoiceBox<DefectLog> defectFix;
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
	
	private List<DefectLog> defectInfo;
	private DefectLog currDefect;
	
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
			// TODO Auto-generated method stub
			if (defectLog != null)
				return defectLog.getName();
			return "-no defect selected-";
		}
	
	
	};
	
	
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
			defectSelector.setItems(FXCollections.observableList(defectInfo));
			defectFix.setItems(FXCollections.observableList(defectInfo));				// TODO: try this to ensure it works
		}
	}
	
	
	public void updateDefect(ActionEvent event) {
		
		currDefect.setName(defectName.getText());
		currDefect.setCategory(defectCategory.getValue());
		
		
		for (DefectLog d : defectInfo) {			// just to prove it works
			System.out.println(d);
		}
		//ExcelController.writeDefectLog(projFile, (ArrayList<DefectLog>)defectInfo);
		
		// now need to call updateDefect in the Excel creator and pass it defectInfo to insert into Excel
		// get list of defects from Excel and pass it to populateDefectSelector()
		
	}
	
	public void addDefect(ActionEvent event) {
		currDefect = new DefectLog();
		defectInfo.add(currDefect);
		defectSelector.setItems(FXCollections.observableList(defectInfo));
		defectFix.setItems(FXCollections.observableList(defectInfo));
	}
	
	public void deleteDefect(ActionEvent event) {
		// ensure that the selected defect is not null before trying to delete anything, and set the selected defect to null after
		System.out.println("Selected defect is no more");
		if (currDefect == null) {
			alert.setTitle("No defect chosen");
			alert.setContentText("You must first select a defect!");
			alert.show();
		} else {
			
		}
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
	
	public void changeStatus(ActionEvent event) {
		
		if (currDefect == null) {
			alert.setTitle("No defect chosen");
			alert.setContentText("You must first select a defect!");
			alert.show();
		}
		else if (defectStatus.getText().equals("Closed"))
			defectStatus.setText("Open");
		else
			defectStatus.setText("Closed");
	}
	
	public void populateDefectSelector(ActionEvent event) {		// setOnAction activates EVERY TIME a choice is selected
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
		defectFix.setValue(defectInfo.get(currDefect.getFix() - 2));
		
	}
	
	// initializes the options in the choice boxes
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		//defectSelector.getItems().addAll(defects);
		//defectFix.getItems().addAll(defects);
		stepInjected.getItems().addAll(lifecycleSteps);
		stepRemoved.getItems().addAll(lifecycleSteps);
		defectCategory.getItems().addAll(defectCategories);
		defectSelector.setConverter(converter);
		defectFix.setConverter(converter);
		//defectSelector.getItems().addAll(defectInfo);
		defectSelector.setOnAction(this::populateDefectSelector);
	}
}
