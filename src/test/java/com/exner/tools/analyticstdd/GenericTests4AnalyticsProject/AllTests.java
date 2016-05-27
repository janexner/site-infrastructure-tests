package com.exner.tools.analyticstdd.GenericTests4AnalyticsProject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.commons.pool2.PoolUtils;
import org.apache.commons.pool2.impl.GenericObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.openqa.selenium.WebDriver;

import junit.extensions.TestSetup;
import junit.framework.Test;
import junit.framework.TestSuite;

public class AllTests extends TestSuite {
	private final static String TESTDESCRIPTIONFILENAME = "testdescription.json";
	private final static Logger LOGGER = Logger.getLogger(AllTests.class.getName());

	private static GenericObjectPool<WebDriver> _webDriverPool;

	public static Test suite() throws FileNotFoundException, IOException, ParseException, InterruptedException {
		// try loading JSON from the file
		JSONParser parser = new JSONParser();
		String filename = TESTDESCRIPTIONFILENAME;
		String fnProperty = System.getProperty("test.description.file");
		if (null != fnProperty && fnProperty.length() > 0) {
			filename = fnProperty;
		}
		File tempFile = new File(fnProperty);
		if (!tempFile.exists() || !tempFile.canRead()) {
			throw new FileNotFoundException("Could not find test description file " + filename);
		}
		LOGGER.log(Level.CONFIG, "Reading test description from file " + filename);
		Object stuff = parser.parse(new FileReader(filename));
		JSONObject jsonObject = (JSONObject) stuff;

		// use the JSON for test setup
		TestSuiteFactory factory = new TestSuiteFactory();
		TestSuite suite = factory.makeSuiteFromJSON(jsonObject);

		TestSetup ts = new TestSetup(suite) {

			protected void setUp() throws Exception {
				LOGGER.log(Level.INFO, "Setting up web driver pool");
				GenericObjectPoolConfig _webDriverPoolConfig = new GenericObjectPoolConfig();
				_webDriverPoolConfig.setBlockWhenExhausted(true);
				_webDriverPoolConfig.setMinIdle(5);
				_webDriverPoolConfig.setMaxTotal(15);
				BasePooledWebDriverFactory factory = new BasePooledWebDriverFactory();
				_webDriverPool = new GenericObjectPool<WebDriver>(PoolUtils.synchronizedPooledFactory(factory),
						_webDriverPoolConfig);
			}

			protected void tearDown() throws Exception {
				LOGGER.log(Level.CONFIG, "Closing web driver pool");
				_webDriverPool.close();
			}

		};

		return ts;
	}

	public static GenericObjectPool<WebDriver> getWebDriverPool() {
		return _webDriverPool;
	}

}
