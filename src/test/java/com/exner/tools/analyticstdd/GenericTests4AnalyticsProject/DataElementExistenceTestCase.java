package com.exner.tools.analyticstdd.GenericTests4AnalyticsProject;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;

import junit.framework.TestCase;

public class DataElementExistenceTestCase extends TestCase {
	private String _elementName;
	private WebDriver _webDriver;
	private JavascriptExecutor _jsExecutor;

	public DataElementExistenceTestCase(WebDriver webDriver, String elementName) {
		super();
		_webDriver = webDriver;
		_elementName = elementName;
		setName(elementName + " existence");
		_jsExecutor = (JavascriptExecutor) _webDriver;
	}

	@Override
	protected void runTest() throws Throwable {
		// get the value of the dl element from the page
		Object response = _jsExecutor.executeScript(
				"if (typeof _satellite.getVar('" + _elementName + "') !== 'undefined') { return true } else { return false }");

		// make sure the element exists
		if (Boolean.class.isAssignableFrom(response.getClass())) {
			assertTrue("Data Element " + _elementName + " must exist", (Boolean) response);
		} else {
			fail("Data Element " + _elementName + " does not exist");
		}

	}

}
