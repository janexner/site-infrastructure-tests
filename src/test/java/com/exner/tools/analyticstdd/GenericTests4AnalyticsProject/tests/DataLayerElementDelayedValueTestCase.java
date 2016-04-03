package com.exner.tools.analyticstdd.GenericTests4AnalyticsProject.tests;

public class DataLayerElementDelayedValueTestCase extends WebDriverBasedTestCase {
	private final String _elementName;
	private String _elementExpectedValue;
	private long _delay;

	public DataLayerElementDelayedValueTestCase(String pageURL, String elementName, String elementExpectedValue,
			long delay) {
		super(pageURL);
		_elementName = elementName;
		_elementExpectedValue = elementExpectedValue;
		_delay = delay;
		setName(elementName + " delayed value - " + pageURL);
	}

	@Override
	protected void runTest() throws Throwable {
		// TODO - maybe test for undefined first!
		// sleep
		Thread.sleep(_delay);

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
