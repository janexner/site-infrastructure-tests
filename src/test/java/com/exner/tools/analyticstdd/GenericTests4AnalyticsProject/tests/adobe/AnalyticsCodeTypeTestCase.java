package com.exner.tools.analyticstdd.GenericTests4AnalyticsProject.tests.adobe;

import com.exner.tools.analyticstdd.GenericTests4AnalyticsProject.Tools;
import com.exner.tools.analyticstdd.GenericTests4AnalyticsProject.tests.WebDriverBasedTestCase;

public class AnalyticsCodeTypeTestCase extends WebDriverBasedTestCase {
	private final String _libType;

	public AnalyticsCodeTypeTestCase(String pageURL, Object params) {
		super(pageURL);

		if (!String.class.isAssignableFrom(params.getClass())) {
			throw new IllegalArgumentException("Must provide lib type");
		}

		_libType = (String) params;

		setName(Tools.AA + " code type " + _libType + " - " + pageURL);
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
			fail(Tools.AA + " code has not been found");
		}
	}

}
