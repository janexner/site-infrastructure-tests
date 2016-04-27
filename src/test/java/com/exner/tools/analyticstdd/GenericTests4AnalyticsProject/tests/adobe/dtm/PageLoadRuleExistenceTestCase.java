package com.exner.tools.analyticstdd.GenericTests4AnalyticsProject.tests.adobe.dtm;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.exner.tools.analyticstdd.GenericTests4AnalyticsProject.tests.WebDriverBasedTestCase;

public class PageLoadRuleExistenceTestCase extends WebDriverBasedTestCase {
	private String _ruleName;

	public PageLoadRuleExistenceTestCase(String pageURL, String pageLoadRuleName) {
		super(pageURL);
		_ruleName = pageLoadRuleName;
		setName("DTM PLR " + pageLoadRuleName + " exists - " + pageURL);
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
		fail("DTM PLR " + _ruleName + " doesn't exist");
	}
}
