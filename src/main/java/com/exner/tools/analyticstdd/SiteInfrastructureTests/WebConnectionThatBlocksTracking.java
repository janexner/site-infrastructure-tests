package com.exner.tools.analyticstdd.SiteInfrastructureTests;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.WebRequest;
import com.gargoylesoftware.htmlunit.WebResponse;
import com.gargoylesoftware.htmlunit.WebResponseData;
import com.gargoylesoftware.htmlunit.util.NameValuePair;
import com.gargoylesoftware.htmlunit.util.WebConnectionWrapper;

public class WebConnectionThatBlocksTracking extends WebConnectionWrapper {
	private final static Logger LOGGER = Logger.getLogger(WebConnectionThatBlocksTracking.class.getName());

	final List<String> _blockPatterns;

	public WebConnectionThatBlocksTracking(WebClient webClient) throws IllegalArgumentException {
		super(webClient);
		_blockPatterns = null;
	}

	public WebConnectionThatBlocksTracking(WebClient webClient, List<String> blockPatterns) {
		super(webClient);
		_blockPatterns = blockPatterns;
	}

	@Override
	public WebResponse getResponse(WebRequest request) throws IOException {
		WebResponse response;
		if (null != _blockPatterns && matchesPatterns(request.getUrl().toExternalForm())) {
			LOGGER.log(Level.INFO,
					"Not requesting URL " + request.getUrl().toExternalForm() + ", returning 200 instead.");
			// this is a tracking call. We'll deliver a fake 200 OK
			String content = "All good";
			NameValuePair nvp = new NameValuePair("Link", request.getUrl().toExternalForm());
			List<NameValuePair> headers = new ArrayList<NameValuePair>();
			headers.add(nvp);
			WebResponseData data = new WebResponseData(content.getBytes(), 200, "OK", headers);
			response = new WebResponse(data, request, 0);
		} else {
			response = super.getResponse(request);
		}
		return response;
	}

	private boolean matchesPatterns(String url) {
		for (Iterator<String> iter = _blockPatterns.iterator(); iter.hasNext();) {
			String pattern = iter.next();
			if (url.contains(pattern)) {
				// got it!
				return true;
			}
		}
		return false;
	}
}
