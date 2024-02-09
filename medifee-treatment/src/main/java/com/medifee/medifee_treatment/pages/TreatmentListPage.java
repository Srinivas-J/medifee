package com.medifee.medifee_treatment.pages;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.medifee.medifee_treatment.base.TestBase;

public class TreatmentListPage extends TestBase{

	//Page Factory - Object Repository
	@FindBy(xpath="//div[@class='grid-section']//ul//li//a")
	List<WebElement> listOfTreatments;

	//Initializing the Page Objects
	public TreatmentListPage() {
		PageFactory.initElements(driver, this);
	}

	//Actions
	public String getPageTitle() {
		return driver.getTitle();
	}

	public ArrayList<String> getAllTreatmentsList() {

		ArrayList<String> list = new ArrayList<String>();
		System.out.println("listOfTreatmentsSize = "+listOfTreatments.size());

		if (!listOfTreatments.isEmpty() && listOfTreatments.size() > 1) {
			for(int i=0; i<listOfTreatments.size(); i++){
				String treatmentName = listOfTreatments.get(i).getText();
				System.out.println("treatmentName= "+treatmentName);
				list.add(treatmentName);
			}}
		return list;
	}

	public void clickTreatmentType(String treatmentName) {

		By tratmentType = By.xpath("//div[@class='grid-section']//ul//li//a[text()='"+treatmentName+"']");		
		driver.findElement(tratmentType).click();
	}
}
