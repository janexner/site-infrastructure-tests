package com.exner.tools.analyticstdd.SiteInfrastructureTests;

import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.openqa.selenium.chrome.ChromeDriverService;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;

@RunWith(Cucumber.class)
@CucumberOptions(plugin = { "pretty", "html:target/cucumber-html-report" }, features = "src/test/resources")
public class CucumberFeatureTest {
	private static final Logger logger = LogManager.getRootLogger();
	private static ChromeDriverService _chromeDriverService;

	@BeforeClass
	public static void setup() throws IOException {
		logger.info("Starting ChromeDriverService...");
		_chromeDriverService = new ChromeDriverService.Builder().usingAnyFreePort().build();
		_chromeDriverService.start();
	}

	@AfterClass
	public static void tearDown() {
		logger.info("Stopping ChromeDriverService.");
		_chromeDriverService.stop();
	}

	public static ChromeDriverService getService() {
		if (logger.isDebugEnabled()) {
			logger.debug("Getting ChromeDriverService.");
		}
		return _chromeDriverService;
	}
}