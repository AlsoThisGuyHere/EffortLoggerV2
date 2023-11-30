package FXDirectoryExcel;

import java.io.File;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

//import FXDirectoryExcel.ExcelCreator;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;	// testing something

public class TestDriver extends Application{

	public void start(Stage primaryStage) throws Exception{
    	
		int effortNumber = 1;
		String effortDate = "2023-11-09";
		String effortStart = "20:21:28";
		String effortStop = "20:21:30";
		double effortTimeElapsed = 0.03;
		String effortLifeCycleStep = "Verifying";
		String effortCategory = "Interruptions";
		String effortDelInt = "Break";
		
		EffortLog effort = new EffortLog(effortNumber, effortDate, effortStart, effortStop,
											effortTimeElapsed, effortLifeCycleStep, effortCategory,
											effortDelInt);
		//System.out.println(effort.toString());
		
		int defectNumber = 1;
		String defectName = "-new defect-";
		String defectDetail = "i messes up";
		String defectInjected = "Finalizing";
		String defectRemoved = "Stakeholder Meeting";
		String defectCategory = "20 Syntax";
		String defectStatus = "Closed";
		int defectFix = 2;
		
		DefectLog defect = new DefectLog(defectNumber, defectName, defectDetail, defectInjected,
											defectRemoved, defectCategory, defectStatus, defectFix);
		//System.out.println(defect.toString());
		
		List<EffortLog> effortArray = new ArrayList<EffortLog>();
		effortArray.add(effort);
		effortArray.add(new EffortLog(2, "2023-11-09", "20:21:37", "20:21:38", 0.02, "Verifying",
										"Deliverables", "Conceptual Design"));
		effortArray.add(new EffortLog(3, "2023-11-09", "20:25:17", "20:25:19", 0.03, "Verifying",
										"Defects", "- new defect -"));
		
		List<DefectLog> defectArray = new ArrayList<DefectLog>();
		defectArray.add(defect);
		defectArray.add(new DefectLog(2, "defect2", "i did it again", "Team Meeting",
										"Team Meeting", "30 Build, Package", "Open", 0));
		
		for(int i = 0; i < effortArray.size(); i++)
		{
			System.out.println(effortArray.get(i).toString());
		}
		
		for(int i = 0; i < defectArray.size(); i++)
		{
			System.out.println(defectArray.get(i).toString());
		}
		
		Button newFile = new Button();
        newFile.setText("Create Excel File");
        Button getFile = new Button();
        getFile.setText("Import Excel File");
        Button saveFile = new Button();						// change this
        saveFile.setText("Save Excel File");
        
		
		// to make basic test UI
        Label logNum = new Label();
        Label date = new Label();
        Label start = new Label();
        Label stop = new Label();
        
        Label defectNum = new Label();
        Label name = new Label();
        Label detail = new Label();
        Label injected = new Label();
        
        GridPane root = new GridPane();
        root.addRow(0, newFile, logNum, defectNum);
        root.addRow(1, getFile, date, name);
        root.addRow(2, saveFile, start, detail);
        //grid.addRow(3, null, stop, injected);
        root.setAlignment(Pos.CENTER);
		
		primaryStage.setTitle("Excel Creation");
        newFile.setOnAction(new EventHandler<ActionEvent>() {
 
            @Override
            public void handle(ActionEvent event) {
            	FileChooser chooser = new FileChooser();
            	chooser.getExtensionFilters().addAll(
       		         new ExtensionFilter("Worksheets", "*.xlsx")
       		         );
            	chooser.setInitialFileName("Book1.xlsx");
                File file = chooser.showSaveDialog(primaryStage);
                ExcelController_Old.write(file, effortArray, defectArray);
            }
        });
        
        getFile.setOnAction(new EventHandler<ActionEvent>() {
 
            @Override
            public void handle(ActionEvent event) {
            	FileChooser chooser = new FileChooser();
            	chooser.getExtensionFilters().addAll(
       		         new ExtensionFilter("Worksheets", "*.xlsx")
       		         );
                File excelToRead = chooser.showOpenDialog(primaryStage);
        		
        		List<EffortLog> outputEffort = ExcelController.readEffortLogs(excelToRead);
        		
        		logNum.setText(Integer.toString(outputEffort.get(0).getNumber()));
        		
        		for(int i = 0; i < outputEffort.size(); i++)
        		{
        			System.out.println(outputEffort.get(i).toString());
        		}
        		
        		List<DefectLog> outputDefect = ExcelController.readDefectLogs(excelToRead);
        		
        		for(int i = 0; i < outputDefect.size(); i++)
        		{
        			System.out.println(outputDefect.get(i).toString());
        		}
            }
        });
        
        saveFile.setOnAction(new EventHandler<ActionEvent>() {
 
            @Override
            public void handle(ActionEvent event) {
            	FileChooser chooser = new FileChooser();
            	chooser.getExtensionFilters().addAll(
       		         new ExtensionFilter("Worksheets", "*.xlsx")
       		         );
                File excelToRead = chooser.showOpenDialog(primaryStage);
        		
        		List<EffortLog> outputEffort = ExcelController.readEffortLogs(excelToRead);
        		
        		for(int i = 0; i < outputEffort.size(); i++)
        		{
        			System.out.println(outputEffort.get(i).toString());
        		}
        		
        		List<DefectLog> outputDefect = ExcelController.readDefectLogs(excelToRead);
        		
        		for(int i = 0; i < outputDefect.size(); i++)
        		{
        			System.out.println(outputDefect.get(i).toString());
        		}//*/
            }
        });
        
        
        //StackPane root = new StackPane();
        //root.getChildren().add(newFile);
        primaryStage.setScene(new Scene(root, 300, 250));
        primaryStage.show();
    }
	
	public static File chooseFile(Stage primaryStage) {
		FileChooser chooser = new FileChooser();
    	chooser.getExtensionFilters().addAll(
		         new ExtensionFilter("Worksheets", "*.xlsx")
		         );
    	File excelToRead = chooser.showOpenDialog(primaryStage);
    	return excelToRead;
	}
	
	public static void main(String[] args) {
		
		launch(args);
		
		/*int effortNumber = 1;
		String effortDate = "2023-11-09";
		String effortStart = "20:21:28";
		String effortStop = "20:21:30";
		double effortTimeElapsed = 0.03;
		String effortLifeCycleStep = "Verifying";
		String effortCategory = "Interruptions";
		String effortDelInt = "Break";
		
		EffortLog effort = new EffortLog(effortNumber, effortDate, effortStart, effortStop,
											effortTimeElapsed, effortLifeCycleStep, effortCategory,
											effortDelInt);
		//System.out.println(effort.toString());
		
		int defectNumber = 1;
		String defectName = "-new defect-";
		String defectDetail = "i messes up";
		String defectInjected = "Finalizing";
		String defectRemoved = "Stakeholder Meeting";
		String defectCategory = "20 Syntax";
		String defectStatus = "Closed";
		int defectFix = 2;
		
		DefectLog defect = new DefectLog(defectNumber, defectName, defectDetail, defectInjected,
											defectRemoved, defectCategory, defectStatus, defectFix);
		//System.out.println(defect.toString());
		
		EffortLog[] effortArray = new EffortLog[3];
		effortArray[0] = effort;
		effortArray[1] = new EffortLog(2, "2023-11-09", "20:21:37", "20:21:38", 0.02, "Verifying",
										"Deliverables", "Conceptual Design");
		effortArray[2] = new EffortLog(3, "2023-11-09", "20:25:17", "20:25:19", 0.03, "Verifying",
										"Defects", "- new defect -");
		
		DefectLog[] defectArray = new DefectLog[2];
		defectArray[0] = defect;
		defectArray[1] = new DefectLog(2, "defect2", "i did it again", "Team Meeting",
										"Team Meeting", "30 Build, Package", "Open", 0);
		
		for(int i = 0; i < effortArray.length; i++)
		{
			System.out.println(effortArray[i].toString());
		}
		
		for(int i = 0; i < defectArray.length; i++)
		{
			System.out.println(defectArray[i].toString());
		}
		
		File savePath = new File("C:\\Users\\Emil\\Downloads");
		ExcelController.write(savePath, "Business Project", effortArray, defectArray);
		
		// haven't tested yet 
		
		File excelToRead = new File("C:\\Users\\Emil\\Downloads\\Business Project.xlsx");
		
		EffortLog[] outputEffort = ExcelController.readEffortLogs(excelToRead);
		
		for(int i = 0; i < outputEffort.length; i++)
		{
			System.out.println(outputEffort[i].toString());
		}
		
		DefectLog[] outputDefect = ExcelController.readDefectLogs(excelToRead);
		
		for(int i = 0; i < outputDefect.length; i++)
		{
			System.out.println(outputDefect[i].toString());
		}*/
	}

}
