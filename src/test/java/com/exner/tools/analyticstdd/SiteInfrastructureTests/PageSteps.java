package com.exner.tools.analyticstdd.SiteInfrastructureTests;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;

import io.cucumber.junit.Cucumber;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;

public class PageSteps {
	private static final Logger logger = LogManager.getLogger("PageTests");
	private WebDriver driver;
	private JavascriptExecutor _jsExecutor;

	@Before
	public void beforeTest() {
		logger.info("Test setup...");
		ChromeOptions chromeOptions = new ChromeOptions();
		chromeOptions.addArguments("--headless");
		// driver = new ChromeDriver(chromeOptions);
		if (logger.isDebugEnabled()) {
			logger.debug("Initialising ChromeDriver...");
		}
		driver = new RemoteWebDriver(CucumberFeatureTest.getService().getUrl(), chromeOptions);
		_jsExecutor = (JavascriptExecutor) driver;
		logger.info("Test setup complete.");
	}

	/***
	 * General tests for vendor-independent data
	 */

	@Given("^the page \"(.*)\" is loaded$")
	public void the_page_is_loaded(String url) {
		logger.info("Loading page {}...", url);
		driver.get(url);
	}

	@Given("^the snippet \"(.*)\" is executed$")
	public void the_snippet_is_executed(String snippet) {
		logger.info("Executing JS snippet {}...", snippet);
		_jsExecutor.executeScript(snippet);
	}

	@Given("^we wait for (\\d+) seconds?$")
	public void we_waited_for_seconds(int seconds) {
		logger.info("Waiting {} seconds...", seconds);
		try {
			Thread.sleep(1000l * seconds);
		} catch (InterruptedException e) {
			logger.warn("Sleep interrupted {}", e.getLocalizedMessage());
		}
	}

	@Then("^the \"(.*)\" data layer element is \"(.*)\"$")
	public void the_data_layer_element_is(String dataLayerElementName, String value) {
		logger.info("Testing - data layer element {} must be {}...", dataLayerElementName, value);
		TestTools.assertScriptExecutionReturnsCorrectStringValue(driver, "return " + dataLayerElementName, value);
	}

	@Then("^the \"(.*)\" data layer element is (\\d+)$")
	public void the_data_layer_element_is_numeric(String dataLayerElementName, long value) {
		logger.info("Testing - data layer element {} must be {}...", dataLayerElementName, value);
		TestTools.assertScriptExecutionReturnsCorrectNumericalValue(driver, "return " + dataLayerElementName, value);
	}

	@Then("^there is a data layer element called \"(.*)\"$")
	public void there_is_a_data_layer_element_called(String elementName) {
		logger.info("Testing - data layer element {} must exist...", elementName);
		TestTools.assertScriptExecutionReturnsTrue(driver,
				"if (typeof " + elementName + " !== 'undefined') { return true } else { return false }");
	}

	@Then("^the DOM element \"(.*)\" exists$")
	public void the_DOM_element_exists(String elementSelector) {
		logger.info("Testing - DOM element {} must exist...", elementSelector);
		WebElement element = null;
		try {
			element = driver.findElement(By.cssSelector(elementSelector));
		} catch (NoSuchElementException e) {
			// it ain't there
			Assert.fail();
		}
		// make sure the element exists
		Assert.assertNotNull(element);
	}

	@Then("^the DOM element \"(.*)\" exists (\\d+) times$")
	public void the_DOM_element_exists_times(String elementSelector, int n) {
		logger.info("Testing - DOM element {} must exist {} times...", elementSelector, n);
		List<WebElement> element = null;
		try {
			element = driver.findElements(By.cssSelector(elementSelector));
		} catch (NoSuchElementException e) {
			// it ain't there
			Assert.fail();
		}
		// make sure the element exists n times
		Assert.assertEquals(n, element.size());
	}

	@Then("^the snippet \"(.*)\" returns true$")
	public void the_snippet_returns_true_on_this_page(String snippet) {
		logger.info("Testing - JS snippet {} must return true...", snippet);
		TestTools.assertScriptExecutionReturnsTrue(driver, snippet);
	}

	/***
	 * General tests for infrastructure on pages
	 */

	@Then("^DTM is present$")
	public void dtm_is_present() {
		logger.info("Testing - {} must be present...", Tools.DTM);
		TestTools.assertScriptExecutionReturnsTrue(driver,
				"if (typeof _satellite !== 'undefined' && _satellite && typeof _satellite.appVersion !== 'undefined' && _satellite.appVersion) { return true } else { return false }");
	}

	@Then("^Launch is present$")
	public void launch_is_present() {
		logger.info("Testing - {} must be present...", Tools.LAUNCH);
		// TBD have to fix
		TestTools.assertScriptExecutionReturnsTrue(driver,
				"if (typeof _satellite !== 'undefined' && _satellite && typeof _satellite.buildInfo !== 'undefined' && _satellite.buildInfo) { return true } else { return false }");
	}

	@Then("^the DTM library is \"(.*)\"$")
	public void the_DTM_library_is(String libraryName) {
		logger.info("Testing - {} library must be {}...", Tools.DTM, libraryName);
		TestTools.assertScriptExecutionReturnsCorrectStringValue(driver,
				"return _satellite && _satellite.settings && _satellite.settings.libraryName", libraryName);
	}

	@Then("^the Launch property is called \"(.*)\"$")
	public void the_Launch_property_name_is(String propertyName) {
		logger.info("Testing - {} property name must be {}...", Tools.LAUNCH, propertyName);
		TestTools.assertScriptExecutionReturnsCorrectStringValue(driver, 
			"return _satellite && _satellite.property && _satellite.property.name", propertyName);
	}

	@Then("^jQuery is present$")
	public void jquery_is_present() {
		logger.info("Testing - {} must be present...", Tools.JQUERY);
		TestTools.assertScriptExecutionReturnsTrue(driver,
				"if (typeof jQuery !== 'undefined') { return true } else { return false }");
	}

	@Then("^the jQuery version is \"(.*)\" or later$")
	public void the_jQuery_version_is_or_later(String minVersion) {
		logger.info("Testing - {} version must be {} or later...", Tools.JQUERY, minVersion);
		TestTools.assertToolVersionIsOrLater(driver,
				"if (typeof jQuery !== 'undefined') { return jQuery.fn.jquery } else { return 'unavailable' }",
				minVersion);
	}

	@Then("^the jQuery version is below \"(.*)\"$")
	public void the_jQuery_version_is_below(String maxVersion) {
		logger.info("Testing - {} version must be below {}...", Tools.JQUERY, maxVersion);
		TestTools.assertToolVersionIsBelow(driver,
				"if (typeof jQuery !== 'undefined') { return jQuery.fn.jquery } else { return 'unavailable' }",
				maxVersion);
	}

	/***
	 * out of scope tests for Adobe Tools
	 */

	@Then("^(?:MCID|ECID|Experience Cloud ID Service) is present$")
	public void ecid_is_present() {
		logger.info("Testing - {} must be present...", Tools.MCVID);
		TestTools.assertScriptExecutionReturnsTrue(driver,
				"if (typeof Visitor === 'function') { return true } else { return false }");
	}

	@Then("^(?:MCID|ECID|Experience Cloud ID Service) version is \"(.*)\" or later$")
	public void ecid_version_is_or_later(String minVersion) {
		logger.info("Testing - {} version must be {} or later...", Tools.MCVID, minVersion);
		TestTools.assertToolVersionIsOrLater(driver,
				"if (typeof Visitor !== 'undefined') { for (vv in s_c_il) { var nvv = s_c_il[vv]; if (typeof nvv._c !== 'undefined' && nvv._c == \"Visitor\") {  return nvv.version; } } return 'unavailable' } else { return 'unavailable' }",
				minVersion);
	}

	@Then("^(?:MCID|ECID|Experience Cloud ID Service) version is below \"(.*)\"$")
	public void ecid_version_is_below(String maxVersion) {
		logger.info("Testing - {} version must be below {}...", Tools.MCVID, maxVersion);
		TestTools.assertToolVersionIsBelow(driver,
				"if (typeof Visitor !== 'undefined') { return Visitor.version } else { return 'unavailable' }",
				maxVersion);
	}

	@Then("^(?:AA|Adobe Analytics) is present$")
	public void aa_is_present() {
		logger.info("Testing - {} must be present...", Tools.AA);
		TestTools.assertScriptExecutionReturnsTrue(driver,
				"if (typeof AppMeasurement == 'function' || typeof s_gi == 'function') { return true; } else { return false; }");
	}

	@Then("^(?:AA|Adobe Analytics) version is \"(.*)\" or later$")
	public void aa_version_is_or_later(String minVersion) {
		logger.info("Testing - {} version must be {} or later...", Tools.AA, minVersion);
		TestTools.assertToolVersionIsOrLater(driver,
				"if (typeof AppMeasurement !== 'undefined') { return (new AppMeasurement()).version } else if (typeof s_gi !== 'undefined') { return s.version } else { return 'unavailable' }",
				minVersion);
	}

	@Then("^(?:AA|Adobe Analytics) lib type is \"(.*)\"$")
	public void aa_lib_type_is(String libType) {
		logger.info("Testing - {} library must be {}...", Tools.AA, libType);
		Object response = _jsExecutor.executeScript(
				"if (typeof AppMeasurement == 'function' ) { return 'AppMeasurement' } else if (typeof s_gi == 'function') { return 'legacy'; } else { return 'none'; }");
		// make sure the element exists
		if (String.class.isAssignableFrom(response.getClass())) {
			Assert.assertEquals(libType, (String) response);
		} else {
			Assert.fail();
		}
	}

	@Then("^an (?:AA|Adobe Analytics) call has been sent for report suite id \"(.*)\"$")
	public void aa_tag_for_reportsuite_fired(String rsid) {
		logger.info("Testing - {} must have fired a call to report suite id {}...", Tools.AA, rsid);
		// replace "," between rsids with "_"
		rsid = rsid.replace(',', '_');
		TestTools.assertScriptExecutionReturnsTrue(driver, "var rstest='\" + rsid\r\n"
				+ "				+ \"';var rsprfx='s_i_';var retVal=false;for(var b in window){if(window['hasOwnProperty'](b)){if(b['indexOf'](rsprfx)===0){rsarr=b['substr'](rsprfx['length'],b['length'])['split']('_');if(rsarr['indexOf'](rstest)>=0){retVal=true}}}};return retVal");
    }
    
    @Then("^latest (?:AA|Adobe Analytics) tracking call contains key \"(.*)\" with value \"(.*)\"$")
    public void latest_aa_tracking_call_contains_key_with_value(String key, String value) {
        logger.info("Testing - {} tracking call must contain key {} with value {}", Tools.AA, key, value);
        String snippet = "var entryList = performance.getEntriesByType('resource');var result = false;for (var i = entryList.length - 1; i > 0; i--) {if ('undefined' !== typeof entryList[i].name && entryList[i].name.indexOf('/b/ss/') >= 0) {var keys = entryList[i].name.split('&');for (var j = keys.length - 1; j > 0; j--) {var tmp = keys[j].split('=');if ('" + key + "' === tmp[0]) {if ('" + value + "' === decodeURIComponent(tmp[1])) {result = true;break;}}}}} return result;";
        TestTools.assertScriptExecutionReturnsTrue(driver, snippet);
    }

	@Then("^(?:AT|Adobe Target) is present$")
	public void at_is_present() {
		logger.info("Testing - {} must be present...", Tools.AT);
		TestTools.assertScriptExecutionReturnsTrue(driver,
				"if ('undefined' !== adobe && adobe && 'undefined' !== adobe.target && adobe.target) { return true; } else { if (typeof TNT == 'object') { return true; } else { return false; } }");
	}

	@Then("^(?:AT|Adobe Target) version is \"(.*)\" or later$")
	public void at_version_is_or_later(String minVersion) {
		logger.info("Testing - {} version must be {} or later...", Tools.AT, minVersion);
		TestTools.assertToolVersionIsOrLater(driver,
				"if (typeof mboxVersion !== 'undefined') { return mboxVersion } else if (typeof adobe !== 'undefined' && typeof adobe.target !== 'undefined' && typeof adobe.target.VERSION !== 'undefined') { return adobe.target.VERSION } else { return 'unavailable' }",
				minVersion);
	}

	@Then("^(?:AT|Adobe Target) lib type is \"(.*)\"$")
	public void at_lib_type_is(String libType) {
		logger.info("Testing - {} library must be {}...", Tools.AT, libType);
		Object response = _jsExecutor.executeScript(
				"if (typeof mboxVersion !== 'undefined' ) { return 'legacy' } else if (typeof adobe !== 'undefined' && typeof adobe.target !== 'undefined' && typeof adobe.target.VERSION !== 'undefined') { return 'at.js'; } else { return 'none'; }");
		// make sure the element exists
		if (String.class.isAssignableFrom(response.getClass())) {
			Assert.assertEquals(libType, (String) response);
		} else {
			Assert.fail();
		}
	}

	@Then("^an (?:AT|Adobe Target) mbox named \"(.*)\" exists $")
	public void at_mbox_named_exists(String mboxName) {
		logger.info("Testing - {} mbox called {} must exist...", Tools.AT, mboxName);
		TestTools.assertScriptExecutionReturnsTrue(driver,
				"if (typeof mboxCurrent != 'undefined' && mboxCurrent.getName() == '" + mboxName
						+ "') { return true; } else { " 
						+ "var resources = performance.getEntriesByType('resource'); if ('undefined' !== resources && resources) { for(var i in resources) { if (resources[i].initiatorType === 'xmlhttprequest' && resources[i].name.match(/mbox\\/json\\?mbox=/)) { var mboxName = resources[i].name.replace(/.*\\/json\\?mbox=/, '').replace(/&.*/, ''); if ('undefined' !== mboxName && mboxName && mboxName === '" + mboxName + "') { return true; } } } return false; };");
	}

	/***
	 * Some experimental tests
	 */

	@Then("^GTM is present$")
	public void gtm_is_present() {
		logger.info("Testing - {} must be present...", Tools.GTM);
		TestTools.assertScriptExecutionReturnsTrue(driver,
				"if (typeof google_tag_manager === 'object') { return true } else { return false }");
	}

	@Then("^Ensighten Manage is present$")
	public void ensighten_manage_is_present() {
		logger.info("Testing - {} must be present...", Tools.ENSIGHTEN);
		TestTools.assertScriptExecutionReturnsTrue(driver,
				"if (typeof Bootstrapper === 'object') { return true } else { return false }");
	}

	@Then("^Tealium IQ is present$")
	public void tealium_iq_is_present() {
		logger.info("Testing - {} must be present...", Tools.TEALIUM);
		TestTools.assertScriptExecutionReturnsTrue(driver,
				"if (typeof utag === 'object') { return true } else { return false }");
	}

    @Then("^log Browser Performance Timing$")
    public void log_browser_performance_timing() {
        logger.info("Testing - logging Performance TImings to file...");
        Object response = _jsExecutor.executeScript("return JSON.stringify(performance.timing)");
        if (null != response && String.class.isAssignableFrom(response.getClass())) {
            // write to file
            try {
                FileWriter fileWriter = new FileWriter("timings.csv", true);
                BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
                bufferedWriter.write(driver.getCurrentUrl());
                bufferedWriter.write(",");
                bufferedWriter.write((String) response);
                bufferedWriter.newLine();
                bufferedWriter.close();
            } catch (IOException e) {
                Assert.fail("Error writing timings to file: " + e.getLocalizedMessage());
            }
        } else {
            Assert.fail("Unable to write timings to file, timings empty or not a string.");
        }
        Assert.assertTrue(true);
    }

    @After
	public void afterTest() {
		logger.info("Test teardown...");
		driver.quit();
	}
}
