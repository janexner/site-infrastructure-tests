package com.exner.tools.analyticstdd.GenericTests4AnalyticsProject.tests.adobe;

import com.exner.tools.analyticstdd.GenericTests4AnalyticsProject.Tools;
import com.exner.tools.analyticstdd.GenericTests4AnalyticsProject.tests.WebDriverBasedTestCase;

public class VisitorIDServiceLoadedTestCase extends WebDriverBasedTestCase {
	
	public VisitorIDServiceLoadedTestCase(String pageURL, Object params) {
		super(pageURL);
		setName(Tools.MCVID + " loaded - " + pageURL);
	}

	@Override
	protected void runTest() throws Throwable {
		// check whether MCVID has been loaded on the page
		Object response = _jsExecutor
				.executeScript("if (typeof Visitor === 'function') { return true } else { return false }");

		// make sure the element exists
		if (Boolean.class.isAssignableFrom(response.getClass())) {
			assertTrue(Tools.MCVID + " must load", (Boolean) response);
		} else {
			fail(Tools.MCVID + " not loaded");
		}

	}

}
