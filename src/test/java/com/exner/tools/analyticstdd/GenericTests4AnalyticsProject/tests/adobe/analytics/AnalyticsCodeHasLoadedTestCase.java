package com.exner.tools.analyticstdd.GenericTests4AnalyticsProject.tests.adobe.analytics;

import com.exner.tools.analyticstdd.GenericTests4AnalyticsProject.tests.WebDriverBasedTestCase;

public class AnalyticsCodeHasLoadedTestCase extends WebDriverBasedTestCase {

	public AnalyticsCodeHasLoadedTestCase(String pageURL) {
		super(pageURL);
		setName("Adobe Analytics JS code loaded on page - " + pageURL);
	}

	@Override
	protected void runTest() throws Throwable {
		// inject and run JS test
		Object response = _jsExecutor.executeScript(
				"if (typeof AppMeasurement == 'function' || typeof s_gi == 'function') { return true; } else { return false; }");

		// make sure the element exists
		if (Boolean.class.isAssignableFrom(response.getClass())) {
			assertTrue("Adobe Analytics JS code must be loaded", (Boolean) response);
		} else {
			fail("Adobe Analytics JS code has not been loaded");
		}
	}

}
