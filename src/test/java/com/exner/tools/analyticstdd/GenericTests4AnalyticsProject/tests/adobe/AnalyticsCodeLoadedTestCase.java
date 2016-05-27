package com.exner.tools.analyticstdd.GenericTests4AnalyticsProject.tests.adobe;

import com.exner.tools.analyticstdd.GenericTests4AnalyticsProject.Tools;
import com.exner.tools.analyticstdd.GenericTests4AnalyticsProject.tests.WebDriverBasedTestCase;

public class AnalyticsCodeLoadedTestCase extends WebDriverBasedTestCase {

	public AnalyticsCodeLoadedTestCase(String pageURL, Object params) {
		super(pageURL);
		setName(Tools.AA + " JS code loaded on page - " + pageURL);
	}

	@Override
	protected void runTest() throws Throwable {
		// inject and run JS test
		Object response = _jsExecutor.executeScript(
				"if (typeof AppMeasurement == 'function' || typeof s_gi == 'function') { return true; } else { return false; }");

		// make sure the element exists
		if (Boolean.class.isAssignableFrom(response.getClass())) {
			assertTrue(Tools.AA + " JS code must be loaded", (Boolean) response);
		} else {
			fail(Tools.AA + " JS code has not been loaded");
		}
	}

}
