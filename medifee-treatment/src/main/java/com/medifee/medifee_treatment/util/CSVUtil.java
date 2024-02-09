package com.medifee.medifee_treatment.util;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import com.opencsv.CSVWriter;

public class CSVUtil {

	static String csvFile;
	static FileWriter fileWriter;
	static CSVWriter csvWriter;

	public CSVUtil() throws IOException {

		// Create a FileWriter and CSVWriter
		csvFile = getCSVFile();
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

	public static String getCSVFile() {

		LocalDateTime dateTime = LocalDateTime.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH-mm");
		String formattedDateTime = dateTime.format(formatter);

		return System.getProperty("user.dir") + "/pricefile/"+formattedDateTime+".csv";
	}
}
