package forLater;

import java.util.ArrayList;
import java.util.List;

import application.User;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.MultipleSelectionModel;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class TaskController {

	Alert alert = new Alert(AlertType.ERROR);
	private String task;
	private User user;
	private ArrayList<String> tasks = new ArrayList<String>();

	@FXML
	private Stage primaryStage;
	@FXML
	private Scene scene;
	@FXML
	private Parent root;
	
	@FXML
	private Button deleteButton;
	@FXML
    private Button deleteButtonWithoutAuthorizarion;
	@FXML
    private Button addButton;
	@FXML
    private Button addTaskWithoutAuthorization;
	@FXML
    private Button logoutButton;
	
	@FXML
	private ListView<String> listView;
	
	public void addTask(ActionEvent event) {
		System.out.println("adding");
		if ("admin".equals(user.getRole())) {
			alert.setTitle("Missing Authorization");
			alert.setContentText("You aren't authorized to add tasks.");
			alert.show();
		}
		else {
			//addTaskPage.addTaskPage(primaryStage, users, tasks, true, user);
			try {
	    		root = FXMLLoader.load(getClass().getResource("AddTaskPage.fxml"));    // add try/catch and finish tutorial
	    		primaryStage = (Stage)((Node)event.getSource()).getScene().getWindow();
	    		scene = new Scene(root);
	    		primaryStage.setScene(scene);
	    		primaryStage.show();
	    	}
	    	catch (Exception except) {
	    		except.printStackTrace();
	    	}
		}
	}
	public void removeTask(ActionEvent event) {
		System.out.println("removing");
		deleteTask(primaryStage, alert, true, user, /*users, */tasks);
	}
	public void addTaskNoAuth(ActionEvent event) {
		System.out.println("adding without authR");
		
	}
	public void removeTaskNoAuth(ActionEvent event) {
		System.out.println("removing without authR");
		deleteTask(primaryStage, alert, false, user, /*users, */tasks);
	}
	public void logout(ActionEvent event) {
		System.out.println("logging out");
		user = null;
		try {
    		root = FXMLLoader.load(getClass().getResource("Main.fxml"));
    		primaryStage = (Stage)((Node)event.getSource()).getScene().getWindow();
    		scene = new Scene(root);
    		primaryStage.setScene(scene);
    		primaryStage.show();
    	}
    	catch (Exception except) {
    		except.printStackTrace();
    	}
	}
	
	private void deleteTask(Stage primaryStage, Alert alert, boolean checkAuthorization, User loggedInUser, /*List<User> users, */List<String> tasks) {
    	System.out.println(checkAuthorization + " " + !"admin".equals(loggedInUser.getRole()));
		if (checkAuthorization && !"admin".equals(loggedInUser.getRole())) {
			alert.setTitle("Missing Authorization");
			alert.setContentText("You aren't authorized to delete tasks.");
			alert.show();
			//createPlanningPokerPage(primaryStage, true, users, loggedInUser, tasks);   
		}
		else if (tasks.isEmpty()) {
			alert.setTitle("No Tasks");
			alert.setContentText("There aren't any task present.");
			alert.show();
			//createPlanningPokerPage(primaryStage, true, users, loggedInUser, tasks);
		}
		else if (task == null) {
			alert.setTitle("Task Not Selected");
			alert.setContentText("There aren't any task selected for deletion");
			alert.show();
			//createPlanningPokerPage(primaryStage, true, users, loggedInUser, tasks);
		}
		else {
			tasks.remove(task);
			//createPlanningPokerPage(primaryStage, true, users, loggedInUser, tasks);    // change this to refer to TaskPage.fxml
		}
	}
	
	private void swapScene(String newScene, ActionEvent event) {
		try {
    		root = FXMLLoader.load(getClass().getResource(newScene));
    		primaryStage = (Stage)((Node)event.getSource()).getScene().getWindow();
    		scene = new Scene(root);
    		primaryStage.setScene(scene);
    		primaryStage.show();
    	}
    	catch (Exception except) {
    		except.printStackTrace();
    	}
	}
}
