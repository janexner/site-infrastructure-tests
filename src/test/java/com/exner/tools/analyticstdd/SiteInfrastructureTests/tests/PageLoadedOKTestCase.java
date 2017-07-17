package com.exner.tools.analyticstdd.SiteInfrastructureTests.tests;

public class PageLoadedOKTestCase extends WebDriverBasedTestCase {

	public PageLoadedOKTestCase(String pageURL, Object params) {
		super(pageURL);
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
