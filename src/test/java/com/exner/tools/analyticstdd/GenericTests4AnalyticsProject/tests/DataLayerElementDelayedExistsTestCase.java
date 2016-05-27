package com.exner.tools.analyticstdd.GenericTests4AnalyticsProject.tests;

import org.json.simple.JSONObject;

public class DataLayerElementDelayedExistsTestCase extends WebDriverBasedTestCase {
	private final String _elementName;
	private final long _milliseconds;

	public DataLayerElementDelayedExistsTestCase(String pageURL, Object params) {
		super(pageURL);
		
		if (!JSONObject.class.isAssignableFrom(params.getClass())) {
			throw new IllegalArgumentException("Must specify name and delay");
		}
		_elementName = (String) ((JSONObject) params).get("name");
		_milliseconds = (Long) ((JSONObject) params).get("delay");

		setName("Data Layer element" + _elementName + " exists after " + _milliseconds + "ms - " + pageURL);
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
			assertTrue("Data Layer element " + _elementName + " must exist after " + _milliseconds + "ms",
					(Boolean) response);
		} else {
			fail("Data Layer element " + _elementName + " does not exist");
		}

	}

}
