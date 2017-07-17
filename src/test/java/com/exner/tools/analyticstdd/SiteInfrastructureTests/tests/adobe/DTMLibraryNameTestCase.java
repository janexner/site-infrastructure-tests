package com.exner.tools.analyticstdd.SiteInfrastructureTests.tests.adobe;

import com.exner.tools.analyticstdd.SiteInfrastructureTests.Tools;
import com.exner.tools.analyticstdd.SiteInfrastructureTests.tests.WebDriverBasedTestCase;
import com.fasterxml.jackson.databind.node.TextNode;

public class DTMLibraryNameTestCase extends WebDriverBasedTestCase {
	private String _libraryName;

	public DTMLibraryNameTestCase(String pageURL, Object params) {
		super(pageURL);

		if (!TextNode.class.isAssignableFrom(params.getClass())) {
			throw new IllegalArgumentException("Must specify a library name");
		}
		_libraryName = ((TextNode) params).asText();

		setName(Tools.DTM + " library name is " + _libraryName + " - " + pageURL);
	}

	@Override
	protected void runTest() throws Throwable {
		// get the list of PLRs from the page
		Object response = _page.executeJavaScript("_satellite.settings.libraryName").getJavaScriptResult();

		// make sure the library name is correct
		if (String.class.isAssignableFrom(response.getClass())) {
			assertEquals(Tools.DTM + " library name must be " + _libraryName, _libraryName, (String) response);
		} else {
			fail(Tools.DTM + " library name has not been found");
		}
	}
}
