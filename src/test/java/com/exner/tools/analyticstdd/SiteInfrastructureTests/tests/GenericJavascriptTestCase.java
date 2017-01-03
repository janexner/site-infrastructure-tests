package com.exner.tools.analyticstdd.SiteInfrastructureTests.tests;

public class GenericJavascriptTestCase extends WebDriverBasedTestCase {
	private final String _jsToRun;

	public GenericJavascriptTestCase(String pageURL, Object params) {
		super(pageURL);
		setName("Generic JS - " + pageURL);
		
		if (String.class.isAssignableFrom(params.getClass())) {
			_jsToRun = (String) params;
		} else {
			throw new IllegalArgumentException("Must define some script to run!");
		}
	}

	@Override
	protected void runTest() throws Throwable {
		Object response = _jsExecutor.executeScript(_jsToRun);
		
		if (Boolean.class.isAssignableFrom(response.getClass())) {
			boolean br = (Boolean) response;
			assertTrue("JS must return true", br);
		} else {
			fail("JS must return true!");
		}
	}

}
