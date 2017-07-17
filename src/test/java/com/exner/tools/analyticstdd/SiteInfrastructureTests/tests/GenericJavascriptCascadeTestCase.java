package com.exner.tools.analyticstdd.SiteInfrastructureTests.tests;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class GenericJavascriptCascadeTestCase extends WebDriverBasedTestCase {
	private final List<String> _jsToRunForCascade;
	private final String _jsToRunForVerification;
	private final long _delay;

	public GenericJavascriptCascadeTestCase(String pageURL, List<String> blockPatterns, Object params) {
		super(pageURL, blockPatterns);
		setName("Generic JS Cascade - " + pageURL);

		if (!ObjectNode.class.isAssignableFrom(params.getClass())) {
			throw new IllegalArgumentException("Must specify cascade scripts, check script, and delay");
		}
		JsonNode cascadeScripts = ((ObjectNode) params).get("scripts");
		_jsToRunForCascade = new ArrayList<String>();
		if (cascadeScripts.isArray()) {
			for (final JsonNode node : cascadeScripts) {
				_jsToRunForCascade.add(node.asText());
			}
		}
		_jsToRunForVerification = ((ObjectNode) params).get("verificationScript").asText();
		_delay = ((ObjectNode) params).get("delay").asLong();

		setName("Generic JS cascade with " + _delay + "ms wait - " + pageURL);
	}

	@Override
	protected void runTest() throws Throwable {
		// iterate through "first" scripts, including wait after each one
		for (final String cmd : _jsToRunForCascade) {
			_page.executeJavaScript(cmd);
			Thread.sleep(_delay);
		}

		// run second script for verification
		Object response = _page.executeJavaScript(_jsToRunForVerification).getJavaScriptResult();

		if (Boolean.class.isAssignableFrom(response.getClass())) {
			boolean br = (Boolean) response;
			assertTrue("JS must give true", br);
		} else {
			fail("JS must give true!");
		}
	}

}
