package com.exner.tools.analyticstdd.GenericTests4AnalyticsProject.tests.adobe;

import java.util.ArrayList;
import java.util.Iterator;

import com.exner.tools.analyticstdd.GenericTests4AnalyticsProject.Tools;
import com.exner.tools.analyticstdd.GenericTests4AnalyticsProject.tests.WebDriverBasedTestCase;

public class DTMRuleHasRunTestCase extends WebDriverBasedTestCase {
	private final String _ruleName;

	public DTMRuleHasRunTestCase(String pageURL, Object params) {
		super(pageURL);

		if (!String.class.isAssignableFrom(params.getClass())) {
			throw new IllegalArgumentException("Must specify an element");
		}
		_ruleName = (String) params;

		setName(Tools.DTM + " Rule " + _ruleName + " fires - " + pageURL);
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
		fail(Tools.DTM + " Rule " + _ruleName + " doesn't fire");
	}

}
