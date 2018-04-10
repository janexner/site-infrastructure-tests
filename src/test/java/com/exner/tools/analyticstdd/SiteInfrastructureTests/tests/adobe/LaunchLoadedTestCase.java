package com.exner.tools.analyticstdd.SiteInfrastructureTests.tests.adobe;

import com.exner.tools.analyticstdd.SiteInfrastructureTests.Tools;
import com.exner.tools.analyticstdd.SiteInfrastructureTests.tests.WebDriverBasedTestCase;
import com.fasterxml.jackson.databind.node.BooleanNode;

public class LaunchLoadedTestCase extends WebDriverBasedTestCase {

	private final boolean _test;
	private final String _note;

	public LaunchLoadedTestCase(String pageURL, Object params) {
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

		setName(Tools.LAUNCH + " " + _note + "loaded" + " - " + pageURL);
	}

	@Override
	protected void runTest() throws Throwable {
		// check whether DTM has been loaded on the page
		Object response = _jsExecutor
					.executeScript("if (typeof _satellite !== 'undefined') { return " + String.valueOf(_test) + " } else { return " + String.valueOf(!_test) + " }");

		// make sure the element exists
		if (Boolean.class.isAssignableFrom(response.getClass())) {
			assertTrue(Tools.LAUNCH + " must " + _note + "load", (Boolean) response);
		} else {
			fail(Tools.LAUNCH + " " + _note + "not loaded");
		}
	}

}
