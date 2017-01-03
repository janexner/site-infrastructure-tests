package com.exner.tools.analyticstdd.SiteInfrastructureTests.tests;

import java.util.List;

import org.json.simple.JSONObject;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;

public class ElementSelectedByCSSSelectorExistsNTimesTestCase extends WebDriverBasedTestCase {
	private final String _elementSelector;
	private final int _n;

	public ElementSelectedByCSSSelectorExistsNTimesTestCase(String pageURL, Object params) {
		super(pageURL);

		if (!JSONObject.class.isAssignableFrom(params.getClass())) {
			throw new IllegalArgumentException("Must specify selector and n");
		}

		_elementSelector = (String) ((JSONObject) params).get("selector");
		_n = (Integer) ((JSONObject) params).get("n");

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
			fail("DOM element " + _elementSelector + " must exist");
		}

		// make sure the element exists
		assertEquals("DOM element " + _elementSelector + " must exist " + _n + " times", _n, element.size());
	}

}
