package com.framework.testcases;

import java.lang.reflect.Method;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import com.aventstack.extentreports.Status;
import com.framework.Listerners.ExtentTestManager;
import com.framework.services.Utilities;

public class AccountsPageTest {

	@BeforeMethod
	public void startSession(Method method) throws Exception {
		Utilities.launchBrowser();
	}

	@Test
	public void VerifyTestFailure() {
		System.out.println("Executing verify validation");
		Assert.fail();
	}

	@Test
	public void VerifyNoMode() {
		System.out.println("Executing VerifyNoMode");
	}

	@Test
	public void VerifyValidation() {
		ExtentTestManager.getTest().log(Status.INFO, "login");
		System.out.println("Executing VerifyValidation");
	}

	@AfterMethod
	public void endSession() throws Exception {
		Utilities.closeBrowser();
	}
}
