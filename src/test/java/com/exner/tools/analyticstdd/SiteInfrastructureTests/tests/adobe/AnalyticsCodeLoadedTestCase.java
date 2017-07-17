package com.exner.tools.analyticstdd.SiteInfrastructureTests.tests.adobe;

import java.util.List;

import com.exner.tools.analyticstdd.SiteInfrastructureTests.Tools;
import com.exner.tools.analyticstdd.SiteInfrastructureTests.tests.WebDriverBasedTestCase;

public class AnalyticsCodeLoadedTestCase extends WebDriverBasedTestCase {

	public AnalyticsCodeLoadedTestCase(String pageURL, List<String> blockPatterns, Object params) {
		super(pageURL, blockPatterns);
		setName(Tools.AA + " JS code loaded on page - " + pageURL);
	}

	@Override
	protected void runTest() throws Throwable {
		// inject and run JS test
		Object response = _page.executeJavaScript("(typeof AppMeasurement == 'function' || typeof s_gi == 'function')")
				.getJavaScriptResult();

		// make sure the element exists
		if (Boolean.class.isAssignableFrom(response.getClass())) {
			assertTrue(Tools.AA + " JS code must be loaded", (Boolean) response);
		} else {
			fail(Tools.AA + " JS code has not been loaded");
		}
	}

}
