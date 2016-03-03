package com.exner.tools.analyticstdd.GenericTests4AnalyticsProject;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriver;

import junit.framework.TestSuite;

public class PageTests extends TestSuite {

	public static TestSuite suite(String pageURL) {
		TestSuite suite = new TestSuite("PageTests " + pageURL);
		// setup
		WebDriver webDriver = new PhantomJSDriver();
		webDriver.get(pageURL);
		//$JUnit-BEGIN$
		suite.addTest(new DTMLoadedTestCase(webDriver));
		suite.addTest(new DTMIsInDebugModeTestCase(webDriver));
		suite.addTest(new DataLayerElementExistenceTestCase(webDriver, "dataLayer.page.pageInfo.pageName"));
		suite.addTest(new DataLayerElementValueTestCase(webDriver, "dataLayer.page.pageInfo.pageName", "Home"));
		suite.addTest(new DataLayerElementExistenceTestCase(webDriver, "dataLayer.page.pageInfo.language"));
		suite.addTest(new PageLoadRuleExistenceTestCase(webDriver, "Normal Page Load"));
		suite.addTest(new RuleHasRunTestCase(webDriver, "Normal Page Load"));
		//$JUnit-END$
		return suite;
	}

}
