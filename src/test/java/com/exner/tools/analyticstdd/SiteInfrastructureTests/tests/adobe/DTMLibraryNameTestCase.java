package com.exner.tools.analyticstdd.SiteInfrastructureTests.tests.adobe;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.exner.tools.analyticstdd.SiteInfrastructureTests.Tools;
import com.exner.tools.analyticstdd.SiteInfrastructureTests.tests.WebDriverBasedTestCase;

public class DTMLibraryNameTestCase extends WebDriverBasedTestCase {
	private String _libraryName;

	public DTMLibraryNameTestCase(String pageURL, Object params) {
		super(pageURL);

		if (!String.class.isAssignableFrom(params.getClass())) {
			throw new IllegalArgumentException("Must specify a library name");
		}
		_libraryName = (String) params;

		setName(Tools.DTM + " library name is " + _libraryName + " - " + pageURL);
	}

	@SuppressWarnings("unchecked")
	@Override
	protected void runTest() throws Throwable {
		// get the list of PLRs from the page
		Object response = _jsExecutor.executeScript("return _satellite.settings.libraryName");

		// make sure the library name is correct
		if (String.class.isAssignableFrom(response.getClass())) {
			assertEquals(Tools.DTM + " library name must be " + _libraryName, (String) response, _libraryName);
		} else {
			fail(Tools.DTM + " library name has not been found");
		}
	}
}
