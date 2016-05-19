package com.exner.tools.analyticstdd.GenericTests4AnalyticsProject.tests;

import com.exner.tools.analyticstdd.GenericTests4AnalyticsProject.Tools;
import com.exner.tools.analyticstdd.GenericTests4AnalyticsProject.tests.WebDriverBasedTestCase;

public class JQueryMinVersionTestCase extends WebDriverBasedTestCase {
	protected String _minVersion;

	public JQueryMinVersionTestCase(String pageURL, String minVersion) {
		super(pageURL);
		setName("jQuery min version " + minVersion + " - " + pageURL);
		_minVersion = minVersion;
	}

	@Override
	protected void runTest() throws Throwable {
		// check whether DTM has been loaded on the page
		Object response = _jsExecutor.executeScript(
				"if (typeof jQuery !== 'undefined') { return jQuery.fn.jquery } else { return 'unavailable' }");

		// make sure the element exists
		if (String.class.isAssignableFrom(response.getClass())) {
			boolean result = Tools.testVersionNotOlderThanBaseVersion((String) response, _minVersion);
			assertTrue("jQuery min version should be " + _minVersion, result);
		} else {
			fail("jQuery version not available");
		}

	}

}
