package com.exner.tools.analyticstdd.GenericTests4AnalyticsProject.tests.adobe.dtm;

import com.exner.tools.analyticstdd.GenericTests4AnalyticsProject.tests.WebDriverBasedTestCase;

public class PageLoadedOKTestCase extends WebDriverBasedTestCase {

	protected PageLoadedOKTestCase(String pageURL) {
		super("Page load successful - " + pageURL);
	}

	@Override
	protected void runTest() throws Throwable {
		_jsExecutor.executeScript("");
	}
}
