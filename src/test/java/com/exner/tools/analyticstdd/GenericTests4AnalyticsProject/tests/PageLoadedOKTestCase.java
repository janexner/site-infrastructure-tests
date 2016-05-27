package com.exner.tools.analyticstdd.GenericTests4AnalyticsProject.tests;

public class PageLoadedOKTestCase extends WebDriverBasedTestCase {

	public PageLoadedOKTestCase(String pageURL, Object params) {
		super(pageURL);
		setName("Page loads successful - " + pageURL);
	}

	@Override
	protected void runTest() throws Throwable {
		// TODO define test case
		_jsExecutor.executeScript("");
	}
}
