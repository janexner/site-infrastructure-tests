package com.exner.tools.analyticstdd.SiteInfrastructureTests.tests;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;

import com.fasterxml.jackson.databind.node.ObjectNode;

public class ElementSelectedByCSSSelectorExistsNTimesTestCase extends WebDriverBasedTestCase {
	private final String _elementSelector;
	private final long _n;

	public ElementSelectedByCSSSelectorExistsNTimesTestCase(String pageURL, Object params) {
		super(pageURL);

		if (!ObjectNode.class.isAssignableFrom(params.getClass())) {
			throw new IllegalArgumentException("Must specify selector and n");
		}

		_elementSelector = ((ObjectNode) params).get("selector").asText();
		_n = ((ObjectNode) params).get("n").asLong();

		setName("DOM element " + _elementSelector + " exists " + _n + " times - " + _pageURL);
	}

	@Override
	protected void runTest() throws Throwable {
		// try finding the element in the page
		List<WebElement> element = null;
		try {
			element = _webDriver.findElements(By.cssSelector(_elementSelector));
		} catch (NoSuchElementException e) {
			// it ain't there
			fail("DOM element " + _elementSelector + " must exist " + _n + " times");
		}

		// make sure the element exists
		assertEquals("DOM element " + _elementSelector + " must exist " + _n + " times", _n, element.size());
	}

}
