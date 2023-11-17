import java.io.File;
import java.util.ArrayList;

public class TestDriver {

	public static void main(String[] args) {
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
		
		ArrayList<EffortLog> effortList = new ArrayList<EffortLog>();
		effortList.add(effort);
		effortList.add(new EffortLog(2, "2023-11-09", "20:21:37", "20:21:38", 0.02, "Verifying",
										"Deliverables", "Conceptual Design"));
		effortList.add(new EffortLog(3, "2023-11-09", "20:25:17", "20:25:19", 0.03, "Verifying",
										"Defects", "- new defect -"));
		
		ArrayList<DefectLog> defectList = new ArrayList<DefectLog>();
		defectList.add(defect);
		defectList.add(new DefectLog(2, "defect2", "i did it again", "Team Meeting",
										"Team Meeting", "30 Build, Package", "Open", 0));
		
		for(int i = 0; i < effortList.size(); i++)
		{
			System.out.println(effortList.get(i).toString());
		}
		
		for(int i = 0; i < defectList.size(); i++)
		{
			System.out.println(defectList.get(i).toString());
		}
		
		File savePath = new File("C:\\Users\\Emil\\Downloads\\Business Project.xlsx");
		//ExcelController.write(savePath, "Business Project", effortArray, defectArray);
		
		File excelToRead = new File("C:\\Users\\Emil\\Downloads\\Business Project.xlsx");
		
		ArrayList<EffortLog> outputEffort = ExcelController.readEffortLogs(excelToRead);
		
		for(int i = 0; i < outputEffort.size(); i++)
		{
			System.out.println(outputEffort.get(i).toString());
		}
		
		ArrayList<DefectLog> outputDefect = ExcelController.readDefectLogs(excelToRead);
		
		for(int i = 0; i < outputDefect.size(); i++)
		{
			System.out.println(outputDefect.get(i).toString());
		}
		
		File saveEffort = new File("C:\\Users\\Emil\\Downloads\\Effort Save.xlsx");
		ExcelController.writeEffortLog(saveEffort, outputEffort);
		
		File saveDefect = new File("C:\\Users\\Emil\\Downloads\\Defect Save.xlsx");
		ExcelController.writeDefectLog(saveDefect, outputDefect);
	}

}
