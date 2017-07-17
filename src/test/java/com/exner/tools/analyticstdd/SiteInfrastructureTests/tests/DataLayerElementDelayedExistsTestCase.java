package com.exner.tools.analyticstdd.SiteInfrastructureTests.tests;

import java.util.List;

import org.openqa.selenium.WebDriverException;

import com.fasterxml.jackson.databind.node.ObjectNode;

public class DataLayerElementDelayedExistsTestCase extends WebDriverBasedTestCase {
	private final String _elementName;
	private final long _milliseconds;

	public DataLayerElementDelayedExistsTestCase(String pageURL, List<String> blockPatterns, Object params) {
		super(pageURL, blockPatterns);

		if (!ObjectNode.class.isAssignableFrom(params.getClass())) {
			throw new IllegalArgumentException("Must specify name and delay");
		}
		_elementName = ((ObjectNode) params).get("name").asText();
		_milliseconds = ((ObjectNode) params).get("delay").asLong();

		setName("Data Layer element" + _elementName + " exists after " + _milliseconds + "ms - " + pageURL);
	}

	@Override
	protected void runTest() throws Throwable {
		// wait
		Thread.sleep(_milliseconds);

		try {
			// get the value of the dl element from the page
			Object response = _page.executeJavaScript("(typeof " + _elementName + " !== 'undefined')")
					.getJavaScriptResult();

			// make sure the element exists
			if (Boolean.class.isAssignableFrom(response.getClass())) {
				assertTrue("Data Layer element " + _elementName + " must exist after " + _milliseconds + "ms",
						(Boolean) response);
			} else {
				fail("Data Layer element " + _elementName + " does not exist");
			}
		} catch (WebDriverException we) {
			fail("Data Layer element " + _elementName + " does not exist");
		}

	}

}
