package com.exner.tools.analyticstdd.SiteInfrastructureTests.tests;

import java.util.List;

import org.openqa.selenium.WebDriverException;

import com.fasterxml.jackson.databind.JsonNode;

public class DataLayerElementDelayedValueTestCase extends WebDriverBasedTestCase {
	private final String _elementName;
	private final String _elementExpectedValue;
	private long _delay;

	public DataLayerElementDelayedValueTestCase(String pageURL, List<String> blockPatterns, Object params) {
		super(pageURL, blockPatterns);

		if (!JsonNode.class.isAssignableFrom(params.getClass())) {
			throw new IllegalArgumentException("Must specify name, value, and delay");
		}
		_elementName = ((JsonNode) params).get("name").asText();
		_elementExpectedValue = ((JsonNode) params).get("value").asText();
		_delay = ((JsonNode) params).get("delay").asLong();

		setName("Data Layer element " + _elementName + " value is " + _elementExpectedValue + " after " + _delay
				+ "ms - " + pageURL);
	}

	@Override
	protected void runTest() throws Throwable {
		try {
			// sleep
			Thread.sleep(_delay);

			// get the value of the dl element from the page
			Object response = _page.executeJavaScript(_elementName).getJavaScriptResult();

			// make sure the element exists
			if (String.class.isAssignableFrom(response.getClass())) {
				assertEquals("Data Layer element " + _elementName + " value should be " + _elementExpectedValue
						+ "after " + _delay + "ms", _elementExpectedValue, (String) response);
			} else {
				fail("Data Layer element " + _elementName + " does not exist");
			}
		} catch (WebDriverException we) {
			fail("Data Layer element " + _elementName + " does not exist");
		}
	}
}
