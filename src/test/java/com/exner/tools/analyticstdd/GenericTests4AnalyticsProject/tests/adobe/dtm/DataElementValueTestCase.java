package com.exner.tools.analyticstdd.GenericTests4AnalyticsProject.tests.adobe.dtm;

import com.exner.tools.analyticstdd.GenericTests4AnalyticsProject.tests.WebDriverBasedTestCase;

public class DataElementValueTestCase extends WebDriverBasedTestCase {
	private final String _elementName;
	private final String _elementExpectedValue;

	public DataElementValueTestCase(String pageURL, String elementName, String elementExpectedValue) {
		super(pageURL);
		_elementName = elementName;
		_elementExpectedValue = elementExpectedValue;
		setName("DTM DE " + elementName + " value is " + _elementExpectedValue + " - " + pageURL);
	}

	@Override
	protected void runTest() throws Throwable {
		// get the value of the dl element from the page
		Object response = _jsExecutor.executeScript("return _satellite.getVar('" + _elementName + "');");

		// make sure the element exists
		if (null != response && String.class.isAssignableFrom(response.getClass())) {
			assertEquals("Data Element " + _elementName + " value should be " + _elementExpectedValue,
					_elementExpectedValue, (String) response);
		} else {
			fail("Data Element " + _elementName + " does not exist");
		}

	}
}
