package com.exner.tools.analyticstdd.SiteInfrastructureTests.tests;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.google.common.base.Predicate;

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
		_webDriver = new HtmlUnitDriver(true);
		try {
			_webDriver.get(_pageURL);
			_jsExecutor = (JavascriptExecutor) _webDriver;
			_jsExecutor.executeScript("localStorage.setItem('sdsat_debug', true);");
			_jsExecutor.executeScript("if (typeof _satellite !== 'undefined') { _satellite.setDebug(true); }");
			_webDriver.get(_pageURL);

			// Wait up to 10 seconds for jQuery to load
			WebDriverWait waiting = new WebDriverWait(_webDriver, 30);
			waiting.until(new Predicate<WebDriver>() {
				public boolean apply(WebDriver driver) {
					String testresult = (String) ((JavascriptExecutor) driver)
							.executeScript("return document.readyState");
					LOGGER.log(Level.FINE, "Page " + _pageURL + " - document.readyState: " + testresult);
					return testresult.equals("complete");
				}
			});
		} catch (Exception e) {
			LOGGER.log(Level.SEVERE, "Page load failed: " + e.getMessage());
			_webDriver.close();
			_webDriver = null;
		}

	}

	@Override
	protected void tearDown() throws Exception {
		if (null != _webDriver) {
			_webDriver.close();
			_webDriver = null;
		}
		super.tearDown();
	}

}