package com.exner.tools.analyticstdd.GenericTests4AnalyticsProject;

public class DataElementExistenceTestCase extends WebDriverBasedTestCase {
	private final String _elementName;

	public DataElementExistenceTestCase(String pageURL, String elementName) {
		super(pageURL);
		_elementName = elementName;
		setName(elementName + " existence");
	}

	@Override
	protected void runTest() throws Throwable {
		// get the value of the dl element from the page
		Object response = _jsExecutor.executeScript("if (typeof _satellite.getVar('" + _elementName
				+ "') !== 'undefined') { return true } else { return false }");

		// make sure the element exists
		if (Boolean.class.isAssignableFrom(response.getClass())) {
			assertTrue("Data Element " + _elementName + " must exist", (Boolean) response);
		} else {
			fail("Data Element " + _elementName + " does not exist");
		}

	}

}
