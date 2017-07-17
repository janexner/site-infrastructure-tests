package com.exner.tools.analyticstdd.SiteInfrastructureTests.tests.adobe;

import com.exner.tools.analyticstdd.SiteInfrastructureTests.Tools;
import com.exner.tools.analyticstdd.SiteInfrastructureTests.tests.WebDriverBasedTestCase;
import com.fasterxml.jackson.databind.node.TextNode;

public class AnalyticsCodeMinVersionTestCase extends WebDriverBasedTestCase {
	protected final String _minVersion;

	public AnalyticsCodeMinVersionTestCase(String pageURL, Object params) {
		super(pageURL);

		if (TextNode.class.isAssignableFrom(params.getClass())) {
			_minVersion = ((TextNode) params).asText();
		} else {
			_minVersion = "0";
			throw new IllegalArgumentException("Must define a min version!");
		}

		setName(Tools.AA + " code min version " + _minVersion + " - " + pageURL);
	}

	@Override
	protected void runTest() throws Throwable {
		// check whether DTM has been loaded on the page
		Object response = _page
				.executeJavaScript(
						"(typeof AppMeasurement !== 'undefined') ? (new AppMeasurement()).version : (typeof s_gi !== 'undefined') ? s.version : 'unavailable'")
				.getJavaScriptResult();

		// make sure the element exists
		if (String.class.isAssignableFrom(response.getClass())) {
			boolean result = Tools.testVersionNotOlderThanBaseVersion((String) response, _minVersion);
			assertTrue(Tools.AA + " code min version should be " + _minVersion, result);
		} else {
			fail(Tools.AA + " code version not available");
		}

	}

}
