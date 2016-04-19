package com.exner.tools.analyticstdd.GenericTests4AnalyticsProject.tests.adobe;

import com.exner.tools.analyticstdd.GenericTests4AnalyticsProject.tests.WebDriverBasedTestCase;

public class AnalyticsCodeLoadedTestCase extends WebDriverBasedTestCase {

	public AnalyticsCodeLoadedTestCase(String pageURL) {
		super(pageURL);
		setName("Adobe Analytics code loaded - " + pageURL);
	}

	@Override
	protected void runTest() throws Throwable {
		// check whether Analytics code has been loaded on the page
		Object response = _jsExecutor
				.executeScript("if (typeof window['s_c_il'] == 'object') { return true } else { return false }");

		// make sure the element exists
		if (Boolean.class.isAssignableFrom(response.getClass())) {
			assertTrue("Adobe Analytics code must exist", (Boolean) response);
		} else {
			fail("Adobe Analytics code not loaded");
		}

	}

}
