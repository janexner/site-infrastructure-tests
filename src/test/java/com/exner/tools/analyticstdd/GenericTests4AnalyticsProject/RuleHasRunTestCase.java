package com.exner.tools.analyticstdd.GenericTests4AnalyticsProject;

import java.util.ArrayList;
import java.util.Iterator;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;

import junit.framework.TestCase;

public class RuleHasRunTestCase extends TestCase {
	private String _ruleName;
	private WebDriver _webDriver;
	private JavascriptExecutor _jsExecutor;

	public RuleHasRunTestCase(WebDriver webDriver, String pageLoadRuleName) {
		super();
		_webDriver = webDriver;
		_ruleName = pageLoadRuleName;
		setName("Rule " + pageLoadRuleName + " execution");
		_jsExecutor = (JavascriptExecutor) _webDriver;
	}

	@SuppressWarnings("unchecked")
	@Override
	protected void runTest() throws Throwable {
		// get the list of Rules which fired on the page
		ArrayList<ArrayList<String>> logEntries = (ArrayList<ArrayList<String>>) _jsExecutor
				.executeScript("return _satellite.Logger.getHistory()");
		for (Iterator<ArrayList<String>> iterator = logEntries.iterator(); iterator.hasNext();) {
			ArrayList<String> arrayList = (ArrayList<String>) iterator.next();
			String logMessage = arrayList.get(1);
			if (logMessage.startsWith("Rule ") && logMessage.endsWith("fired.")) {
				String ruleName = logMessage.replace("Rule \"", "").replace("\" fired.", "");
				if (ruleName.equals(_ruleName)) {
					// yay, it fired! It's a pass!
					return;
				}
			}
		}

		// didn't find the rule? Well...
		fail("Rule " + _ruleName + " didn't fire here");
	}

}
