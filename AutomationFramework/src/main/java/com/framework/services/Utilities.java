package com.framework.services;

import java.io.File;
import java.io.FileInputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.openqa.selenium.ElementNotVisibleException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import com.framework.Listerners.WebEventListerner;
import com.framework.config.ConstantValues;
import com.framework.config.ExcelOperations;
import com.google.common.io.Files;

public class Utilities {

	private static Logger logs = Logger.getLogger(Utilities.class.getName());

	public static Properties prop;
	public static WebDriver driver;
	public static WebEventListerner driverListener;
	public static EventFiringWebDriver eventDriver;

	public Utilities() {
		try {
			prop = new Properties();
			FileInputStream fis = new FileInputStream(ConstantValues.config_Path);
			prop.load(fis);
			logs.info("INFO: Configuration property file loaded successfully");
		} catch (Exception e) {
			logs.error("Exception occured in reading configuration file:" + e.getStackTrace());

		}
	}

	public static void launchBrowser() throws Exception {
		String browser = ExcelOperations.getCellData(ConstantValues.configSheetName, ConstantValues.row_BrowserValue,
				ConstantValues.col_BrowserValue);
//			String browser = prop.getProperty("Browser");

		if (browser.equalsIgnoreCase("INTERNET_EXPLORER")) {
			// start Internet explorer driver instance
			logs.info("INFO: Launching IE browser");
			// WebDriverManager.iedriver().setup();
			// driver = new InternetExplorerDriver();

		} else if (browser.equalsIgnoreCase("CHROME")) {
			System.setProperty("webdriver.chrome.silentOutput", "true");
			System.setProperty("webdriver.chrome.driver", ConstantValues.chromeExePath);
			ChromeOptions options = new ChromeOptions();
			options.addArguments("--disable-notifications");
			driver = new ChromeDriver();
			logs.info("INFO: Launching Chrome browser");

		} else {

			// start firefox driver instance
			System.setProperty("webdriver.gecko.driver", "gecodriverpath");
			driver = new FirefoxDriver();
			logs.info("INFO: Launching FireFox browser");
		}
		eventDriver = new EventFiringWebDriver(driver);
		driverListener = new WebEventListerner();
		eventDriver.register(driverListener);
		driver = eventDriver;
		driver.manage().timeouts().implicitlyWait(ConstantValues.ImplicitWaitTime, TimeUnit.SECONDS);
		driver.manage().deleteAllCookies();
		driver.manage().window().maximize();
		try {
			String url = ExcelOperations.getCellData(ConstantValues.configSheetName, ConstantValues.row_UrlValue,
					ConstantValues.col_UrlValue);
			// String url = prop.getProperty("url");
			if (!url.isEmpty()) {
				if (getCode(url) == 200) {
					driver.get(prop.getProperty("url"));
				} else {
					logs.error("Class Utilities | Method launchBrowser | Exception desc : ERROR: URL is invalid");
					throw new EmptyURLException("The url response is: " + getCode(url));

				}
			}

			else {
				logs.error("Class Utilities | Method launchBrowser | Exception desc : ERROR: URL is empty");
				throw new EmptyURLException("The url is empty");
			}

		} catch (Exception e) {
			logs.error("Class Utilities | Method launchBrowser | Exception desc :  ERROR: failed to navigate to url"
					+ e.getMessage());

		}
	}

	/*
	 * public void launchURL() { try { if (!prop.getProperty("url").isEmpty()) {
	 * driver.get(prop.getProperty("url")); }
	 * 
	 * else { logs.error("ERROR: URL is empty"); throw new
	 * EmptyURLException("The url is empty"); }
	 * 
	 * } catch (Exception e) { logs.error("ERROR: failed to navigate to url" +
	 * e.getMessage());
	 * 
	 * } }
	 */

	public String getTitle() {
		return driver.getTitle();
	}

	/**
	 * To take screenshot and log message in report
	 * 
	 * @param message: string , the message to be logged in report
	 * @return: String: the path of screenshot
	 * @throws Exception
	 */
	public static String takeScreenshot(String message) throws Exception {

		String screenshotName = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new java.util.Date());

		File sourcePath = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);

		File destinationPath = new File(
				ConstantValues.screenshotsFolder_path + File.separator + screenshotName + ".png");

		Files.copy(sourcePath, destinationPath);

		return destinationPath.toString();
	}

	public boolean isElementDisplayed(WebElement element) {
		return element.isDisplayed() ? true : false;
	}

	public void clickElement(WebElement ele, int timeoutSecs) {
		new WebDriverWait(driver, timeoutSecs).ignoring(ElementNotVisibleException.class)
				.until(ExpectedConditions.elementToBeClickable(ele));
		if (isElementDisplayed(ele)) {
			ele.click();
		} else
			throw new ElementNotVisibleException("Could not find element" + ele);
	}

	public void enterValue(WebElement ele, int timeoutSecs, String value) {
		new WebDriverWait(driver, timeoutSecs).ignoring(ElementNotVisibleException.class)
				.until(ExpectedConditions.elementToBeClickable(ele));
		if (isElementDisplayed(ele)) {
			ele.clear();
			ele.sendKeys(value);
		} else
			throw new ElementNotVisibleException("Could not find element" + ele);
	}

	/**
	 * Wait for page to load based on document.readyState=complete
	 */
	public void domLoaded() {
		logs.debug("checking that the DOM is loaded");
		final JavascriptExecutor js = (JavascriptExecutor) driver;
		Boolean domReady = js.executeScript("return document.readyState").equals("complete");

		if (!domReady) {
			new WebDriverWait(driver, ConstantValues.pageLoadTime).until(new ExpectedCondition<Boolean>() {
				public Boolean apply(WebDriver d) {
					return (js.executeScript("return document.readyState").equals("complete"));
				}
			});
		}
	}

	public static int getCode(String url) {
		int connectionCode = 0;
		try {

			URL link = new URL(url);

			HttpURLConnection httpConn = (HttpURLConnection) link.openConnection();

			httpConn.setConnectTimeout(2000);
			httpConn.connect();

			connectionCode = httpConn.getResponseCode();
		}

		catch (Exception e) {
			logs.error("Class Utilities | Method getCode | Exception desc :  Error: invalid link" + e.getMessage());
		}
		return connectionCode;
	}

	public static String getTestCaseName(String sTestCase) throws Exception {
		String value = sTestCase;
		try {
			int posi = value.indexOf("@");
			value = value.substring(0, posi);
			posi = value.lastIndexOf(".");
			value = value.substring(posi + 1);
			return value;
		} catch (Exception e) {
			logs.error("Class Utilities | Method getTestCaseName | Exception desc : " + e.getMessage());
			throw (e);
		}
	}

	public static String getRunMode(String testcaseName) {

		String runmode = null;

		int row = ExcelOperations.getRowNubWithTextInColumn(ConstantValues.TestCasesSheetName, testcaseName,
				ConstantValues.Col_TestCaseName);
		try {
			runmode = ExcelOperations.getCellData(ConstantValues.TestCasesSheetName, row, ConstantValues.Col_RunMode);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return runmode;
	}

	public static void closeBrowser() {
		driver.close();
	}

	public static void tearDown() {
		driver.quit();
	}
}
