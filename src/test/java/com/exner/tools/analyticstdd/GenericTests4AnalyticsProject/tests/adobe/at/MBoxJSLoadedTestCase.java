package com.exner.tools.analyticstdd.GenericTests4AnalyticsProject.tests.adobe.at;

import com.exner.tools.analyticstdd.GenericTests4AnalyticsProject.tests.WebDriverBasedTestCase;

public class MBoxJSLoadedTestCase extends WebDriverBasedTestCase {

	public MBoxJSLoadedTestCase(String pageURL) {
		super(pageURL);
		setName("Adobe Target mbox.js is loaded - " + pageURL);
	}

	@Override
	protected void runTest() throws Throwable {
		// check whether mbox.js has been loaded on the page
		Object response = _jsExecutor
				.executeScript("if (typeof TNT == 'object') { return true; } else { return false; }");

		// make sure the element exists
		if (Boolean.class.isAssignableFrom(response.getClass())) {
			assertTrue("Adobe Target mbox.js must exist", (Boolean) response);
		} else {
			fail("Adobe Target mbox.js has not been loaded");
		}

	}

}
