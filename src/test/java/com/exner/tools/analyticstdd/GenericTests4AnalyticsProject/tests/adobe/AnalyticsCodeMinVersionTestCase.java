package com.exner.tools.analyticstdd.GenericTests4AnalyticsProject.tests.adobe;

import com.exner.tools.analyticstdd.GenericTests4AnalyticsProject.Tools;
import com.exner.tools.analyticstdd.GenericTests4AnalyticsProject.tests.WebDriverBasedTestCase;

public class AnalyticsCodeMinVersionTestCase extends WebDriverBasedTestCase {
	protected final String _minVersion;

	public AnalyticsCodeMinVersionTestCase(String pageURL, Object params) {
		super(pageURL);

		if (String.class.isAssignableFrom(params.getClass())) {
			_minVersion = (String) params;
		} else if (int.class.isAssignableFrom(params.getClass())) {
			_minVersion = String.valueOf((Integer) params);
		} else {
			_minVersion = "0";
			throw new IllegalArgumentException("Must define a min version!");
		}

		setName(Tools.AA + " code min version " + _minVersion + " - " + pageURL);
	}

	@Override
	protected void runTest() throws Throwable {
		// check whether DTM has been loaded on the page
		Object response = _jsExecutor.executeScript(
				"if (typeof AppMeasurement !== 'undefined') { return (new AppMeasurement()).version } else if (typeof s_gi !== 'undefined') { return s.version } else { return 'unavailable' }");

		// make sure the element exists
		if (String.class.isAssignableFrom(response.getClass())) {
			boolean result = Tools.testVersionNotOlderThanBaseVersion((String) response, _minVersion);
			assertTrue(Tools.AA + " code min version should be " + _minVersion, result);
		} else {
			fail(Tools.AA + " code version not available");
		}

	}

}
