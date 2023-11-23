package FXDirectoryExcel;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

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

public class ExcelController {
	
	/**
	 *  Outputs an array of Effort Logs read from the Excel file given in the argument.
	 *   Excel file format must be exact in order for method to work.
	 * @param file	The file to be read.
	 * @return	A list of all effort logs found in the file.
	 */
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
		}
		
		return null;
	}
	
	/**
	 *  Outputs an array of Defect Logs read from the Excel file given in the argument.
	 *   Excel file format must be exact in order for method to work.
	 * @param file	The file to be read.
	 * @return	A list of all defect logs found in the file.
	 */
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
		}
		
		return null;
	}
	
	
	/**
	 * Updates the effort log entries in a file.
	 * @param 		file	The file being written to.
	 * @param effortLogs	An up-to-date list of effort logs to write.
	 */
	public static void writeEffortLog(File file, ArrayList<EffortLog> effortLogs)
	{
		try
		{
			//Workbook workbook = new XSSFWorkbook();			// needs to get the existing workbook
			FileInputStream fis = new FileInputStream(file);
			Workbook workbook = WorkbookFactory.create(fis);	// memory-intensive, stupid workaround
			Sheet sheet = workbook.getSheet("Sheet0");
			
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
			
			Cell fileNameCell = firstRow.createCell(2);
			fileNameCell.setCellValue(file.getName().substring(0, file.getName().indexOf('.')));
			fileNameCell.setCellStyle(styleCenter);
			
			Cell effortEntriesCell = firstRow.createCell(6);
			effortEntriesCell.setCellValue(effortLogs.size());
			effortEntriesCell.setCellStyle(styleLeft);
			
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
			FileOutputStream fos = new FileOutputStream(file);
			
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
	
	/**
	 * Updates the defect log entries in a file.
	 * Uses a WorkbookFactory to get the file to update. There are two ways to indicate the file: by directly passing a File object,
	 * or FileInputStream object. If a File is passed directly, the file will get corrupted unless you output to a temporary file, close
	 * the workbook, delete the old file, then rename the temporary file to replace the old one. Using a FileOutputStream does not
	 * require that workaround, but is more memory-intensive.
	 * 
	 * @param file			The file being written to.
	 * @param defectLogs	An up-to-date list of defect logs to write.
	 */
	public static void writeDefectLog(File file, ArrayList<DefectLog> defectLogs)
	{
		try
		{
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
			
			Cell fileNameCell = firstRow.createCell(10);
			fileNameCell.setCellValue(file.getName().substring(0, file.getName().indexOf('.')));
			fileNameCell.setCellStyle(styleCenter);
			
			Cell defectEntriesCell = firstRow.createCell(14);
			defectEntriesCell.setCellValue(defectLogs.size());
			defectEntriesCell.setCellStyle(styleLeft);
			
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
			
			fis.close();
			FileOutputStream fos = new FileOutputStream(file + ".new");
			workbook.write(fos);
			workbook.close();
			fos.close();
			//Files.delete(Paths.get(file + ""));							// allows usage of file in WorkbookFactory, but is annoying
			//Files.move(Paths.get(file + ".new"), Paths.get(file + ""));
		}
		catch(Exception e)
		{
			System.out.println("Saving failed.");
			System.out.println(e.toString());
		}
	}
	
	
	/**
	 * Replaces the contents of an existing project log with an updated list of all effort and defect logs. 
	 * This is also used to initialize a new file with no entries.
	 * The project file is formatted to match the format of logs in EffortLoggerV1.
	 * 
	 * @param file			The file to replace the contents of.
	 * @param effortLogs	The list of effort logs to write.
	 * @param defectLogs	The list of defect logs to write.
	 */
	public static void write(File file, ArrayList<EffortLog> effortLogs, ArrayList<DefectLog> defectLogs)
	{
		try
		{
			
			Workbook workbook = new XSSFWorkbook();
			Sheet sheet = workbook.createSheet();
			
			setColumnWidths(sheet);
			
			String filename = file.getName().substring(0, file.getName().indexOf('.'));
			firstRow(workbook, sheet, filename, effortLogs.size(), defectLogs.size());
			secondRow(workbook, sheet);
			
			int rows = effortLogs.size() > defectLogs.size() ? effortLogs.size() : defectLogs.size();
			XSSFCellStyle styleCenter = (XSSFCellStyle) workbook.createCellStyle();
			styleCenter.setAlignment(HorizontalAlignment.CENTER);
			
			for(int i = 0; i < rows; i++)
			{
				Row row = sheet.createRow(2+i);
				if(i < effortLogs.size())
				{
					row.createCell(0).setCellValue(i+1);//effortLogs.get(i).getNumber());
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
					row.createCell(9).setCellValue(i+1);//defectLogs.get(i).getNumber());
					row.createCell(10).setCellValue(defectLogs.get(i).getName());
					row.createCell(11).setCellValue(defectLogs.get(i).getDetail());
					row.createCell(12).setCellValue(defectLogs.get(i).getInjected());
					row.createCell(13).setCellValue(defectLogs.get(i).getRemoved());
					row.createCell(14).setCellValue(defectLogs.get(i).getCategory());
					row.createCell(15).setCellValue(defectLogs.get(i).getStatus());
					row.createCell(16).setCellValue(defectLogs.get(i).getFix());
				}
			}
			
			FileOutputStream fos = new FileOutputStream(file);
			workbook.write(fos);
			workbook.close();
			fos.close();
		}
		catch(Exception e)
		{
			System.out.println("Saving failed.");
			System.out.println(e.toString());
		}
	}
	
	
	// Obsolete? Kept in case this is needed for later use
	/**
	 * Replaces the contents of an existing project log with an updated list of all effort and defect logs. 
	 * This method is deprecated. It is recommended to use the version that accepts ArrayLists as parameters.
	 * 
	 * @param directory		The directory of the file to replace the contents of.
	 * @param fileName		The name of the file to replace the contents of.
	 * @param effortLogs	The list of effort logs to write.
	 * @param defectLogs	The list of defect logs to write.
	 */
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
	
	/**
	 * Formats the width of columns in the sheet.
	 * 
	 * @param sheet		The sheet to format.
	 */
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
	
	/**
	 * Formats the first row of headers in the sheet.
	 * 
	 * @param workbook		The workbook which changes are being applied to.
	 * @param sheet			The sheet to format.
	 * @param fileName		The name of the project file. This becomes part of the header.
	 * @param effortEntries	The number of effort entries in the project file.
	 * @param defectEntries	The number of defect entries in the project file.
	 */
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
	
	/**
	 * Sets and formats the column titles.
	 * 
	 * @param workbook		The workbook which changes are being applied to.
	 * @param sheet			The sheet to format.
	 */
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
