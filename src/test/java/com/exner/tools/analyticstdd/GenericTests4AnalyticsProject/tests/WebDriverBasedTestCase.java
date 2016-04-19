package com.exner.tools.analyticstdd.GenericTests4AnalyticsProject.tests;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.exner.tools.analyticstdd.GenericTests4AnalyticsProject.AllTests;
import com.google.common.base.Predicate;

import junit.framework.TestCase;

public abstract class WebDriverBasedTestCase extends TestCase {

	protected final String _pageURL;
	protected WebDriver _webDriver;
	protected JavascriptExecutor _jsExecutor;

	protected WebDriverBasedTestCase(String pageURL) {
		super();
		_pageURL = pageURL;
	}
	
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		_webDriver = AllTests.getWebDriverPool().borrowObject();
		try {
			_webDriver.get(_pageURL);
			_jsExecutor = (JavascriptExecutor) _webDriver;
			_jsExecutor.executeScript("localStorage.setItem('sdsat_debug', true);");
			_jsExecutor.executeScript("if (typeof _satellite !== 'undefined') { _satellite.setDebug(true); }");
			_webDriver.get(_pageURL);

			// Wait up to 10 seconds for jQuery to load
			WebDriverWait waiting = new WebDriverWait(_webDriver, 10);
			waiting.until(new Predicate<WebDriver>() {
				public boolean apply(WebDriver driver) {
					String testresult = (String) ((JavascriptExecutor) driver).executeScript("return typeof jQuery");
					System.out.println("Page " + _pageURL + " - jQuery status: " + testresult);
					return testresult.equals("function");
				}
			});
		} catch (Exception e) {
			System.err.println(e.getMessage());
			AllTests.getWebDriverPool().returnObject(_webDriver);
			_webDriver = null;
		}

	}

	@Override
	protected void tearDown() throws Exception {
		if (null != _webDriver) {
			AllTests.getWebDriverPool().returnObject(_webDriver);
		}
		super.tearDown();
	}

}