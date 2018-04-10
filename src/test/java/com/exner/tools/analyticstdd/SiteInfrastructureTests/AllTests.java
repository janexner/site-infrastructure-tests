package com.exner.tools.analyticstdd.SiteInfrastructureTests;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriverService;

import com.fasterxml.jackson.databind.JsonNode;
import com.github.fge.jackson.JsonLoader;
import com.github.fge.jsonschema.core.exceptions.ProcessingException;

import junit.extensions.TestSetup;
import junit.framework.Test;
import junit.framework.TestSuite;

public class AllTests extends TestSuite {
	private final static String TESTDESCRIPTIONFILENAME = "testdescription.json";
	private final static Logger LOGGER = Logger.getLogger(AllTests.class.getName());

	protected static ChromeDriverService _service;

	public static Test suite() throws FileNotFoundException, IOException, InterruptedException, ProcessingException {
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

		TestSetup ts = new TestSetup(suite) {

			protected void setUp() throws Exception {
				LOGGER.log(Level.INFO, "Setting up web driver service");
				_service = new ChromeDriverService.Builder().usingDriverExecutable(new File("c:/bin/chromedriver.exe"))
						.usingAnyFreePort().build();
				_service.start();
			}

			protected void tearDown() throws Exception {
				LOGGER.log(Level.CONFIG, "Closing web driver service");
				_service.stop();
			}

		};

		return ts;
	}

	public static ChromeDriverService getChromeDriverService() {
		return _service;
	}
}
