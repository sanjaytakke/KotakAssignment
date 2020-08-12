package com.kt.containers;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

import com.google.inject.ImplementedBy;

public class HomePageMobileContainer {

	
	@FindBy(how = How.XPATH, using = "//*[@routerlink='/home']")
	public WebElement homeButton;

	@FindBy(how = How.XPATH, using = "//div[contains(@class,'ieco-tab-text') and contains(text(),'Deposits')]")
	public WebElement depositeTab;

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

	@FindBy(how = How.XPATH, using = "//div[contains(@class,'ieco-maturity-price-val')]")
	public WebElement maturityPrice;

	@FindBy(how = How.XPATH, using = "//div[contains(@class,'ieco-interest-rate')]")
	public WebElement payoutInterestRate;

}
