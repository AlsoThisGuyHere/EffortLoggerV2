package asuHelloWorldJavaFX;

import java.util.ArrayList;
import java.util.List;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
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

public class TaskPage {
	
	private AddTaskPage addTaskPage;
	
	private Task task;
	
	private User user;
	
	private ASUHelloWorldJavaFX main = new ASUHelloWorldJavaFX();
	
	public void createPlanningPokerPage(Stage primaryStage,
			boolean authenticationCheck,
			List<User> users,
			User loggedInUser) {
		addTaskPage = new AddTaskPage();
		createPlanningPokerPage(primaryStage, authenticationCheck, users, loggedInUser, new ArrayList<Task>());
	}

	public void createPlanningPokerPage(Stage primaryStage,
			boolean authenticationCheck,
			List<User> users,
			User loggedInUser,
			List<Task> tasks) {
		addTaskPage = new AddTaskPage();
    	GridPane gridPane = new GridPane();
    	user = loggedInUser;
    	
    	Alert alert = new Alert(AlertType.ERROR);

    	if (authenticationCheck && (user == null || !user.getIsLoggedIn())) {
    		alert.setTitle("Invalid Access");
    		alert.setContentText("User is not logged in");
    		alert.show();
    		main.login(primaryStage, users, tasks);
    	}
    	else {
    		Rectangle rectangle = new Rectangle();
        	rectangle.setX(0);
        	rectangle.setY(0);
        	rectangle.setWidth(350);
        	rectangle.setHeight(350);
        	rectangle.setFill(Color.ALICEBLUE);
        	
        	Label username = new Label();
            username.setText("Username");
            username.setUnderline(true);
            
            Text text = new Text();
            text.setText("Sample");
            
            Button deleteButton = new Button("Delete Task");
            Button deleteButtonWithoutAuthorizarion = new Button("Delete Task without Authorization");
            Button addButton = new Button("Add Task");
            Button addTaskWithoutAuthorization = new Button("Add Task without Authorization");
            Button logoutButton = new Button("Logout");
            
            List<String> stringTasks = new ArrayList<String>();
            for (int i = 0; i < tasks.size(); i++) {
            	stringTasks.add(tasks.get(i).getTask() + " " + tasks.get(i).getEstimates());
            }

            ListView<String> listView = new ListView<String>();
            ObservableList<String> items = FXCollections.observableArrayList(stringTasks);
            System.out.println("Items: " + items.size());
            listView.setItems(items);
            
            MultipleSelectionModel<String> lvSelModel = listView.getSelectionModel();
            lvSelModel.selectedItemProperty().addListener(new ChangeListener<String>() {
    			@Override
    			public void changed(ObservableValue<? extends String> changed, String oldVal, String newVal) {
    				String[] split = newVal.split(" ");
    				task = getTask(split[0], tasks);
    			}
    		});
            
            deleteButton.setOnAction(new EventHandler<ActionEvent>() {
    			@Override
    			public void handle(ActionEvent actionEvent) {
    				deleteTask(primaryStage, alert, true, user, users, tasks);
    			}
    		});
            
            addButton.setOnAction(new EventHandler<ActionEvent>() {
    			@Override
    			public void handle(ActionEvent actionEvent) {
    				if (user != null && "admin".equals(user.getRole())) {
    					alert.setTitle("Missing Authorization");
    					alert.setContentText("You aren't authorized to add tasks.");
    					alert.show();
    				}
    				else {
    					addTaskPage.addTaskPage(primaryStage, users, tasks, true, user);
    				}
    			}
    		});
            
            deleteButtonWithoutAuthorizarion.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent actionEvent) {
					deleteTask(primaryStage, alert, false, user, users, tasks);
				}
			});
            
            addTaskWithoutAuthorization.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent actionEvent) {
					addTaskPage.addTaskPage(primaryStage, users, tasks, false, user);
				}
			});
            
            logoutButton.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent arg0) {					
					user = null;
					main.login(primaryStage, users, tasks);
				}
			});
        	
        	gridPane.setHgap(0.1);
        	gridPane.setVgap(0.1);
        	gridPane.setAlignment(Pos.CENTER);
        	
        	gridPane.addRow(0, username, text);
        	gridPane.addRow(1, listView);
        	gridPane.addRow(2, deleteButtonWithoutAuthorizarion);
        	gridPane.addRow(3, addTaskWithoutAuthorization);
        	gridPane.addRow(4, addButton, deleteButton);
        	gridPane.addRow(5, logoutButton);
//        	gridPane.addRow(1, rectangle);
        	
        	primaryStage.setScene(new Scene(gridPane, 400, 400));
        	primaryStage.show();
    	}
    }
	
	private void deleteTask(Stage primaryStage, Alert alert, boolean checkAuthorization, User loggedInUser, List<User> users, List<Task> tasks) {
		if (checkAuthorization && (loggedInUser != null && !"admin".equals(loggedInUser.getRole()))) {
			alert.setTitle("Missing Authorization");
			alert.setContentText("You aren't authorized to delete tasks.");
			alert.show();
			createPlanningPokerPage(primaryStage, checkAuthorization, users, loggedInUser, tasks);
		}
		else if (tasks.isEmpty()) {
			alert.setTitle("No Tasks");
			alert.setContentText("There aren't any task present.");
			alert.show();
			createPlanningPokerPage(primaryStage, checkAuthorization, users, loggedInUser, tasks);
		}
		else if (task == null) {
			alert.setTitle("Task Not Selected");
			alert.setContentText("There aren't any task selected for deletion");
			alert.show();
			createPlanningPokerPage(primaryStage, checkAuthorization, users, loggedInUser, tasks);
		}
		else {
			tasks.remove(task);
			createPlanningPokerPage(primaryStage, checkAuthorization, users, loggedInUser, tasks);
		}
	}
	
	private Task getTask(final String task, final List<Task> tasks) {
		return tasks.stream().filter(u -> u.getTask().equals(task)).findFirst().orElse(null);
	}
}
