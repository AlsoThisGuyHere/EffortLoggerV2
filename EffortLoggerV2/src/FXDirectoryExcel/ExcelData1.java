package FXDirectoryExcel;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import org.apache.poi.ss.usermodel.Cell;  
import org.apache.poi.ss.usermodel.*;  
import org.apache.poi.ss.usermodel.Sheet;  
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellReference;
import org.apache.poi.xssf.usermodel.XSSFWorkbook; 

public class ExcelData1 {
	// method to read and gather data from excel files
	public static void readExcel(String filePath) {
	    File file = new File(filePath); // creating a new file = to the file path given
	    try { // try catch loop that loops through an entire excel file and prints the data
	        FileInputStream inputStream = new FileInputStream(file);
	        Workbook effortLoggerBook = new XSSFWorkbook(inputStream);
	        for (Sheet sheet : effortLoggerBook) {
	        	int firstRow = sheet.getFirstRowNum();
	        	int lastRow = sheet.getLastRowNum();
	        	for (int index = firstRow + 1; index <= lastRow; index++) {
	        	    Row row = sheet.getRow(index);
	        	    // can add code here to use specific row data as needed
	        	    for (int cellIndex = row.getFirstCellNum(); cellIndex < row.getLastCellNum(); cellIndex++) {
	        	        Cell cell = row.getCell(cellIndex, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
	        	        // can add code here to get specific cell data as needed
	        	        printCellValue(cell);
	        	    }
	        	}
	        }
	        inputStream.close();
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	}
	// method to print the value of a cell from an excel sheet
	public static void printCellValue(Cell cell) {
	   // checking what type the cell is, prints the value when the type is found
		CellType cellType = cell.getCellType().equals(CellType.FORMULA)
	      ? cell.getCachedFormulaResultType() : cell.getCellType();
	    if (cellType.equals(CellType.STRING)) {
	        System.out.print(cell.getStringCellValue() + " | ");
	    }
	    if (cellType.equals(CellType.NUMERIC)) {
	        if (DateUtil.isCellDateFormatted(cell)) {
	            System.out.print(cell.getDateCellValue() + " | ");
	        } else {
	            System.out.print(cell.getNumericCellValue() + " | ");
	        }
	    }
	    if (cellType.equals(CellType.BOOLEAN)) {
	        System.out.print(cell.getBooleanCellValue() + " | ");
	    }
	}
	// method to get the project name according to effortLogger specific excel files
	public static String getPName(String Path) {
		System.out.println("Gathering data from:" + Path);
		File file = new File(Path);
		try { // try catch loop that finds the correct cell and returns the data for the project date
			FileInputStream inputStream = new FileInputStream(file);
			Workbook effortLoggerBook = new XSSFWorkbook(inputStream);
			Sheet sheet = effortLoggerBook.getSheetAt(0);
		    
		    CellReference pnameData = new CellReference("C1");
		    Row row = sheet.getRow(pnameData.getRow());
		    Cell cell = row.getCell(pnameData.getCol()); 
		    String cellData = cell.getStringCellValue();	     
			
		    return cellData;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "ERROR"; // this is what returns if the data wasnt gathered properly for any reason
	}
	// method to get the project date according to specific effortLogger excel files
	public static String getPDate(String Path) {
		File file = new File(Path);
		// try catch loop that finds the correct cell and returns the data for the project date
		try {
			FileInputStream inputStream = new FileInputStream(file);
			Workbook effortLoggerBook = new XSSFWorkbook(inputStream);
			Sheet sheet = effortLoggerBook.getSheetAt(0);
		    
		    CellReference pdateData = new CellReference("B3");
		    Row row = sheet.getRow(pdateData.getRow());
		    Cell cell = row.getCell(pdateData.getCol()); 
		    String cellData = cell.getStringCellValue();
		    return cellData;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return "ERROR"; // this is what returns if the data wasnt gathered properly for any reason
	}
	// method to get the number of entries from an excel file according to effortLogger specifications
	public static String getNEntries(String Path) {
		File file = new File(Path);
		// try catch loop that finds the specific number of entries data and returns it
		try {
			FileInputStream inputStream = new FileInputStream(file);
			Workbook effortLoggerBook = new XSSFWorkbook(inputStream);
			Sheet sheet = effortLoggerBook.getSheetAt(0);
		    
		    CellReference nEntriesData = new CellReference("G1");
		    Row row = sheet.getRow(nEntriesData.getRow());
		    Cell cell = row.getCell(nEntriesData.getCol());
			double cellData = cell.getNumericCellValue();
		    String cellDataFinal = Double.toString(cellData);
			return cellDataFinal;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	   
		return "ERROR"; // this is what returns if the data wasnt gathered properly
	}
	
}
