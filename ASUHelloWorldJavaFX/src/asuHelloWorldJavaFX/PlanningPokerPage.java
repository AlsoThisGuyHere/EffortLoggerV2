package asuHelloWorldJavaFX;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

// Hasan Shahid
public class PlanningPokerPage {
	
	public void createPlanningPokerPage(Stage primaryStage) {
		SinglePlayerPage singlePlayerPage = new SinglePlayerPage();
		
		GridPane gridPane = new GridPane();
		
		gridPane.setHgap(0.1);
    	gridPane.setVgap(0.1);
    	gridPane.setAlignment(Pos.CENTER);
    	
    	Button singlePlayerButton = new Button("Single Player");
    	singlePlayerButton.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent actionEvent) {
				singlePlayerPage.createSinglePlayerScreen(primaryStage);
			}
		});
    	
    	gridPane.addRow(0, singlePlayerButton);
    	
    	primaryStage.setScene(new Scene(gridPane, 400, 400));
    	primaryStage.show();
	}
}
