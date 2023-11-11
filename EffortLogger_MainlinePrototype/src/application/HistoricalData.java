package application;

import java.util.List;

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
import javafx.scene.text.Font;

// Author : Connor Ott
public class HistoricalData {
	private TaskPage taskPage = new TaskPage();
	
	public void ProjectDataPage(Stage primaryStage, List<User> users, List<String> tasks, boolean checkAuthentication, User user) {
		//GridPane gridPane = new GridPane();
		table.setEditable(true);
		
		//Label taskLabel = new Label("Historical Data:");
		TextField projectData = new TextField();
		Alert error = new Alert(AlertType.ERROR);
		
		Button dataButton = new Button("Project Data");
		dataButton.setOnAction(new EventHandler<ActionEvent>() {
            
            @Override
            public void handle(ActionEvent actionEvent) {
            	if (checkTextInput(projectData.getText())) {
                    error.setTitle("Please search for a project");
                    error.setContentText("Please include the project name.");
                    error.show();
                    ProjectDataPage(primaryStage, users, tasks, checkAuthentication, user);
            	} else {
            		tasks.add(projectData.getText());
                    taskPage.createPlanningPokerPage(primaryStage, true, users, user, tasks);
            	}
            }  
		}); 
	        Scene scene = new Scene(new Group());
	        primaryStage.setTitle("Viewing Historical Data");
	        primaryStage.setWidth(450);
	        primaryStage.setHeight(550);

	        final Label label = new Label("Historical Data:");
	        label.setFont(new Font("Arial", 20));
	        
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

	        FilteredList<ProjectData> flProjectData = new FilteredList(data, p -> true);//Pass the data to a filtered list
	        table.setItems(flProjectData);//Set the table's items using the filtered list
	        table.getColumns().addAll(projectNameCol, projectDateCol, numEntriesCol);

	        //Adding ChoiceBox and TextField here!
	        ChoiceBox<String> choiceBox = new ChoiceBox();
	        choiceBox.getItems().addAll("Project Name", "Project Date", "Number of Entries");
	        choiceBox.setValue("Project Name");

	        TextField textField = new TextField();
	        textField.setPromptText("Search here!");
	        textField.textProperty().addListener((obs, oldValue, newValue) -> {
	            switch (choiceBox.getValue())//Switch on choiceBox value
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
	                -> {//reset table and textfield when new choice is selected
	            if (newVal != null) {
	                textField.setText("");
	            }
	        });

	        HBox hBox = new HBox(choiceBox, textField);//Add choiceBox and textField to hBox
	        hBox.setAlignment(Pos.CENTER);//Center HBox
	        final VBox vbox = new VBox();
	        vbox.setSpacing(5);
	        vbox.setPadding(new Insets(10, 0, 0, 10));
	        vbox.getChildren().addAll(label, table, hBox);

	        ((Group) scene.getRoot()).getChildren().addAll(vbox);

	        primaryStage.setScene(scene);
	        primaryStage.show();
	    
}
	

	  private TableView<ProjectData> table = new TableView<ProjectData>();
	    private final ObservableList<ProjectData> data
	            = FXCollections.observableArrayList(
	                // eventually add data from excel file here
	            	// currently adds sample data to table
	            	new ProjectData("Sample Project", "11-11-2023", "5"),
	                new ProjectData("EffortLoggerV1", "10-16-2022", "30"),
	                new ProjectData("EfortLoggerV2", "10-22-2023", "50"),
	                new ProjectData("Sample Project 2", "8-19-2021", "45"),
	                new ProjectData("Test Project", "6-06-2022", "88")
	            );
	  public static class ProjectData {
		  	private final SimpleStringProperty projectName = new SimpleStringProperty();
	        private final SimpleStringProperty projectDate = new SimpleStringProperty();
	        private final SimpleStringProperty numEntries = new SimpleStringProperty();
	        
	        private ProjectData(String pName, String pDate, String nEntries) {
	        	this.projectName.setValue(pName);
	            this.projectDate.setValue(pDate);
	            this.numEntries.setValue(nEntries);
	        }
	        public String getProjectName()
	        {
	            return projectName.get();
	        }

	        public void setProjectName(String pName)
	        {
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

	        public void setLastName(String pDate)
	        {
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
	            numEntries.set(nEntries);
	        }
	        
	        public SimpleStringProperty getnumEntriesProperty()
	        {
	            return numEntries;
	        }
	    }
	  
		 private boolean checkTextInput(String value) {
	        return value == null || value.isBlank() || value.isEmpty();
	    } 
		
}
