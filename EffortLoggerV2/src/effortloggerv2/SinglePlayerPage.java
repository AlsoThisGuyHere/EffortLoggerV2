package effortloggerv2;

import java.util.ArrayList;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

// Hasan Shahid
public class SinglePlayerPage {
	List<PlanningPoker> planningPoker = new ArrayList<PlanningPoker>();
	
	SearchHistoricalDataPage historicalDataPage = new SearchHistoricalDataPage();
	
	SharePage sharePage = new SharePage();
	
	HistoricalData historicalData = new HistoricalData();
	
	ReadWrite readWrite = new ReadWrite("src\\task.txt");

	// Method to show single player screen for users to add estimates
	public void createSinglePlayerScreen(Stage primaryStage, User user, List<User> users, boolean authenticationStatus) {
		GridPane gridPane = new GridPane();
		
		gridPane.setHgap(0.1);
    	gridPane.setVgap(0.1);
    	gridPane.setAlignment(Pos.CENTER);
    	
    	Label taskLabel = new Label("Task Title");
    	
    	TextField tfTaskName = new TextField();
    	
    	Label estimateLabel = new Label("Estimate");
    	
    	String estimates[] = {"1", "2", "3", "5", "8", "?"};
    	ComboBox<String> comboBox = new ComboBox<String>(FXCollections.observableArrayList(estimates));
    	
    	Label reasonLabel = new Label("Reason for the estimate");
    	
    	TextArea reasonArea = new TextArea();
    	reasonArea.setPrefHeight(300);
    	reasonArea.setPrefWidth(300);
    	
    	Button searchHistoricalDataButton = new Button("Search Historical Data");
    	Button saveButton = new Button("Save");
    	Button shareButton = new Button("Share");
    	Button effortConsoleButton = new Button("Return to Effort Console");
    	
    	Alert alert = new Alert(AlertType.ERROR);
    	
    	saveButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent actionEvent) {
				if (checkTextInput(tfTaskName.getText()) || checkTextInput(comboBox.getValue()) || checkTextInput(reasonArea.getText())) {
					alert.setAlertType(AlertType.ERROR);
					alert.setContentText("All the fields are required");
					alert.setTitle("Planning Poker Estimation Error");
					alert.show();
				}
				else {
					PlanningPoker poker = new PlanningPoker(tfTaskName.getText(), comboBox.getValue(), reasonArea.getText());
					planningPoker.add(poker);
					alert.setAlertType(AlertType.INFORMATION);
					alert.setContentText("Task estimated successfully with estimate of " + comboBox.getValue());
					alert.setTitle("Planning Poker Estimate");
					readWrite.append(tfTaskName.getText() + " " + comboBox.getValue() + " " + reasonArea.getText(), true);
					System.out.println("Read: " + readWrite.read());
					alert.show();
					tfTaskName.setText(null);
					comboBox.setValue(null);
					reasonArea.setText(null);
				}
			}
		});
    	
    	searchHistoricalDataButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent actionEvent) {
//				historicalDataPage.createSearchHistoricalPage();
				historicalData.ProjectDataPage(primaryStage, users, null, authenticationStatus, user);
			}
		});
    	
    	shareButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent actionEvent) {
				sharePage.sharePage(planningPoker);
			}
		});
    	
    	effortConsoleButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent actionEvent) {
				try {																					
					
					FXMLLoader loader = new FXMLLoader(getClass().getResource("EffortConsole.fxml"));
					Parent root = loader.load();
					EffortConsoleController consoleController = loader.getController();
					consoleController.keepUser(primaryStage, authenticationStatus, users, user);
					Scene scene = new Scene(root,1280,720);
					primaryStage.setScene(scene);
					primaryStage.show();
				} catch(Exception e) {
					System.out.println("Could not load scene EffortConsole.fxml");
					e.printStackTrace();
				}
			}
		});
    	
    	gridPane.addRow(0, taskLabel);
    	gridPane.addRow(1, tfTaskName);
    	gridPane.addRow(2, estimateLabel);
    	gridPane.addRow(3, comboBox);
    	gridPane.addRow(4, reasonLabel);
    	gridPane.addRow(5, reasonArea);
    	gridPane.addRow(6, saveButton);
    	gridPane.addRow(7, searchHistoricalDataButton);
    	gridPane.addRow(8, shareButton);
    	gridPane.addRow(9, effortConsoleButton);
    	
    	primaryStage.setScene(new Scene(gridPane, 1280, 720));
    	primaryStage.show();
	}
	
	private boolean checkTextInput(String value) {
    	return value == null || value.isBlank() || value.isEmpty();
    }
}
