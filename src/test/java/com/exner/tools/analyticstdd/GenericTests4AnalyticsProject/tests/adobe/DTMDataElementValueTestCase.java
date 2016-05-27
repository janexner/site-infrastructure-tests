package com.exner.tools.analyticstdd.GenericTests4AnalyticsProject.tests.adobe;

import org.json.simple.JSONObject;

import com.exner.tools.analyticstdd.GenericTests4AnalyticsProject.Tools;
import com.exner.tools.analyticstdd.GenericTests4AnalyticsProject.tests.WebDriverBasedTestCase;

public class DTMDataElementValueTestCase extends WebDriverBasedTestCase {
	private final String _elementName;
	private final String _elementExpectedValue;

	public DTMDataElementValueTestCase(String pageURL, Object params) {
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

		setName(Tools.DTM + " DE " + _elementName + " value is " + _elementExpectedValue + " - " + pageURL);
	}

	@Override
	protected void runTest() throws Throwable {
		// get the value of the dl element from the page
		Object response = _jsExecutor.executeScript("return _satellite.getVar('" + _elementName + "');");

		// make sure the element exists
		if (null != response && String.class.isAssignableFrom(response.getClass())) {
			assertEquals(Tools.DTM + " Data Element " + _elementName + " value should be " + _elementExpectedValue,
					_elementExpectedValue, (String) response);
		} else {
			fail(Tools.DTM + " Data Element " + _elementName + " does not exist");
		}

	}
}
