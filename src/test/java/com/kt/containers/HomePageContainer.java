package com.kt.containers;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

public class HomePageContainer {

	@FindBy(how = How.XPATH, using = "//span[contains(text(),' Home ')]")
	public static WebElement homeButton;

	@FindBy(how = How.XPATH, using = "//div[contains(@class,'ieco-tab-text') and contains(text(),'Deposits')]")
	public static WebElement depositeTab;

	@FindBy(how = How.XPATH, using = "//*[contains(@class,'ieco-title-stock') and contains(text(),'Fixed deposit')]")
	public WebElement fixedDepositeLink;

	@FindBy(how = How.TAG_NAME, using = "img")
	public List<WebElement> allImages;

	@FindBy(how = How.XPATH, using = "//div[contains(text(),'Amount')]//following-sibling::div[2]")
	public WebElement amount;

	@FindBy(how = How.XPATH, using = "//input[contains(@name,'investedAmt')]")
	public WebElement amountTxt;

	@FindBy(how = How.XPATH, using = "//span[contains(text(),'Proceed')]//parent::button")
	public WebElement proceedBtn;
	
	@FindBy(how = How.XPATH, using = "//div[contains(@class,'min-max-error')]")
	public WebElement amtFieldError;

	@FindBy(how = How.XPATH, using = "//div[contains(@class,'ieco-maturity-price-val')]")
	public WebElement maturityPrice;

	@FindBy(how = How.XPATH, using = "//div[contains(@class,'ieco-interest-rate')]")
	public WebElement payoutInterestRate;

	@FindBy(how = How.ID, using = "mat-input-0")
	public WebElement depositeDuration;

	@FindBy(how = How.NAME, using = "dipositDurationYear")
	public WebElement depositeDurationYr;

	@FindBy(how = How.NAME, using = "dipositDurationMonth")
	public WebElement depositeDurationMonth;

	@FindBy(how = How.NAME, using = "dipositDurationDay")
	public WebElement depositeDurationDay;

	@FindBy(how = How.XPATH, using = "//span[contains(text(),'Done')]//parent::button")
	public WebElement depositeDurationDone;

	@FindBy(how = How.NAME, using = "interestPayPlan")
	public WebElement interestPlan;

	@FindBy(how = How.XPATH, using = "//div[text()='Cumulative ']")
	public WebElement cumulative;

	@FindBy(how = How.XPATH, using = "//div[text()='Non-cumulative ']")
	public WebElement nonCumulative;
	
	@FindBy(how = How.XPATH, using = "//div[text()='Quarterly ']")
	public WebElement quarterly;
	
	@FindBy(how = How.XPATH, using = "//div[text()='Monthly ']")
	public WebElement monthly;
	
	

}
