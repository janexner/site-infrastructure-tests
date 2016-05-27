package com.exner.tools.analyticstdd.GenericTests4AnalyticsProject.tests.adobe;

import com.exner.tools.analyticstdd.GenericTests4AnalyticsProject.Tools;
import com.exner.tools.analyticstdd.GenericTests4AnalyticsProject.tests.WebDriverBasedTestCase;

public class GlobalMBoxExistsTestCase extends WebDriverBasedTestCase {
	private final String _mboxName;

	public GlobalMBoxExistsTestCase(String pageURL, Object params) {
		super(pageURL);

		if (String.class.isAssignableFrom(params.getClass())) {
			_mboxName = (String) params;
		} else {
			_mboxName = "";
			throw new IllegalArgumentException("Must specify an element");
		}

		setName(Tools.AT + " global mbox " + _mboxName + " exists - " + pageURL);
	}

	@Override
	protected void runTest() throws Throwable {
		// get the value of the dl element from the page
		Object response = _jsExecutor
				.executeScript("if (typeof mboxCurrent != 'undefined' && mboxCurrent.getName() == '" + _mboxName
						+ "') { return true; } else { return false; }");

		// make sure the element exists
		if (Boolean.class.isAssignableFrom(response.getClass())) {
			assertTrue(Tools.AT + " global mbox " + _mboxName + " must exist", (Boolean) response);
		} else {
			fail(Tools.AT + " global mbox " + _mboxName + " does not exist");
		}
	}

}
