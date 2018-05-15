package com.exner.tools.analyticstdd.SiteInfrastructureTests;

import org.junit.Assert;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;

public class TestTools {

	public static void assertScriptExecutionReturnsTrue(WebDriver driver, String script) {
		JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
		Object response = jsExecutor.executeScript(script);
		if (Boolean.class.isAssignableFrom(response.getClass())) {
			Assert.assertEquals(true, (Boolean) response);
		} else {
			Assert.fail();
		}
	}

	public static void assertScriptExecutionReturnsCorrectStringValue(WebDriver driver, String script,
			String expectedValue) {
		JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
		Object response = jsExecutor.executeScript(script);
		if (null != response && String.class.isAssignableFrom(response.getClass())) {
			Assert.assertEquals(expectedValue, (String) response);
		} else {
			Assert.fail();
		}
	}

	public static void assertScriptExecutionReturnsCorrectNumericalValue(WebDriver driver, String script,
			long expectedValue) {
		JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
		Object response = jsExecutor.executeScript(script);
		if (null != response && Long.class.isAssignableFrom(response.getClass())) {
			Long temp = (Long) response;
			Assert.assertEquals(expectedValue, (long) temp);
		} else {
			Assert.fail();
		}
	}

	public static void assertToolVersionIsOrLater(WebDriver driver, String script, String minVersion) {
		JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
		Object response = jsExecutor.executeScript(script);
		// make sure the element exists
		if (String.class.isAssignableFrom(response.getClass())) {
			boolean result = Tools.testVersionNotOlderThanBaseVersion((String) response, minVersion);
			Assert.assertEquals(true, result);
		} else {
			Assert.fail();
		}
	}

	public static void assertToolVersionIsBelow(WebDriver driver, String script, String maxVersion) {
		JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
		Object response = jsExecutor.executeScript(script);
		// make sure the element exists
		if (String.class.isAssignableFrom(response.getClass())) {
			boolean result = Tools.testVersionIsOlderThanBaseVersion((String) response, maxVersion);
			Assert.assertEquals(true, result);
		} else {
			Assert.fail();
		}
	}

}
