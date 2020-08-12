package com.kt.base;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.slf4j.Logger;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;

import io.github.bonigarcia.wdm.WebDriverManager;

public class TestBaseKotak {

	Properties prop = new Properties();
	public static ExtentReports extent;
	public static ExtentTest extentTest;
	public static WebDriver driver;

	public WebDriver getDriver(String browserName) {
		if ("Chrome".equalsIgnoreCase(browserName)) {
			driver = createChromeDriver();
			return driver;
		} else if ("IE".equalsIgnoreCase(browserName)) {
			driver = createIEDriver();
			return driver;
		} else if ("ChromeEmulator".equalsIgnoreCase(browserName)) {
			driver = ChromeEmulator();
			return driver;
		} else
			return null;
	}

	public ChromeDriver createChromeDriver() {
		WebDriverManager.chromedriver().version("84").setup();
		ChromeOptions options = new ChromeOptions();
		options.addArguments("--start-maximized");

		return new ChromeDriver(options);
	}

	public InternetExplorerDriver createIEDriver() {
		WebDriverManager.iedriver().setup();
		return new InternetExplorerDriver();
	}

	public ChromeDriver ChromeEmulator() {
		WebDriverManager.chromedriver().version("84").setup();
		Map<String, String> mobileEmulation = new HashMap();
		mobileEmulation.put("deviceName", "Nexus 5");
		ChromeOptions chromeOptions = new ChromeOptions();
		chromeOptions.setExperimentalOption("mobileEmulation", mobileEmulation);

		return new ChromeDriver(chromeOptions);
	}

	public String getUrl() throws Exception {
		prop.load(new FileReader(
				System.getProperty("user.dir") + "\\src\\test\\resources\\Properties\\config.properties"));
		return prop.getProperty("kotak.URL");
	}

	public String getPropertyValue(String value) {
		try {
			prop.load(new FileReader(
					System.getProperty("user.dir") + "\\src\\test\\resources\\Properties\\config.properties"));

		} catch (IOException e) {
			System.out.println("Exception - getPropertyValue : " + e);
		}
		return prop.getProperty(value);
	}

	public void extentTestConfig() {
		ExtentHtmlReporter reporter = new ExtentHtmlReporter(
				System.getProperty("user.dir") + "\\test-output\\ExtentReports\\extent-report.html");
		// reporter.loadConfig(System.getProperty("user.dir") +
		// "\\src\\test\\resources\\extent-config.xml");
		extent = new ExtentReports();
		extent.attachReporter(reporter);
	}

	public void createTest(String testName) {
		extentTest = extent.createTest(testName);
	}

	public String takeScreenshot() {
		File file = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		String fileName = System.getProperty("user.dir") + "\\src\\test\\resources\\Screenshots\\KotakTest_"
				+ new Date().getDate() + "_" + new Date().getTime() + "_" + new Date().getSeconds();
		try {
			FileUtils.copyFile(file, new File(fileName));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return fileName;
	}

	public void reportInfo(String msg) throws IOException {
		extentTest.info(msg);
		System.out.println("Info : " + msg);
	}

	public void reportError(String msg) throws IOException {
		extentTest.error(msg);
		System.out.println("Error : " + msg);
	}

	public void reportInfo(String msg, String screenshots) throws IOException {
		extentTest.info(msg, MediaEntityBuilder.createScreenCaptureFromPath(screenshots).build());
		System.out.println(msg);

	}

}
