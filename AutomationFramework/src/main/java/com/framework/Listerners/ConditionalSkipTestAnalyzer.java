package com.framework.Listerners;

import java.lang.reflect.Method;

import org.testng.IInvokedMethod;
import org.testng.IInvokedMethodListener;
import org.testng.ITestResult;
import org.testng.SkipException;

import com.aventstack.extentreports.Status;
import com.framework.services.Utilities;

public class ConditionalSkipTestAnalyzer implements IInvokedMethodListener {

	public void beforeInvocation(IInvokedMethod invokedMethod, ITestResult result) {

		Method method = result.getMethod().getConstructorOrMethod().getMethod();
		if (Utilities.getRunMode(method.getName()).equalsIgnoreCase("no")) {
			ExtentTestManager.getTest().log(Status.INFO, "Tests case is skipped as per excel");
			throw new SkipException("These Tests are marked not to run in excel");

		}
		return;
	}

	@Override
	public void afterInvocation(IInvokedMethod method, ITestResult testResult) {
		// TODO Auto-generated method stub

	}
}
