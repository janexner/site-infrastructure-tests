package com.exner.tools.analyticstdd.SiteInfrastructureTests.tests.adobe;

import java.util.List;

import com.exner.tools.analyticstdd.SiteInfrastructureTests.Tools;
import com.exner.tools.analyticstdd.SiteInfrastructureTests.tests.WebDriverBasedTestCase;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class DTMDataElementValueTestCase extends WebDriverBasedTestCase {
	private final String _elementName;
	private final String _elementExpectedValue;

	public DTMDataElementValueTestCase(String pageURL, List<String> blockPatterns, Object params) {
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

		setName(Tools.DTM + " DE " + _elementName + " value is " + _elementExpectedValue + " - " + pageURL);
	}

	@Override
	protected void runTest() throws Throwable {
		// get the value of the dl element from the page
		Object response = _page.executeJavaScript("_satellite.getVar('" + _elementName + "');").getJavaScriptResult();

		// make sure the element exists
		if (null != response && String.class.isAssignableFrom(response.getClass())) {
			assertEquals(Tools.DTM + " Data Element " + _elementName + " value should be " + _elementExpectedValue,
					_elementExpectedValue, (String) response);
		} else {
			fail(Tools.DTM + " Data Element " + _elementName + " does not exist");
		}

	}
}
