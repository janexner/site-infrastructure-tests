package com.exner.tools.analyticstdd.GenericTests4AnalyticsProject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class PageTestDefinition implements Serializable {

	private static final long serialVersionUID = 2L;

	private String _pageURL;
	private List<String> _dataLayerElementsThatMustExist;
	private List<Map<String, String>> _dataLayerElementsThatMustHaveSpecificValue;
	private List<String> _pageLoadRulesThatMustExist;
	private List<String> _pageLoadRulesThatMustHaveRun;
	private List<String> _directCallRulesThatMustExist;
	private List<String> _eventBasedRulesThatMustExist;

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

	public void createFromJSON(JSONObject jsonObject) {
		// get the pageURL
		String pageURL = (String) jsonObject.get("pageURL");
		setPageURL(pageURL);

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
	}

}
