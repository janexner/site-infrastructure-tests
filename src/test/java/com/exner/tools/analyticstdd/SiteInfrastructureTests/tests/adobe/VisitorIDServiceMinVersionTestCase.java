package com.exner.tools.analyticstdd.SiteInfrastructureTests.tests.adobe;

import org.openqa.selenium.WebDriverException;

import com.exner.tools.analyticstdd.SiteInfrastructureTests.Tools;
import com.exner.tools.analyticstdd.SiteInfrastructureTests.tests.WebDriverBasedTestCase;
import com.fasterxml.jackson.databind.node.TextNode;

public class VisitorIDServiceMinVersionTestCase extends WebDriverBasedTestCase {
	protected String _minVersion;

	public VisitorIDServiceMinVersionTestCase(String pageURL, Object params) {
		super(pageURL);

		if (TextNode.class.isAssignableFrom(params.getClass())) {
			_minVersion = ((TextNode) params).asText();
		} else {
			_minVersion = "0";
			throw new IllegalArgumentException("Must define a min version!");
		}

		setName(Tools.MCVID + " min version " + _minVersion + " - " + pageURL);
	}

	@Override
	protected void runTest() throws Throwable {
		try {
			// check whether DTM has been loaded on the page
			Object response = _page
					.executeJavaScript("(typeof Visitor !== 'undefined') ? Visitor.version : 'unavailable'")
					.getJavaScriptResult();

			// make sure the element exists
			if (String.class.isAssignableFrom(response.getClass()) && !((String) response).equals("unavailable")) {
				boolean result = Tools.testVersionNotOlderThanBaseVersion((String) response, _minVersion);
				assertTrue(Tools.MCVID + " min version should be " + _minVersion, result);
			} else {
				fail(Tools.MCVID + " version not available");
			}
		} catch (WebDriverException we) {
			fail(Tools.MCVID + " not found");
		}
	}

}
