package application;
// importing useful java and javafx tools
import java.util.List;

import FXDirectoryExcel.ExcelData1;
import application.HistoricalData.ProjectData;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.application.Application;
import javafx.collections.transformation.FilteredList;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

// Author : Connor Ott
// class containing various methods to implement historical data functionality into planning poker
public class HistoricalData {
	private EffortLoggerPage taskPage = new EffortLoggerPage(); // creating a new effortLogger page
	private static int count = 0; // creating a count for enterExcel() and setting to 0
	// method to display the project data page
	public void ProjectDataPage(Stage primaryStage, List<User> users, List<Task> tasks, boolean checkAuthentication, User user) {
		//GridPane gridPane = new GridPane();
		table.setEditable(true); // making the table editable
			
			// creating a new scene and setting its title, height and width
	        Scene scene = new Scene(new Group());
	        primaryStage.setTitle("Viewing Historical Data");
	        primaryStage.setWidth(450);
	        primaryStage.setHeight(550);
	        // creating a label for the scene and setting its font
	        final Label label = new Label("Historical Data:");
	        label.setFont(new Font("Arial", 20));
	        
	        // creating columns for the table
	        TableColumn projectNameCol = new TableColumn("Project Name");
	        projectNameCol.setMinWidth(100);
	        projectNameCol.setCellValueFactory(
	                new PropertyValueFactory<ProjectData, String>("projectName"));

	        TableColumn projectDateCol = new TableColumn("Project Date");
	        projectDateCol.setMinWidth(100);
	        projectDateCol.setCellValueFactory(
	                new PropertyValueFactory<ProjectData, String>("projectDate"));

	        TableColumn numEntriesCol = new TableColumn("Number of Entries");
	        numEntriesCol.setMinWidth(200);
	        numEntriesCol.setCellValueFactory(
	                new PropertyValueFactory<ProjectData, String>("numEntries"));
	        
	        // creating a filtered list to implement searching for historical data
	        FilteredList<ProjectData> flProjectData = new FilteredList(data, p -> true);//Pass the data to a filtered list
	        table.setItems(flProjectData);//Set the table's items using the filtered list
	        table.getColumns().addAll(projectNameCol, projectDateCol, numEntriesCol);

	        //Adding ChoiceBox and TextField for the data list here!
	        ChoiceBox<String> choiceBox = new ChoiceBox();
	        choiceBox.getItems().addAll("Project Name", "Project Date", "Number of Entries");
	        choiceBox.setValue("Project Name");

	        TextField textField = new TextField();
	        textField.setPromptText("Search here!");
	        textField.textProperty().addListener((obs, oldValue, newValue) -> {
	            switch (choiceBox.getValue())//Switch on choiceBox value so user can choose how they want to search the data
	            {
	                case "Project Name":
	                    flProjectData.setPredicate(p -> p.getProjectName().toLowerCase().contains(newValue.toLowerCase().trim()));//filter table by first name
	                    break;
	                case "Project Date":
	                    flProjectData.setPredicate(p -> p.getProjectDate().toLowerCase().contains(newValue.toLowerCase().trim()));//filter table by last name
	                    break;
	                case "Number of Entries":
	                    flProjectData.setPredicate(p -> p.getNumEntries().toLowerCase().contains(newValue.toLowerCase().trim()));//filter table by email
	                    break;
	            }
	        });

	        choiceBox.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal)
	                -> {//resetting table and textfield when new choice is selected
	            if (newVal != null) {
	                textField.setText("");
	            }
	        });
	        // creating a back button
	        Button button = new Button("Back");
	        button.setOnAction(new EventHandler<ActionEvent>() {
				
				@Override // going back to planning poker page when the back button is selected
				public void handle(ActionEvent actionEvent) {
					SinglePlayerPage singlePlayerPage = new SinglePlayerPage();
					primaryStage.setTitle("Planning Poker");
					primaryStage.setWidth(400);
					primaryStage.setHeight(400);
					singlePlayerPage.createSinglePlayerScreen(primaryStage);
				}
			});
	        // creating new hbox and adjusting some box settings
	        HBox hBox = new HBox(choiceBox, textField);//Adding choiceBox and textField to hBox
	        hBox.setAlignment(Pos.CENTER);//Center HBox
	        final VBox vbox = new VBox();
	        vbox.setSpacing(5);
	        vbox.setPadding(new Insets(10, 0, 0, 10));
	        vbox.getChildren().addAll(label, table, hBox, button);

	        ((Group) scene.getRoot()).getChildren().addAll(vbox);
	        
	        // displaying the historical data and enterExcel screens
	        primaryStage.setScene(scene);
	        primaryStage.show();
	        enterExcel();
	        
	} 
		// method to gather excel data and add it to the list
		public static void enterExcel() {
			// open new window asking user to give file path
  		  TextField textField1 = new TextField();
  	      Button button = new Button("Submit file path");
  	      button.setTranslateX(250);
  	      button.setTranslateY(100);
  	      //Creating label
  	      String fileForm = " C:\\Users\\user name\\file location\\file name(ending in .xlsx)";
  	      Label label1 = new Label("Please enter a file path of the form" + fileForm + " and make sure the project name is in C1, the date is in B2, and # of entries in G1");
  	      Label label = new Label("File Path: ");
  	      //Setting the message with read data
  	      Text text = new Text("");
  	      //Setting font to the label and changing some UI settings
  	      Font font = Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 10);
  	      text.setFont(font);
  	      text.setTranslateX(15);
  	      text.setTranslateY(125);
  	      text.setFill(Color.BROWN);
  	      text.maxWidth(580);
  	      text.setWrappingWidth(580);
  	      button.setOnAction(e -> {
  	         //Retrieving data
  	         String path = textField1.getText(); // setting the file path to a String variable
  	         System.out.println(path); // printing the path to console for testing purposes
  	         if(path.contains(".xlsx")) {      	        	
  	        	 count++; // incrementing count if user gives a proper excel file
  	        	 String name = ExcelData1.getPName(path); // setting a name variable = to the value in excel
  	        	 String date = ExcelData1.getPDate(path); // setting a data variable = to the value in excel
  	        	 String entries = ExcelData1.getNEntries(path); // setting a entry variable = to the value in excel
  	        	 data.add(new ProjectData(name, date, entries)); // adding the data to the historicaldata table
  	        	 text.setText("Thank you for entering an excel file");   // message that prints to user
  	         } else {
  	        	 text.setText("Please enter a valid excel file path"); // message that prints to user
  	         }
  	      });
  	      //Adding labels for nodes
  	      VBox box = new VBox(5);
  	      box.setPadding(new Insets(25, 5 , 5, 50));
  	      box.getChildren().addAll(label1, label, textField1);
  	      Group root = new Group(box, button, text);
  	      //Setting the stage
  	      Stage stage = new Stage();
  	      Scene scene = new Scene(root, 1000, 150, Color.BEIGE);
  	      stage.setTitle("Add addtional excel files for Historical Data Analysis");
  	      stage.setScene(scene);
  	      stage.show();
		}
		// constructing a new table containing sample excel data as well as data added by the user in the enterExcel method
	  public static TableView<ProjectData> table = new TableView<ProjectData>();
	  	public static ObservableList<ProjectData> data
	            = FXCollections.observableArrayList(
	                new ProjectData("Sample Project", "11-29-2023", "1")
	                // data from enterExcel() gets added through data.add as user inputs the file paths in the application
	            		); 
	  // creating a public project data class that is used to get and set the historical data values in the table          
	  public static class ProjectData {
		  	// initializing project name, date, and entry variables
		  	private final SimpleStringProperty projectName = new SimpleStringProperty();
	        private final SimpleStringProperty projectDate = new SimpleStringProperty();
	        private final SimpleStringProperty numEntries = new SimpleStringProperty();
	        // contructor method that sets the values
	        private ProjectData(String pName, String pDate, String nEntries) {
	        	this.projectName.setValue(pName);
	            this.projectDate.setValue(pDate);
	            this.numEntries.setValue(nEntries);
	        }
	        // a bunch of getter and setter methods from 201-248
			public String getProjectName()
			{
				return projectName.get();
	        }

	        public void setProjectName(String pName)
	        {
	        	System.out.println(pName);
	        	projectName.set(pName);
	        }
	        
	        public SimpleStringProperty getProjectNameProperty()
	        {
	            return projectName;
	        }
	        
	        public String getProjectDate()
	        {
	            return projectDate.get();
	        }

	        public void setDate(String pDate)
	        {
	            System.out.println(pDate);
	        	projectDate.set(pDate);
	        }

	        public SimpleStringProperty getProjectDateProperty()
	        {
	            return projectDate;
	        }
	        
	        public String getNumEntries()
	        {
	            return numEntries.get();
	        }

	        public void setNumEntries(String nEntries)
	        {
	            System.out.println(nEntries);
	        	numEntries.set(nEntries);
	        }
	        
	        public SimpleStringProperty getnumEntriesProperty()
	        {
	            return numEntries;
	        }
	    }
	  	// input to check text input as needed
		 private boolean checkTextInput(String value) {
	        return value == null || value.isBlank() || value.isEmpty();
	    } 
		
}
