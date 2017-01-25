package com.exner.tools.analyticstdd.SiteInfrastructureTests.tests;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;

import com.fasterxml.jackson.databind.node.TextNode;

public class ElementSelectedByCSSSelectorExistsTestCase extends WebDriverBasedTestCase {
	private final String _elementSelector;

	public ElementSelectedByCSSSelectorExistsTestCase(String pageURL, Object params) {
		super(pageURL);

		if (TextNode.class.isAssignableFrom(params.getClass())) {
			_elementSelector = ((TextNode) params).asText();
		} else {
			_elementSelector = "";
			throw new IllegalArgumentException("You must specify a CSS selector");
		}

		setName("DOM element " + _elementSelector + " exists - " + _pageURL);
	}

	@Override
	protected void runTest() throws Throwable {
		// try finding the element in the page
		WebElement element = null;
		try {
			element = _webDriver.findElement(By.cssSelector(_elementSelector));
		} catch (NoSuchElementException e) {
			// it ain't there
			fail("DOM element " + _elementSelector + " must exist");
		}

		// make sure the element exists
		assertNotNull("DOM element " + _elementSelector + " must exist", element);
	}

}
