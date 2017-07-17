package com.exner.tools.analyticstdd.SiteInfrastructureTests.tests.adobe;

import java.util.List;

import com.exner.tools.analyticstdd.SiteInfrastructureTests.Tools;
import com.exner.tools.analyticstdd.SiteInfrastructureTests.tests.WebDriverBasedTestCase;

public class TargetCodeLoadedTestCase extends WebDriverBasedTestCase {

	public TargetCodeLoadedTestCase(String pageURL, List<String> blockPatterns, Object params) {
		super(pageURL, blockPatterns);
		setName(Tools.AT + " mbox.js is loaded - " + pageURL);
	}

	@Override
	protected void runTest() throws Throwable {
		// check whether mbox.js has been loaded on the page
		Object response = _page.executeJavaScript("(typeof TNT == 'object')").getJavaScriptResult();

		// make sure the element exists
		if (Boolean.class.isAssignableFrom(response.getClass())) {
			assertTrue(Tools.AT + " mbox.js must exist", (Boolean) response);
		} else {
			fail(Tools.AT + " mbox.js has not been loaded");
		}

	}

}
