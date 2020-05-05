package com.framework.Listerners;

import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

import com.framework.config.ConstantValues;

public class RetryAnalyzer implements IRetryAnalyzer {

	int counter = 0;
	int retryCount = ConstantValues.Retry_Count;

	@Override
	public boolean retry(ITestResult result) {
		// TODO Auto-generated method stub
		if (counter < retryCount) {
			counter++;
			return true;
		}
		return false;
	}

}
