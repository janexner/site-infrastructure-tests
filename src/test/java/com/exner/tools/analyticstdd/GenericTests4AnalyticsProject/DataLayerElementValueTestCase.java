package com.exner.tools.analyticstdd.GenericTests4AnalyticsProject;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;

import junit.framework.TestCase;

public class DataLayerElementValueTestCase extends TestCase {
	private String _elementName;
	private String _elementExpectedValue;
	private WebDriver _webDriver;
	private JavascriptExecutor _jsExecutor;

	public DataLayerElementValueTestCase(WebDriver webDriver, String elementName, String elementExpectedValue) {
		super();
		_webDriver = webDriver;
		_elementName = elementName;
		_elementExpectedValue = elementExpectedValue;
		setName(elementName + " value");
		_jsExecutor = (JavascriptExecutor) _webDriver;
	}

	@Override
	protected void runTest() throws Throwable {
		// get the value of the dl element from the page
		Object response = _jsExecutor.executeScript("return " + _elementName);

		// make sure the element exists
		if (String.class.isAssignableFrom(response.getClass())) {
			assertEquals("Data Layer element " + _elementName + " value should be " + _elementExpectedValue,
					_elementExpectedValue, (String) response);
		} else {
			fail("Data Layer element " + _elementName + " does not exist");
		}

	}

}
