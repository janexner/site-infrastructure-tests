package com.exner.tools.analyticstdd.GenericTests4AnalyticsProject.tests.dtm;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.exner.tools.analyticstdd.GenericTests4AnalyticsProject.tests.WebDriverBasedTestCase;

public class EventBasedRuleExistenceTestCase extends WebDriverBasedTestCase {
	private String _ruleName;

	public EventBasedRuleExistenceTestCase(String pageURL, String eventBasedRuleName) {
		super(pageURL);
		_ruleName = eventBasedRuleName;
		setName("EBR '" + eventBasedRuleName + "' existence");
	}

	@SuppressWarnings("unchecked")
	@Override
	protected void runTest() throws Throwable {
		// get the list of EBRs from the page
		List<Object> response = (List<Object>) _jsExecutor.executeScript("return _satellite.rules");

		// loop through the list until we find the rule we're looking for
		for (Iterator<Object> iterator = response.iterator(); iterator.hasNext();) {
			Object ebr = iterator.next();
			Map<String, Object> map = (Map<String, Object>) ebr;
			String ruleName = (String) map.get("name");
			if (ruleName.equals(_ruleName)) {
				// good, we found the rule! It's a pass!
				return;
			}
		}

		// didn't find the rule? Well...
		fail("Unable to find Event-based Rule: " + _ruleName);
	}

}
