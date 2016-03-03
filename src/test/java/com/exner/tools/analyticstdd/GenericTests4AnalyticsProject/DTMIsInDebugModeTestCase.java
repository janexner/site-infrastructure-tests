package com.exner.tools.analyticstdd.GenericTests4AnalyticsProject;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;

import junit.framework.TestCase;

public class DTMIsInDebugModeTestCase extends TestCase {
	private WebDriver _webDriver;
	private JavascriptExecutor _jsExecutor;

	public DTMIsInDebugModeTestCase(WebDriver webDriver) {
		super();
		_webDriver = webDriver;
		setName("DTM in debug mode");
		_jsExecutor = (JavascriptExecutor) _webDriver;
	}

	@Override
	protected void runTest() throws Throwable {
		// check whether DTM has been loaded on the page
		_jsExecutor.executeScript("localStorage.setItem('sdsat_debug', true);");
		String url = _webDriver.getCurrentUrl();
		_webDriver.get(url);
		Object response = _jsExecutor.executeScript("return localStorage.getItem('sdsat_debug');");

		// make sure the element exists
		if (Boolean.class.isAssignableFrom(response.getClass())) {
			assertTrue("DTM in debug mode ", (Boolean) response);
		} else if (String.class.isAssignableFrom(response.getClass())) {
			assertEquals("DTM in debug mode", "true", (String) response);
		} else {
			fail("DTM not in debug mode");
		}

	}

}
