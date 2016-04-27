package com.exner.tools.analyticstdd.GenericTests4AnalyticsProject.tests.adobe.dtm;

import java.util.ArrayList;
import java.util.Iterator;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.exner.tools.analyticstdd.GenericTests4AnalyticsProject.tests.WebDriverBasedTestCase;

public class EventBasedRuleHasRunTestCase extends WebDriverBasedTestCase {
	private String _ruleName;
	private String _triggerType;
	private String _triggerElement;

	public EventBasedRuleHasRunTestCase(String pageURL, String ruleName, String triggerType, String triggerElement) {
		super(pageURL);
		_ruleName = ruleName;
		_triggerType = triggerType;
		_triggerElement = triggerElement;
		setName("DTM EBR " + _ruleName + " fires - " + pageURL);
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
		fail("DTM EBR " + _ruleName + "doesn't fire");
	}

}
