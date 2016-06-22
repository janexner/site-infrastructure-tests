package com.exner.tools.analyticstdd.SiteInfrastructureTests.tests.others;

import com.exner.tools.analyticstdd.SiteInfrastructureTests.Tools;
import com.exner.tools.analyticstdd.SiteInfrastructureTests.tests.WebDriverBasedTestCase;

public class TealiumIQLoadedTestCase extends WebDriverBasedTestCase {
	
	public TealiumIQLoadedTestCase(String pageURL) {
		super(pageURL);
		setName(Tools.TEALIUM + " loaded - " + pageURL);
	}

	@Override
	protected void runTest() throws Throwable {
		// check whether tool has been loaded on the page
		Object response = _jsExecutor
				.executeScript("if (typeof utag === 'object') { return true } else { return false }");

		// make sure the element exists
		if (Boolean.class.isAssignableFrom(response.getClass())) {
			assertTrue(Tools.TEALIUM + " must load", (Boolean) response);
		} else {
			fail(Tools.TEALIUM + " not loaded");
		}

	}

}
