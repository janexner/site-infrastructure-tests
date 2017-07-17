package com.exner.tools.analyticstdd.SiteInfrastructureTests.tests.others;

import java.util.List;

import com.exner.tools.analyticstdd.SiteInfrastructureTests.Tools;
import com.exner.tools.analyticstdd.SiteInfrastructureTests.tests.WebDriverBasedTestCase;

public class TealiumIQLoadedTestCase extends WebDriverBasedTestCase {

	public TealiumIQLoadedTestCase(String pageURL, List<String> blockPatterns) {
		super(pageURL, blockPatterns);
		setName(Tools.TEALIUM + " loaded - " + pageURL);
	}

	@Override
	protected void runTest() throws Throwable {
		// check whether tool has been loaded on the page
		Object response = _page.executeJavaScript("(typeof utag === 'object')").getJavaScriptResult();

		// make sure the element exists
		if (Boolean.class.isAssignableFrom(response.getClass())) {
			assertTrue(Tools.TEALIUM + " must load", (Boolean) response);
		} else {
			fail(Tools.TEALIUM + " not loaded");
		}

	}

}
