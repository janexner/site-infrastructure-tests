package com.exner.tools.analyticstdd.GenericTests4AnalyticsProject;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.exner.tools.analyticstdd.GenericTests4AnalyticsProject.tests.WebDriverBasedTestCase;

import junit.framework.TestSuite;

public class TestSuiteFactory {

	public TestSuite makeSuiteFromJSON(JSONObject jsonObject) {
		String testName = (String) jsonObject.get("name");
		TestSuite suite = new TestSuite("Site test - " + testName);

		// loop through pages to test and add one test suite per page
		JSONArray pagesToTest = (JSONArray) jsonObject.get("pagesToTest");
		for (Iterator<JSONObject> iterator = pagesToTest.iterator(); iterator.hasNext();) {
			suite.addTest(makeSuiteForPage(iterator.next()));
		}
		return suite;
	}

	private TestSuite makeSuiteForPage(JSONObject pageTests) {
		String pageURL = (String) pageTests.get("pageURL");
		TestSuite pageSuite = new TestSuite("Page tests - " + pageURL);
		Set<String> keys = pageTests.keySet();
		for (Iterator<String> iter2 = keys.iterator(); iter2.hasNext();) {
			String key = iter2.next();
			if (!key.equals("pageURL")) {
				Object params = pageTests.get(key);
				String testClass = testNameFromKey(key);
				List<Object> testParams = parseTestParameters(params);
				try {
					for (Iterator<Object> iter4 = testParams.iterator(); iter4.hasNext();) {
						Class[] cArgs = new Class[2];
						cArgs[0] = String.class;
						cArgs[1] = Object.class;
						WebDriverBasedTestCase testCase = (WebDriverBasedTestCase) Class.forName(testClass)
								.getDeclaredConstructor(cArgs).newInstance(pageURL, iter4.next());
						pageSuite.addTest(testCase);
					}
				} catch (InstantiationException e) {
					System.err.println("Problem with test setup: " + e.getLocalizedMessage());
					System.exit(-1);
				} catch (IllegalAccessException e) {
					System.err.println("Problem with test setup: " + e.getLocalizedMessage());
					System.exit(-1);
				} catch (ClassNotFoundException e) {
					System.err.println("Problem with test setup: " + e.getLocalizedMessage());
					System.exit(-1);
				} catch (IllegalArgumentException e) {
					System.err.println("Problem with test setup: " + e.getLocalizedMessage());
					System.exit(-1);
				} catch (InvocationTargetException e) {
					System.err.println("Problem with test setup: " + e.getLocalizedMessage());
					System.exit(-1);
				} catch (NoSuchMethodException e) {
					System.err.println("Problem with test setup: " + e.getLocalizedMessage());
					System.exit(-1);
				} catch (SecurityException e) {
					System.err.println("Problem with test setup: " + e.getLocalizedMessage());
					System.exit(-1);
				}
			}
		}
		return pageSuite;
	}

	private List<Object> parseTestParameters(Object params) {
		List<Object> testParams = new ArrayList<Object>();
		// is this an array?
		if (JSONArray.class.isAssignableFrom(params.getClass())) {
			for (Iterator<JSONObject> iter3 = ((JSONArray) params).iterator(); iter3.hasNext();) {
				testParams.add(iter3.next());
			}
		} else {
			testParams.add(params);
		}
		return testParams;
	}

	private static String testNameFromKey(String key) {
		// turn testType into a real class name
		String testType = key;
		if (!testType.contains(".")) {
			testType = key.substring(0, 1).toUpperCase() + key.substring(1);
		}
		testType = "com.exner.tools.analyticstdd.GenericTests4AnalyticsProject.tests." + testType;
		testType = testType + "TestCase";
		return testType;
	}

}
