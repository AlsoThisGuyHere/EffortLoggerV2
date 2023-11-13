package forLater;

import java.io.File;
import java.io.FileOutputStream;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelCreator {
	public static void createExcel(File directory)
	{
		try
		{
			Workbook workbook = new XSSFWorkbook();
			Sheet sheet = workbook.createSheet("Optional Name");
			Row row = sheet.createRow(0);
			int cellNum = 0;
			Cell cell = row.createCell(0);
			cell.setCellValue("Project Name:");
			sheet.addMergedRegion(new CellRangeAddress(cell.getRowIndex(), cell.getRowIndex(), cell.getColumnIndex(), cell.getColumnIndex()+1));
			cell = row.createCell(2);
			sheet.addMergedRegion(new CellRangeAddress(cell.getRowIndex(), cell.getRowIndex(), cell.getColumnIndex(), cell.getColumnIndex()+2));
			row.createCell(5).setCellValue("Number of Entries:");
			row.createCell(9).setCellValue("Defect Log:");
			row.createCell(13).setCellValue("Number of Entries:");
			
			cellNum=0;
			row = sheet.createRow(1);
			//row.setRowStyle();
			row.createCell(cellNum++).setCellValue("Number");
			row.createCell(cellNum++).setCellValue("Date");
			row.createCell(cellNum++).setCellValue("Start");
			row.createCell(cellNum++).setCellValue("Stop");
			row.createCell(cellNum++).setCellValue("Time Elapsed");
			row.createCell(cellNum++).setCellValue("Life Cycle Step");
			row.createCell(cellNum++).setCellValue("Category");
			row.createCell(cellNum++).setCellValue("Deliverable/Interruption/etc.");
			cellNum++;
			row.createCell(cellNum++).setCellValue("Number");
			row.createCell(cellNum++).setCellValue("Name");
			row.createCell(cellNum++).setCellValue("Details");
			row.createCell(cellNum++).setCellValue("Injected");
			row.createCell(cellNum++).setCellValue("Removed");
			row.createCell(cellNum++).setCellValue("Category");
			row.createCell(cellNum++).setCellValue("Status");
			row.createCell(cellNum++).setCellValue("Fix");
			//System.out.println(row.getCell(0).getCellStyle().toString());
			
			
			
			FileOutputStream fos =
					new FileOutputStream(new File(directory.getAbsolutePath()/* + "\\CreatedExcel.xlsx"*/));
			workbook.write(fos);
			workbook.close();
			
			System.out.println("File created");
		}
		catch(Exception e)
		{
			System.out.println("Error occurred.");
			System.out.println(e.toString());
		}
	}
	
	
	/*public static List<String> readExcel(File filePath) {
		try {
			
			Workbook workbook = new XSSFWorkbook();
			
		} catch (Exception e) {
			System.out.println("Error occurred.");
			System.out.println(e.toString());
		}
	}*/
	
	/*public static void saveExcel(File directory, ArrayList<String> fields)
	{
		try
		{
			Workbook workbook = new XSSFWorkbook();
			Sheet sheet = workbook.createSheet("Optional Name");
			Row row = sheet.createRow(0);
			int cellNum = 0;
			Cell cell = row.createCell(0);
			cell.setCellValue("Project Name:");
			sheet.addMergedRegion(new CellRangeAddress(cell.getRowIndex(), cell.getRowIndex(), cell.getColumnIndex(), cell.getColumnIndex()+1));
			cell = row.createCell(2);
			sheet.addMergedRegion(new CellRangeAddress(cell.getRowIndex(), cell.getRowIndex(), cell.getColumnIndex(), cell.getColumnIndex()+2));
			row.createCell(5).setCellValue("Number of Entries:");
			row.createCell(9).setCellValue("Defect Log:");
			row.createCell(13).setCellValue("Number of Entries:");
			
			cellNum=0;
			row = sheet.createRow(1);
			//row.setRowStyle();
			row.createCell(cellNum++).setCellValue("Number");
			row.createCell(cellNum++).setCellValue("Date");
			row.createCell(cellNum++).setCellValue("Start");
			row.createCell(cellNum++).setCellValue("Stop");
			row.createCell(cellNum++).setCellValue("Time Elapsed");
			row.createCell(cellNum++).setCellValue("Life Cycle Step");
			row.createCell(cellNum++).setCellValue("Category");
			row.createCell(cellNum+=2).setCellValue("Deliverable/Interruption/etc.");
			row.createCell(cellNum++).setCellValue("Number");
			row.createCell(cellNum++).setCellValue("Name");
			row.createCell(cellNum++).setCellValue("Details");
			row.createCell(cellNum++).setCellValue("Injected");
			row.createCell(cellNum++).setCellValue("Removed");
			row.createCell(cellNum++).setCellValue("Category");
			row.createCell(cellNum++).setCellValue("Status");
			row.createCell(cellNum++).setCellValue("Fix");
			//System.out.println(row.getCell(0).getCellStyle().toString());
			
			
			
			FileOutputStream fos =
					new FileOutputStream(new File(directory.getAbsolutePath()));
			workbook.write(fos);
			workbook.close();
			
			System.out.println("File created");
		}
		catch(Exception e)
		{
			System.out.println("Error occurred.");
			System.out.println(e.toString());
		}
	}*/
}
