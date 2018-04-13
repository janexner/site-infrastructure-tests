package com.exner.tools.analyticstdd.SiteInfrastructureTests.tests;

import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import com.exner.tools.analyticstdd.SiteInfrastructureTests.AllTests;

import junit.framework.TestCase;

public abstract class WebDriverBasedTestCase extends TestCase {
	private final static Logger LOGGER = Logger.getLogger(WebDriverBasedTestCase.class.getName());

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
		LOGGER.log(Level.FINE, "Setting up test for " + _pageURL);
		ChromeOptions chromeOptions = new ChromeOptions();
		chromeOptions.addArguments("--headless");
		_webDriver = new RemoteWebDriver(AllTests.getChromeDriverService().getUrl(), chromeOptions);
		try {
			_webDriver.get(_pageURL);
			_jsExecutor = (JavascriptExecutor) _webDriver;
			_webDriver.manage().timeouts().pageLoadTimeout(10, TimeUnit.SECONDS);
		} catch (Exception e) {
			LOGGER.log(Level.SEVERE, "Page load took too long: " + e.getMessage());
			_webDriver.quit();
			_webDriver = null;
		}

	}

	@Override
	protected void tearDown() throws Exception {
		LOGGER.log(Level.FINE, "Tearing down test for " + _pageURL);
		if (null != _webDriver) {
			_webDriver.quit();
		}
		super.tearDown();
	}

}