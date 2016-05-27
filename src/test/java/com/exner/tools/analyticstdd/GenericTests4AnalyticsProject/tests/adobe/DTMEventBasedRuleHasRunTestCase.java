package com.exner.tools.analyticstdd.GenericTests4AnalyticsProject.tests.adobe;

import java.util.ArrayList;
import java.util.Iterator;

import org.json.simple.JSONObject;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.exner.tools.analyticstdd.GenericTests4AnalyticsProject.Tools;
import com.exner.tools.analyticstdd.GenericTests4AnalyticsProject.tests.WebDriverBasedTestCase;

public class DTMEventBasedRuleHasRunTestCase extends WebDriverBasedTestCase {
	private String _ruleName;
	private String _triggerType;
	private String _triggerElement;

	public DTMEventBasedRuleHasRunTestCase(String pageURL, Object params) {
		super(pageURL);

		if (JSONObject.class.isAssignableFrom(params.getClass())) {
			_ruleName = (String) ((JSONObject) params).get("name");
			_triggerType = (String) ((JSONObject) params).get("triggerType");
			_triggerElement = (String) ((JSONObject) params).get("triggerElement");
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
			WebElement el = _webDriver.findElement(By.cssSelector(_triggerElement));
			if (null != el)
				el.click();
		}

		// wait a sec
		Thread.sleep(1000l);

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
		fail(Tools.DTM + " EBR " + _ruleName + "doesn't fire");
	}

}
