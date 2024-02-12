package com.medifee.medifee_treatment.util;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class ExcelUtil {

	public static void excelColumnToRowConverter(String inputFile, String outputFile) throws InterruptedException {

		try {
			convertColumnsToRows(inputFile, outputFile);
			System.out.println("Column to Row Data Conversion completed successfully.");
		} catch (IOException e) {
			System.err.println("Error during conversion: " + e.getMessage());
		}
	}

	public static void convertColumnsToRows(String inputFile, String outputFile) throws IOException {
		try (FileInputStream fis = new FileInputStream(inputFile);
				Workbook workbook = new XSSFWorkbook(fis);
				FileOutputStream fos = new FileOutputStream(outputFile)) {

			Sheet sheet = workbook.getSheetAt(0); // Assuming you are working with the first sheet

			int numRows = sheet.getPhysicalNumberOfRows();
			int numCols = 100;

			Workbook newWorkbook = new XSSFWorkbook();
			Sheet newSheet = newWorkbook.createSheet("ConvertedData");

			for (int colIndex = 0; colIndex < numCols; colIndex++) {
				Row newRow = newSheet.createRow(colIndex);

				for (int rowIndex = 0; rowIndex < numRows; rowIndex++) {
					Cell sourceCell = sheet.getRow(rowIndex).getCell(colIndex);
					Cell newCell = newRow.createCell(rowIndex);
					if (sourceCell != null) {
						switch (sourceCell.getCellType()) {
						case STRING:
							newCell.setCellValue(sourceCell.getStringCellValue().replaceAll("[\"\\[\\]]", ""));
							break;
						case NUMERIC:
							newCell.setCellValue(sourceCell.getNumericCellValue());
							break;
							// Handle other cell types as needed
						default:
							// Handle other cell types or leave blank
						}
					}
				}
			}

			newWorkbook.write(fos);
			newWorkbook.close();
		}
	}

}