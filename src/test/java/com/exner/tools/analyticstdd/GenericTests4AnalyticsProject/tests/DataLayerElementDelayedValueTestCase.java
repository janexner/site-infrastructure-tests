package com.exner.tools.analyticstdd.GenericTests4AnalyticsProject.tests;

import org.json.simple.JSONObject;
import org.openqa.selenium.WebDriverException;

public class DataLayerElementDelayedValueTestCase extends WebDriverBasedTestCase {
	private final String _elementName;
	private final String _elementExpectedValue;
	private long _delay;

	public DataLayerElementDelayedValueTestCase(String pageURL, Object params) {
		super(pageURL);

		if (!JSONObject.class.isAssignableFrom(params.getClass())) {
			throw new IllegalArgumentException("Must specify name, value, and delay");
		}
		_elementName = (String) ((JSONObject) params).get("name");
		_elementExpectedValue = (String) ((JSONObject) params).get("value");
		_delay = (Long) ((JSONObject) params).get("delay");

		setName("Data Layer element " + _elementName + " value is " + _elementExpectedValue + " after " + _delay
				+ "ms - " + pageURL);
	}

	@Override
	protected void runTest() throws Throwable {
		try {
			// sleep
			Thread.sleep(_delay);

			// get the value of the dl element from the page
			Object response = _jsExecutor.executeScript("return " + _elementName);

			// make sure the element exists
			if (String.class.isAssignableFrom(response.getClass())) {
				assertEquals("Data Layer element " + _elementName + " value should be " + _elementExpectedValue
						+ "after " + _delay + "ms", _elementExpectedValue, (String) response);
			} else {
				fail("Data Layer element " + _elementName + " does not exist");
			}
		} catch (WebDriverException we) {
			fail("Data Layer element " + _elementName + " does not exist");
		}
	}
}
