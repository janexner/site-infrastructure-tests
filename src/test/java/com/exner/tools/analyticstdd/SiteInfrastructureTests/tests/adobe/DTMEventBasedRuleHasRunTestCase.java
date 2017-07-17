package com.exner.tools.analyticstdd.SiteInfrastructureTests.tests.adobe;

import java.util.Iterator;
import java.util.List;

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

	public DTMEventBasedRuleHasRunTestCase(String pageURL, List<String> blockPatterns, Object params) {
		super(pageURL, blockPatterns);

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
		fail(Tools.DTM + " EBR " + _ruleName + "doesn't fire");
	}

}
