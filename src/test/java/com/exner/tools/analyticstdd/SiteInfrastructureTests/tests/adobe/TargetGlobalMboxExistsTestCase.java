package com.exner.tools.analyticstdd.SiteInfrastructureTests.tests.adobe;

import java.util.List;

import com.exner.tools.analyticstdd.SiteInfrastructureTests.Tools;
import com.exner.tools.analyticstdd.SiteInfrastructureTests.tests.WebDriverBasedTestCase;
import com.fasterxml.jackson.databind.node.TextNode;

public class TargetGlobalMboxExistsTestCase extends WebDriverBasedTestCase {
	private final String _mboxName;

	public TargetGlobalMboxExistsTestCase(String pageURL, List<String> blockPatterns, Object params) {
		super(pageURL, blockPatterns);

		if (TextNode.class.isAssignableFrom(params.getClass())) {
			_mboxName = ((TextNode) params).asText();
		} else {
			_mboxName = "";
			throw new IllegalArgumentException("Must specify an mbox name");
		}

		setName(Tools.AT + " global mbox " + _mboxName + " exists - " + pageURL);
	}

	@Override
	protected void runTest() throws Throwable {
		// get the value of the dl element from the page
		Object response = _page
				.executeJavaScript(
						"(typeof mboxCurrent != 'undefined' && mboxCurrent.getName() == '" + _mboxName + "')")
				.getJavaScriptResult();

		// make sure the element exists
		if (Boolean.class.isAssignableFrom(response.getClass())) {
			assertTrue(Tools.AT + " global mbox " + _mboxName + " must exist", (Boolean) response);
		} else {
			fail(Tools.AT + " global mbox " + _mboxName + " does not exist");
		}
	}

}
