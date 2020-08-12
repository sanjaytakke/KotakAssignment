package com.kt.utilities;

import org.testng.ITestContext;
import org.testng.annotations.DataProvider;

import com.kt.base.TestBaseKotak;

public class TestDataReader extends TestBaseKotak{

	InputExcelDataReader inputDataReader=new InputExcelDataReader();
	@DataProvider
	public Object[][] bankDetails(ITestContext context){
		System.out.println("Test Name BankFDInterest" );
		return inputDataReader.readData("BankFDInterest",
				getPropertyValue("testData.excel"));
	}
}
