package asuHelloWorldJavaFX;

import java.util.ArrayList;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
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

	public void createSinglePlayerScreen(Stage primaryStage) {
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
				historicalData.ProjectDataPage(primaryStage, null, null, false, null);
			}
		});
    	
    	shareButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent actionEvent) {
				sharePage.sharePage(planningPoker);
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
    	
    	primaryStage.setScene(new Scene(gridPane, 400, 400));
    	primaryStage.show();
	}
	
	private boolean checkTextInput(String value) {
    	return value == null || value.isBlank() || value.isEmpty();
    }
}
