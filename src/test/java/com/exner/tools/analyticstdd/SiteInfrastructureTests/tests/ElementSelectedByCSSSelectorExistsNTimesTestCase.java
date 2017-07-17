package com.exner.tools.analyticstdd.SiteInfrastructureTests.tests;

import java.util.List;

import org.openqa.selenium.NoSuchElementException;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.gargoylesoftware.htmlunit.javascript.host.dom.Node;

import se.fishtank.css.selectors.Selectors;
import se.fishtank.css.selectors.dom.DOMNode;
import se.fishtank.css.selectors.dom.W3CNode;

public class ElementSelectedByCSSSelectorExistsNTimesTestCase extends WebDriverBasedTestCase {
	private final String _elementSelector;
	private final long _n;

	public ElementSelectedByCSSSelectorExistsNTimesTestCase(String pageURL, List<String> blockPatterns, Object params) {
		super(pageURL, blockPatterns);

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
		List<Node> element = null;
		try {
			Selectors selectors = new Selectors(new W3CNode(_page));
			element = (List<Node>) selectors.querySelectorAll(_elementSelector);
		} catch (NoSuchElementException e) {
			// it ain't there
			fail("DOM element " + _elementSelector + " must exist " + _n + " times");
		}

		// make sure the element exists
		assertEquals("DOM element " + _elementSelector + " must exist " + _n + " times", _n, element.size());
	}

}
