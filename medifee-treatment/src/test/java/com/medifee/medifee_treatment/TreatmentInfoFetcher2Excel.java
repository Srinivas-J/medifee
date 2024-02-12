package com.medifee.medifee_treatment;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.medifee.medifee_treatment.base.TestBase;
import com.medifee.medifee_treatment.pages.TreatmentListPage;
import com.medifee.medifee_treatment.pages.TreatmentPricePage;
import com.medifee.medifee_treatment.util.CSVUtil;
import com.medifee.medifee_treatment.util.ExcelUtil;

public class TreatmentInfoFetcher2Excel extends TestBase {

	TreatmentListPage treatmentPage;
	TreatmentPricePage treatmentPricePage;
	CSVUtil csvUtil;
	ExcelUtil excelUtil;

	//Initialize the Parent class properties
	public TreatmentInfoFetcher2Excel() {
		super();
	}

	//Initialize the Browser and Open the URL
	@BeforeMethod
	public void setup() throws IOException {
		initialization();
		treatmentPage = new TreatmentListPage();
		treatmentPricePage = new TreatmentPricePage();
		csvUtil = new CSVUtil();
		excelUtil = new ExcelUtil();
	}

	//Actions to get TreatmentPriceData 
	@Test
	public void writeTreatmentPriceData() {
		try {
			String pageTitle = treatmentPage.getPageTitle();
			CSVUtil.writeCSV(new String[]{pageTitle});

			@SuppressWarnings("rawtypes")
			Map<String, Map> tInfoMap = new HashMap<>();
			@SuppressWarnings("rawtypes")
			Map<String, Map> tCityMap = new HashMap<>();

			ArrayList<String> treatmentList = treatmentPage.getAllTreatmentsList();

			for(int i=0; i<treatmentList.size(); i++) {
				String treatmentName = treatmentList.get(i).toString();
				scrollUp();
				treatmentPage.clickTreatmentType(treatmentName);

				if(treatmentPricePage.isPriceListAvailable()) {
					String headingText = treatmentPricePage.getPageHeading();
					CSVUtil.writeCSV(new String[] {treatmentList.get(i)});
					treatmentPricePage.validatePageHeading(headingText);

					//Getting Treatment Information (Low, Avg, High costs, Type, Time...etc.)
					ArrayList<String> priceText = treatmentPricePage.getTreatmentPriceText();
					ArrayList<String> priceValue = treatmentPricePage.getTreatmentPriceValue();
					ArrayList<String> infoText = treatmentPricePage.getTreatmentInfoText();
					ArrayList<String> infoValue = treatmentPricePage.getTreatmentInfoValue();

					Map<ArrayList<String>, ArrayList<String>> infoMap = new HashMap<>();
					ArrayList<String> mergedTextList = new ArrayList<>();
					mergedTextList.addAll(priceText);
					mergedTextList.addAll(infoText);
					ArrayList<String> mergedValueList = new ArrayList<>();
					mergedValueList.addAll(priceValue);
					mergedValueList.addAll(infoValue);
					infoMap.put(mergedTextList, mergedValueList);	

					//Getting Treatment Price range City wise
					ArrayList<String> cityNameMinMax = treatmentPricePage.getCityNameMinMaxList();
					ArrayList<String> cityPriceMinMax = treatmentPricePage.getCityPriceMinMaxList();
					Map<ArrayList<String>, ArrayList<String>>	cityMap = new HashMap<>();
					cityMap.put(cityNameMinMax, cityPriceMinMax);

					tInfoMap.put(treatmentList.get(i), infoMap);
					tCityMap.put(treatmentList.get(i), cityMap);

					for (Map.Entry<ArrayList<String>, ArrayList<String>> entry : infoMap.entrySet()) {

						CSVUtil.writeCSV(entry.getKey().toString().split(","));
						CSVUtil.writeCSV(entry.getValue().toString().split(","));
					}

					for (Map.Entry<ArrayList<String>, ArrayList<String>> entry : cityMap.entrySet()) {

						CSVUtil.writeCSV(entry.getKey().toString().split(","));
						CSVUtil.writeCSV(entry.getValue().toString().split(","));
					}

					System.out.println("Getting Data for "+headingText);
				} else {
					String noPriceText = "No price information available as of now, for "+treatmentList.get(i).toString();
					System.out.println(noPriceText);
				}

				driver.navigate().back();
			}
			CSVUtil.closeFileConnection();
			CSVUtil.convertCSV2XLSX();
			CSVUtil.convertExcelColToRow();
		}
		catch(Exception e) {
			e.printStackTrace();}

	}

	//Quit the Browser
	@AfterMethod
	public void tearDown() throws IOException {

		driver.quit();
	}
}