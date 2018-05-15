package com.exner.tools.analyticstdd.SiteInfrastructureTests;

import java.io.IOException;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.openqa.selenium.chrome.ChromeDriverService;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;

@RunWith(Cucumber.class)
@CucumberOptions(format = { "pretty", "html:target/cucumber-html-report" }, features="src/test/resources")
public class CucumberFeatureTest {
	private static ChromeDriverService _chromeDriverService;

	@BeforeClass
	public static void setup() throws IOException {
		System.out.println("Starting ChromeDriverService...");
		_chromeDriverService = new ChromeDriverService.Builder().usingAnyFreePort().build();
		_chromeDriverService.start();
	}

	@AfterClass
	public static void tearDown() {
		System.out.println("Stopping ChromeDriverService.");
		_chromeDriverService.stop();
	}
	
	public static ChromeDriverService getService() {
		return _chromeDriverService;
	}
}