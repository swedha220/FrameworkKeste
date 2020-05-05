package com.framework.testcases;

import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import com.aventstack.extentreports.Status;
import com.framework.Listerners.ExtentTestManager;

public class LoginPageTest {

	@AfterSuite
	public void cleanUp() {
		System.out.println("clean up and send email report");
	}

	@BeforeSuite
	public void setenvironmnet() {
		System.out.println("in before suite");
	}

	@Test
	public void verifyLogin() {
		ExtentTestManager.getTest().log(Status.INFO, "Testing login");
		System.out.println("Executing verify login method");
	}

}
