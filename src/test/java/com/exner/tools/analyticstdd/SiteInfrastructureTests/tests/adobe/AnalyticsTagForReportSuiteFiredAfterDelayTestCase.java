package com.exner.tools.analyticstdd.SiteInfrastructureTests.tests.adobe;

import java.util.List;

import com.exner.tools.analyticstdd.SiteInfrastructureTests.Tools;
import com.exner.tools.analyticstdd.SiteInfrastructureTests.tests.WebDriverBasedTestCase;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class AnalyticsTagForReportSuiteFiredAfterDelayTestCase extends WebDriverBasedTestCase {
	private final String _rsid;
	private final long _delay;

	public AnalyticsTagForReportSuiteFiredAfterDelayTestCase(String pageURL, List<String> blockPatterns, Object params) {
		super(pageURL, blockPatterns);

		if (!ObjectNode.class.isAssignableFrom(params.getClass())) {
			throw new IllegalArgumentException("Must specify rsid and delay");
		}
		_rsid = ((ObjectNode) params).get("rsid").asText();
		_delay = ((ObjectNode) params).get("delay").asLong();

		setName(Tools.AA + " tag sent to " + _rsid + " after " + _delay + " - " + pageURL);
	}

	@Override
	protected void runTest() throws Throwable {
		// delay
		Thread.sleep(_delay);

		// replace "," between rsids with "_"
		String rsid = _rsid.replace(',', '_');

		// check whether Analytics code has been loaded on the page
		Object response = _page
				.executeJavaScript("var rstest='" + rsid
						+ "';var rsprfx='s_i_';var retVal=false;for(var b in window){if(window['hasOwnProperty'](b)){if(b['indexOf'](rsprfx)===0){rsarr=b['substr'](rsprfx['length'],b['length'])['split']('_');if(rsarr['indexOf'](rstest)>=0){retVal=true}}}};retVal")
				.getJavaScriptResult();

		// make sure the element exists
		if (Boolean.class.isAssignableFrom(response.getClass())) {
			assertTrue(Tools.AA + " tag to " + _rsid + " must fire", (Boolean) response);
		} else {
			fail(Tools.AA + " tag to " + _rsid + " has not fired");
		}

	}

}
