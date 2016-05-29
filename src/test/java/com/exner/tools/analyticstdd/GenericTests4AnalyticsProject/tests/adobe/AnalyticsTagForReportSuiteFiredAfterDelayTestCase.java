package com.exner.tools.analyticstdd.GenericTests4AnalyticsProject.tests.adobe;

import org.json.simple.JSONObject;

import com.exner.tools.analyticstdd.GenericTests4AnalyticsProject.Tools;
import com.exner.tools.analyticstdd.GenericTests4AnalyticsProject.tests.WebDriverBasedTestCase;

public class AnalyticsTagForReportSuiteFiredAfterDelayTestCase extends WebDriverBasedTestCase {
	private final String _rsid;
	private final long _delay;

	public AnalyticsTagForReportSuiteFiredAfterDelayTestCase(String pageURL, Object params) {
		super(pageURL);

		if (!JSONObject.class.isAssignableFrom(params.getClass())) {
			throw new IllegalArgumentException("Must specify rsid and delay");
		}
		_rsid = (String) ((JSONObject) params).get("rsid");
		_delay = (Long) ((JSONObject) params).get("delay");

		setName(Tools.AA + " tag sent to " + _rsid + " after " + _delay + " - " + pageURL);
	}

	@Override
	protected void runTest() throws Throwable {
		// delay
		Thread.sleep(_delay);
		
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
