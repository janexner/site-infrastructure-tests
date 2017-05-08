package com.exner.tools.analyticstdd.SiteInfrastructureTests;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.commons.pool2.BasePooledObjectFactory;
import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.impl.DefaultPooledObject;
import org.openqa.selenium.Proxy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriverService;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;

import io.netty.handler.codec.http.DefaultHttpResponse;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.HttpResponse;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.HttpVersion;
import net.lightbody.bmp.BrowserMobProxy;
import net.lightbody.bmp.BrowserMobProxyServer;
import net.lightbody.bmp.client.ClientUtil;
import net.lightbody.bmp.filters.RequestFilter;
import net.lightbody.bmp.util.HttpMessageContents;
import net.lightbody.bmp.util.HttpMessageInfo;

public class BasePooledWebDriverFactory extends BasePooledObjectFactory<WebDriver> {
	private final static Logger LOGGER = Logger.getLogger(BasePooledWebDriverFactory.class.getName());

	private final boolean _useProxy;
	private final BrowserMobProxy _proxy;
	private final Proxy _seleniumProxy;

	public BasePooledWebDriverFactory(SuppressAdobeAnalyticsOptions suppressAdobeAnalyticsTracking) {
		super();

		// check whether we'll use a proxy or not
		if (SuppressAdobeAnalyticsOptions.NeverSuppress == suppressAdobeAnalyticsTracking) {
			_useProxy = false;

			// no need
			_proxy = null;
			_seleniumProxy = null;
		} else {
			_useProxy = true;

			// create a proxy for the web drivers
			LOGGER.log(Level.INFO, "Creating Proxy");
			_proxy = new BrowserMobProxyServer();
			_proxy.addRequestFilter(new RequestFilter() {

				@Override
				public HttpResponse filterRequest(HttpRequest request, HttpMessageContents contents,
						HttpMessageInfo messageInfo) {
					String oURL = messageInfo.getOriginalUrl();
					if (oURL.contains("b/ss") || oURL.contains("mboxhost") || oURL.contains("demdex") || oURL.contains("omtrdc.net")) {
						LOGGER.log(Level.INFO, "Skipping call to " + oURL);
						return new DefaultHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK);
					}
					return null;
				}
			});
			_proxy.start(0);

			_seleniumProxy = ClientUtil.createSeleniumProxy(_proxy);
		}

	}

	@Override
	public WebDriver create() throws Exception {

		// configure it as a desired capability
		DesiredCapabilities dcap = DesiredCapabilities.phantomjs();
		if (_useProxy) {
			dcap.setCapability(CapabilityType.PROXY, _seleniumProxy);
			dcap.setJavascriptEnabled(true);
			dcap.setCapability(PhantomJSDriverService.PHANTOMJS_CLI_ARGS, new String[] {
					"--ssl-client-certificate-file=C:\\tmp\\ca-certificate-rsa.cer", "--ignore-ssl-errors=yes" });
		}

		// start the browser up
		LOGGER.log(Level.INFO, "Creating WebDriver");
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
