package com.framework.Listerners;

import java.io.IOException;

import org.apache.log4j.Logger;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.Reporter;

import com.aventstack.extentreports.Status;
import com.framework.services.Utilities;

public class TestListener implements ITestListener {
	private static Logger logs = Logger.getLogger(TestListener.class.getName());

	public void onStart(ITestContext context) {
		logs.info("*** Test Suite " + context.getName() + " started ***");
		Reporter.log("*** Test Suite " + context.getName() + " started ***");
	}

	public void onFinish(ITestContext context) {
		logs.info(("*** Test Suite " + context.getName() + " ending ***"));
		ExtentTestManager.endTest();
		ExtentManager.getInstance().flush();
	}

	public void onTestStart(ITestResult result) {
		logs.info(("*** Running test method " + result.getMethod().getMethodName() + "..."));
		ExtentTestManager.startTest(result.getMethod().getMethodName());
	}

	public void onTestSuccess(ITestResult result) {
		logs.info("*** Executed " + result.getMethod().getMethodName() + " test successfully...");
		ExtentTestManager.getTest().log(Status.PASS, "Test passed");
	}

	public void onTestFailure(ITestResult result) {
		logs.info("*** Test execution " + result.getMethod().getMethodName() + " failed...");
		// logs.info((result.getMethod().getMethodName() + " failed!"));
		ExtentTestManager.getTest().log(Status.FAIL, "Test Failed");
		try {
			ExtentTestManager.getTest().addScreenCaptureFromPath(Utilities.takeScreenshot(result.getName()));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void onTestSkipped(ITestResult result) {
		logs.info("*** Test " + result.getMethod().getMethodName() + " skipped...");
		ExtentTestManager.getTest().log(Status.SKIP, "Test Skipped");
	}

	public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
		logs.info("*** Test failed but within percentage % " + result.getMethod().getMethodName());
	}

}