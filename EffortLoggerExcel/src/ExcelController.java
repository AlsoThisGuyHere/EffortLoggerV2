import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelController {
	
	// Outputs an array of Effort Logs read from the Excel file given in the argument
	// Excel file format must be exact in order for method to work
	public static EffortLog[] readEffortLogs(File file)
	{
		try
		{
			FileInputStream fis = new FileInputStream(file);
			Workbook workbook = new XSSFWorkbook(fis);
			Sheet sheet = workbook.getSheetAt(0);
			int numEntries = (int) sheet.getRow(0).getCell(6).getNumericCellValue();
			EffortLog[] output = new EffortLog[numEntries];
			for(int i = 0; i < numEntries; i++)
			{
				Row row = sheet.getRow(i+3);
				int number = (int) row.getCell(0).getNumericCellValue();
				String date = row.getCell(1).getStringCellValue();
				String start = row.getCell(2).getStringCellValue();
				String stop = row.getCell(3).getStringCellValue();
				double timeElapsed = row.getCell(4).getNumericCellValue();
				String lifeCycleStep = row.getCell(5).getStringCellValue();
				String category = row.getCell(6).getStringCellValue();
				String delInt = row.getCell(7).getStringCellValue();
				output[i] = new EffortLog(number, date, start, stop, timeElapsed, lifeCycleStep,
											category, delInt);
			}
			workbook.close();
			return output;
		}
		catch(Exception e)
		{
			System.out.println("Saving failed.");
			System.out.println(e.toString());
		}
		
		return null;
	}
	
	// Outputs an array of Defect Logs read from the Excel file given in the argument
	// Excel file format must be exact in order for method to work
	public static DefectLog[] readDefectLogs(File file)
	{
		try
		{
			FileInputStream fis = new FileInputStream(file);
			Workbook workbook = new XSSFWorkbook(fis);
			Sheet sheet = workbook.getSheetAt(0);
			int numEntries = (int) sheet.getRow(0).getCell(14).getNumericCellValue();
			DefectLog[] output = new DefectLog[numEntries];
			for(int i = 0; i < numEntries; i++)
			{
				Row row = sheet.getRow(i+3);
				int number = (int) row.getCell(9).getNumericCellValue();
				String name = row.getCell(10).getStringCellValue();
				String detail = row.getCell(11).getStringCellValue();
				String injected = row.getCell(12).getStringCellValue();
				String removed = row.getCell(13).getStringCellValue();
				String category = row.getCell(14).getStringCellValue();
				String status = row.getCell(15).getStringCellValue();
				int fix = (int) row.getCell(16).getNumericCellValue();
				output[i] = new DefectLog(number, name, detail, injected, removed, category,
											status, fix);
			}
			workbook.close();
			return output;
		}
		catch(Exception e)
		{
			System.out.println("Saving failed.");
			System.out.println(e.toString());
		}
		
		return null;
	}
	
	// Saves an excel containing the Effort Log and Defect Log information given to it
	public static void write(File directory, String fileName, EffortLog[] effortLogs, DefectLog[] defectLogs)
	{
		try
		{
			
			Workbook workbook = new XSSFWorkbook();
			Sheet sheet = workbook.createSheet();
			
			setColumnWidths(sheet);
			
			firstRow(workbook, sheet, fileName, effortLogs.length, defectLogs.length);
			secondRow(workbook, sheet);
			
			int rows = effortLogs.length > defectLogs.length ? effortLogs.length : defectLogs.length;
			XSSFCellStyle styleCenter = (XSSFCellStyle) workbook.createCellStyle();
			styleCenter.setAlignment(HorizontalAlignment.CENTER);
			
			for(int i = 0; i < rows; i++)
			{
				Row row = sheet.createRow(3+i);
				if(i < effortLogs.length)
				{
					row.createCell(0).setCellValue(effortLogs[i].getNumber());
					row.createCell(1).setCellValue(effortLogs[i].getDate());
					row.createCell(2).setCellValue(effortLogs[i].getStart());
					row.createCell(3).setCellValue(effortLogs[i].getStop());
					row.createCell(4).setCellValue(effortLogs[i].getTimeElapsed());
					row.createCell(5).setCellValue(effortLogs[i].getLifeCycleStep());
					row.createCell(6).setCellValue(effortLogs[i].getCategory());
					row.createCell(7).setCellValue(effortLogs[i].getDelInt());
					
					row.getCell(0).setCellStyle(styleCenter);
					row.getCell(1).setCellStyle(styleCenter);
					row.getCell(2).setCellStyle(styleCenter);
					row.getCell(3).setCellStyle(styleCenter);
					row.getCell(4).setCellStyle(styleCenter);
				}
				
				if(i < defectLogs.length)
				{
					row.createCell(9).setCellValue(defectLogs[i].getNumber());
					row.createCell(10).setCellValue(defectLogs[i].getName());
					row.createCell(11).setCellValue(defectLogs[i].getDetail());
					row.createCell(12).setCellValue(defectLogs[i].getInjected());
					row.createCell(13).setCellValue(defectLogs[i].getRemoved());
					row.createCell(14).setCellValue(defectLogs[i].getCategory());
					row.createCell(15).setCellValue(defectLogs[i].getStatus());
					row.createCell(16).setCellValue(defectLogs[i].getFix());
				}
			}
			
			FileOutputStream fos =
					new FileOutputStream(new File(directory.getAbsolutePath() + "\\" + fileName + ".xlsx"));
			workbook.write(fos);
			workbook.close();
		}
		catch(Exception e)
		{
			System.out.println("Saving failed.");
			System.out.println(e.toString());
		}
	}
	
	private static void setColumnWidths(Sheet sheet)
	{
		sheet.setColumnWidth(1, 12*256);    //  Date
		sheet.setColumnWidth(4, 14*256);    //  Time Elapsed
		sheet.setColumnWidth(5, 20*256);  	//  Number of Entries
		sheet.setColumnWidth(6, 13*256);	//  Category
		sheet.setColumnWidth(7, 37*256);    //  Deliverable / Interruption / etc.
		sheet.setColumnWidth(8, 3*256);     //  Filler Space
		sheet.setColumnWidth(9, 10*256);  	//  Defect Log
		sheet.setColumnWidth(10, 15*256);   //  Name
		sheet.setColumnWidth(11, 12*256);   //  Detail
		sheet.setColumnWidth(12, 10*256);	//  Injected
		sheet.setColumnWidth(13, 20*256);	//  Number of Entries
		sheet.setColumnWidth(14, 12*256);	//  Category
	}
	
	private static void firstRow(Workbook workbook, Sheet sheet, String fileName,
									int effortEntries, int defectEntries)
	{
		XSSFFont fontBold = (XSSFFont) workbook.createFont();
		fontBold.setBold(true);
		
		XSSFCellStyle styleBoldCenter = (XSSFCellStyle) workbook.createCellStyle();
		styleBoldCenter.setFont(fontBold);
		styleBoldCenter.setAlignment(HorizontalAlignment.CENTER);
		
		XSSFCellStyle styleCenter = (XSSFCellStyle) workbook.createCellStyle();
		styleCenter.setAlignment(HorizontalAlignment.CENTER);
		
		XSSFCellStyle styleLeft = (XSSFCellStyle) workbook.createCellStyle();
		styleLeft.setAlignment(HorizontalAlignment.LEFT);
		
		XSSFCellStyle styleBold = (XSSFCellStyle) workbook.createCellStyle();
		styleBold.setFont(fontBold);
		
		Row firstRow = sheet.createRow(0);
		Cell projNameCell = firstRow.createCell(0);
		projNameCell.setCellValue("Project Name:");
		projNameCell.setCellStyle(styleBoldCenter);
		sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 1));
		
		Cell fileNameCell = firstRow.createCell(2);
		fileNameCell.setCellValue(fileName);
		fileNameCell.setCellStyle(styleCenter);
		sheet.addMergedRegion(new CellRangeAddress(0, 0, 2, 4));
		firstRow.setHeight((short) 500);
		
		Cell numOfEffortEntriesCell = firstRow.createCell(5);
		numOfEffortEntriesCell.setCellValue("Number of Entries:");
		numOfEffortEntriesCell.setCellStyle(styleBoldCenter);
		
		Cell effortEntriesCell = firstRow.createCell(6);
		effortEntriesCell.setCellValue(effortEntries);
		effortEntriesCell.setCellStyle(styleLeft);
		
		Cell defectLogCell = firstRow.createCell(9);
		defectLogCell.setCellValue("Defect Log");
		defectLogCell.setCellStyle(styleBold);
		
		Cell numOfDefectEntriesCell = firstRow.createCell(13);
		numOfDefectEntriesCell.setCellValue("Number of Entries:");
		numOfDefectEntriesCell.setCellStyle(styleBold);
		
		Cell defectEntriesCell = firstRow.createCell(14);
		defectEntriesCell.setCellValue(defectEntries);
		defectEntriesCell.setCellStyle(styleLeft);
	}
	
	private static void secondRow(Workbook workbook, Sheet sheet)
	{
		XSSFFont fontBold = (XSSFFont) workbook.createFont();
		fontBold.setBold(true);
		
		XSSFCellStyle styleBold = (XSSFCellStyle) workbook.createCellStyle();
		styleBold.setFont(fontBold);
		
		XSSFCellStyle styleBoldCenter = (XSSFCellStyle) workbook.createCellStyle();
		styleBoldCenter.setFont(fontBold);
		styleBoldCenter.setAlignment(HorizontalAlignment.CENTER);
		
		Row secondRow = sheet.createRow(1);
		secondRow.createCell(0).setCellValue("Number");
		secondRow.createCell(1).setCellValue("Date");
		secondRow.createCell(2).setCellValue("Start");
		secondRow.createCell(3).setCellValue("Stop");
		secondRow.createCell(4).setCellValue("Time Elapsed");
		secondRow.createCell(5).setCellValue("Life Cycle Step");
		secondRow.createCell(6).setCellValue("Category");
		secondRow.createCell(7).setCellValue("Deliverable / Interruption / etc.");
		secondRow.createCell(9).setCellValue("Number");
		secondRow.createCell(10).setCellValue("Name");
		secondRow.createCell(11).setCellValue("Detail");
		secondRow.createCell(12).setCellValue("Injected");
		secondRow.createCell(13).setCellValue("Removed");
		secondRow.createCell(14).setCellValue("Category");
		secondRow.createCell(15).setCellValue("Status");
		secondRow.createCell(16).setCellValue("Fix");
		
		for(int i = 0; i <= 7; i++) { secondRow.getCell(i).setCellStyle(styleBoldCenter); }
		for(int i = 9; i <= 16; i++) { secondRow.getCell(i).setCellStyle(styleBold); }
	}
}
