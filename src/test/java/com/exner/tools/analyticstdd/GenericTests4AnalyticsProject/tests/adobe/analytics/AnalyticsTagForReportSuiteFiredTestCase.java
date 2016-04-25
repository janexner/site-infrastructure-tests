package com.exner.tools.analyticstdd.GenericTests4AnalyticsProject.tests.adobe.analytics;

import com.exner.tools.analyticstdd.GenericTests4AnalyticsProject.tests.WebDriverBasedTestCase;

public class AnalyticsTagForReportSuiteFiredTestCase extends WebDriverBasedTestCase {
	private String _rsid;

	public AnalyticsTagForReportSuiteFiredTestCase(String pageURL, String rsid) {
		super(pageURL);
		setName("Adobe Analytics tag sent to " + rsid + " - " + pageURL);
		_rsid = rsid;
	}

	@Override
	protected void runTest() throws Throwable {
		// check whether Analytics code has been loaded on the page
		Object response = _jsExecutor.executeScript("var rstest='" + _rsid
				+ "';var rsprfx='s_i_';var retVal=false;for(var b in window){if(window['hasOwnProperty'](b)){if(b['indexOf'](rsprfx)===0){rsarr=b['substr'](rsprfx['length'],b['length'])['split']('_');if(rsarr['indexOf'](rstest)>=0){retVal=true}}}};return retVal");

		// make sure the element exists
		if (Boolean.class.isAssignableFrom(response.getClass())) {
			assertTrue("Adobe Analytics tag to " + _rsid + " must fire", (Boolean) response);
		} else {
			fail("Adobe Analytics tag to " + _rsid + " has fired");
		}

	}

}
