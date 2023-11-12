package asuHelloWorldJavaFX;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class SearchHistoricalDataPage {

	public void createSearchHistoricalPage() {
		List<Task> tasks = new ArrayList<Task>();
		tasks.add(new Task("Some task name", "2"));
		
		GridPane gridPane = new GridPane();
		
		Label label = new Label("Search by keyword");
		label.setUnderline(true);
		
		TextField tfKeywordSearch = new TextField();
		
		Button searchButton = new Button("Search");
		searchButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent actionEvent) {
				
				List<Task> searchTasks = tasks.stream()
						.filter(task -> task.getTask().toLowerCase().contains(tfKeywordSearch.getText().toLowerCase()))
						.collect(Collectors.toList());
				
				if (!searchTasks.isEmpty()) {
					ObservableList<Task> task = FXCollections.observableList(searchTasks);
					ListView<Task> listView = new ListView<Task>(task);
					gridPane.getChildren().removeIf(node -> GridPane.getRowIndex(node) == 3);
					gridPane.addRow(3, listView);
				}
				else {
					Label label = new Label("No tasks found");
					gridPane.getChildren().removeIf(node -> GridPane.getRowIndex(node) == 3);
					gridPane.addRow(3, label);
				}
			}
		});
		
		gridPane.setHgap(0.1);
    	gridPane.setVgap(0.1);
    	gridPane.setAlignment(Pos.CENTER);
		gridPane.addRow(0, label);
		gridPane.addRow(1, tfKeywordSearch);
		gridPane.addRow(2, searchButton);
		
		Stage stage = new Stage();
		stage.initModality(Modality.APPLICATION_MODAL);
		stage.setTitle("Search Historical Data");
		stage.setScene(new Scene(gridPane, 400, 400));
		stage.show();
	}
}
