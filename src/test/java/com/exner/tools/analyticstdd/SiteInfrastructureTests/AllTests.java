package com.exner.tools.analyticstdd.SiteInfrastructureTests;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.fasterxml.jackson.databind.JsonNode;
import com.github.fge.jackson.JsonLoader;

import junit.extensions.TestSetup;
import junit.framework.Test;
import junit.framework.TestSuite;

public class AllTests extends TestSuite {
	private final static String TESTDESCRIPTIONFILENAME = "testdescription.json";
	private final static Logger LOGGER = Logger.getLogger(AllTests.class.getName());

	public static Test suite() throws FileNotFoundException, IOException, InterruptedException {
		// try to make HTMLUnit shut up just a little bit
		System.getProperties().put("org.apache.commons.logging.simplelog.defaultlog", "error");
		
		// try loading JSON from the file
		String filename = TESTDESCRIPTIONFILENAME;
		String fnProperty = System.getProperty("test.description.file");
		if (null != fnProperty && fnProperty.length() > 0) {
			filename = fnProperty;
		}
		File tempFile = new File(filename);
		if (!tempFile.exists() || !tempFile.canRead()) {
			throw new FileNotFoundException("Could not find test description file " + filename);
		}
		LOGGER.log(Level.CONFIG, "Reading test description from file " + filename);
		JsonNode testDescription = JsonLoader.fromFile(new File(filename));

		// use the JSON for test setup
		TestSuiteFactory factory = new TestSuiteFactory();
		TestSuite suite = factory.makeSuiteFromJSON(testDescription);

		TestSetup ts = new TestSetup(suite);

		return ts;
	}

}
