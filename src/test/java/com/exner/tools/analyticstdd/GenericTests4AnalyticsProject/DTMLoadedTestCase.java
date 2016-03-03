package com.exner.tools.analyticstdd.GenericTests4AnalyticsProject;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;

import junit.framework.TestCase;

public class DTMLoadedTestCase extends TestCase {
	private WebDriver _webDriver;
	private JavascriptExecutor _jsExecutor;

	public DTMLoadedTestCase(WebDriver webDriver) {
		super();
		_webDriver = webDriver;
		setName("DTM loaded");
		_jsExecutor = (JavascriptExecutor) _webDriver;
	}

	@Override
	protected void runTest() throws Throwable {
		// check whether DTM has been loaded on the page
		Object response = _jsExecutor.executeScript(
				"if (typeof _satellite !== 'undefined') { return true } else { return false }");

		// make sure the element exists
		if (Boolean.class.isAssignableFrom(response.getClass())) {
			assertTrue("DTM must exist", (Boolean) response);
		} else {
			fail("DTM not loaded");
		}

	}

}
