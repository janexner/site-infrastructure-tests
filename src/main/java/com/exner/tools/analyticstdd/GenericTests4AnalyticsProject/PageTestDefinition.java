package com.exner.tools.analyticstdd.GenericTests4AnalyticsProject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class PageTestDefinition implements Serializable {

	private static final long serialVersionUID = 6L;

	private String _pageURL;
	private List<String> _dataLayerElementsThatMustExist;
	private List<Map<String, String>> _dataLayerElementsThatMustHaveSpecificValue;
	private List<StringStringLongTuple> _dataLayerElementsThatMustHaveSpecificValueAfterDelay;
	private List<StringLongTuple> _dataLayerElementsThatMustExistAfterDelay;
	private List<String> _pageLoadRulesThatMustExist;
	private List<String> _pageLoadRulesThatMustHaveRun;
	private List<String> _directCallRulesThatMustExist;
	private List<String> _eventBasedRulesThatMustExist;
	private List<Map<String, String>> _eventBasedRulesThatMustFire;
	private List<String> _reportSuiteIDsThatMustReceiveTags;
	private boolean _dtmLoaded;
	private boolean _dtmInDebugMode;
	private List<Map<String, String>> _dataElementsThatMustHaveASpecificValue;
	private List<StringStringLongTuple> _dataElementsThatMustHaveSpecificValueAfterDelay;

	public String getPageURL() {
		return _pageURL;
	}

	public void setPageURL(String pageURL) {
		_pageURL = pageURL;
	}

	public List<String> getDataLayerElementsThatMustExist() {
		return _dataLayerElementsThatMustExist;
	}

	public void setDataLayerElementsThatMustExist(List<String> dataElementsThatMustExist) {
		_dataLayerElementsThatMustExist = dataElementsThatMustExist;
	}

	public List<Map<String, String>> getDataLayerElementsThatMustHaveSpecificValue() {
		return _dataLayerElementsThatMustHaveSpecificValue;
	}

	public void setDataLayerElementsThatMustHaveSpecificValue(
			List<Map<String, String>> dataElementsThatMustHaveSpecificValue) {
		_dataLayerElementsThatMustHaveSpecificValue = dataElementsThatMustHaveSpecificValue;
	}

	public List<StringStringLongTuple> getDataLayerElementsThatMustHaveSpecificValueAfterDelay() {
		return _dataLayerElementsThatMustHaveSpecificValueAfterDelay;
	}

	public void setDataLayerElementsThatMustHaveSpecificValueAfterDelay(
			List<StringStringLongTuple> dataLayerElementsThatMustHaveSpecificValueAfterDelay) {
		_dataLayerElementsThatMustHaveSpecificValueAfterDelay = dataLayerElementsThatMustHaveSpecificValueAfterDelay;
	}

	public List<StringLongTuple> getDataLayerElementsThatMustExistAfterDelay() {
		return _dataLayerElementsThatMustExistAfterDelay;
	}

	public void setDataLayerElementsThatMustExistAfterDelay(
			List<StringLongTuple> dataLayerElementsThatMustExistAfterDelay) {
		_dataLayerElementsThatMustExistAfterDelay = dataLayerElementsThatMustExistAfterDelay;
	}

	public List<String> getPageLoadRulesThatMustExist() {
		return _pageLoadRulesThatMustExist;
	}

	public void setPageLoadRulesThatMustExist(List<String> pageLoadRulesThatMustExist) {
		_pageLoadRulesThatMustExist = pageLoadRulesThatMustExist;
	}

	public List<String> getPageLoadRulesThatMustHaveRun() {
		return _pageLoadRulesThatMustHaveRun;
	}

	public void setPageLoadRulesThatMustHaveRun(List<String> pageLoadRulesThatMustHaveRun) {
		_pageLoadRulesThatMustHaveRun = pageLoadRulesThatMustHaveRun;
	}

	public List<String> getDirectCallRulesThatMustExist() {
		return _directCallRulesThatMustExist;
	}

	public void setDirectCallRulesThatMustExist(List<String> directCallRulesThatMustExist) {
		_directCallRulesThatMustExist = directCallRulesThatMustExist;
	}

	public List<String> getEventBasedRulesThatMustExist() {
		return _eventBasedRulesThatMustExist;
	}

	public void setEventBasedRulesThatMustExist(List<String> eventBasedRulesThatMustExist) {
		_eventBasedRulesThatMustExist = eventBasedRulesThatMustExist;
	}

	public List<Map<String, String>> getEventBasedRulesThatMustFire() {
		return _eventBasedRulesThatMustFire;
	}

	public void setEventBasedRulesThatMustFire(List<Map<String, String>> eventBasedRulesThatMustFire) {
		_eventBasedRulesThatMustFire = eventBasedRulesThatMustFire;
	}

	public List<String> getReportSuiteIDsThatMustReceiveTags() {
		return _reportSuiteIDsThatMustReceiveTags;
	}

	public void setReportSuiteIDsThatMustReceiveTags(List<String> reportSuiteIDsThatMustReceiveTags) {
		_reportSuiteIDsThatMustReceiveTags = reportSuiteIDsThatMustReceiveTags;
	}

	public boolean isDtmLoaded() {
		return _dtmLoaded;
	}

	public void setDtmLoaded(boolean dtmLoaded) {
		this._dtmLoaded = dtmLoaded;
	}

	public boolean isDtmInDebugMode() {
		return _dtmInDebugMode;
	}

	public List<Map<String, String>> getDataElementsThatMustHaveASpecificValue() {
		return _dataElementsThatMustHaveASpecificValue;
	}

	public void setDataElementsThatMustHaveASpecificValue(
			List<Map<String, String>> dataElementsThatMustHaveASpecificValue) {
		this._dataElementsThatMustHaveASpecificValue = dataElementsThatMustHaveASpecificValue;
	}

	public List<StringStringLongTuple> getDataElementsThatMustHaveSpecificValueAfterDelay() {
		return _dataElementsThatMustHaveSpecificValueAfterDelay;
	}

	public void setDataElementsThatMustHaveSpecificValueAfterDelay(
			List<StringStringLongTuple> dataElementsThatMustHaveSpecificValueAfterDelay) {
		this._dataElementsThatMustHaveSpecificValueAfterDelay = dataElementsThatMustHaveSpecificValueAfterDelay;
	}

	public void setDtmInDebugMode(boolean dtmInDebugMode) {
		this._dtmInDebugMode = dtmInDebugMode;
	}

	public void createFromJSON(JSONObject jsonObject) {
		// get the pageURL
		String pageURL = (String) jsonObject.get("pageURL");
		setPageURL(pageURL);

		// check whether DTM should be tests
		JSONObject dtmStuff = (JSONObject) jsonObject.get("tagManagementDTMActive");
		if (null != dtmStuff) {
			boolean testDTMLoaded = (Boolean) dtmStuff.get("testLoaded");
			setDtmLoaded(testDTMLoaded);
			boolean testDTMInDebugMode = (Boolean) dtmStuff.get("testDebugMode");
			setDtmInDebugMode(testDTMInDebugMode);
		} else {
			setDtmLoaded(false);
			setDtmInDebugMode(false);
		}

		// get the list of data layer elements that must exist
		List<String> dlemx = new ArrayList<String>();
		JSONArray dlemxlist = (JSONArray) jsonObject.get("dataLayerElementsThatMustExist");
		if (null != dlemxlist) {
			for (Iterator<String> iterator = dlemxlist.iterator(); iterator.hasNext();) {
				dlemx.add(iterator.next());
			}
		}
		setDataLayerElementsThatMustExist(dlemx);

		// get the list of data layer elements that must have a specific value
		List<Map<String, String>> dlemv = new ArrayList<Map<String, String>>();
		JSONArray dlemvlist = (JSONArray) jsonObject.get("dataLayerElementsThatMustHaveASpecificValue");
		if (null != dlemvlist) {
			for (Iterator<Map<String, String>> iterator = dlemvlist.iterator(); iterator.hasNext();) {
				Map<String, String> el = iterator.next();
				dlemv.add(el);
			}
		}
		setDataLayerElementsThatMustHaveSpecificValue(dlemv);

		// get the list of data layer elements that must have a specific value
		// after delay
		List<StringStringLongTuple> dlemvdd = new ArrayList<StringStringLongTuple>();
		JSONArray dlemvddlist = (JSONArray) jsonObject.get("dataLayerElementsThatMustHaveASpecificValueAfterDelay");
		if (null != dlemvddlist) {
			for (Iterator<JSONObject> iterator = dlemvddlist.iterator(); iterator.hasNext();) {
				JSONObject el = iterator.next();
				StringStringLongTuple el2 = new StringStringLongTuple();
				el2.setName((String) el.get("name"));
				el2.setValue((String) el.get("value"));
				el2.setDelay((Long) el.get("delay"));
				dlemvdd.add(el2);
			}
		}
		setDataLayerElementsThatMustHaveSpecificValueAfterDelay(dlemvdd);

		// get the list of data layer elements that must exist after a delay
		List<StringLongTuple> dlemvd = new ArrayList<StringLongTuple>();
		JSONArray dlemvdlist = (JSONArray) jsonObject.get("dataLayerElementsThatMustExistAfterDelay");
		if (null != dlemvdlist) {
			for (Iterator<JSONObject> iterator = dlemvdlist.iterator(); iterator.hasNext();) {
				JSONObject el = iterator.next();
				StringLongTuple el2 = new StringLongTuple();
				el2.setName((String) el.get("name"));
				el2.setDelay((Long) el.get("delay"));
				dlemvd.add(el2);
			}
		}
		setDataLayerElementsThatMustExistAfterDelay(dlemvd);

		// get the list of PLRs that must exist
		List<String> plrmx = new ArrayList<String>();
		JSONArray plrmxlist = (JSONArray) jsonObject.get("pageLoadRulesThatMustExist");
		if (null != plrmxlist) {
			for (Iterator<String> iterator = plrmxlist.iterator(); iterator.hasNext();) {
				plrmx.add(iterator.next());
			}
		}
		setPageLoadRulesThatMustExist(plrmx);

		// get the list of PLRs that must fire
		List<String> plrmr = new ArrayList<String>();
		JSONArray plrmrlist = (JSONArray) jsonObject.get("pageLoadRulesThatMustHaveRun");
		if (null != plrmrlist) {
			for (Iterator<String> iterator = plrmrlist.iterator(); iterator.hasNext();) {
				plrmr.add(iterator.next());
			}
		}
		setPageLoadRulesThatMustHaveRun(plrmr);

		// get the list of EBRs that must exist
		List<String> ebrmx = new ArrayList<String>();
		JSONArray ebrmxlist = (JSONArray) jsonObject.get("eventBasedRulesThatMustExist");
		if (null != ebrmxlist) {
			for (Iterator<String> iterator = ebrmxlist.iterator(); iterator.hasNext();) {
				ebrmx.add(iterator.next());
			}
		}
		setEventBasedRulesThatMustExist(ebrmx);

		// get the list of DCRs that must exist
		List<String> dcrmx = new ArrayList<String>();
		JSONArray dcrmxlist = (JSONArray) jsonObject.get("directCallRulesThatMustExist");
		if (null != dcrmxlist) {
			for (Iterator<String> iterator = dcrmxlist.iterator(); iterator.hasNext();) {
				dcrmx.add(iterator.next());
			}
		}
		setDirectCallRulesThatMustExist(dcrmx);

		// get list of EBRs that must fire
		List<Map<String, String>> ebrmf = new ArrayList<Map<String, String>>();
		JSONArray ebrmflist = (JSONArray) jsonObject.get("eventBasedRulesThatMustFire");
		if (null != ebrmflist) {
			for (Iterator<Map<String, String>> iterator = ebrmflist.iterator(); iterator.hasNext();) {
				Map<String, String> el = iterator.next();
				ebrmf.add(el);
			}
		}
		setEventBasedRulesThatMustFire(ebrmf);

		// get the list of rsids that must receive a tag
		List<String> rsidstmrt = new ArrayList<String>();
		JSONArray rsidstmrtlist = (JSONArray) jsonObject.get("analyticsTagForReportSuiteFired");
		if (null != rsidstmrtlist) {
			for (Iterator<String> iterator = rsidstmrtlist.iterator(); iterator.hasNext();) {
				String el = iterator.next();
				rsidstmrt.add(el);
			}
		}
		setReportSuiteIDsThatMustReceiveTags(rsidstmrt);

		// get the list of dtm data elements that must have a specific value
		List<Map<String, String>> demv = new ArrayList<Map<String, String>>();
		JSONArray demvlist = (JSONArray) jsonObject.get("dataElementsThatMustHaveASpecificValue");
		if (null != demvlist) {
			for (Iterator<Map<String, String>> iterator = demvlist.iterator(); iterator.hasNext();) {
				Map<String, String> el = iterator.next();
				demv.add(el);
			}
		}
		setDataElementsThatMustHaveASpecificValue(demv);

		// get the list of dtm data elements that must have a specific value
		// after delay
		List<StringStringLongTuple> demvdd = new ArrayList<StringStringLongTuple>();
		JSONArray demvddlist = (JSONArray) jsonObject.get("dataElementsThatMustHaveASpecificValueAfterDelay");
		if (null != demvddlist) {
			for (Iterator<JSONObject> iterator = demvddlist.iterator(); iterator.hasNext();) {
				JSONObject el = iterator.next();
				StringStringLongTuple el2 = new StringStringLongTuple();
				el2.setName((String) el.get("name"));
				el2.setValue((String) el.get("value"));
				el2.setDelay((Long) el.get("delay"));
				demvdd.add(el2);
			}
		}
		setDataElementsThatMustHaveSpecificValueAfterDelay(demvdd);

}

}
