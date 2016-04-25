package com.exner.tools.analyticstdd.GenericTests4AnalyticsProject.tests.dtm;

import com.exner.tools.analyticstdd.GenericTests4AnalyticsProject.tests.WebDriverBasedTestCase;

public class DataElementDelayedValueTestCase extends WebDriverBasedTestCase {
	private final String _elementName;
	private final String _elementExpectedValue;
	private final long _delay;

	public DataElementDelayedValueTestCase(String pageURL, String elementName, String elementExpectedValue,
			long delay) {
		super(pageURL);
		_elementName = elementName;
		_elementExpectedValue = elementExpectedValue;
		_delay = delay;
		setName("DE " + elementName + " delayed value - " + pageURL);
	}

	@Override
	protected void runTest() throws Throwable {
		// wait
		Thread.sleep(_delay);

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
