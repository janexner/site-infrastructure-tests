package com.exner.tools.analyticstdd.SiteInfrastructureTests.tests.adobe;

import com.exner.tools.analyticstdd.SiteInfrastructureTests.Tools;
import com.exner.tools.analyticstdd.SiteInfrastructureTests.tests.WebDriverBasedTestCase;
import com.fasterxml.jackson.databind.node.TextNode;

public class TargetCodeTypeTestCase extends WebDriverBasedTestCase {
	private final String _libType;

	public TargetCodeTypeTestCase(String pageURL, Object params) {
		super(pageURL);

		if (!TextNode.class.isAssignableFrom(params.getClass())) {
			throw new IllegalArgumentException("Must provide lib type");
		}

		_libType = ((TextNode) params).asText();

		setName(Tools.AT + " code type " + _libType + " - " + pageURL);
	}

	@Override
	protected void runTest() throws Throwable {
		// inject and run JS test
		Object response = _jsExecutor.executeScript(
				"if (typeof mboxVersion !== 'undefined' ) { return 'legacy' } else if (typeof adobe !== 'undefined' && typeof adobe.target !== 'undefined' && typeof adobe.target.VERSION !== 'undefined') { return 'legacy'; } else { return 'none'; }");
		// make sure the element exists
		if (String.class.isAssignableFrom(response.getClass())) {
			assertEquals(Tools.AT + " code type must be " + _libType, _libType, (String) response);
		} else {
			fail(Tools.AT + " code has not been found");
		}
	}

}
