package com.exner.tools.analyticstdd.GenericTests4AnalyticsProject.tests;

import org.json.simple.JSONObject;

public class DataLayerElementValueTestCase extends WebDriverBasedTestCase {
	private final String _elementName;
	private final String _elementExpectedValue;

	public DataLayerElementValueTestCase(String pageURL, Object params) {
		super(pageURL);

		if (JSONObject.class.isAssignableFrom(params.getClass())) {
			_elementName = (String) ((JSONObject) params).get("name");
			_elementExpectedValue = (String) ((JSONObject) params).get("value");
		} else {
			_elementName = null;
			_elementExpectedValue = null;
		}
		if (null == _elementName) {
			throw new IllegalArgumentException("Must specify name and value of element");
		}
		if (null == _elementExpectedValue) {
			throw new IllegalArgumentException("Must specify value for element");
		}

		setName("Data Layer element " + _elementName + " value is " + _elementExpectedValue + " - " + pageURL);
	}

	@Override
	protected void runTest() throws Throwable {
		// TODO - maybe test for undefined first!
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
