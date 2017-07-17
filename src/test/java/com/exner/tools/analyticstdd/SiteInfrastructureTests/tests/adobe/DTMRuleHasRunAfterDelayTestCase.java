package com.exner.tools.analyticstdd.SiteInfrastructureTests.tests.adobe;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.exner.tools.analyticstdd.SiteInfrastructureTests.Tools;
import com.exner.tools.analyticstdd.SiteInfrastructureTests.tests.WebDriverBasedTestCase;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class DTMRuleHasRunAfterDelayTestCase extends WebDriverBasedTestCase {
	private final String _ruleName;
	private final long _delay;

	public DTMRuleHasRunAfterDelayTestCase(String pageURL, List<String> blockPatterns, Object params) {
		super(pageURL, blockPatterns);

		if (!ObjectNode.class.isAssignableFrom(params.getClass())) {
			throw new IllegalArgumentException("Must specify name and delay");
		}
		_ruleName = ((ObjectNode) params).get("name").asText();
		_delay = ((ObjectNode) params).get("delay").asLong();

		setName(Tools.DTM + " Rule " + _ruleName + " fires after " + _delay + " - " + pageURL);
	}

	@SuppressWarnings("unchecked")
	@Override
	protected void runTest() throws Throwable {
		// delay
		Thread.sleep(_delay);

		// get the list of Rules which fired on the page
		ArrayList<ArrayList<String>> logEntries = (ArrayList<ArrayList<String>>) _page
				.executeJavaScript("_satellite.Logger.getHistory()").getJavaScriptResult();
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
