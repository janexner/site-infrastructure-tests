package com.exner.tools.analyticstdd.SiteInfrastructureTests.tests;

import java.util.List;

import com.fasterxml.jackson.databind.node.TextNode;

public class GenericJavascriptTestCase extends WebDriverBasedTestCase {
	private final String _jsToRun;

	public GenericJavascriptTestCase(String pageURL, List<String> blockPatterns, Object params) {
		super(pageURL, blockPatterns);
		setName("Generic JS - " + pageURL);

		if (TextNode.class.isAssignableFrom(params.getClass())) {
			_jsToRun = ((TextNode) params).asText();
		} else {
			throw new IllegalArgumentException("Must define some script to run!");
		}

		setName("Generic JS - " + pageURL);
	}

	@Override
	protected void runTest() throws Throwable {
		Object response = _page.executeJavaScript(_jsToRun).getJavaScriptResult();

		if (Boolean.class.isAssignableFrom(response.getClass())) {
			boolean br = (Boolean) response;
			assertTrue("JS must give true", br);
		} else {
			fail("JS must give true!");
		}
	}

}
