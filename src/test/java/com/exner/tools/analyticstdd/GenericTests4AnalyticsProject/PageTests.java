package com.exner.tools.analyticstdd.GenericTests4AnalyticsProject;

import java.util.Iterator;
import java.util.Map;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriver;

import junit.framework.TestSuite;

public class PageTests extends TestSuite {

	public static TestSuite suite(PageTestDefinition pageTestDefinition) {
		TestSuite suite = new TestSuite("PageTests " + pageTestDefinition.getPageURL());
		// setup
		WebDriver webDriver = new PhantomJSDriver();
		webDriver.get(pageTestDefinition.getPageURL());

		// $JUnit-BEGIN$
		suite.addTest(new DTMLoadedTestCase(webDriver));
		suite.addTest(new DTMIsInDebugModeTestCase(webDriver));
		for (Iterator<String> iterator = pageTestDefinition.getDataElementsThatMustExist().iterator(); iterator
				.hasNext();) {
			String dataLayerElementName = (String) iterator.next();
			suite.addTest(new DataLayerElementExistenceTestCase(webDriver, dataLayerElementName));
		}
		for (Iterator<Map<String, String>> iterator = pageTestDefinition.getDataElementsThatMustHaveSpecificValue()
				.iterator(); iterator.hasNext();) {
			Map<String, String> element = (Map<String, String>) iterator.next();
			suite.addTest(new DataLayerElementValueTestCase(webDriver, element.get("name"), element.get("value")));
		}
		for (Iterator<String> iterator = pageTestDefinition.getPageLoadRulesThatMustExist().iterator(); iterator
				.hasNext();) {
			String plr = (String) iterator.next();
			suite.addTest(new PageLoadRuleExistenceTestCase(webDriver, plr));
		}
		for (Iterator<String> iterator = pageTestDefinition.getPageLoadRulesThatMustHaveRun().iterator(); iterator
				.hasNext();) {
			String rule = (String) iterator.next();
			suite.addTest(new RuleHasRunTestCase(webDriver, rule));
		}
		// $JUnit-END$
		return suite;
	}

}
