package com.exner.tools.analyticstdd.SiteInfrastructureTests.tests.adobe;

import java.util.Iterator;
import java.util.List;

import com.exner.tools.analyticstdd.SiteInfrastructureTests.Tools;
import com.exner.tools.analyticstdd.SiteInfrastructureTests.tests.WebDriverBasedTestCase;
import com.fasterxml.jackson.databind.node.TextNode;

import net.sourceforge.htmlunit.corejs.javascript.NativeArray;

public class DTMRuleHasRunTestCase extends WebDriverBasedTestCase {
	private final String _ruleName;

	public DTMRuleHasRunTestCase(String pageURL, List<String> blockPatterns, Object params) {
		super(pageURL, blockPatterns);

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
		Object logEntries = _page.executeJavaScript("_satellite.Logger.getHistory()").getJavaScriptResult();
		if (NativeArray.class.isAssignableFrom(logEntries.getClass())) {
			for (Iterator<NativeArray> iter = ((NativeArray) logEntries).iterator(); iter.hasNext();) {
				NativeArray line = iter.next();
				String logMessage = line.get(1).toString();
				if (logMessage.startsWith("Rule ") && logMessage.endsWith("fired.")) {
					String ruleName = logMessage.replace("Rule \"", "").replace("\" fired.", "");
					if (ruleName.equals(_ruleName)) {
						// yay, it fired! It's a pass!
						return;
					}
				}
			}
		}

		// didn't find the rule? Well...
		fail(Tools.DTM + " Rule " + _ruleName + " doesn't fire");
	}

}
