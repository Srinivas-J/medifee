package com.medifee.medifee_treatment.base;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.Duration;
import java.util.Properties;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class TestBase {

	public static WebDriver driver;
	public static Properties prop;

	public TestBase() {
		try {

			String configFile = "/src/main/java/com/medifee/medifee_treatment/config/config.properties";
			prop = new Properties();

			FileInputStream fis = new FileInputStream(System.getProperty("user.dir") + configFile);
			prop.load(fis);
		}catch(FileNotFoundException e) {
			e.printStackTrace();
		}catch(IOException e) {
			e.printStackTrace();
		}
	}

	public static void initialization() {
		System.out.println(System.getProperty("user.dir"));
		String browserName = prop.getProperty("browser");
		String url = prop.getProperty("url");

		if(browserName.equalsIgnoreCase("CHROME")) {
			driver = new ChromeDriver();
		} else if(browserName.equalsIgnoreCase("FIREFOX")) {
			driver = new FirefoxDriver();
		}	else if(browserName.equalsIgnoreCase("EDGE")) {
			driver =  new EdgeDriver();
		}

		driver.manage().window().maximize();
		driver.manage().deleteAllCookies();
		driver.manage().timeouts().pageLoadTimeout(Duration.ofMillis(10000));
		driver.manage().timeouts().implicitlyWait(Duration.ofMillis(2000));

		driver.get(url);

	}

	public static void scrollUp() {
		JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
		// Scroll to the top of the page
		jsExecutor.executeScript("window.scrollTo(0, 0);");
	}

}
