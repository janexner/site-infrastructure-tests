package com.exner.tools.analyticstdd.SiteInfrastructureTests.tests.others;

import java.util.List;

import com.exner.tools.analyticstdd.SiteInfrastructureTests.Tools;
import com.exner.tools.analyticstdd.SiteInfrastructureTests.tests.WebDriverBasedTestCase;

public class GTMLoadedTestCase extends WebDriverBasedTestCase {

	protected GTMLoadedTestCase(String pageURL, List<String> blockPattern) {
		super(pageURL, blockPattern);
		setName(Tools.GTM + " loaded - " + pageURL);
	}

	@Override
	protected void runTest() throws Throwable {
		// check whether tool has been loaded on the page
		Object response = _page.executeJavaScript("(typeof google_tag_manager === 'object')").getJavaScriptResult();

		// make sure the element exists
		if (Boolean.class.isAssignableFrom(response.getClass())) {
			assertTrue(Tools.GTM + " must load", (Boolean) response);
		} else {
			fail(Tools.GTM + " not loaded");
		}

	}

}
