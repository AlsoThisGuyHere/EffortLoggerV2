package asuHelloWorldJavaFX;

import java.util.List;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class AddTaskPage {
	
	private TaskPage taskPage = new TaskPage();
	
	public void addTaskPage(Stage primaryStage, List<User> users, List<Task> tasks, boolean checkAuthentication, User user) {
    	GridPane gridPane = new GridPane();
    	
    	Label taskLabel = new Label("Please add a task");
    	
    	TextField tfTask = new TextField();
    	
    	Label estimateLabel = new Label("Please add estimate");
    	
    	TextField tfEstimate = new TextField();
    	
    	Alert alert = new Alert(AlertType.ERROR);
    	
    	Button taskButton = new Button("Add Task");
    	taskButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent actionEvent) {
				if (checkTextInput(tfTask.getText())) {
					alert.setTitle("Missing Task");
					alert.setContentText("Please add the task name.");
					alert.show();
					addTaskPage(primaryStage, users, tasks, checkAuthentication, user);
				}
				else {
					tasks.add(new Task(tfTask.getText(), tfEstimate.getText()));
					taskPage.createPlanningPokerPage(primaryStage, checkAuthentication, users, user, tasks);
				}
			}
		});
    	
    	gridPane.setHgap(0.1);
    	gridPane.setVgap(0.1);
    	gridPane.setAlignment(Pos.CENTER);
    	
    	gridPane.addRow(0, taskLabel);
    	gridPane.addRow(1, tfTask);
    	gridPane.addRow(3, estimateLabel);
    	gridPane.addRow(4, tfEstimate);
    	gridPane.addRow(5, taskButton);
    	
    	primaryStage.setScene(new Scene(gridPane, 400, 400));
    	primaryStage.show();
    }
	
	private boolean checkTextInput(String value) {
    	return value == null || value.isBlank() || value.isEmpty();
    }
}
