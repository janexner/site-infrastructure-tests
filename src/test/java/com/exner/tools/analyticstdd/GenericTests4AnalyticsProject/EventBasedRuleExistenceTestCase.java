package com.exner.tools.analyticstdd.GenericTests4AnalyticsProject;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;

import junit.framework.TestCase;

public class EventBasedRuleExistenceTestCase extends TestCase {
	private String _ruleName;
	private WebDriver _webDriver;
	private JavascriptExecutor _jsExecutor;

	public EventBasedRuleExistenceTestCase(WebDriver webDriver, String eventBasedRuleName) {
		super();
		_webDriver = webDriver;
		_ruleName = eventBasedRuleName;
		setName("EBR " + eventBasedRuleName + " existence");
		_jsExecutor = (JavascriptExecutor) _webDriver;
	}

	@SuppressWarnings("unchecked")
	@Override
	protected void runTest() throws Throwable {
		// get the list of DCRs from the page
		List<Object> response = (List<Object>) _jsExecutor.executeScript("return _satellite.rules");

		// loop through the list until we find the rule we're looking for
		for (Iterator<Object> iterator = response.iterator(); iterator.hasNext();) {
			Object ebr = iterator.next();
			Map<String, Object> map = (Map<String, Object>) ebr;
			String ruleName = (String) map.get("name");
			if (ruleName == _ruleName) {
				// good, we found the rule! It's a pass!
				return;
			}
		}

		// didn't find the rule? Well...
		fail("Unable to find Event-based Rule " + _ruleName);
	}

}
