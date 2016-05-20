package com.exner.tools.analyticstdd.GenericTests4AnalyticsProject.tests.adobe.analytics;

import com.exner.tools.analyticstdd.GenericTests4AnalyticsProject.Tools;
import com.exner.tools.analyticstdd.GenericTests4AnalyticsProject.tests.WebDriverBasedTestCase;

public class AnalyticsCodeLibTypeTestCase extends WebDriverBasedTestCase {
	private String _libType;

	public AnalyticsCodeLibTypeTestCase(String pageURL, String libType) {
		super(pageURL);
		setName(Tools.AA + " code type " + libType + " - " + pageURL);
		_libType = libType;
	}

	@Override
	protected void runTest() throws Throwable {
		// inject and run JS test
		Object response = _jsExecutor.executeScript(
				"if (typeof AppMeasurement == 'function' ) { return 'AppMeasurement' } else if (typeof s_gi == 'function') { return 'legacy'; } else { return 'none'; }");

		// make sure the element exists
		if (String.class.isAssignableFrom(response.getClass())) {
			assertEquals(Tools.AA + " code type must be " + _libType, (String) response, _libType);
		} else {
			fail(Tools.AA + " code has not found");
		}
	}

}
