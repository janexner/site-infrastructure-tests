package com.exner.tools.analyticstdd.SiteInfrastructureTests.tests.adobe;

import java.util.List;

import com.exner.tools.analyticstdd.SiteInfrastructureTests.Tools;
import com.exner.tools.analyticstdd.SiteInfrastructureTests.tests.WebDriverBasedTestCase;
import com.fasterxml.jackson.databind.node.TextNode;

public class DTMDataElementExistsTestCase extends WebDriverBasedTestCase {
	private final String _elementName;

	public DTMDataElementExistsTestCase(String pageURL, List<String> blockPatterns, Object params) {
		super(pageURL, blockPatterns);

		if (!TextNode.class.isAssignableFrom(params.getClass())) {
			throw new IllegalArgumentException("Must specify an element");
		}
		_elementName = ((TextNode) params).asText();

		setName(Tools.DTM + " DE " + _elementName + " exists - " + pageURL);
	}

	@Override
	protected void runTest() throws Throwable {
		// get the value of the dl element from the page
		// TODO this is ugly!
		Object response = _page
				.executeJavaScript("var result = false;for(var de in _satellite.dataElements) { if (de == '"
						+ _elementName + "') { result = true; }}; result")
				.getJavaScriptResult();

		// make sure the element exists
		if (Boolean.class.isAssignableFrom(response.getClass())) {
			assertTrue(Tools.DTM + " Data Element " + _elementName + " must exist", (Boolean) response);
		} else {
			fail(Tools.DTM + " Data Element " + _elementName + " does not exist");
		}

	}

}
