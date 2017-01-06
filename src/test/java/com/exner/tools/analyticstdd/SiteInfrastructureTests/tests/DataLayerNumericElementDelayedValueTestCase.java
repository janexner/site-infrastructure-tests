package com.exner.tools.analyticstdd.SiteInfrastructureTests.tests;

import org.json.simple.JSONObject;
import org.openqa.selenium.WebDriverException;

public class DataLayerNumericElementDelayedValueTestCase extends WebDriverBasedTestCase {
	private final String _elementName;
	private final Long _elementExpectedValue;
	private long _delay;

	public DataLayerNumericElementDelayedValueTestCase(String pageURL, Object params) {
		super(pageURL);

		if (!JSONObject.class.isAssignableFrom(params.getClass())) {
			throw new IllegalArgumentException("Must specify name, value, and delay");
		}
		_elementName = (String) ((JSONObject) params).get("name");
		_elementExpectedValue = (Long) ((JSONObject) params).get("value");
		_delay = (Long) ((JSONObject) params).get("delay");

		setName("Data Layer element " + _elementName + " numeric value is " + _elementExpectedValue + " after " + _delay
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
			if (Long.class.isAssignableFrom(response.getClass())) {
				assertEquals("Data Layer element " + _elementName + " numeric value should be " + _elementExpectedValue
						+ "after " + _delay + "ms", _elementExpectedValue, (Long) response);
			} else {
				fail("Data Layer element " + _elementName + " does not exist");
			}
		} catch (WebDriverException we) {
			fail("Data Layer element " + _elementName + " does not exist");
		}
	}
}
