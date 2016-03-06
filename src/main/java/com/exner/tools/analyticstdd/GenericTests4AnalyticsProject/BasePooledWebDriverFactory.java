package com.exner.tools.analyticstdd.GenericTests4AnalyticsProject;

import org.apache.commons.pool2.BasePooledObjectFactory;
import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.impl.DefaultPooledObject;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriver;

public class BasePooledWebDriverFactory extends BasePooledObjectFactory<WebDriver> {

	@Override
	public WebDriver create() throws Exception {
		// create the web driver
		WebDriver driver = new PhantomJSDriver();
		return driver;
	}

	@Override
	public PooledObject<WebDriver> wrap(WebDriver webDriver) {
		return new DefaultPooledObject<WebDriver>(webDriver);
	}

	@Override
	public void destroyObject(PooledObject<WebDriver> p) throws Exception {
		WebDriver driver = p.getObject();
		driver.close();
	}

}
