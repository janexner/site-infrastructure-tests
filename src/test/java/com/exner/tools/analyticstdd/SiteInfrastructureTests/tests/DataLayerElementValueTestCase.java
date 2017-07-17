package com.exner.tools.analyticstdd.SiteInfrastructureTests.tests;

import java.util.List;

import com.fasterxml.jackson.databind.node.ObjectNode;

public class DataLayerElementValueTestCase extends WebDriverBasedTestCase {
	private final String _elementName;
	private final String _elementExpectedValue;

	public DataLayerElementValueTestCase(String pageURL, List<String> blockPatterns, Object params) {
		super(pageURL, blockPatterns);

		if (ObjectNode.class.isAssignableFrom(params.getClass())) {
			_elementName = ((ObjectNode) params).get("name").asText();
			_elementExpectedValue = ((ObjectNode) params).get("value").asText();
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

		setName("Data Layer element " + _elementName + " value is " + _elementExpectedValue + " - " + pageURL);
	}

	@Override
	protected void runTest() throws Throwable {
		// TODO - maybe test for undefined first!
		// get the value of the dl element from the page
		Object response = _page.executeJavaScript(_elementName).getJavaScriptResult();

		// make sure the element exists
		if (String.class.isAssignableFrom(response.getClass())) {
			assertEquals("Data Layer element " + _elementName + " value should be " + _elementExpectedValue,
					_elementExpectedValue, (String) response);
		} else {
			fail("Data Layer element " + _elementName + " does not exist");
		}

	}
}
