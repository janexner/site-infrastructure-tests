package com.exner.tools.analyticstdd.GenericTests4AnalyticsProject;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;

import org.apache.commons.pool2.PoolUtils;
import org.apache.commons.pool2.impl.GenericObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.openqa.selenium.WebDriver;

import junit.extensions.TestSetup;
import junit.framework.Test;
import junit.framework.TestSuite;

public class AllTests extends TestSuite {
	private final static String TESTDESCRIPTIONFILENAME = "testdescription.json";

	private static GenericObjectPool<WebDriver> _webDriverPool;

	public static Test suite() throws FileNotFoundException, IOException, ParseException, InterruptedException {
		// try loading JSON from the file
		JSONParser parser = new JSONParser();
		Object stuff = parser.parse(new FileReader(TESTDESCRIPTIONFILENAME));
		JSONObject jsonObject = (JSONObject) stuff;

		// use the JSON for test setup
		String testName = (String) jsonObject.get("name");
		TestSuite suite = new TestSuite("Site test - " + testName);

		// loop through pages to test and add one test suite per page
		JSONArray pagesToTest = (JSONArray) jsonObject.get("pagesToTest");
		for (Iterator<JSONObject> iterator = pagesToTest.iterator(); iterator.hasNext();) {
			PageTestDefinition ptd = new PageTestDefinition();
			ptd.createFromJSON(iterator.next());
			suite.addTest(PageTests.suite(ptd));
		}

		TestSetup ts = new TestSetup(suite) {

			protected void setUp() throws Exception {
				System.out.println("Global setUp");
				GenericObjectPoolConfig _webDriverPoolConfig = new GenericObjectPoolConfig();
				_webDriverPoolConfig.setBlockWhenExhausted(true);
				_webDriverPoolConfig.setMinIdle(5);
				_webDriverPoolConfig.setMaxTotal(15);
				BasePooledWebDriverFactory factory = new BasePooledWebDriverFactory();
				_webDriverPool = new GenericObjectPool<WebDriver>(PoolUtils.synchronizedPooledFactory(factory),
						_webDriverPoolConfig);
			}

			protected void tearDown() throws Exception {
				System.out.println("Global tearDown");
				_webDriverPool.close();
			}

		};

		return ts;
	}

	public static GenericObjectPool<WebDriver> getWebDriverPool() {
		return _webDriverPool;
	}

}
