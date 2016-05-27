package com.exner.tools.analyticstdd.GenericTests4AnalyticsProject.tests.adobe;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.exner.tools.analyticstdd.GenericTests4AnalyticsProject.Tools;
import com.exner.tools.analyticstdd.GenericTests4AnalyticsProject.tests.WebDriverBasedTestCase;

public class DTMPageLoadRuleExistsTestCase extends WebDriverBasedTestCase {
	private String _ruleName;

	public DTMPageLoadRuleExistsTestCase(String pageURL, Object params) {
		super(pageURL);

		if (!String.class.isAssignableFrom(params.getClass())) {
			throw new IllegalArgumentException("Must specify an element");
		}
		_ruleName = (String) params;

		setName(Tools.DTM + " PLR " + _ruleName + " exists - " + pageURL);
	}

	@SuppressWarnings("unchecked")
	@Override
	protected void runTest() throws Throwable {
		// get the list of PLRs from the page
		List<Object> response = (List<Object>) _jsExecutor.executeScript("return _satellite.pageLoadRules");

		// loop through the list until we find the rule we're looking for
		for (Iterator<Object> iterator = response.iterator(); iterator.hasNext();) {
			Object plr = iterator.next();
			Map<String, Object> map = (Map<String, Object>) plr;
			String ruleName = (String) map.get("name");
			if (ruleName.equals(_ruleName)) {
				// good, we found the rule! It's a pass!
				return;
			}
		}

		// didn't find the rule? Well...
		fail(Tools.DTM + " PLR " + _ruleName + " doesn't exist");
	}
}
