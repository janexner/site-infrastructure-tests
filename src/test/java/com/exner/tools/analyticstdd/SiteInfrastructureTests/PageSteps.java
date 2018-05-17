package com.exner.tools.analyticstdd.SiteInfrastructureTests;

import java.util.List;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;

import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;

public class PageSteps {
	WebDriver driver;
	private JavascriptExecutor _jsExecutor;

	@Before
	public void beforeTest() {
		ChromeOptions chromeOptions = new ChromeOptions();
		chromeOptions.addArguments("--headless");
		// driver = new ChromeDriver(chromeOptions);
		driver = new RemoteWebDriver(CucumberFeatureTest.getService().getUrl(), chromeOptions);
		_jsExecutor = (JavascriptExecutor) driver;
	}

	/***
	 * General tests for vendor-independent data
	 */

	@Given("^the page \"(.*)\" is loaded$")
	public void the_page_is_loaded(String url) {
		driver.get(url);
	}

	@Given("^the snippet \"(.*)\" is executed$")
	public void the_snippet_is_executed(String snippet) {
		_jsExecutor.executeScript(snippet);
	}

	@Given("^we wait for (\\d+) seconds?$")
	public void we_waited_for_seconds(int seconds) {
		try {
			Thread.sleep(1000l * seconds);
		} catch (InterruptedException e) {
			System.err.println("Sleep interrupted " + e.getLocalizedMessage());
		}
	}

	@Then("^the \"(.*)\" data layer element is \"(.*)\"$")
	public void the_data_layer_element_is(String dataLayerElementName, String value) {
		TestTools.assertScriptExecutionReturnsCorrectStringValue(driver, "return " + dataLayerElementName, value);
	}

	@Then("^the \"(.*)\" data layer element is (\\d+)$")
	public void the_data_layer_element_is_numeric(String dataLayerElementName, long value) {
		TestTools.assertScriptExecutionReturnsCorrectNumericalValue(driver, "return " + dataLayerElementName, value);
	}

	@Then("^there is a data layer element called \"(.*)\"$")
	public void there_is_a_data_layer_element_called(String elementName) {
		TestTools.assertScriptExecutionReturnsTrue(driver,
				"if (typeof " + elementName + " !== 'undefined') { return true } else { return false }");
	}

	@Then("^the DOM element \"(.*)\" exists$")
	public void the_DOM_element_exists(String elementSelector) {
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
		TestTools.assertScriptExecutionReturnsTrue(driver, snippet);
	}

	/***
	 * General tests for infrastructure on pages
	 */

	@Then("^DTM is present$")
	public void dtm_is_present() {
		TestTools.assertScriptExecutionReturnsTrue(driver,
				"if (typeof _satellite !== 'undefined' && _satellite && typeof _satellite.appVersion !== 'undefined' && _satellite.appVersion) { return true } else { return false }");
	}

	@Then("^Launch is present$")
	public void launch_is_present() {
		// TBD have to fix
		TestTools.assertScriptExecutionReturnsTrue(driver,
				"if (typeof _satellite !== 'undefined' && _satellite && typeof _satellite.appVersion !== 'undefined' && _satellite.appVersion) { return true } else { return false }");
	}

	@Then("^the DTM library is \"(.*)\"$")
	public void the_DTM_library_is(String libraryName) {
		TestTools.assertScriptExecutionReturnsCorrectStringValue(driver,
				"return _satellite && _satellite.settings && _satellite.settings.libraryName", libraryName);
	}

	@Then("^jQuery is present$")
	public void jquery_is_present() {
		TestTools.assertScriptExecutionReturnsTrue(driver,
				"if (typeof jQuery !== 'undefined') { return true } else { return false }");
	}

	@Then("^the jQuery version is \"(.*)\" or later$")
	public void the_jQuery_version_is_or_later(String minVersion) {
		TestTools.assertToolVersionIsOrLater(driver,
				"if (typeof jQuery !== 'undefined') { return jQuery.fn.jquery } else { return 'unavailable' }",
				minVersion);
	}

	@Then("^the jQuery version is below \"(.*)\"$")
	public void the_jQuery_version_is_below(String maxVersion) {
		TestTools.assertToolVersionIsBelow(driver,
				"if (typeof jQuery !== 'undefined') { return jQuery.fn.jquery } else { return 'unavailable' }",
				maxVersion);
	}

	/***
	 * out of scope tests for Adobe Tools
	 */

	@Then("^(?:MCID|ECID|Experience Cloud ID Service) is present$")
	public void ecid_is_present() {
		TestTools.assertScriptExecutionReturnsTrue(driver,
				"if (typeof Visitor === 'function') { return true } else { return false }");
	}

	@Then("^(?:MCID|ECID|Experience Cloud ID Service) version is \"(.*)\" or later$")
	public void ecid_version_is_or_later(String minVersion) {
		TestTools.assertToolVersionIsOrLater(driver,
				"if (typeof Visitor !== 'undefined') { for (vv in s_c_il) { var nvv = s_c_il[vv]; if (typeof nvv._c !== 'undefined' && nvv._c == \"Visitor\") {  return nvv.version; } } return 'unavailable' } else { return 'unavailable' }",
				minVersion);
	}

	@Then("^(?:MCID|ECID|Experience Cloud ID Service) version is below \"(.*)\"$")
	public void ecid_version_is_below(String maxVersion) {
		TestTools.assertToolVersionIsBelow(driver,
				"if (typeof Visitor !== 'undefined') { return Visitor.version } else { return 'unavailable' }",
				maxVersion);
	}

	@Then("^(?:AA|Adobe Analytics) is present$")
	public void aa_is_present() {
		TestTools.assertScriptExecutionReturnsTrue(driver,
				"if (typeof AppMeasurement == 'function' || typeof s_gi == 'function') { return true; } else { return false; }");
	}

	@Then("^(?:AA|Adobe Analytics) version is \"(.*)\" or later$")
	public void aa_version_is_or_later(String minVersion) {
		TestTools.assertToolVersionIsOrLater(driver,
				"if (typeof AppMeasurement !== 'undefined') { return (new AppMeasurement()).version } else if (typeof s_gi !== 'undefined') { return s.version } else { return 'unavailable' }",
				minVersion);
	}

	@Then("^(?:AA|Adobe Analytics) lib type is \"(.*)\"$")
	public void aa_lib_type_is(String libType) {
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
		// replace "," between rsids with "_"
		rsid = rsid.replace(',', '_');
		TestTools.assertScriptExecutionReturnsTrue(driver, "var rstest='\" + rsid\r\n"
				+ "				+ \"';var rsprfx='s_i_';var retVal=false;for(var b in window){if(window['hasOwnProperty'](b)){if(b['indexOf'](rsprfx)===0){rsarr=b['substr'](rsprfx['length'],b['length'])['split']('_');if(rsarr['indexOf'](rstest)>=0){retVal=true}}}};return retVal");
	}

	@Then("^(?:AT|Adobe Target) is present$")
	public void at_is_present() {
		TestTools.assertScriptExecutionReturnsTrue(driver,
				"if (typeof TNT == 'object') { return true; } else { return false; }");
	}

	@Then("^(?:AT|Adobe Target) version is \"(.*)\" or later$")
	public void at_version_is_or_later(String minVersion) {
		TestTools.assertToolVersionIsOrLater(driver,
				"if (typeof mboxVersion !== 'undefined') { return mboxVersion } else if (typeof adobe !== 'undefined' && typeof adobe.target !== 'undefined' && typeof adobe.target.VERSION !== 'undefined') { return adobe.target.VERSION } else { return 'unavailable' }",
				minVersion);
	}

	@Then("^(?:AT|Adobe Target) lib type is \"(.*)\"$")
	public void at_lib_type_is(String libType) {
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
		TestTools.assertScriptExecutionReturnsTrue(driver,
				"if (typeof mboxCurrent != 'undefined' && mboxCurrent.getName() == '" + mboxName
						+ "') { return true; } else { return false; }\"");
	}

	/***
	 * Some experimental tests
	 */

	@Then("^GTM is present$")
	public void gtm_is_present() {
		TestTools.assertScriptExecutionReturnsTrue(driver,
				"if (typeof google_tag_manager === 'object') { return true } else { return false }");
	}

	@Then("^Ensighten Manage is present$")
	public void ensighten_manage_is_present() {
		TestTools.assertScriptExecutionReturnsTrue(driver,
				"if (typeof Bootstrapper === 'object') { return true } else { return false }");
	}

	@Then("^Tealium IQ is present$")
	public void tealium_iq_is_present() {
		TestTools.assertScriptExecutionReturnsTrue(driver,
				"if (typeof utag === 'object') { return true } else { return false }");
	}

	@After
	public void afterTest() {
		driver.quit();
	}
}
