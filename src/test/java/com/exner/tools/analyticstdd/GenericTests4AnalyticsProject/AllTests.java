package com.exner.tools.analyticstdd.GenericTests4AnalyticsProject;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import junit.framework.TestSuite;

public class AllTests extends TestSuite {
	private final static String TESTDESCRIPTIONFILENAME = "testdescription.json";
	
	public static TestSuite suite() {
		TestSuite suite = new TestSuite("Site Test");
		// fake it for now
		String pageURL = "http://www.jan-exner.de/";
		
		//$JUnit-BEGIN$
		suite.addTest(PageTests.suite(pageURL));
		//$JUnit-END$
		return suite;
	}

}
