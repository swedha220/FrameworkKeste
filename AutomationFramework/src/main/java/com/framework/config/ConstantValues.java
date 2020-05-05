package com.framework.config;

import java.io.File;

public class ConstantValues {

	public static final String config_Path = System.getProperty("user.dir") + File.separator
			+ "src\\main\\java\\com\\framework\\config\\config.properties";

	public static final String TESTDATA_PATH = System.getProperty("user.dir") + File.separator
			+ "TestData.xlsx";

	public static final String chromeExePath = System.getProperty("user.dir") + File.separator + "src\\main\\resources\\executables\\chromedriver.exe";
	public static final String configSheetName = "ConfigDetails";
	public static final int col_BrowserValue = 1;
	public static final int row_BrowserValue = 1;
	public static final int col_UrlValue = 1;
	public static final int row_UrlValue = 2;

	public static final String TestCasesSheetName = "TestCases";
	public static final int Col_TestCaseName = 1;
	public static final int Col_RunMode = 3;
	public static final int Col_TestInput = 4;

	public static final String extentReportPath = System.getProperty("user.dir") + File.separator
			+ "test-output/ExtentReport.html";

	public static final String screenshotsFolder_path = System.getProperty("user.dir") + File.separator + "Screenshots";
	public static final long ImplicitWaitTime = 20;
	public static final long pageLoadTime = 30;

//public static final String Path_TestData = System.getProperty("user.dir") + File.separator +  "src\\main\\resources\\TestData";
//public static final String File_TestData = "TestData.xlsx";

	public static final int Retry_Count = 2;

}
