package com.kt.utilities;

import java.io.IOException;

import org.testng.ISuite;
import org.testng.ISuiteListener;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.MediaEntityBuilder;
import com.kt.base.TestBaseKotak;

public class Listners extends TestBaseKotak implements ITestListener, ISuiteListener {

	public void onTestFailure(ITestResult result) {
		
			try {
				extentTest.fail(result.getTestName() + " - " + result.getThrowable().getMessage(),
						MediaEntityBuilder.createScreenCaptureFromPath(takeScreenshot()).build());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			extent.flush();
			driver.close();
	}

	public void onTestSuccess(ITestResult result) {
		System.out.println("Exit method...!!!");
		extent.flush();
		driver.close();
	}

	public void onTestStart(ITestResult result) {

	}

	public void onStart(ISuite suite) {
		extentTestConfig();
	}
	
	public void onFinish(ISuite suite) {
		driver.quit();
	}
}
