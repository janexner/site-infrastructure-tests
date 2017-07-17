package com.exner.tools.analyticstdd.SiteInfrastructureTests.tests;

import com.fasterxml.jackson.databind.node.ObjectNode;

public class GenericJavascriptSetupAndCheckLaterTestCase extends WebDriverBasedTestCase {
	private final String _jsToRunForSetup;
	private final String _jsToRunForVerification;
	private final long _delay;

	public GenericJavascriptSetupAndCheckLaterTestCase(String pageURL, Object params) {
		super(pageURL);
		setName("Generic JS - " + pageURL);

		if (!ObjectNode.class.isAssignableFrom(params.getClass())) {
			throw new IllegalArgumentException("Must specify setup script, check script, and delay");
		}
		_jsToRunForSetup = ((ObjectNode) params).get("setupScript").asText();
		_jsToRunForVerification = ((ObjectNode) params).get("verificationScript").asText();
		_delay = ((ObjectNode) params).get("delay").asLong();

		setName("Generic JS in 2 steps with " + _delay + "ms wait - " + pageURL);
	}

	@Override
	protected void runTest() throws Throwable {
		// run first script to perform some setup
		Object response = _page.executeJavaScript(_jsToRunForSetup);
		// wait a tick
		Thread.sleep(_delay);
		// run second script for verification
		response = _page.executeJavaScript(_jsToRunForVerification).getJavaScriptResult();

		if (Boolean.class.isAssignableFrom(response.getClass())) {
			boolean br = (Boolean) response;
			assertTrue("JS must give true", br);
		} else {
			fail("JS must give true!");
		}
	}

}
