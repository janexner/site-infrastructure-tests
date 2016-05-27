package com.exner.tools.analyticstdd.GenericTests4AnalyticsProject.tests.adobe;

import com.exner.tools.analyticstdd.GenericTests4AnalyticsProject.Tools;
import com.exner.tools.analyticstdd.GenericTests4AnalyticsProject.tests.WebDriverBasedTestCase;

public class DTMIsInDebugModeTestCase extends WebDriverBasedTestCase {

	private final boolean _test;

	public DTMIsInDebugModeTestCase(String pageURL, Object params) {
		super(pageURL);

		String note = "";
		if (Boolean.class.isAssignableFrom(params.getClass())) {
			_test = (Boolean) params;
			if (!_test) {
				note = " (inactive)";
			}
		} else {
			_test = true;
		}

		setName(Tools.DTM + " in debug mode " + note + " - " + pageURL);
	}

	@Override
	protected void runTest() throws Throwable {
		if (_test) {
			Object response = _jsExecutor.executeScript("return localStorage.getItem('sdsat_debug');");

			// make sure the element exists
			if (null == response) {
				fail(Tools.DTM + " is not in debug mode");
			}
			if (Boolean.class.isAssignableFrom(response.getClass())) {
				assertTrue(Tools.DTM + " in debug mode ", (Boolean) response);
			} else if (String.class.isAssignableFrom(response.getClass())) {
				assertEquals(Tools.DTM + " in debug mode", "true", (String) response);
			} else {
				fail(Tools.DTM + " not in debug mode");
			}
		}
	}
}
