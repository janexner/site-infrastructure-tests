package com.exner.tools.analyticstdd.GenericTests4AnalyticsProject.tests.adobe.core;

import com.exner.tools.analyticstdd.GenericTests4AnalyticsProject.Tools;
import com.exner.tools.analyticstdd.GenericTests4AnalyticsProject.tests.WebDriverBasedTestCase;

public class VisitorIDServiceVersionBelowTestCase extends WebDriverBasedTestCase {
	protected String _maxVersion;

	public VisitorIDServiceVersionBelowTestCase(String pageURL, String maxVersion) {
		super(pageURL);
		setName(Tools.MCVID + " version before " + maxVersion + " - " + pageURL);
		_maxVersion = maxVersion;
	}

	@Override
	protected void runTest() throws Throwable {
		// check whether DTM has been loaded on the page
		Object response = _jsExecutor.executeScript(
				"if (typeof Visitor !== 'undefined') { return Visitor.version } else { return 'unavailable' }");

		// make sure the element exists
		if (String.class.isAssignableFrom(response.getClass())) {
			boolean result = Tools.testVersionIsOlderThanBaseVersion((String) response, _maxVersion);
			assertTrue(Tools.MCVID + " version should be before " + _maxVersion, result);
		} else {
			fail(Tools.MCVID + " version not available");
		}

	}

}
