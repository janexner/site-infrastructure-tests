package com.exner.tools.analyticstdd.SiteInfrastructureTests.tests;

import java.util.List;

import org.openqa.selenium.NoSuchElementException;

import com.fasterxml.jackson.databind.node.TextNode;

import se.fishtank.css.selectors.Selectors;
import se.fishtank.css.selectors.dom.W3CNode;

public class ElementSelectedByCSSSelectorExistsTestCase extends WebDriverBasedTestCase {
	private final String _elementSelector;

	public ElementSelectedByCSSSelectorExistsTestCase(String pageURL, List<String> blockPatterns, Object params) {
		super(pageURL, blockPatterns);

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
		Object element = null;
		try {
			Selectors selectors = new Selectors(new W3CNode(_page));
			element = selectors.querySelector(_elementSelector);
		} catch (NoSuchElementException e) {
			// it ain't there
			fail("DOM element " + _elementSelector + " must exist");
		}

		// make sure the element exists
		assertNotNull("DOM element " + _elementSelector + " must exist", element);
	}

}
