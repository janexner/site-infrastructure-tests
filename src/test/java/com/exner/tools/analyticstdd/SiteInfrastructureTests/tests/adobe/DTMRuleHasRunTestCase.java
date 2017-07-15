package com.exner.tools.analyticstdd.SiteInfrastructureTests.tests.adobe;

import java.util.ArrayList;
import java.util.Iterator;

import com.exner.tools.analyticstdd.SiteInfrastructureTests.Tools;
import com.exner.tools.analyticstdd.SiteInfrastructureTests.tests.WebDriverBasedTestCase;
import com.fasterxml.jackson.databind.node.TextNode;

public class DTMRuleHasRunTestCase extends WebDriverBasedTestCase {
	private final String _ruleName;

	public DTMRuleHasRunTestCase(String pageURL, Object params) {
		super(pageURL);

		if (!TextNode.class.isAssignableFrom(params.getClass())) {
			throw new IllegalArgumentException("Must specify an element");
		}
		_ruleName = ((TextNode) params).asText();

		setName(Tools.DTM + " Rule " + _ruleName + " fires - " + pageURL);
	}

	@SuppressWarnings("unchecked")
	@Override
	protected void runTest() throws Throwable {
		// get the list of Rules which fired on the page
		ArrayList<ArrayList<Object>> logEntries = (ArrayList<ArrayList<Object>>) _jsExecutor
				.executeScript("return _satellite.Logger.getHistory()");
		for (Iterator<ArrayList<Object>> iterator = logEntries.iterator(); iterator.hasNext();) {
			ArrayList<Object> arrayList = (ArrayList<Object>) iterator.next();
			String logMessage = arrayList.get(1).toString();
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
