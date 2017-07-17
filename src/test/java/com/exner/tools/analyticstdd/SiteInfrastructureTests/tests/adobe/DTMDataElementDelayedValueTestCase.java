package com.exner.tools.analyticstdd.SiteInfrastructureTests.tests.adobe;

import java.util.List;

import com.exner.tools.analyticstdd.SiteInfrastructureTests.Tools;
import com.exner.tools.analyticstdd.SiteInfrastructureTests.tests.WebDriverBasedTestCase;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class DTMDataElementDelayedValueTestCase extends WebDriverBasedTestCase {
	private final String _elementName;
	private final String _elementExpectedValue;
	private final long _delay;

	public DTMDataElementDelayedValueTestCase(String pageURL, List<String> blockPatterns, Object params) {
		super(pageURL, blockPatterns);

		if (!ObjectNode.class.isAssignableFrom(params.getClass())) {
			throw new IllegalArgumentException("Must specify name, value, and delay");
		}
		_elementName = ((ObjectNode) params).get("name").asText();
		_elementExpectedValue = ((ObjectNode) params).get("value").asText();
		_delay = ((ObjectNode) params).get("delay").asLong();

		setName(Tools.DTM + " DE " + _elementName + " value is " + _elementExpectedValue + " after " + _delay + "ms - "
				+ pageURL);
	}

	@Override
	protected void runTest() throws Throwable {
		// wait
		Thread.sleep(_delay);

		// get the value of the dl element from the page
		Object response = _page.executeJavaScript("_satellite.getVar('" + _elementName + "');").getJavaScriptResult();

		// make sure the element exists
		if (null != response && String.class.isAssignableFrom(response.getClass())) {
			assertEquals(Tools.DTM + " Data Element " + _elementName + " value should be " + _elementExpectedValue
					+ " after " + _delay + "ms", _elementExpectedValue, (String) response);
		} else {
			fail(Tools.DTM + " Data Element " + _elementName + " does not exist");
		}

	}
}
