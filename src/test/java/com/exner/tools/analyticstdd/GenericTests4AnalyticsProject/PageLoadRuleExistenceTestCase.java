package com.exner.tools.analyticstdd.GenericTests4AnalyticsProject;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;

import junit.framework.TestCase;

public class PageLoadRuleExistenceTestCase extends TestCase {
	private String _ruleName;
	private WebDriver _webDriver;
	private JavascriptExecutor _jsExecutor;

	public PageLoadRuleExistenceTestCase(WebDriver webDriver, String pageLoadRuleName) {
		super();
		_webDriver = webDriver;
		_ruleName = pageLoadRuleName;
		setName("PLR " + pageLoadRuleName + " existence");
		_jsExecutor = (JavascriptExecutor) _webDriver;
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
		fail("Unable to find Page Load Rule " + _ruleName);
	}

}
