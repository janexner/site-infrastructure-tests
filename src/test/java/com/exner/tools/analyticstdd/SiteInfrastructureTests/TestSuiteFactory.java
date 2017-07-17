package com.exner.tools.analyticstdd.SiteInfrastructureTests;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.exner.tools.analyticstdd.SiteInfrastructureTests.tests.WebDriverBasedTestCase;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;

import junit.framework.TestSuite;

public class TestSuiteFactory {
	@SuppressWarnings("rawtypes")
	private Class[] _cArgs;

	public TestSuiteFactory() {
		_cArgs = new Class[2];
		_cArgs[0] = String.class;
		_cArgs[1] = Object.class;
	}

	public TestSuite makeSuiteFromJSON(JsonNode jsonObject) {
		String testName = jsonObject.get("name").asText();
		TestSuite suite = new TestSuite("Site test - " + testName);

		// loop through pages to test and add one test suite per page
		ArrayNode pagesToTest = (ArrayNode) jsonObject.get("pagesToTest");
		for (Iterator<JsonNode> iterator = pagesToTest.iterator(); iterator.hasNext();) {
			suite.addTest(makeSuiteForPage(iterator.next()));
		}
		return suite;
	}

	private TestSuite makeSuiteForPage(JsonNode pageTests) {
		String pageURL = pageTests.get("pageURL").asText();
		String description = null;
		if (pageTests.hasNonNull("description")) {
			description = pageTests.get("description").asText();
		}
		TestSuite pageSuite = new TestSuite("Page tests - " + pageURL);
		// pageSuite.addDescription(description);
		for (Iterator<String> iter2 = pageTests.fieldNames(); iter2.hasNext();) {
			// this looks stupid, but remember: keys in JSON nodes are unique!
			// common practice is to pass back the last one if there's more than
			// one
			String key = iter2.next();
			if (!key.equals("pageURL") && !key.equals("description")) {
				Object params = pageTests.get(key);
				String testClass = testNameFromKey(key);
				List<Object> testParams = parseTestParameters(params);
				try {
					for (Iterator<Object> iter4 = testParams.iterator(); iter4.hasNext();) {
						WebDriverBasedTestCase testCase = (WebDriverBasedTestCase) Class.forName(testClass)
								.getDeclaredConstructor(_cArgs).newInstance(pageURL, iter4.next());
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
		if (ArrayNode.class.isAssignableFrom(params.getClass())) {
			for (Iterator<JsonNode> iter3 = ((ArrayNode) params).iterator(); iter3.hasNext();) {
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
		testType = "com.exner.tools.analyticstdd.SiteInfrastructureTests.tests." + testType;
		testType = testType + "TestCase";
		return testType;
	}

}
