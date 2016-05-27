package com.exner.tools.analyticstdd.GenericTests4AnalyticsProject.tests;

public class DataLayerElementExistsTestCase extends WebDriverBasedTestCase {
	private final String _elementName;

	public DataLayerElementExistsTestCase(String pageURL, Object params) {
		super(pageURL);

		if (String.class.isAssignableFrom(params.getClass())) {
			_elementName = (String) params;
		} else {
			_elementName = "";
			throw new IllegalArgumentException("Must specify an element");
		}

		setName("Data Layer element " + _elementName + " exists - " + pageURL);
	}

	@Override
	protected void runTest() throws Throwable {
		// get the value of the dl element from the page
		Object response = _jsExecutor.executeScript(
				"if (typeof " + _elementName + " !== 'undefined') { return true } else { return false }");

		// make sure the element exists
		if (Boolean.class.isAssignableFrom(response.getClass())) {
			assertTrue("Data Layer element " + _elementName + " must exist", (Boolean) response);
		} else {
			fail("Data Layer element " + _elementName + " does not exist");
		}
	}

}
