package com.exner.tools.analyticstdd.GenericTests4AnalyticsProject;

public class PageLoadedOKTestCase extends WebDriverBasedTestCase {

	protected PageLoadedOKTestCase(String pageURL) {
		super("Page load successful");
	}

	@Override
	protected void runTest() throws Throwable {
		_jsExecutor.executeScript("");
	}
}
