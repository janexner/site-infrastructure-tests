package com.exner.tools.analyticstdd.GenericTests4AnalyticsProject.tests.adobe.target;

import com.exner.tools.analyticstdd.GenericTests4AnalyticsProject.Tools;
import com.exner.tools.analyticstdd.GenericTests4AnalyticsProject.tests.WebDriverBasedTestCase;

public class MBoxJSLoadedTestCase extends WebDriverBasedTestCase {

	public MBoxJSLoadedTestCase(String pageURL) {
		super(pageURL);
		setName(Tools.AT + " mbox.js is loaded - " + pageURL);
	}

	@Override
	protected void runTest() throws Throwable {
		// check whether mbox.js has been loaded on the page
		Object response = _jsExecutor
				.executeScript("if (typeof TNT == 'object') { return true; } else { return false; }");

		// make sure the element exists
		if (Boolean.class.isAssignableFrom(response.getClass())) {
			assertTrue(Tools.AT + " mbox.js must exist", (Boolean) response);
		} else {
			fail(Tools.AT + " mbox.js has not been loaded");
		}

	}

}
