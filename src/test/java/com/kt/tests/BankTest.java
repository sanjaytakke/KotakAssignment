package com.kt.tests;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.io.IOException;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.SkipException;
import org.testng.annotations.Test;

import com.kt.base.TestBaseKotak;
import com.kt.constants.Constants;
import com.kt.containers.HomePageContainer;
import com.kt.utilities.TestDataReader;

public class BankTest extends TestBaseKotak {
	// public static final Logger LOGGER = LoggerFactory.getLogger(BankTest.class);
	HomePageContainer homePageContainer = new HomePageContainer();
	WebDriverWait wait = null;
	WebDriver driver;

	@Test(dataProvider = "bankDetails", dataProviderClass = TestDataReader.class)
	public void test1(String testNumber, String browser, String amount, String interestRate, String duration,
			String planType) throws Exception {
		createTest(testNumber);
		driver = getDriver(browser);
		homePageContainer = PageFactory.initElements(driver, HomePageContainer.class);
		driver.get(getPropertyValue("kotak.URL"));
		reportInfo("Kotal Url : " + getPropertyValue("kotak.URL"), takeScreenshot());
		Thread.sleep(2000);
		driver.navigate().refresh();
		Thread.sleep(2000);
		homePageContainer.homeButton.click();
		reportInfo("Click on Home Button", takeScreenshot());
		Thread.sleep(2000);
		homePageContainer.depositeTab.click();
		reportInfo("Click on Deposite Tab", takeScreenshot());
		validateBrokenImage();
		Thread.sleep(2000);
		homePageContainer.fixedDepositeLink.click();
		Thread.sleep(2000);
		reportInfo("Click on fix Deposite Link", takeScreenshot());
		addAmount(amount);
		addDuration(duration, planType);

		String actPayoutAmount = homePageContainer.maturityPrice.getText().substring(1);
		reportInfo("Total interest payout : " + actPayoutAmount);
		reportInfo("Validating Payout Interest Amount ", takeScreenshot());
		assertEquals(actPayoutAmount, calculateExp(amount, homePageContainer.payoutInterestRate.getText()));
	}

	/*
	 * Function to validate broken images
	 */
	public void validateBrokenImage() throws IOException {
		reportInfo("Validate Broken images");
		int brokenImages = 0;
		List<WebElement> allImg = homePageContainer.allImages;
		reportInfo("Total number of images on page : " + allImg.size());
		try {
			for (WebElement imageElement : allImg) {
				if (imageElement != null) {
					HttpClient httpClient = HttpClientBuilder.create().build();
					HttpGet httpRequest = new HttpGet(imageElement.getAttribute("src"));
					HttpResponse response = httpClient.execute(httpRequest);
					if (response.getStatusLine().getStatusCode() != 200) {
						brokenImages++;
						System.out.println("Broken Image " + brokenImages + " : " + imageElement.getAttribute("alt"));
					}
				}
			}
			reportInfo("Total number of broken images : " + brokenImages, takeScreenshot());
		} catch (Exception e) {
			reportError("Exception - validateBrokenImage : " + e);
		}
	}

	/*
	 * Function to Add amount in amount field
	 */
	public void addAmount(String amount) throws InterruptedException, IOException {

		reportInfo("Click on amount");
		homePageContainer.amount.click();
		homePageContainer.amountTxt.clear();
		homePageContainer.amountTxt.sendKeys(amount);
		reportInfo("Click on proceed button", takeScreenshot());
		homePageContainer.proceedBtn.click();

		if (10000 > Integer.parseInt(amount) || 100000 < Integer.parseInt(amount))
			validateAmtField(amount);

		Thread.sleep(3000);	
	}

	/*
	 * Function for Amount field validation(Max limit and Min Limit)
	 */
	public void validateAmtField(String amount) throws IOException {
		int amt = Integer.parseInt(amount);

		if (homePageContainer.amtFieldError.isDisplayed()) {
			if (10000 > amt) {
				assertEquals(Constants.MINAMTERRORMSG, homePageContainer.amtFieldError.getText());
				reportInfo("Validating Error Message for Amount " + amt, takeScreenshot());
				throw new SkipException("Amount " + amt + " is less than 1000");
			} else if (100000 < amt) {
				assertEquals(Constants.MAXAMTERRORMSG, homePageContainer.amtFieldError.getText());
				reportInfo("Validating Error Message for Amount " + amt, takeScreenshot());
				throw new SkipException("Amount " + amt + " is greater than 100000");
			}
		} else {
			assertTrue(false, "Amount Field Error Message Not Display for Amount : " + amount);
		}

	}

	/*
	 * function to calculate expected amount
	 */
	public String calculateExp(String amount, String interestRate) throws IOException {

		int fdAmount = Integer.parseInt(amount);
		double fdInterest = Double.parseDouble(interestRate.replace("%", ""));
		double payout = (fdAmount * fdInterest) / 100;
		reportInfo("Payout Amount Expected : " + payout);
		return String.valueOf(payout);

	}

	/*
	 * function to add duration(yr , month and Day)
	 */
	public void addDuration(String duration, String planType) throws Exception {
		reportInfo("Click on Deposite duration");
		Thread.sleep(4000);
		homePageContainer.depositeDuration.click();
		reportInfo("Insert duration : " + duration);
		Thread.sleep(3000);
		homePageContainer.depositeDurationYr.clear();
		homePageContainer.depositeDurationYr.click();
		homePageContainer.depositeDurationYr.sendKeys(Keys.BACK_SPACE);
		homePageContainer.depositeDurationYr.sendKeys(Keys.BACK_SPACE);
		homePageContainer.depositeDurationYr.sendKeys(duration.substring(1, 3));

		homePageContainer.depositeDurationMonth.clear();
		homePageContainer.depositeDurationMonth.click();
		homePageContainer.depositeDurationMonth.sendKeys(Keys.BACK_SPACE);
		homePageContainer.depositeDurationMonth.sendKeys(Keys.BACK_SPACE);
		homePageContainer.depositeDurationMonth.sendKeys(duration.substring(3, 5));

		homePageContainer.depositeDurationDay.clear();
		homePageContainer.depositeDurationDay.click();
		homePageContainer.depositeDurationDay.sendKeys(Keys.BACK_SPACE);
		homePageContainer.depositeDurationDay.sendKeys(Keys.BACK_SPACE);
		homePageContainer.depositeDurationDay.sendKeys(duration.substring(5));

		homePageContainer.depositeDurationDone.click();
		Thread.sleep(2000);

		if ("" != planType) {
			homePageContainer.interestPlan.click();
			if ("Cumulative".equalsIgnoreCase(planType.split("-")[0])) {
				homePageContainer.cumulative.click();
			} else {
				homePageContainer.nonCumulative.click();
				if ("monthly".equalsIgnoreCase(planType.split("-")[1]))
					homePageContainer.monthly.click();
				else
					homePageContainer.quarterly.click();

			}
			reportInfo("Click on Deposite duration Done Button", takeScreenshot());
			homePageContainer.depositeDurationDone.click();
		}
	}
}
