package com.exner.tools.analyticstdd.GenericTests4AnalyticsProject.tests.adobe.dtm;

import com.exner.tools.analyticstdd.GenericTests4AnalyticsProject.Tools;
import com.exner.tools.analyticstdd.GenericTests4AnalyticsProject.tests.WebDriverBasedTestCase;

public class DataElementValueTestCase extends WebDriverBasedTestCase {
	private final String _elementName;
	private final String _elementExpectedValue;

	public DataElementValueTestCase(String pageURL, String elementName, String elementExpectedValue) {
		super(pageURL);
		_elementName = elementName;
		_elementExpectedValue = elementExpectedValue;
		setName(Tools.DTM + " DE " + elementName + " value is " + _elementExpectedValue + " - " + pageURL);
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
