package com.exner.tools.analyticstdd.SiteInfrastructureTests.tests.adobe;

import com.exner.tools.analyticstdd.SiteInfrastructureTests.Tools;
import com.exner.tools.analyticstdd.SiteInfrastructureTests.tests.WebDriverBasedTestCase;
import com.fasterxml.jackson.databind.node.BooleanNode;

public class DTMLoadedTestCase extends WebDriverBasedTestCase {

	private final boolean _test;
	private final String _note;

	public DTMLoadedTestCase(String pageURL, Object params) {
		super(pageURL);

		// handle parameters
		if (BooleanNode.class.isAssignableFrom(params.getClass())) {
			_test = ((BooleanNode) params).asBoolean();
			if (_test) {
				_note = "";
			} else {
				_note = "not ";
			}
		} else {
			_test = true;
			_note = "";
		}

		setName(Tools.DTM + " " + _note + "loaded" + " - " + pageURL);
	}

	@Override
	protected void runTest() throws Throwable {
		// check whether DTM has been loaded on the page
		String testScript = "(typeof _satellite !== 'undefined')";
		if (!_test) {
			testScript = "(typeof _satellite === 'undefined')";
		}
		Object response = _page.executeJavaScript(testScript).getJavaScriptResult();

		// make sure the element exists
		if (Boolean.class.isAssignableFrom(response.getClass())) {
			assertTrue(Tools.DTM + " must " + _note + "load", (Boolean) response);
		} else {
			fail(Tools.DTM + " " + _note + "not loaded");
		}
	}

}
