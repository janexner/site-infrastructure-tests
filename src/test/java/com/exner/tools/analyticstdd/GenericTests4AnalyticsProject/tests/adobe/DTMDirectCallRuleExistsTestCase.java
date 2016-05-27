package com.exner.tools.analyticstdd.GenericTests4AnalyticsProject.tests.adobe;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.exner.tools.analyticstdd.GenericTests4AnalyticsProject.Tools;
import com.exner.tools.analyticstdd.GenericTests4AnalyticsProject.tests.WebDriverBasedTestCase;

public class DTMDirectCallRuleExistsTestCase extends WebDriverBasedTestCase {
	private final String _ruleName;

	public DTMDirectCallRuleExistsTestCase(String pageURL, Object params) {
		super(pageURL);

		if (!String.class.isAssignableFrom(params.getClass())) {
			throw new IllegalArgumentException("Must specify an element");
		}
		_ruleName = (String) params;

		setName(Tools.DTM + " DCR " + _ruleName + " exists - " + pageURL);
	}

	@SuppressWarnings("unchecked")
	@Override
	protected void runTest() throws Throwable {
		// get the list of DCRs from the page
		List<Object> response = (List<Object>) _jsExecutor.executeScript("return _satellite.directCallRules");

		// loop through the list until we find the rule we're looking for
		for (Iterator<Object> iterator = response.iterator(); iterator.hasNext();) {
			Object dcr = iterator.next();
			Map<String, Object> map = (Map<String, Object>) dcr;
			String ruleName = (String) map.get("name");
			if (ruleName == _ruleName) {
				// good, we found the rule! It's a pass!
				return;
			}
		}

		// didn't find the rule? Well...
		fail(Tools.DTM + " DCR " + _ruleName + "doesn't exist");
	}
}
