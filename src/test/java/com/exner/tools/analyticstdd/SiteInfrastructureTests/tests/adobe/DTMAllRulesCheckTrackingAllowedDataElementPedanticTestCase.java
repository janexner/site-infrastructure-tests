package com.exner.tools.analyticstdd.SiteInfrastructureTests.tests.adobe;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.exner.tools.analyticstdd.SiteInfrastructureTests.Tools;
import com.exner.tools.analyticstdd.SiteInfrastructureTests.tests.WebDriverBasedTestCase;
import com.fasterxml.jackson.databind.node.TextNode;

public class DTMAllRulesCheckTrackingAllowedDataElementPedanticTestCase extends WebDriverBasedTestCase {
	private final String _dataElementName;

	public DTMAllRulesCheckTrackingAllowedDataElementPedanticTestCase(String pageURL, List<String> blockPatterns, Object params) {
		super(pageURL, blockPatterns);

		if (!TextNode.class.isAssignableFrom(params.getClass())) {
			throw new IllegalArgumentException("Must specify an element");
		}
		_dataElementName = ((TextNode) params).asText();

		setName(Tools.DTM + " All Rules check DE '" + _dataElementName + "' (pedantic) - " + pageURL);
	}

	@SuppressWarnings("unchecked")
	@Override
	protected void runTest() throws Throwable {
		String rulesThatDoNotCheckTheDE = "";

		// get the list of EBRs from the page
		List<Object> responseEBRs = (List<Object>) _page.executeJavaScript("_satellite.rules").getJavaScriptResult();
		List<Object> responsePLRs = (List<Object>) _page.executeJavaScript("_satellite.pageLoadRules")
				.getJavaScriptResult();
		List<Object> response = responsePLRs;
		response.addAll(responseEBRs);

		// loop through the list until we find the rule we're looking for
		for (Iterator<Object> iterator = response.iterator(); iterator.hasNext();) {
			Object ebr = iterator.next();
			Map<String, Object> map = (Map<String, Object>) ebr;
			String ruleName = (String) map.get("name");
			System.err.print("Name: " + ruleName);
			ArrayList<Object> conditions = (ArrayList<Object>) map.get("conditions");
			if (null != conditions) {
				System.err.print(" has conditions...");
				boolean checksTheDE = false;
				for (Iterator<Object> iter = conditions.iterator(); iter.hasNext();) {
					Object condition = iter.next();
					if (String.class.isAssignableFrom(condition.getClass())) {
						String func = (String) condition;
						if (func.contains("return _satellite.textMatch(_satellite.getVar(\"" + _dataElementName
								+ "\")),\"yes\")")) {
							checksTheDE = true;
							break;
						}
					}
				}
				if (checksTheDE) {
					System.err.println(" checks the DE.");
				} else {
					System.err.println(" doesn't check the DE.");
					rulesThatDoNotCheckTheDE += rulesThatDoNotCheckTheDE.length() > 0 ? "," : "";
					rulesThatDoNotCheckTheDE += ruleName;
				}
			} else {
				System.err.println(" doesn't have any conditions!");
				rulesThatDoNotCheckTheDE += rulesThatDoNotCheckTheDE.length() > 0 ? "," : "";
				rulesThatDoNotCheckTheDE += ruleName;
			}
		}

		// didn't find the rule? Well...
		if (rulesThatDoNotCheckTheDE.length() > 0) {
			fail(Tools.DTM + " Some rules do not check DE '" + _dataElementName + "' (pedantic): "
					+ rulesThatDoNotCheckTheDE);
		}
	}

}
