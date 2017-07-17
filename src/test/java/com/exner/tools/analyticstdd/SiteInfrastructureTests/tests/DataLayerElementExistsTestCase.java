package com.exner.tools.analyticstdd.SiteInfrastructureTests.tests;

import com.fasterxml.jackson.databind.node.TextNode;

public class DataLayerElementExistsTestCase extends WebDriverBasedTestCase {
	private final String _elementName;

	public DataLayerElementExistsTestCase(String pageURL, Object params) {
		super(pageURL);

		if (!TextNode.class.isAssignableFrom(params.getClass())) {
			throw new IllegalArgumentException("Must specify an element");
		}
		_elementName = ((TextNode) params).asText();

		setName("Data Layer element " + _elementName + " exists - " + pageURL);
	}

	@Override
	protected void runTest() throws Throwable {
		// get the value of the dl element from the page
		Object response = _page.executeJavaScript("(typeof " + _elementName + " !== 'undefined')")
				.getJavaScriptResult();

		// make sure the element exists
		if (Boolean.class.isAssignableFrom(response.getClass())) {
			assertTrue("Data Layer element " + _elementName + " must exist", (Boolean) response);
		} else {
			fail("Data Layer element " + _elementName + " does not exist");
		}
	}

}
