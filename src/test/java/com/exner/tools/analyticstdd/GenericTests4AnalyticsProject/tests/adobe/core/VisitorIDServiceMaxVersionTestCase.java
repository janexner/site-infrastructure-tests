package com.exner.tools.analyticstdd.GenericTests4AnalyticsProject.tests.adobe.core;

import com.exner.tools.analyticstdd.GenericTests4AnalyticsProject.Tools;
import com.exner.tools.analyticstdd.GenericTests4AnalyticsProject.tests.WebDriverBasedTestCase;

public class VisitorIDServiceMaxVersionTestCase extends WebDriverBasedTestCase {
	protected String _maxVersion;

	public VisitorIDServiceMaxVersionTestCase(String pageURL, String maxVersion) {
		super(pageURL);
		setName("Visitor ID Service max version " + maxVersion + " - " + pageURL);
		_maxVersion = maxVersion;
	}

	@Override
	protected void runTest() throws Throwable {
		// check whether DTM has been loaded on the page
		Object response = _jsExecutor.executeScript(
				"if (typeof Visitor !== 'undefined') { return Visitor.version } else { return 'unavailable' }");

		// make sure the element exists
		if (String.class.isAssignableFrom(response.getClass())) {
			boolean result = Tools.testVersionNotNewerThanBaseVersion((String) response, _maxVersion);
			assertTrue("Visitor ID Service max version should be " + _maxVersion, result);
		} else {
			fail("Visitor ID Service version not available");
		}

	}

}
