package com.exner.tools.analyticstdd.SiteInfrastructureTests.tests;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.exner.tools.analyticstdd.SiteInfrastructureTests.WebConnectionThatBlocksTracking;
import com.gargoylesoftware.htmlunit.NicelyResynchronizingAjaxController;
import com.gargoylesoftware.htmlunit.ScriptResult;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

import junit.framework.TestCase;

public abstract class WebDriverBasedTestCase extends TestCase {
	private final static Logger LOGGER = Logger.getLogger(WebDriverBasedTestCase.class.getName());

	protected final String _pageURL;
	protected final List<String> _blockPatterns;

	private WebClient _webClient;
	protected HtmlPage _page;

	protected WebDriverBasedTestCase(String pageURL, List<String> blockPatterns) {
		super();
		_pageURL = pageURL;
		_blockPatterns = blockPatterns;
	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		LOGGER.log(Level.FINE, "Setting up test for " + _pageURL);
		_webClient = new WebClient();
		_webClient.setWebConnection(new WebConnectionThatBlocksTracking(_webClient, _blockPatterns));
		_webClient.waitForBackgroundJavaScriptStartingBefore(10000);
		_webClient.getOptions().setJavaScriptEnabled(true);
		_webClient.getOptions().setThrowExceptionOnScriptError(false);
		_webClient.setAjaxController(new NicelyResynchronizingAjaxController());
		_webClient.getOptions().setTimeout(10000);
		try {
			_page = _webClient.getPage(_pageURL);
			_page.executeJavaScript("localStorage.setItem('sdsat_debug', true);");
			ScriptResult result = _page
					.executeJavaScript("if (typeof _satellite !== 'undefined') { _satellite.setDebug(true); }");
			_page = (HtmlPage) result.getNewPage();
		} catch (Exception e) {
			LOGGER.log(Level.SEVERE, "Page load failed: " + e.getMessage());
		}

	}

	@Override
	protected void tearDown() throws Exception {
		if (null != _webClient) {
			_webClient.close();
			_webClient = null;
		}
		super.tearDown();
	}

}