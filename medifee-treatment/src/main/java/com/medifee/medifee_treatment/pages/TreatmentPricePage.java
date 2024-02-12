package com.medifee.medifee_treatment.pages;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.medifee.medifee_treatment.base.TestBase;

public class TreatmentPricePage extends TestBase {

	//Page Factory - Object Repository
	@FindBy(xpath="//h1[@class='page-main-heading']")
	WebElement pageHeadingText;

	@FindBy(xpath="//div[@class='cost-pricing-item low']//p[@class='cost-pricing-item-price']")
	WebElement lowestCost;

	@FindBy(xpath="//div[@class='cost-pricing-item average']//p[@class='cost-pricing-item-price']")
	WebElement averageCost;

	@FindBy(xpath="//div[@class='cost-pricing-item highest']//p[@class='cost-pricing-item-price']")
	WebElement highestCost;

	@FindBy(xpath="//table[@class='city-list-table']//tbody//tr//td[1]/a[1]")
	List<WebElement> listOfCities;

	@FindBy(xpath="//div[@class='show-more-city-list ']//span")
	List<WebElement> showMore;

	@FindBy(xpath="//div[@class='cost-page-details-inner']//ul//li/p[@class='cost-page-details-list-text']")
	List<WebElement> treatmentInfoText;

	@FindBy(xpath="//div[@class='cost-page-details-inner']//ul//li/p[@class='cost-page-details-list-value']")
	List<WebElement> treatmentInfoValue;

	@FindBy(xpath="//div[@class='cost-pricing']//p[@class='cost-pricing-item-text']")
	List<WebElement> priceText;

	@FindBy(xpath="//div[@class='cost-pricing']//p[@class='cost-pricing-item-price']")
	List<WebElement> priceVal;

	//Initializing the Page Objects
	public TreatmentPricePage() {
		PageFactory.initElements(driver, this);
	}

	//Actions
	public String getPageHeading() {

		return pageHeadingText.getText();
	}

	public boolean isPriceListAvailable() {
		return listOfCities.size() != 0 ;
	}

	public boolean validatePageHeading(String expectedHeading) {

		boolean flag = false;
		if(getPageHeading().contains(expectedHeading)){
			flag = true;
		}
		return flag;
	}

	public String getLowestCost() {

		String lowCost = lowestCost.getText().trim().substring(1);
		return lowCost;
	}

	public String getAverageCost() {

		String avgCost = averageCost.getText().trim().substring(1);
		return avgCost;
	}

	public String getHighestCost() {
		String highCost = highestCost.getText().trim().substring(1);
		return highCost;
	}

	public ArrayList<String> getTreatmentPriceText(){

		ArrayList<String> list = new ArrayList<String>();

		for(int i=0; i<priceText.size(); i++){
			String infoText = priceText.get(i).getText();
			list.add(infoText);
		}
		return list;
	}

	public ArrayList<String> getTreatmentPriceValue(){

		ArrayList<String> list = new ArrayList<String>();

		for(int i=0; i<priceVal.size(); i++){
			String infoPrice = priceVal.get(i).getText().trim().substring(1).replaceAll(",", "");
			list.add(infoPrice);
		}
		return list;
	}

	public ArrayList<String> getTreatmentInfoText() {

		ArrayList<String> list = new ArrayList<String>();

		for(int i=0; i<treatmentInfoText.size(); i++){
			String infoText = treatmentInfoText.get(i).getText();
			list.add(infoText);
		}
		return list;
	}

	public ArrayList<String> getTreatmentInfoValue() {

		ArrayList<String> list = new ArrayList<String>();

		for(int i=0; i<treatmentInfoValue.size(); i++){
			String infoValue = treatmentInfoValue.get(i).getText();
			list.add(infoValue);
		}
		return list;
	}

	public ArrayList<String> getAllCities() {

		if(areMoreCities()) {
			showMore.get(0).click();
		}
		ArrayList<String> list = new ArrayList<String>();

		for(int i=0; i<listOfCities.size(); i++){

			String treatmentName = listOfCities.get(i).getText();
			list.add(treatmentName);
		}
		return list;
	}

	public boolean areMoreCities() {
		return showMore.size()!=0;
	}

	public String getPriceRange(String cityName) {

		By priceRange = By.xpath("//table[@class='city-list-table']//tbody//tr/td/a[text()='"+cityName+"']/../following-sibling::td[1]");
		return driver.findElement(priceRange).getText();
	}

	public String getMinPrice(String cityName) {

		String priceRange = getPriceRange(cityName);
		String[] priceMinMax = priceRange.split("-");
		return priceMinMax[0].toString().toString().trim().substring(1);
	}

	public String getMaxPrice(String cityName) {

		String priceRange = getPriceRange(cityName);
		String[] priceMinMax = priceRange.split("-");
		return priceMinMax[1].toString().toString().trim().substring(1);
	}

	public String[] getMinMaxPrice(String cityName) {

		String priceRange = getPriceRange(cityName);
		return priceRange.split("-");
	}

	public void getPriceRangeByCity() {

		ArrayList<String> allCities = getAllCities();

		for(int i=0; i<allCities.size(); i++) {

			String cityName = allCities.get(i).toString();
			String[] priceRange = getMinMaxPrice(cityName);
			String minPrice = priceRange[0].toString().trim().substring(1);
			String maxPrice = priceRange[1].toString().trim().substring(1);

			System.out.println(cityName+"\t"+minPrice+"\t"+maxPrice);
		}
	}

	public ArrayList<String> getCityNameMinMaxList() {

		ArrayList<String> cityMinMax = new ArrayList<String>();
		ArrayList<String> cityList = getAllCities();

		for(String cityName: cityList) {
			String minCity = cityName+"_Min";
			String maxCity = cityName+"_Max";
			cityMinMax.add(minCity);
			cityMinMax.add(maxCity);
		}
		return cityMinMax;
	}

	public ArrayList<String> getCityPriceMinMaxList() {

		ArrayList<String> cityList = getAllCities();
		ArrayList<String> priceMinMax = new ArrayList<String>();

		for(String cityName: cityList) {

			String minPrice = getMinPrice(cityName);
			String maxPrice = getMaxPrice(cityName);
			priceMinMax.add(minPrice);
			priceMinMax.add(maxPrice);
		}
		return priceMinMax;
	}
}