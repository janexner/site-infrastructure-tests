package com.exner.tools.analyticstdd.SiteInfrastructureTests.tests;

import org.openqa.selenium.WebDriverException;

import com.fasterxml.jackson.databind.node.ObjectNode;

public class DataLayerNumericElementDelayedValueTestCase extends WebDriverBasedTestCase {
	private final String _elementName;
	private final Long _elementExpectedValue;
	private long _delay;

	public DataLayerNumericElementDelayedValueTestCase(String pageURL, Object params) {
		super(pageURL);

		if (!ObjectNode.class.isAssignableFrom(params.getClass())) {
			throw new IllegalArgumentException("Must specify name, value, and delay");
		}
		_elementName = ((ObjectNode) params).get("name").asText();
		_elementExpectedValue = ((ObjectNode) params).get("value").asLong();
		_delay = ((ObjectNode) params).get("delay").asLong();

		setName("Data Layer element " + _elementName + " numeric value is " + _elementExpectedValue + " after " + _delay
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
			if (Long.class.isAssignableFrom(response.getClass())) {
				assertEquals("Data Layer element " + _elementName + " numeric value should be " + _elementExpectedValue
						+ "after " + _delay + "ms", _elementExpectedValue, (Long) response);
			} else {
				fail("Data Layer element " + _elementName + " does not exist");
			}
		} catch (WebDriverException we) {
			fail("Data Layer element " + _elementName + " does not exist");
		}
	}
}
