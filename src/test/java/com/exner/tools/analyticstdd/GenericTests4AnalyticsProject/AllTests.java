package com.exner.tools.analyticstdd.GenericTests4AnalyticsProject;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriver;

import junit.extensions.TestSetup;
import junit.framework.Test;
import junit.framework.TestSuite;

public class AllTests extends TestSuite {
	private final static String TESTDESCRIPTIONFILENAME = "testdescription.json";

	public static Test suite() throws FileNotFoundException, IOException, ParseException {
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
			protected void tearDown() throws Exception {
				System.out.println("Global tearDown ");
			}

		};

		return ts;
	}

}
