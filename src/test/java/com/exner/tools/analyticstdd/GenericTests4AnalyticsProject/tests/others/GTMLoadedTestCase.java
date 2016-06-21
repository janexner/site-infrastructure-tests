package com.exner.tools.analyticstdd.GenericTests4AnalyticsProject.tests.others;

import com.exner.tools.analyticstdd.GenericTests4AnalyticsProject.Tools;
import com.exner.tools.analyticstdd.GenericTests4AnalyticsProject.tests.WebDriverBasedTestCase;

public class GTMLoadedTestCase extends WebDriverBasedTestCase {

	protected GTMLoadedTestCase(String pageURL) {
		super(pageURL);
		setName(Tools.GTM + " loaded - " + pageURL);
	}

	@Override
	protected void runTest() throws Throwable {
		// check whether tool has been loaded on the page
		Object response = _jsExecutor
				.executeScript("if (typeof google_tag_manager === 'object') { return true } else { return false }");

		// make sure the element exists
		if (Boolean.class.isAssignableFrom(response.getClass())) {
			assertTrue(Tools.GTM + " must load", (Boolean) response);
		} else {
			fail(Tools.GTM + " not loaded");
		}

	}

}
