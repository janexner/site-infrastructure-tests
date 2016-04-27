package com.exner.tools.analyticstdd.GenericTests4AnalyticsProject.tests;

public class DataLayerElementExistenceTestCase extends WebDriverBasedTestCase {
	private String _elementName;

	public DataLayerElementExistenceTestCase(String pageURL, String elementName) {
		super(pageURL);
		_elementName = elementName;
		setName("Data Layer element " + elementName + " exists - " + pageURL);
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
