import java.io.File;

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
		ExcelController.save(savePath, "Business Project", effortArray, defectArray);
		
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
		}
	}

}
