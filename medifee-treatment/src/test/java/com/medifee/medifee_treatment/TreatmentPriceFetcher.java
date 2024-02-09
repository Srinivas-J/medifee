package com.medifee.medifee_treatment;

import java.io.IOException;
import java.util.ArrayList;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import com.medifee.medifee_treatment.base.TestBase;
import com.medifee.medifee_treatment.pages.TreatmentListPage;
import com.medifee.medifee_treatment.pages.TreatmentPricePage;
import com.medifee.medifee_treatment.util.CSVUtil;


public class TreatmentPriceFetcher extends TestBase {

	TreatmentListPage treatmentPage;
	TreatmentPricePage treatmentPricePage;
	CSVUtil csvUtil;

	//Initialize the Parent class properties
	public TreatmentPriceFetcher() {
		super();
	}

	//Initialize the Browser and Open the URL
	@BeforeMethod
	public void setup() throws IOException {
		initialization();
		treatmentPage = new TreatmentListPage();
		treatmentPricePage = new TreatmentPricePage();
		csvUtil = new CSVUtil();
	}

	//Actions to get TreatmentPriceData 
	//@Test
	public void getTreatmentPriceData2() {
		try {
			String pageTitle = treatmentPage.getPageTitle();
			CSVUtil.writeCSV(new String[]{pageTitle});
			CSVUtil.addEmptyLine();

			ArrayList<String> treatmentList = treatmentPage.getAllTreatmentsList();

			for(int i=0; i<treatmentList.size(); i++) {
				String treatmentName = treatmentList.get(i).toString();
				CSVUtil.writeCSV(new String[]{treatmentName});
				scrollUp();
				treatmentPage.clickTreatmentType(treatmentName);

				if(treatmentPricePage.isPriceListAvailable()) {
					String headingText = treatmentPricePage.getPageHeading();
					treatmentPricePage.validatePageHeading(headingText);
					CSVUtil.writeCSV(new String[] {headingText, "Lowest Cost (approx.)", "Average Cost (approx.)", "Highest Cost (approx.)"});
					String lowCost = treatmentPricePage.getLowestCost();
					String avgCost = treatmentPricePage.getAverageCost();
					String highCost = treatmentPricePage.getHighestCost();
					CSVUtil.writeCSV(new String[] {"", lowCost, avgCost, highCost});

					ArrayList<String> cityList = treatmentPricePage.getAllCities();
					int totalCityCount = cityList.size();
					ArrayList<String> cityListForCSV = new ArrayList<String>();
					ArrayList<String> priceRangeForCSV = new ArrayList<String>();

					for(int j=0; j<totalCityCount; j++) {

						String cityName = cityList.get(j).toString();
						String minPrice = treatmentPricePage.getMinPrice(cityName);
						String maxPrice = treatmentPricePage.getMaxPrice(cityName);

						cityListForCSV.add( cityName+"_MIN");
						cityListForCSV.add( cityName+"_MAX");
						priceRangeForCSV.add(minPrice);
						priceRangeForCSV.add(maxPrice);

					}
					int count = cityListForCSV.size();
					String[] cityListArray = new String[count];
					String[] priceListArray = new String[count];

					for(int k=0; k<count; k++) {
						cityListArray[k] = cityListForCSV.get(k);
						priceListArray[k] = priceRangeForCSV.get(k);
					}

					CSVUtil.writeCSV(cityListArray);
					CSVUtil.writeCSV(priceListArray);

				} else {
					String noPriceText = "No price information available as of now.";
					CSVUtil.writeCSV(new String[]{noPriceText});
				}
				CSVUtil.addEmptyLine();
				driver.navigate().back();
			}
		}
		catch(Exception e) {
			e.printStackTrace();}

	}

	//Quit the Browser
	@AfterMethod
	public void tearDown() throws IOException {
		CSVUtil.closeFileConnection();
		driver.quit();
	}
}