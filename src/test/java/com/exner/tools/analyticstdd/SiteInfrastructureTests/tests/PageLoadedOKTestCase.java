package com.exner.tools.analyticstdd.SiteInfrastructureTests.tests;

import java.util.List;

public class PageLoadedOKTestCase extends WebDriverBasedTestCase {

	public PageLoadedOKTestCase(String pageURL, List<String> blockPatterns, Object params) {
		super(pageURL, blockPatterns);
		setName("Page loads successful - " + pageURL);
	}

	@Override
	protected void runTest() throws Throwable {
		// TODO define test case
		_page.getReadyState();
		
		if ("ready".equalsIgnoreCase(_page.getReadyState())) {
			// good
		} else {
			fail("Page was not loaded successfully");
		}
	}
}
