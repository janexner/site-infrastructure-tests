package com.exner.tools.analyticstdd.GenericTests4AnalyticsProject.tests;

import com.exner.tools.analyticstdd.GenericTests4AnalyticsProject.tests.WebDriverBasedTestCase;

public class JQueryLoadedTestCase extends WebDriverBasedTestCase {
	
	public JQueryLoadedTestCase(String pageURL) {
		super(pageURL);
		setName("jQuery loaded - " + pageURL);
	}

	@Override
	protected void runTest() throws Throwable {
		// check whether DTM has been loaded on the page
		Object response = _jsExecutor
				.executeScript("if (typeof jQuery !== 'undefined') { return true } else { return false }");

		// make sure the element exists
		if (Boolean.class.isAssignableFrom(response.getClass())) {
			assertTrue("jQuery must load", (Boolean) response);
		} else {
			fail("jQuery not loaded");
		}

	}

}
