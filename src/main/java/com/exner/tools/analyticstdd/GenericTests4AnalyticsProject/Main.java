package com.exner.tools.analyticstdd.GenericTests4AnalyticsProject;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriver;

public class Main {

	public static void main(String[] args) {
		WebDriver driver = new PhantomJSDriver();
		driver.get("http://www.jan-exner.de/");
		JavascriptExecutor js = (JavascriptExecutor) driver;
		List<Object> dcrlist = (List<Object>) js.executeScript("return _satellite.rules");
		for (Iterator<Object> iterator = dcrlist.iterator(); iterator.hasNext();) {
			Object object = (Object) iterator.next();
			Map<String, Object> map = (Map<String, Object>) object;
			String ruleName = (String) map.get("name");
			System.out.println(ruleName);
		}
	}

}
