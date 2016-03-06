package com.exner.tools.analyticstdd.GenericTests4AnalyticsProject;

public class DTMLoadedTestCase extends WebDriverBasedTestCase {
	public DTMLoadedTestCase(String pageURL) {
		super(pageURL);
		setName("DTM loaded");
	}

	@Override
	protected void runTest() throws Throwable {
		// check whether DTM has been loaded on the page
		Object response = _jsExecutor
				.executeScript("if (typeof _satellite !== 'undefined') { return true } else { return false }");

		// make sure the element exists
		if (Boolean.class.isAssignableFrom(response.getClass())) {
			assertTrue("DTM must exist", (Boolean) response);
		} else {
			fail("DTM not loaded");
		}

	}

}
