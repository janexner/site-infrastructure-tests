package com.exner.tools.analyticstdd.GenericTests4AnalyticsProject.tests;

public class DataLayerElementValueTestCase extends WebDriverBasedTestCase {
	private final String _elementName;
	private String _elementExpectedValue;

	public DataLayerElementValueTestCase(String pageURL, String elementName, String elementExpectedValue) {
		super(pageURL);
		_elementName = elementName;
		_elementExpectedValue = elementExpectedValue;
		setName("Data Layer element " + elementName + " value is " + _elementExpectedValue + " - " + pageURL);
	}

	@Override
	protected void runTest() throws Throwable {
		// TODO - maybe test for undefined first!
		// get the value of the dl element from the page
		Object response = _jsExecutor.executeScript("return " + _elementName);

		// make sure the element exists
		if (String.class.isAssignableFrom(response.getClass())) {
			assertEquals("Data Layer element " + _elementName + " value should be " + _elementExpectedValue,
					_elementExpectedValue, (String) response);
		} else {
			fail("Data Layer element " + _elementName + " does not exist");
		}

	}
}
