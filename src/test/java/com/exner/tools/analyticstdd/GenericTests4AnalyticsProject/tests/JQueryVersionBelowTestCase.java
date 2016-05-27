package com.exner.tools.analyticstdd.GenericTests4AnalyticsProject.tests;

import com.exner.tools.analyticstdd.GenericTests4AnalyticsProject.Tools;

public class JQueryVersionBelowTestCase extends WebDriverBasedTestCase {
	private final String _maxVersion;

	public JQueryVersionBelowTestCase(String pageURL, Object params) {
		super(pageURL);

		if (String.class.isAssignableFrom(params.getClass())) {
			_maxVersion = (String) params;
		} else if (int.class.isAssignableFrom(params.getClass())) {
			_maxVersion = String.valueOf((Integer) params);
		} else {
			_maxVersion = "0";
			throw new IllegalArgumentException("Must define a max version!");
		}

		setName("jQuery version before " + _maxVersion + " - " + pageURL);
	}

	@Override
	protected void runTest() throws Throwable {
		// check whether DTM has been loaded on the page
		Object response = _jsExecutor.executeScript(
				"if (typeof jQuery !== 'undefined') { return jQuery.fn.jquery } else { return 'unavailable' }");

		// make sure the element exists
		if (String.class.isAssignableFrom(response.getClass())) {
			boolean result = Tools.testVersionIsOlderThanBaseVersion((String) response, _maxVersion);
			assertTrue("jQuery version should be before " + _maxVersion, result);
		} else {
			fail("jQuery version not available");
		}

	}

}
