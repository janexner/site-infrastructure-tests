package com.exner.tools.analyticstdd.SiteInfrastructureTests;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.commons.pool2.PoolUtils;
import org.apache.commons.pool2.impl.GenericObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.openqa.selenium.WebDriver;

import com.fasterxml.jackson.databind.JsonNode;
import com.github.fge.jackson.JsonLoader;
import com.github.fge.jsonschema.core.exceptions.ProcessingException;

import junit.extensions.TestSetup;
import junit.framework.Test;
import junit.framework.TestSuite;

public class AllTests extends TestSuite {
	private final static String TESTDESCRIPTIONFILENAME = "testdescription.json";
	private final static String SCHEMAFILENAME = "testschema.json";
	private final static Logger LOGGER = Logger.getLogger(AllTests.class.getName());

	private static GenericObjectPool<WebDriver> _webDriverPool;

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

		// LOGGER.log(Level.INFO, "Validating description against JSON-schema");
		// JsonNode schemaNode = JsonLoader.fromFile(new File(SCHEMAFILENAME));
		// JsonSchemaFactory schemaFactory = JsonSchemaFactory.byDefault();
		// JsonSchema schema = schemaFactory.getJsonSchema(schemaNode);
		// schema.validate(testDescription);

		// use the JSON for test setup
		TestSuiteFactory factory = new TestSuiteFactory();
		TestSuite suite = factory.makeSuiteFromJSON(testDescription);

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
