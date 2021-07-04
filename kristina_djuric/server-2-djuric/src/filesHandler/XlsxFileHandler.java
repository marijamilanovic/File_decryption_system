package filesHandler;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class XlsxFileHandler {
	
	public static void createDecryptedXlsxFile(String fileLocation, String decryptedContent) {
		try {
			XSSFWorkbook workbook = new XSSFWorkbook();
			XSSFSheet sheet = workbook.createSheet("Datatypes in Java");
		 	String[] rows = decryptedContent.split("/ROW/");
		        int rowNum = 0;
		        System.out.println("Creating excel");

		        for (String r : rows) {
		            Row row = sheet.createRow(rowNum++);
		            int colNum = 0;
		            String[] cells = r.split("/CELL/");
		            for (String c : cells) {
		                Cell cell = row.createCell(colNum++);
		                if (isNumeric(c)) {
		                    cell.setCellValue(Integer.parseInt(c));
		                } else{
		                    cell.setCellValue((String) c);
		                }
		            }
		        }

		        try {
		            FileOutputStream outputStream = new FileOutputStream(fileLocation);
		            workbook.write(outputStream);
		            workbook.close();
		        } catch (FileNotFoundException e) {
		            e.printStackTrace();
		        } catch (IOException e) {
		            e.printStackTrace();
		        }

		} catch (Exception e) {
			System.out.println("An error occurred.");
		    e.printStackTrace();
		}
	}
	
	public static boolean isNumeric(String str) {
		  return str.matches("-?\\d+(\\.\\d+)?");
		}
}
