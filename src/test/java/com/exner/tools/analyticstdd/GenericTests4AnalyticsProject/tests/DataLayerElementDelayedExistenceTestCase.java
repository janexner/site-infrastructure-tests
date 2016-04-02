package com.exner.tools.analyticstdd.GenericTests4AnalyticsProject.tests;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriver;

import junit.framework.TestCase;

public class DataLayerElementDelayedExistenceTestCase extends WebDriverBasedTestCase {
	private String _elementName;
	private long _milliseconds;

	public DataLayerElementDelayedExistenceTestCase(String pageURL, String elementName, long delay) {
		super(pageURL);
		_elementName = elementName;
		_milliseconds = delay;
		setName(elementName + " delayed existence " + delay + "ms - " + pageURL);
	}

	@Override
	protected void runTest() throws Throwable {
		// wait
		Thread.sleep(_milliseconds);
		
		// get the value of the dl element from the page
		Object response = _jsExecutor.executeScript(
				"if (typeof " + _elementName + " !== 'undefined') { return true } else { return false }");

		// make sure the element exists
		if (Boolean.class.isAssignableFrom(response.getClass())) {
			assertTrue("Data Layer element " + _elementName + " must exist", (Boolean) response);
		} else {
			fail("Data Layer element " + _elementName + " does not exist");
		}

	}

}
