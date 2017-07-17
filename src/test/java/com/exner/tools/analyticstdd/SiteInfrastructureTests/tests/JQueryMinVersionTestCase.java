package com.exner.tools.analyticstdd.SiteInfrastructureTests.tests;

import java.util.List;

import com.exner.tools.analyticstdd.SiteInfrastructureTests.Tools;
import com.fasterxml.jackson.databind.node.TextNode;

public class JQueryMinVersionTestCase extends WebDriverBasedTestCase {
	private final String _minVersion;

	public JQueryMinVersionTestCase(String pageURL, List<String> blockPatterns, Object params) {
		super(pageURL, blockPatterns);

		if (TextNode.class.isAssignableFrom(params.getClass())) {
			_minVersion = ((TextNode) params).asText();
		} else {
			_minVersion = "0";
			throw new IllegalArgumentException("Must define a min version!");
		}

		setName("jQuery min version " + _minVersion + " - " + pageURL);
	}

	@Override
	protected void runTest() throws Throwable {
		// check whether DTM has been loaded on the page
		Object response = _page.executeJavaScript("(typeof jQuery !== 'undefined') ? jQuery.fn.jquery : 'unavailable'")
				.getJavaScriptResult();

		// make sure the element exists
		if (String.class.isAssignableFrom(response.getClass())) {
			boolean result = Tools.testVersionNotOlderThanBaseVersion((String) response, _minVersion);
			assertTrue("jQuery min version should be " + _minVersion, result);
		} else {
			fail("jQuery version not available");
		}

	}

}
