package com.exner.tools.analyticstdd.GenericTests4AnalyticsProject.tests.adobe;

import com.exner.tools.analyticstdd.GenericTests4AnalyticsProject.Tools;
import com.exner.tools.analyticstdd.GenericTests4AnalyticsProject.tests.WebDriverBasedTestCase;

public class AnalyticsTagForReportSuiteFiredTestCase extends WebDriverBasedTestCase {
	private final String _rsid;

	public AnalyticsTagForReportSuiteFiredTestCase(String pageURL, Object params) {
		super(pageURL);

		if (!String.class.isAssignableFrom(params.getClass())) {
			throw new IllegalArgumentException("Must specify a Report Suite ID");
		}
		_rsid = (String) params;

		setName(Tools.AA + " tag sent to " + _rsid + " - " + pageURL);
	}

	@Override
	protected void runTest() throws Throwable {
		// replace "," between rsids with "_"
		String rsid = _rsid.replace(',', '_');
		
		// check whether Analytics code has been loaded on the page
		Object response = _jsExecutor.executeScript("var rstest='" + rsid
				+ "';var rsprfx='s_i_';var retVal=false;for(var b in window){if(window['hasOwnProperty'](b)){if(b['indexOf'](rsprfx)===0){rsarr=b['substr'](rsprfx['length'],b['length'])['split']('_');if(rsarr['indexOf'](rstest)>=0){retVal=true}}}};return retVal");

		// make sure the element exists
		if (Boolean.class.isAssignableFrom(response.getClass())) {
			assertTrue(Tools.AA + " tag to " + _rsid + " must fire", (Boolean) response);
		} else {
			fail(Tools.AA + " tag to " + _rsid + " has not fired");
		}

	}

}
