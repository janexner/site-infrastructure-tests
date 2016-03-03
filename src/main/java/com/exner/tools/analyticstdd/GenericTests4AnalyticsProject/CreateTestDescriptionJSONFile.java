package com.exner.tools.analyticstdd.GenericTests4AnalyticsProject;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class CreateTestDescriptionJSONFile {
	private final static String TESTDESCRIPTIONFILENAME = "testdescription.json";

	public static void main(String[] args) {
		PageTestDefinition ptd = new PageTestDefinition();
		ptd.setPageURL("http://www.jan-exner.de/");
		List<String> dataLayerElements2Test4Existence = new ArrayList<String>();
		dataLayerElements2Test4Existence.add("dataLayer.page.pageInfo.language");
		ptd.setDataElementsThatMustExist(dataLayerElements2Test4Existence);
		List<Map<String, String>> dataLayerElements2Test4Value = new ArrayList<Map<String, String>>();
		Map<String, String> dataLayerElement2Test = new HashMap<String, String>();
		dataLayerElement2Test.put("name", "dataLayer.page.pageInfo.pageName");
		dataLayerElement2Test.put("value", "Home");
		dataLayerElements2Test4Value.add(dataLayerElement2Test);
		ptd.setDataElementsThatMustHaveSpecificValue(dataLayerElements2Test4Value);
		List<String> plrs2Test4Existence = new ArrayList<String>();
		plrs2Test4Existence.add("Normal Page Load");
		ptd.setPageLoadRulesThatMustExist(plrs2Test4Existence);
		List<String> rules2Test4Execution = new ArrayList<String>();
		rules2Test4Execution.add("Normal Page Load");
		ptd.setPageLoadRulesThatMustHaveRun(rules2Test4Execution);

		// prepare for not faking in the future
		JSONObject jsonPageLevel = new JSONObject();
		jsonPageLevel.put("pageURL", ptd.getPageURL());
		JSONArray dlemx = new JSONArray();
		for (Iterator<String> iterator = ptd.getDataElementsThatMustExist().iterator(); iterator.hasNext();) {
			String dle = (String) iterator.next();
			dlemx.add(dle);
		}
		jsonPageLevel.put("dataLayerElementsThatMustExist", dlemx);
		JSONArray dlemv = new JSONArray();
		for (Iterator<Map<String, String>> iterator = ptd.getDataElementsThatMustHaveSpecificValue()
				.iterator(); iterator.hasNext();) {
			Map<String, String> dlemap = iterator.next();
			JSONObject obj = new JSONObject();
			obj.put("name", dlemap.get("name"));
			obj.put("value", dlemap.get("value"));
			dlemv.add(dlemap);
		}
		jsonPageLevel.put("dataLayerElementsThatMustHaveASpecificValue", dlemv);
		JSONArray plrmx = new JSONArray();
		for (Iterator<String> iterator = ptd.getPageLoadRulesThatMustExist().iterator(); iterator.hasNext();) {
			String dle = (String) iterator.next();
			plrmx.add(dle);
		}
		jsonPageLevel.put("pageLoadRulesThatMustExist", plrmx);
		JSONArray plrmr = new JSONArray();
		for (Iterator<String> iterator = ptd.getPageLoadRulesThatMustHaveRun().iterator(); iterator.hasNext();) {
			String dle = (String) iterator.next();
			plrmr.add(dle);
		}
		jsonPageLevel.put("pageLoadRulesThatMustHaveRun", plrmr);

		// wrap into a site test
		JSONObject jsonSiteLevel = new JSONObject();
		jsonSiteLevel.put("name", "jan-exner.de");
		JSONArray jsonPages = new JSONArray();
		jsonPages.add(jsonPageLevel);
		jsonSiteLevel.put("pagesToTest", jsonPages);
		
		try {
			FileWriter writer = new FileWriter(TESTDESCRIPTIONFILENAME);
			writer.write(jsonSiteLevel.toJSONString());
			writer.flush();
			writer.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
