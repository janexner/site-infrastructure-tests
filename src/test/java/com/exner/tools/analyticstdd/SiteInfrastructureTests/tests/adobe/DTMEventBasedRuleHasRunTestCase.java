package com.exner.tools.analyticstdd.SiteInfrastructureTests.tests.adobe;

import java.util.ArrayList;
import java.util.Iterator;

import com.exner.tools.analyticstdd.SiteInfrastructureTests.Tools;
import com.exner.tools.analyticstdd.SiteInfrastructureTests.tests.WebDriverBasedTestCase;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.gargoylesoftware.htmlunit.html.HtmlElement;

import net.sourceforge.htmlunit.corejs.javascript.NativeArray;
import se.fishtank.css.selectors.Selectors;
import se.fishtank.css.selectors.dom.W3CNode;

public class DTMEventBasedRuleHasRunTestCase extends WebDriverBasedTestCase {
	private String _ruleName;
	private String _triggerType;
	private String _triggerElement;

	public DTMEventBasedRuleHasRunTestCase(String pageURL, Object params) {
		super(pageURL);

		if (ObjectNode.class.isAssignableFrom(params.getClass())) {
			_ruleName = ((ObjectNode) params).get("name").asText();
			_triggerType = ((ObjectNode) params).get("triggerType").asText();
			_triggerElement = ((ObjectNode) params).get("triggerElement").asText();
		} else {
			_ruleName = null;
			_triggerType = null;
			_triggerElement = null;
		}
		if (null == _ruleName) {
			throw new IllegalArgumentException("Must specify name of rule and trigger values");
		}
		if (null == _triggerType) {
			throw new IllegalArgumentException("Must specify type of trigger");
		}
		if (null == _triggerElement) {
			throw new IllegalArgumentException("Must specify element for trigger");
		}

		setName(Tools.DTM + " EBR " + _ruleName + " fires on " + _triggerType + " - " + pageURL);
	}

	@SuppressWarnings("unchecked")
	@Override
	protected void runTest() throws Throwable {
		// trigger the rule
		if ("click".equals(_triggerType)) {
			Selectors selectors = new Selectors(new W3CNode(_page));
			HtmlElement el = (HtmlElement) selectors.querySelector(_triggerElement);
			if (null != el)
				el.click();
		}

		// wait a sec
		Thread.sleep(1000l);

		// get the list of Rules which fired on the page
		NativeArray logEntries = (NativeArray) _page.executeJavaScript("_satellite.Logger.getHistory()")
				.getJavaScriptResult();
		for (Iterator<ArrayList<String>> iterator = logEntries.iterator(); iterator.hasNext();) {
			ArrayList<String> arrayList = iterator.next();
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
		fail(Tools.DTM + " EBR " + _ruleName + "doesn't fire");
	}

}
