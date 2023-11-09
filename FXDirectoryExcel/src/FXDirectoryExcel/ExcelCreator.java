package FXDirectoryExcel;

import java.io.File;
import java.io.FileOutputStream;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

class ExcelCreator {
	static void createExcel(File directory)
	{
		try
		{
			Workbook workbook = new XSSFWorkbook();
			Sheet sheet = workbook.createSheet("Optional Name");
			Row row = sheet.createRow(0);
			Cell cell = row.createCell(0);
			cell.setCellValue("This");
			row.createCell(1).setCellValue("is");
			row.createCell(2).setCellValue("a");
			row.createCell(3).setCellValue("test");
			row.createCell(4).setCellValue("file");
			
			
			FileOutputStream fos =
					new FileOutputStream(new File(directory.getAbsolutePath() + "\\CreatedExcel.xlsx"));
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
}
