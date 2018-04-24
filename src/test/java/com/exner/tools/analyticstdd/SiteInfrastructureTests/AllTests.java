package com.exner.tools.analyticstdd.SiteInfrastructureTests;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.commons.lang3.SystemUtils;
import org.openqa.selenium.chrome.ChromeDriverService;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jackson.JsonLoader;
import com.github.fge.jsonschema.core.exceptions.ProcessingException;

import junit.extensions.TestSetup;
import junit.framework.Test;
import junit.framework.TestSuite;

public class AllTests extends TestSuite {
	private final static String TESTDESCRIPTIONFILENAME = "testdescription.json";
	private final static String CHROMEDRIVERPATHWINDOWS = "c:/bin/chromedriver.exe";
	private final static String CHROMEDRIVERPATHLINUX = "/usr/local/bin/chromedriver";
	private final static Logger LOGGER = Logger.getLogger(AllTests.class.getName());

	protected static ChromeDriverService _service;

	public static Test suite() throws FileNotFoundException, IOException, InterruptedException, ProcessingException {

		// get the testDescription JSON from a URL or file
		JsonNode testDescription = readTestDescription();
		// find the ChromeDriverExecutable
		final File chromeDriverExecutable = findChromeDriverFile();

		// use the JSON for test setup
		TestSuiteFactory factory = new TestSuiteFactory();
		TestSuite suite = factory.makeSuiteFromJSON(testDescription);

		TestSetup ts = new TestSetup(suite) {

			protected void setUp() throws Exception {
				LOGGER.log(Level.INFO, "Setting up web driver service");
				_service = new ChromeDriverService.Builder().usingDriverExecutable(chromeDriverExecutable)
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

	private static File findChromeDriverFile() throws FileNotFoundException {
		// try to find ChromeDriver executable
		String chromeDriverExecPath;
		String cdProperty = System.getProperty("webdriver.chrome.driver");
		if (null != cdProperty && cdProperty.length() > 0) {
			chromeDriverExecPath = cdProperty;
		} else {
			// try some defaults
			if (SystemUtils.IS_OS_WINDOWS) {
				chromeDriverExecPath = CHROMEDRIVERPATHWINDOWS;
			} else if (SystemUtils.IS_OS_LINUX || SystemUtils.IS_OS_UNIX || SystemUtils.IS_OS_MAC) {
				chromeDriverExecPath = CHROMEDRIVERPATHLINUX;
			} else {
				throw new FileNotFoundException(
						"Unable to find chromedriver executable. Please specify path using the webdriver.chrome.driver property!");
			}
		}
		final File chromeDriverExecutable = new File(chromeDriverExecPath);
		if (!chromeDriverExecutable.exists() || !chromeDriverExecutable.canExecute()) {
			throw new FileNotFoundException("Could not find or execute chromedriver file " + chromeDriverExecPath);
		}
		return chromeDriverExecutable;
	}

	private static JsonNode readTestDescription()
			throws IOException, JsonProcessingException, MalformedURLException, FileNotFoundException {
		JsonNode testDescription = null;
		// first, try to load JSON from URL
		String fURLProperty = System.getProperty("test.description.url");
		if (null != fURLProperty && fURLProperty.length() > 0) {
			ObjectMapper mapper = new ObjectMapper();
			LOGGER.log(Level.CONFIG, "Reading test description from URL " + fURLProperty);
			testDescription = mapper.readTree(new URL(fURLProperty));
		} else {
			// try loading JSON from a file
			String fnProperty = System.getProperty("test.description.file");
			if (null != fnProperty && fnProperty.length() > 0) {
				File tempFile = new File(fnProperty);
				if (tempFile.exists() || tempFile.canRead()) {
					LOGGER.log(Level.CONFIG, "Reading test description from file " + fnProperty);
					testDescription = JsonLoader.fromFile(new File(fnProperty));
				}
			} else {
				// try the default JSON file name
				String filename = TESTDESCRIPTIONFILENAME;
				LOGGER.log(Level.CONFIG, "Reading test description from default file " + filename);
				testDescription = JsonLoader.fromFile(new File(filename));
			}
		}
		if (null == testDescription) {
			throw new FileNotFoundException("Unable to find test description. Bailing out!");
		}
		return testDescription;
	}

	public static ChromeDriverService getChromeDriverService() {
		return _service;
	}
}
