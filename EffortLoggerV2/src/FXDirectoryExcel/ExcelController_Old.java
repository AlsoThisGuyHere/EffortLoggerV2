package FXDirectoryExcel;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
//import java.nio.file.Files;
//import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelController_Old {
	
	// Outputs an array of Effort Logs read from the Excel file given in the argument
	// Excel file format must be exact in order for method to work
	public static ArrayList<EffortLog> readEffortLogs(File file)
	{
		try
		{
			FileInputStream fis = new FileInputStream(file);
			Workbook workbook = new XSSFWorkbook(fis);
			Sheet sheet = workbook.getSheetAt(0);
			int numEntries = (int) sheet.getRow(0).getCell(6).getNumericCellValue();
			ArrayList<EffortLog> output = new ArrayList<EffortLog>();
			for(int i = 0; i < numEntries; i++)
			{
				Row row = sheet.getRow(i+2);
				int number = (int) row.getCell(0).getNumericCellValue();
				String date = row.getCell(1).getStringCellValue();
				String start = row.getCell(2).getStringCellValue();
				String stop = row.getCell(3).getStringCellValue();
				double timeElapsed = row.getCell(4).getNumericCellValue();
				String lifeCycleStep = row.getCell(5).getStringCellValue();
				String category = row.getCell(6).getStringCellValue();
				String delInt = row.getCell(7).getStringCellValue();
				output.add(new EffortLog(number, date, start, stop, timeElapsed, lifeCycleStep,
											category, delInt));
			}
			workbook.close();
			fis.close();
			return output;
		}
		catch(Exception e)
		{
			System.out.println("Read failed.");
			System.out.println(e.toString());
			e.printStackTrace();
		}
		
		return null;
	}
	
	// Outputs an array of Defect Logs read from the Excel file given in the argument
	// Excel file format must be exact in order for method to work
	public static ArrayList<DefectLog> readDefectLogs(File file)
	{
		try
		{
			FileInputStream fis = new FileInputStream(file);
			Workbook workbook = new XSSFWorkbook(fis);
			Sheet sheet = workbook.getSheetAt(0);
			int numEntries = (int) sheet.getRow(0).getCell(14).getNumericCellValue();
			ArrayList<DefectLog> output = new ArrayList<DefectLog>();
			for(int i = 0; i < numEntries; i++)
			{
				Row row = sheet.getRow(i+2);
				int number = (int) row.getCell(9).getNumericCellValue();
				String name = row.getCell(10).getStringCellValue();
				String detail = row.getCell(11).getStringCellValue();
				String injected = row.getCell(12).getStringCellValue();
				String removed = row.getCell(13).getStringCellValue();
				String category = row.getCell(14).getStringCellValue();
				String status = row.getCell(15).getStringCellValue();
				int fix = (int) row.getCell(16).getNumericCellValue();
				output.add(new DefectLog(number, name, detail, injected, removed, category,
											status, fix));
			}
			workbook.close();
			fis.close();
			return output;
		}
		catch(Exception e)
		{
			System.out.println("Read failed.");
			System.out.println(e.toString());
			e.printStackTrace();
		}
		
		return null;
	}
	
	public static void writeEffortLog(File file, ArrayList<EffortLog> effortLogs)	// old implementation was deleting all defect logs
	{
		try
		{
			//Workbook workbook = new XSSFWorkbook();			// needs to get the existing workbook
			FileInputStream fis = new FileInputStream(file);
			Workbook workbook = WorkbookFactory.create(fis);	// memory-intensive, stupid workaround
			Sheet sheet = workbook.getSheet("Sheet0");
			
			// Set column widths
			/*sheet.setColumnWidth(1, 12*256);    //  Date
			sheet.setColumnWidth(4, 14*256);    //  Time Elapsed
			sheet.setColumnWidth(5, 20*256);  	//  Number of Entries
			sheet.setColumnWidth(6, 13*256);	//  Category
			sheet.setColumnWidth(7, 37*256);    //  Deliverable / Interruption / etc.*/
			
			// Define fonts & styles
			XSSFFont fontBold = (XSSFFont) workbook.createFont();
			fontBold.setBold(true);
			
			XSSFCellStyle styleBoldCenter = (XSSFCellStyle) workbook.createCellStyle();
			styleBoldCenter.setFont(fontBold);
			styleBoldCenter.setAlignment(HorizontalAlignment.CENTER);
			
			XSSFCellStyle styleCenter = (XSSFCellStyle) workbook.createCellStyle();
			styleCenter.setAlignment(HorizontalAlignment.CENTER);
			
			XSSFCellStyle styleLeft = (XSSFCellStyle) workbook.createCellStyle();
			styleLeft.setAlignment(HorizontalAlignment.LEFT);
			
			// First row
			Row firstRow = sheet.getRow(0);
			if (firstRow == null)
				firstRow = sheet.createRow(0);
			firstRow.setHeight((short) 500);
			
			/*Cell projNameCell = firstRow.createCell(0);				// should never be needed but okay
			projNameCell.setCellValue("Project Name:");
			projNameCell.setCellStyle(styleBoldCenter);
			sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 1));*/
			
			Cell fileNameCell = firstRow.createCell(2);
			fileNameCell.setCellValue(file.getName().substring(0, file.getName().indexOf('.')));
			fileNameCell.setCellStyle(styleCenter);
			//sheet.addMergedRegion(new CellRangeAddress(0, 0, 2, 4));
			
			/*Cell numOfEffortEntriesCell = firstRow.createCell(5);	// should never be needed but okay
			numOfEffortEntriesCell.setCellValue("Number of Entries:");
			numOfEffortEntriesCell.setCellStyle(styleBoldCenter);*/
			
			Cell effortEntriesCell = firstRow.createCell(6);
			effortEntriesCell.setCellValue(effortLogs.size());
			effortEntriesCell.setCellStyle(styleLeft);
			// Second row
			/*Row secondRow = sheet.getRow(1);
			secondRow.createCell(0).setCellValue("Number");
			secondRow.createCell(1).setCellValue("Date");
			secondRow.createCell(2).setCellValue("Start");
			secondRow.createCell(3).setCellValue("Stop");
			secondRow.createCell(4).setCellValue("Time Elapsed");
			secondRow.createCell(5).setCellValue("Life Cycle Step");
			secondRow.createCell(6).setCellValue("Category");
			secondRow.createCell(7).setCellValue("Deliverable / Interruption / etc.");*/
			
			//for(int i = 0; i <= 7; i++) { secondRow.getCell(i).setCellStyle(styleBoldCenter); }
			
			// Effort Logs
			for(int i = 0; i < effortLogs.size(); i++)
			{
				Row row = sheet.getRow(2+i);
				if (row == null) 
					row = sheet.createRow(2+i);
				row.createCell(0).setCellValue(effortLogs.get(i).getNumber());
				row.createCell(1).setCellValue(effortLogs.get(i).getDate());
				row.createCell(2).setCellValue(effortLogs.get(i).getStart());
				row.createCell(3).setCellValue(effortLogs.get(i).getStop());
				row.createCell(4).setCellValue(effortLogs.get(i).getTimeElapsed());
				row.createCell(5).setCellValue(effortLogs.get(i).getLifeCycleStep());
				row.createCell(6).setCellValue(effortLogs.get(i).getCategory());
				row.createCell(7).setCellValue(effortLogs.get(i).getDelInt());
				
				row.getCell(0).setCellStyle(styleCenter);
				row.getCell(1).setCellStyle(styleCenter);
				row.getCell(2).setCellStyle(styleCenter);
				row.getCell(3).setCellStyle(styleCenter);
				row.getCell(4).setCellStyle(styleCenter);
			}
			
			fis.close();
			FileOutputStream fos = new FileOutputStream(file/* + ".new"*/);
			
			workbook.write(fos);
			workbook.close();
			/*Files.delete(Paths.get(file + ""));							// allows usage of file in WorkbookFactory, but is annoying
			Files.move(Paths.get(file + ".new"), Paths.get(file + ""));*/
		}
		catch(Exception e)
		{
			System.out.println("Saving failed.");
			System.out.println(e.toString());
		}
	}
	
	public static void writeDefectLog(File file, ArrayList<DefectLog> defectLogs)	// current implementation is deleting all effort logs
	{
		try
		{
			//Workbook workbook = new XSSFWorkbook();
			FileInputStream fis = new FileInputStream(file);
			Workbook workbook = WorkbookFactory.create(fis);	// memory-intensive, but you can't just supply a file
			Sheet sheet = workbook.getSheet("Sheet0");
			
			// Set column widths
			sheet.setColumnWidth(0, 11*256);  	//  Defect Log
			sheet.setColumnWidth(1, 15*256);    //  Name
			sheet.setColumnWidth(2, 12*256);    //  Detail
			sheet.setColumnWidth(3, 10*256);	//  Injected
			sheet.setColumnWidth(4, 20*256);	//  Number of Entries
			sheet.setColumnWidth(5, 12*256);	//  Category
			
			// Define fonts & styles
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
			
			// First row
			Row firstRow = sheet.getRow(0);
			if (firstRow == null) {			// create new row if firstRow is null (should never happen)
				firstRow = sheet.createRow(0);
			} 
			
			firstRow.setHeight((short) 500);
			
			/*Cell defectLogCell = firstRow.createCell(10);		// should never be needed but okay
			defectLogCell.setCellValue("Defect Log:");
			defectLogCell.setCellStyle(styleBold);*/
			
			Cell fileNameCell = firstRow.createCell(10);
			fileNameCell.setCellValue(file.getName().substring(0, file.getName().indexOf('.')));
			fileNameCell.setCellStyle(styleCenter);
			//sheet.addMergedRegion(new CellRangeAddress(0, 0, 1, 3));
			
			/*Cell numOfDefectEntriesCell = firstRow.createCell(14);	// should never be needed but okay
			numOfDefectEntriesCell.setCellValue("Number of Entries:");
			numOfDefectEntriesCell.setCellStyle(styleBold);*/
			
			Cell defectEntriesCell = firstRow.createCell(14);
			defectEntriesCell.setCellValue(defectLogs.size());
			defectEntriesCell.setCellStyle(styleLeft);
			
			// Second row
			/*Row secondRow = sheet.getRow(1);
			secondRow.createCell(10).setCellValue("Number");
			secondRow.createCell(11).setCellValue("Name");
			secondRow.createCell(12).setCellValue("Detail");
			secondRow.createCell(13).setCellValue("Injected");
			secondRow.createCell(14).setCellValue("Removed");
			secondRow.createCell(15).setCellValue("Category");
			secondRow.createCell(16).setCellValue("Status");
			secondRow.createCell(17).setCellValue("Fix");*/
			
			//for(int i = 0; i <= 7; i++) { secondRow.getCell(i).setCellStyle(styleBold); }
			
			// Defect Logs
			for(int i = 0; i < defectLogs.size(); i++)
			{
				Row row = sheet.getRow(2+i);
				if (row == null) 
					row = sheet.createRow(2+i);
				row.createCell(9).setCellValue(defectLogs.get(i).getNumber());
				row.createCell(10).setCellValue(defectLogs.get(i).getName());
				row.createCell(11).setCellValue(defectLogs.get(i).getDetail());
				row.createCell(12).setCellValue(defectLogs.get(i).getInjected());
				row.createCell(13).setCellValue(defectLogs.get(i).getRemoved());
				row.createCell(14).setCellValue(defectLogs.get(i).getCategory());
				row.createCell(15).setCellValue(defectLogs.get(i).getStatus());
				row.createCell(16).setCellValue(defectLogs.get(i).getFix());
			}
			
			FileOutputStream fos = new FileOutputStream(file);
			workbook.write(fos);
			workbook.close();
			fis.close();
		}
		catch(Exception e)
		{
			System.out.println("Saving failed.");
			System.out.println(e.toString());
		}
	}
	
	
	// Obsolete? Kept in case this is needed for later use
	
	// Saves an excel containing the Effort Log and Defect Log information given to it
	public static void write(File file, List<EffortLog> effortLogs, List<DefectLog> defectLogs)			// changed to only take a file
	{
		try
		{
			
			Workbook workbook = new XSSFWorkbook();
			Sheet sheet = workbook.createSheet();
			
			setColumnWidths(sheet);
			
			firstRow(workbook, sheet, file.getName(), effortLogs.size(), defectLogs.size());
			secondRow(workbook, sheet);
			
			int rows = effortLogs.size() > defectLogs.size() ? effortLogs.size() : defectLogs.size();
			XSSFCellStyle styleCenter = (XSSFCellStyle) workbook.createCellStyle();
			styleCenter.setAlignment(HorizontalAlignment.CENTER);
			
			for(int i = 0; i < rows; i++)
			{
				Row row = sheet.createRow(2+i);			// createRow uses a row index beginning at 0 (so 0 is row 1, 1 is row 2, etc.)
				if(i < effortLogs.size())
				{
					row.createCell(0).setCellValue(effortLogs.get(i).getNumber()); 
					row.createCell(1).setCellValue(effortLogs.get(i).getDate());
					row.createCell(2).setCellValue(effortLogs.get(i).getStart());
					row.createCell(3).setCellValue(effortLogs.get(i).getStop());
					row.createCell(4).setCellValue(effortLogs.get(i).getTimeElapsed());
					row.createCell(5).setCellValue(effortLogs.get(i).getLifeCycleStep());
					row.createCell(6).setCellValue(effortLogs.get(i).getCategory());
					row.createCell(7).setCellValue(effortLogs.get(i).getDelInt());
					
					row.getCell(0).setCellStyle(styleCenter);
					row.getCell(1).setCellStyle(styleCenter);
					row.getCell(2).setCellStyle(styleCenter);
					row.getCell(3).setCellStyle(styleCenter);
					row.getCell(4).setCellStyle(styleCenter);
				}
				
				if(i < defectLogs.size())
				{
					row.createCell(9).setCellValue(defectLogs.get(i).getNumber());
					row.createCell(10).setCellValue(defectLogs.get(i).getName());
					row.createCell(11).setCellValue(defectLogs.get(i).getDetail());
					row.createCell(12).setCellValue(defectLogs.get(i).getInjected());
					row.createCell(13).setCellValue(defectLogs.get(i).getRemoved());
					row.createCell(14).setCellValue(defectLogs.get(i).getCategory());
					row.createCell(15).setCellValue(defectLogs.get(i).getStatus());
					row.createCell(16).setCellValue(defectLogs.get(i).getFix());
				}
			}
			
			FileOutputStream fos = new FileOutputStream(new File(file.getAbsolutePath()/* + "\\" + fileName + ".xlsx"*/));
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
