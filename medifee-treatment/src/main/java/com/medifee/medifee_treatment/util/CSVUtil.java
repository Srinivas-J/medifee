package com.medifee.medifee_treatment.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;

public class CSVUtil {

	static String csvFile, xslxFile;
	static FileWriter fileWriter;
	static CSVWriter convertFileWriter;
	static CSVWriter csvWriter;
	static CSVReader reader;

	public CSVUtil() throws IOException {

		// Create a FileWriter and CSVWriter
		csvFile = createFile("csv");
		xslxFile = createFile("xlsx");
		fileWriter = new FileWriter(csvFile);
		csvWriter = new CSVWriter(fileWriter);
	}
	public static void writeCSV(String[] csvData) throws IOException {

		csvWriter.writeNext(csvData);
	}

	public static void addEmptyLine() {
		String[] emptyLine = {};
		csvWriter.writeNext(emptyLine);
	}

	public static void closeFileConnection() throws IOException {

		// Close the CSVWriter and FileWriter
		csvWriter.close();
		fileWriter.close();
	}

	public static String createFile(String extension) {

		LocalDateTime dateTime = LocalDateTime.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH-mm");
		String formattedDateTime = dateTime.format(formatter);

		return System.getProperty("user.dir") + "/pricefile/"+formattedDateTime+"."+extension;
	}

	public static void convertCSV2XLSX() {

		try {
			// Read CSV data
			List<String[]> csvData = readCSVFile(csvFile);

			// Create and write XLSX file
			writeXLSXFile(xslxFile, csvData);

			System.out.println("CSV to XLSX file Conversion completed successfully.");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static List<String[]> readCSVFile(String filePath) throws IOException {
		List<String[]> data = new ArrayList<>();

		try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
			String line;
			while ((line = br.readLine()) != null) {
				String[] row = line.split(",");
				data.add(row);
			}
		}

		return data;
	}

	private static void writeXLSXFile(String filePath, List<String[]> data) throws IOException {
		try (Workbook workbook = new XSSFWorkbook();
				FileOutputStream fos = new FileOutputStream(new File(filePath))) {

			Sheet sheet = workbook.createSheet("Sheet1");

			int rowNum = 0;
			for (String[] row : data) {
				Row excelRow = sheet.createRow(rowNum++);
				int colNum = 0;
				for (String cellData : row) {
					Cell cell = excelRow.createCell(colNum++);
					cell.setCellValue(cellData);
				}
			}

			workbook.write(fos);
			workbook.close();
		}
	}

	public static void convertExcelColToRow() throws InterruptedException {

		Thread.sleep(20000);
		String outFile = createFile("xlsx");
		ExcelUtil.excelColumnToRowConverter(xslxFile, outFile);
	}
}
