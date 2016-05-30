package com.exner.tools.analyticstdd.GenericTests4AnalyticsProject;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.commons.pool2.BasePooledObjectFactory;
import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.impl.DefaultPooledObject;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

public class BasePooledWebDriverFactory extends BasePooledObjectFactory<WebDriver> {
	private final static Logger LOGGER = Logger.getLogger(BasePooledWebDriverFactory.class.getName());

	@Override
	public WebDriver create() throws Exception {
		// create the web driver
		LOGGER.log(Level.INFO, "Creating WebDriver");
		DesiredCapabilities dcap = DesiredCapabilities.phantomjs();
		WebDriver driver = new PhantomJSDriver(dcap);
		return driver;
	}

	@Override
	public PooledObject<WebDriver> wrap(WebDriver webDriver) {
		return new DefaultPooledObject<WebDriver>(webDriver);
	}

	@Override
	public void destroyObject(PooledObject<WebDriver> p) throws Exception {
		LOGGER.log(Level.INFO, "Closing WebDriver");
		WebDriver driver = p.getObject();
		driver.quit();
	}

}
