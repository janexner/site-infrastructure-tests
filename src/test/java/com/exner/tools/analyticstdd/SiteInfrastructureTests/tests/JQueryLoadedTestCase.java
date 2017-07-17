package com.exner.tools.analyticstdd.SiteInfrastructureTests.tests;

import java.util.List;

import com.exner.tools.analyticstdd.SiteInfrastructureTests.Tools;
import com.fasterxml.jackson.databind.node.BooleanNode;

public class JQueryLoadedTestCase extends WebDriverBasedTestCase {

	private final boolean _test;
	private final String _note;

	public JQueryLoadedTestCase(String pageURL, List<String> blockPatterns, Object params) {
		super(pageURL, blockPatterns);

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

		setName(Tools.JQUERY + " " + _note + "loaded" + " - " + pageURL);
	}

	@Override
	protected void runTest() throws Throwable {
		// check whether jQuery has been loaded on the page
		String testScript = "typeof jQuery !== 'undefined'";
		if (!_test) {
			testScript = "typeof jQuery === 'undefined'";
		}
		Object result = _page.executeJavaScript(testScript).getJavaScriptResult();

		// make sure the element exists
		if (Boolean.class.isAssignableFrom(result.getClass())) {
			assertTrue(Tools.JQUERY + " must " + _note + "load", (Boolean) result);
		} else {
			fail(Tools.JQUERY + " " + _note + "not loaded");
		}
	}

}
