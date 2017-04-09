package com.exner.tools.analyticstdd.SiteInfrastructureTests.tests.adobe;

import com.exner.tools.analyticstdd.SiteInfrastructureTests.Tools;
import com.exner.tools.analyticstdd.SiteInfrastructureTests.tests.WebDriverBasedTestCase;
import com.fasterxml.jackson.databind.node.TextNode;

public class TargetCodeMinVersionTestCase extends WebDriverBasedTestCase {
	protected final String _minVersion;

	public TargetCodeMinVersionTestCase(String pageURL, Object params) {
		super(pageURL);

		if (TextNode.class.isAssignableFrom(params.getClass())) {
			_minVersion = ((TextNode) params).asText();
		} else {
			_minVersion = "0";
			throw new IllegalArgumentException("Must define a min version!");
		}

		setName(Tools.AT + " code min version " + _minVersion + " - " + pageURL);
	}

	@Override
	protected void runTest() throws Throwable {
		// check whether DTM has been loaded on the page
		Object response = _jsExecutor.executeScript(
				"if (typeof mboxVersion !== 'undefined') { return mboxVersion } else if (typeof adobe !== 'undefined' && typeof adobe.target !== 'undefined' && typeof adobe.target.VERSION !== 'undefined') { return adobe.target.VERSION } else { return 'unavailable' }");

		// make sure the element exists
		if (String.class.isAssignableFrom(response.getClass())) {
			boolean result = Tools.testVersionNotOlderThanBaseVersion((String) response, _minVersion);
			assertTrue(Tools.AT + " code min version should be " + _minVersion, result);
		} else {
			fail(Tools.AT + " code version not available");
		}

	}

}
