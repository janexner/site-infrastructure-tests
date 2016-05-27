package com.exner.tools.analyticstdd.GenericTests4AnalyticsProject.tests.adobe;

import org.json.simple.JSONObject;

import com.exner.tools.analyticstdd.GenericTests4AnalyticsProject.Tools;
import com.exner.tools.analyticstdd.GenericTests4AnalyticsProject.tests.WebDriverBasedTestCase;

public class DTMDataElementDelayedValueTestCase extends WebDriverBasedTestCase {
	private final String _elementName;
	private final String _elementExpectedValue;
	private final long _delay;

	public DTMDataElementDelayedValueTestCase(String pageURL, Object params) {
		super(pageURL);

		if (!JSONObject.class.isAssignableFrom(params.getClass())) {
			throw new IllegalArgumentException("Must specify name, value, and delay");
		}
		_elementName = (String) ((JSONObject) params).get("name");
		_elementExpectedValue = (String) ((JSONObject) params).get("value");
		_delay = (Long) ((JSONObject) params).get("delay");

		setName(Tools.DTM + " DE " + _elementName + " value is " + _elementExpectedValue + " after " + _delay + "ms - "
				+ pageURL);
	}

	@Override
	protected void runTest() throws Throwable {
		// wait
		Thread.sleep(_delay);

		// get the value of the dl element from the page
		Object response = _jsExecutor.executeScript("return _satellite.getVar('" + _elementName + "');");

		// make sure the element exists
		if (null != response && String.class.isAssignableFrom(response.getClass())) {
			assertEquals(Tools.DTM + " Data Element " + _elementName + " value should be " + _elementExpectedValue
					+ " after " + _delay + "ms", _elementExpectedValue, (String) response);
		} else {
			fail(Tools.DTM + " Data Element " + _elementName + " does not exist");
		}

	}
}
