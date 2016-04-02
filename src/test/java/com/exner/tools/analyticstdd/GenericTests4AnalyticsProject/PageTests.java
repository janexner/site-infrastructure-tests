package com.exner.tools.analyticstdd.GenericTests4AnalyticsProject;

import java.util.Iterator;
import java.util.Map;

import com.exner.tools.analyticstdd.GenericTests4AnalyticsProject.tests.DataLayerElementDelayedExistenceTestCase;
import com.exner.tools.analyticstdd.GenericTests4AnalyticsProject.tests.DataLayerElementExistenceTestCase;
import com.exner.tools.analyticstdd.GenericTests4AnalyticsProject.tests.DataLayerElementValueTestCase;
import com.exner.tools.analyticstdd.GenericTests4AnalyticsProject.tests.dtm.DTMIsInDebugModeTestCase;
import com.exner.tools.analyticstdd.GenericTests4AnalyticsProject.tests.dtm.DTMLoadedTestCase;
import com.exner.tools.analyticstdd.GenericTests4AnalyticsProject.tests.dtm.EventBasedRuleExistenceTestCase;
import com.exner.tools.analyticstdd.GenericTests4AnalyticsProject.tests.dtm.EventBasedRuleHasRunTestCase;
import com.exner.tools.analyticstdd.GenericTests4AnalyticsProject.tests.dtm.PageLoadRuleExistenceTestCase;
import com.exner.tools.analyticstdd.GenericTests4AnalyticsProject.tests.dtm.RuleHasRunTestCase;

import junit.extensions.TestSetup;
import junit.framework.Test;
import junit.framework.TestSuite;

public class PageTests extends TestSuite {

	public static Test suite(PageTestDefinition pageTestDefinition) {
		TestSuite suite = new TestSuite("PageTests " + pageTestDefinition.getPageURL());

		String pageURL = pageTestDefinition.getPageURL();

		suite.addTest(new DTMLoadedTestCase(pageURL));
		suite.addTest(new DTMIsInDebugModeTestCase(pageURL));
		for (Iterator<String> iterator = pageTestDefinition.getDataLayerElementsThatMustExist().iterator(); iterator
				.hasNext();) {
			String dataLayerElementName = (String) iterator.next();
			suite.addTest(new DataLayerElementExistenceTestCase(pageURL, dataLayerElementName));
		}
		for (Iterator<Map<String, String>> iterator = pageTestDefinition.getDataLayerElementsThatMustHaveSpecificValue()
				.iterator(); iterator.hasNext();) {
			Map<String, String> element = (Map<String, String>) iterator.next();
			suite.addTest(new DataLayerElementValueTestCase(pageURL, element.get("name"), element.get("value")));
		}
		for (Iterator<StringLongTuple> iterator = pageTestDefinition.getDataLayerElementsThatMustExistAfterDelay()
				.iterator(); iterator.hasNext();) {
			StringLongTuple element = (StringLongTuple) iterator.next();
			suite.addTest(new DataLayerElementDelayedExistenceTestCase(pageURL, element.getName(), element.getDelay()));
		}
		for (Iterator<String> iterator = pageTestDefinition.getPageLoadRulesThatMustExist().iterator(); iterator
				.hasNext();) {
			String plr = (String) iterator.next();
			suite.addTest(new PageLoadRuleExistenceTestCase(pageURL, plr));
		}
		for (Iterator<String> iterator = pageTestDefinition.getPageLoadRulesThatMustHaveRun().iterator(); iterator
				.hasNext();) {
			String rule = (String) iterator.next();
			suite.addTest(new RuleHasRunTestCase(pageURL, rule));
		}
		for (Iterator<String> iterator = pageTestDefinition.getEventBasedRulesThatMustExist().iterator(); iterator
				.hasNext();) {
			String rule = iterator.next();
			suite.addTest(new EventBasedRuleExistenceTestCase(pageURL, rule));
		}
		for (Iterator<Map<String, String>> iterator = pageTestDefinition.getEventBasedRulesThatMustFire()
				.iterator(); iterator.hasNext();) {
			Map<String, String> test = iterator.next();
			suite.addTest(new EventBasedRuleHasRunTestCase(pageURL, test.get("name"), test.get("triggerType"),
					test.get("triggerElement")));
		}

		TestSetup ts = new TestSetup(suite) {
			protected void tearDown() throws Exception {
				System.out.println("Page tearDown ");
			}

		};

		return ts;
	}

}
