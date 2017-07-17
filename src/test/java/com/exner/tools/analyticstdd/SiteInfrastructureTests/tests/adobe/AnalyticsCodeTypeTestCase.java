package com.exner.tools.analyticstdd.SiteInfrastructureTests.tests.adobe;

import com.exner.tools.analyticstdd.SiteInfrastructureTests.Tools;
import com.exner.tools.analyticstdd.SiteInfrastructureTests.tests.WebDriverBasedTestCase;
import com.fasterxml.jackson.databind.node.TextNode;

public class AnalyticsCodeTypeTestCase extends WebDriverBasedTestCase {
	private final String _libType;

	public AnalyticsCodeTypeTestCase(String pageURL, Object params) {
		super(pageURL);

		if (!TextNode.class.isAssignableFrom(params.getClass())) {
			throw new IllegalArgumentException("Must provide lib type");
		}

		_libType = ((TextNode) params).asText();

		setName(Tools.AA + " code type " + _libType + " - " + pageURL);
	}

	@Override
	protected void runTest() throws Throwable {
		// inject and run JS test
		Object response = _page
				.executeJavaScript(
						"(typeof AppMeasurement == 'function' ) ? 'AppMeasurement' : (typeof s_gi == 'function') ? 'legacy' : 'none';")
				.getJavaScriptResult();

		// make sure the element exists
		if (String.class.isAssignableFrom(response.getClass())) {
			assertEquals(Tools.AA + " code type must be " + _libType, _libType, (String) response);
		} else {
			fail(Tools.AA + " code has not been found");
		}
	}

}
