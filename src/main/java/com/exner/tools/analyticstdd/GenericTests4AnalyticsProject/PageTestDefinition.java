package com.exner.tools.analyticstdd.GenericTests4AnalyticsProject;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class PageTestDefinition implements Serializable {

	private static final long serialVersionUID = 2L;

	private String _pageURL;
	private List<String> _dataLayerElementsThatMustExist;
	private List<Map<String, String>> _dataLayerElementsThatMustHaveSpecificValue;
	private List<String> _pageLoadRulesThatMustExist;
	private List<String> _pageLoadRulesThatMustHaveRun;

	public String getPageURL() {
		return _pageURL;
	}

	public void setPageURL(String pageURL) {
		_pageURL = pageURL;
	}

	public List<String> getDataElementsThatMustExist() {
		return _dataLayerElementsThatMustExist;
	}

	public void setDataElementsThatMustExist(List<String> dataElementsThatMustExist) {
		_dataLayerElementsThatMustExist = dataElementsThatMustExist;
	}

	public List<Map<String, String>> getDataElementsThatMustHaveSpecificValue() {
		return _dataLayerElementsThatMustHaveSpecificValue;
	}

	public void setDataElementsThatMustHaveSpecificValue(
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

	public void loadFromJSONFile(String testdescriptionfilename) {
	}

	public void createFromJSON(JSONObject jsonObject) {
		// get the pageURL
		String pageURL = (String) jsonObject.get("pageURL");
		setPageURL(pageURL);

		// get the list of data layer elements that must exist
		JSONArray dlemxlist = (JSONArray) jsonObject.get("dataLayerElementsThatMustExist");
		List<String> dlemx = new ArrayList<String>();
		for (Iterator<String> iterator = dlemxlist.iterator(); iterator.hasNext();) {
			dlemx.add(iterator.next());
		}
		setDataElementsThatMustExist(dlemx);

		// get the list of data layer elements that must have a specific value
		JSONArray dlemvlist = (JSONArray) jsonObject.get("dataLayerElementsThatMustHaveASpecificValue");
		List<Map<String, String>> dlemv = new ArrayList<Map<String, String>>();
		for (Iterator<Map<String, String>> iterator = dlemvlist.iterator(); iterator.hasNext();) {
			Map<String, String> el = iterator.next();
			dlemv.add(el);
		}
		setDataElementsThatMustHaveSpecificValue(dlemv);
		
		// get the list of PLRs that must exist
		JSONArray plrmxlist = (JSONArray) jsonObject.get("pageLoadRulesThatMustExist");
		List<String> plrmx = new ArrayList<String>();
		for (Iterator<String> iterator = plrmxlist.iterator(); iterator.hasNext();) {
			plrmx.add(iterator.next());
		}
		setPageLoadRulesThatMustExist(plrmx);
		
		// get the list of PLRs that must fire
		JSONArray plrmrlist = (JSONArray) jsonObject.get("pageLoadRulesThatMustHaveRun");
		List<String> plrmr = new ArrayList<String>();
		for (Iterator<String> iterator = plrmrlist.iterator(); iterator.hasNext();) {
			plrmr.add(iterator.next());
		}
		setPageLoadRulesThatMustHaveRun(plrmr);
	}

}
