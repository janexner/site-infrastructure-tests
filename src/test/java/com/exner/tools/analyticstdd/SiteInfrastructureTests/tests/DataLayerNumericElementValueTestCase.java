package com.exner.tools.analyticstdd.SiteInfrastructureTests.tests;

import com.fasterxml.jackson.databind.node.ObjectNode;

public class DataLayerNumericElementValueTestCase extends WebDriverBasedTestCase {
	private final String _elementName;
	private final Long _elementExpectedValue;

	public DataLayerNumericElementValueTestCase(String pageURL, Object params) {
		super(pageURL);

		if (ObjectNode.class.isAssignableFrom(params.getClass())) {
			_elementName = ((ObjectNode) params).get("name").asText();
			_elementExpectedValue = ((ObjectNode) params).get("value").asLong();
		} else {
			_elementName = null;
			_elementExpectedValue = null;
		}
		if (null == _elementName) {
			throw new IllegalArgumentException("Must specify name and value of element");
		}
		if (null == _elementExpectedValue) {
			throw new IllegalArgumentException("Must specify value for element");
		}

		setName("Data Layer element " + _elementName + " numeric value is " + _elementExpectedValue + " - " + pageURL);
	}

	@Override
	protected void runTest() throws Throwable {
		// TODO - maybe test for undefined first!
		// get the value of the dl element from the page
		Object response = _jsExecutor.executeScript("return " + _elementName);

		// make sure the element exists
		if (Long.class.isAssignableFrom(response.getClass())) {
			assertEquals("Data Layer element " + _elementName + " numeric value should be " + _elementExpectedValue,
					_elementExpectedValue, (Long) response);
		} else {
			fail("Data Layer element " + _elementName + " does not exist");
		}

	}
}
