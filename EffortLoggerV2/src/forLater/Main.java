package forLater;

import java.io.File;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;	// testing something

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
    	
    	primaryStage.setTitle("Excel Creation");
        Button btn = new Button();
        btn.setText("Create Excel File");
        btn.setOnAction(new EventHandler<ActionEvent>() {
 
            @Override
            public void handle(ActionEvent event) {
            	FileChooser chooser = new FileChooser();
            	chooser.getExtensionFilters().addAll(
       		         new ExtensionFilter("Worksheets", "*.xlsx", "*.xlsm", "*.xlsb", "*.xls")
       		         );
            	chooser.setInitialFileName("Book1.xlsx");
                File file = chooser.showSaveDialog(primaryStage);
                ExcelCreator.createExcel(file);
                //System.out.println(file.getAbsolutePath());
            }
        });
        
        StackPane root = new StackPane();
        root.getChildren().add(btn);
        primaryStage.setScene(new Scene(root, 300, 250));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}