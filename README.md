# Site Infrastructure Tests

This project contains code that you can use to build your own test suite.

The suite can be used to test parts of the site infrastructure that are important for whatever your Analytics/Marketing team ads on top of it using the tag management system.

Running this suite as a regression tests effectively prevents dev from pulling the gound from under analytics feet.

For suggestions on how to set this up, refer to these articles:
- Raspberry Pi + Jenkins: https://webanalyticsfordevelopers.com/2017/03/07/automating-tests/,
- Mac: https://webanalyticsfordevelopers.com/2017/01/03/wanna-test-mac-edition/, and 
- Windows: https://webanalyticsfordevelopers.com/2016/06/07/wanna-test-heres-how/

## How it works

A test description (JSON format, can be a URL or a file) contains all pages to be tested along with their tests. If you run the test, the AllTests test suite reads that description, then generates a dynamic test suite for each page. All of those will then run.

You can specify which test description the test suite will read by specifying the `test.description.url` or `test.description.file` properties when you run the test.

You can (and probably *must*) specify the location of your chromedriver executable using the `webdriver.chrome.driver` property! If you're on Linux/Mac, the tool will look for chromedriver in `/usr/local/bin/`, and on Windows it'll look in `c:\bin\`. If your chromedriver is anywhere else, you *must* tell the tool!
See https://sites.google.com/a/chromium.org/chromedriver/getting-started for hints.

## "Drivers"

While I originally used phantomJS, I changed to HTMLUnit at some point, and now to ChromeDriver. I presume you know how to checkout by Release, so suffice it to say that 1.0 uses phantomJS, 2.0 is HTMLUnit and 3.x uses ChromeDriver.

The block tracking feature of 2.0 is gone in 3.x, but might come back if I find out how to do it.

Note that the there is a blog article for version 2.0: https://webanalyticsfordevelopers.com/2017/07/18/automated-testing-blocking-tracking/

## Format of test description JSON

The test description JSON has two top level elements:

- name
- pagesToTest

### `name`

The `name` element is used to give the test suite a name.

Example: "name": "my-test-site.com"

### `pagesToTest`

The `pagesToTest` element contains a list of pages and the corresponding tests that should be run on them.

Each sub-element of `pagesToTest` must contain a `pageURL` element. It can further contain elements that specify which tests should be run.

Those elements have two parts: the name of the test class, plus parameters that the test class can interpret. Section 2.2 and below show the test class names and parameters as currently implemented.

### `pageURL`

The `pageURL` element specifies which page the test should load before it executes the tests specified for the page.

Example: "pageURL": "http://test.com/"

*Note*: this element is mandatory

### Generic Elements

The following elements can be used to test the page itself.

#### `jQueryLoaded`

Specify this element if you want to test whether the page loads [jQuery](https://jquery.com).

Example: "jQueryLoaded": true

#### `jQueryMinVersion`

You can test the minimum version that you need on the page using this element.

Example: "jQueryMinVersion": "2.0"

#### `jQueryVersionBelow`

You can test that the version is below a specific version.

Example: "jQueryVersionBelow": "3"

#### `elementSelectedByCSSSelectorExists`

A test that allows you to test for the existence of DOM elements in the page.

*Note*: the parameters can be one single parameter, or a list of parameters.

Example: "elementSelectedByCSSSelectorExists": "h1"

Example: "elementSelectedByCSSSelectorExists": [ "h1", "div#author" ]

#### `elementSelectedByCSSSelectorExistsNTimes`

A test that allows you to test a DOM element exists exactly n times in the page.

*Note*: single CSS selector, only.

Example: "elementSelectedByCSSSelectorExistsNTimes": { "selector": "h1", "n": 1 }

#### `dataLayerElementExists`

This test allows you to test for a Javascript data layer element in the page.

*Note*: the parameters can be one single parameter, or a list of parameters.

Example: "dataLayerElementExists": "digitalData.page.pageInfo.pageName"

Example: "dataLayerElementExists": [ "digitalData.page.pageInfo.pageName", "digitalData.page.pageInfo.language" ]

#### `dataLayerElementValue`/`dataLayerNumericElementValue`

Similar to the one above, but this one checks whether the data layer element has the right value.

*Note*: the parameters can be one single parameter, or a list of parameters.

Example: "dataLayerElementValue": { "name":"digitalData.page.pageInfo.pageName", "value": "Home" }

Example: "dataLayerNumericElementValue": { "name":"digitalData.page.pageInfo.pageID", "value": 896 }

Example: "dataLayerElementValue": [ { "name":"digitalData.page.pageInfo.pageName", "value": "Home" }, { "name":"digitalData.page.pageInfo.language", "value": "en" } ]

#### `dataLayerElementDelayedExists`

This test allows you to test for a Javascript data layer element in the page after waiting for some time. Ideal for pages that load the data layer asynchronously.

The delay is in milliseconds.

*Note*: the parameters can be one single parameter, or a list of parameters.

Example: "dataLayerElementDelayedExists": { "name": "digitalData.page.pageInfo.pageName", "delay": 500 }

Example: "dataLayerElementDelayedExists": [ { "name": "digitalData.page.pageInfo.pageName", "delay": 500 }, { "name": "digitalData.page.pageInfo.language", "delay": 500 } ]

#### `dataLayerElementDelayedValue` / `dataLayerNumericElementDelayedValue`

Similar to the one above, but this one checks whether the data layer element has the right value. Again, this can be used on pages that load the data layer asynchronously.

The delay is in milliseconds.

*Note*: the parameters can be one single parameter, or a list of parameters.

Example: "dataLayerElementValue": { "name":"digitalData.page.pageInfo.pageName", "value": "Home", "delay": 500 }

Example: "dataLayerElementValue": [ { "name":"digitalData.page.pageInfo.pageName", "value": "Home", "delay": 500 }, { "name":"digitalData.page.pageInfo.language", "value": "en", "delay": 500 } ]

#### `genericJavascript`

This test allows you to inject arbitrary Javascript code into the page and test whether it returns 'true'.

*Note*: the parameters can be one single parameter, or a list of parameters.

Example: "genericJavascript": "return true;"

Example: "genericJavascript": [ "return true;", "return 1 == 1;" ]

#### `genericJavascriptSetupAndCheckLater`

A test that lets you inject two snippets of code into the page. The first snippet can serve as a setup, while the second represents the actual test. The second snippet therefore must return a boolean value.

The third parameter is a delay in milliseconds, which defines how long the test should wait between executing the two snippets.

Example: "genericJavascriptSetupAndCheckLater": { "setupScript": "window.aaa = true;", "verificationScript": "return window.aaa;", "delay": 1000

#### `genericJavascriptCascade`

This test can inject an arbitrary number of arbitrary Javascript snippets into a page in a given order, waiting between each injection, and finally running a verification script that must return true or false.

Example: "genericJavascriptCascade": { "scripts": [ "window.aaa1 = 1;", "window.aaa2 = 2;" ], "verificationsScript": "return (window.aaa1 + window.aaa2 == 3);", 500 }

### Adobe Marketing Cloud-related elements

#### `adobe.DTMLoaded`

A test that checks whether DTM has been loaded on the page

Example: "adobe.DTMLoaded": true

#### `adobe.DTMLibraryName`

A test that checks that the site is loading the correct DTM library.

Example: "adobe.DTMLibraryName": "satelliteLib-xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx"

#### `adobe.DTMDataElementExists`

This test allows you to test for a Javascript data layer element in the page.

*Note*: the parameters can be one single parameter, or a list of parameters.

Example: "adobe.DTMDataElementExists": "Page Name"

Example: "adobe.DTMDataElementExists": [ "Page Name", "Page Type" ]

#### `adobe.DTMDataElementValue`

Similar to the one above, but this one checks whether the data layer element has the right value.

*Note*: the parameters can be one single parameter, or a list of parameters.

Example: "adobe.DTMDataElementValue": { "name":"Page Name", "value": "Home" }

Example: "adobe.DTMDataElementValue": [ { "name":"Page Name", "value": "Home" }, { "name":"Page Language", "value": "en" } ]

#### `adobe.DTMDataElementDelayedExists`

This test allows you to test for a Javascript data layer element in the page after waiting for some time. Ideal for pages that load the data layer asynchronously.

The delay is in milliseconds.

*Note*: the parameters can be one single parameter, or a list of parameters.

Example: "adobe.DTMDataElementDelayedExists": { "name": "Page Name", "delay": 500 }

Example: "adobe.DTMDataElementDelayedExists": [ { "name": "Page Name", "delay": 500 }, { "name": "Page Language", "delay": 500 } ]

#### `adobe.DTMDataElementDelayedValue`

Similar to the one above, but this one checks whether the data layer element has the right value. Again, this can be used on pages that load the data layer asynchronously.

The delay is in milliseconds.

*Note*: the parameters can be one single parameter, or a list of parameters.

Example: "dataLayerElementValue": { "name":"Page Name", "value": "Home", "delay": 500 }

Example: "dataLayerElementValue": [ { "name":"Page Name", "value": "Home", "delay": 500 }, { "name":"Page Language", "value": "en", "delay": 500 } ]

#### `adobe.DTMPageLoadRuleExists`

Allows you to test whether a PLR exists in the current setup.

*Note*: the parameters can be one single parameter, or a list of parameters.

Example: "adobe.DTMPageLoadRuleExists": "Normal Page Load"

Example: "adobe.DTMPageLoadRuleExists": [ "Normal Page Load", "Blog Article Page Load" ]

#### `adobe.DTMDirectCallRuleExists`

Tests whether a DCR exists in the current setup.

See above for syntax.

#### `adobe.DTMEventBasedRuleExists`

A test that checks whether an EBR exists in the current setup.

See above for syntax.

#### `adobe.DTMRuleHasRun`

Allows you to test whether a rule has actually fired.

*Note*: the parameters can be one single parameter, or a list of parameters.

Example: "adobe.DTMRuleHasRun": "Normal Page Load"

Example: "adobe.DTMRuleHasRun": [ "Normal Page Load", "Blog Article Page Load" ]

#### `adobe.DTMRuleHasRunAfterDelay`

Allows you to test whether a rule has actually fired after a delay.

*Note*: the parameters can be one single parameter, or a list of parameters.

Example: "adobe.DTMRuleHasRunAfterDelay": { "name":"Normal Page Load", "delay":500 }

Example: "adobe.DTMRuleHasRunAfterDelay": [ { "name":"Normal Page Load", "delay":500 }, { "name":"Blog Article Page Load", "delay": 500 } ]

#### `adobe.DTMEventBasedRuleHasRun`

You can test whether an EBR runs as a result of triggering something in the document.

*Note*: current implementation only supports "click" triggers!

*Note*: the parameters can be one single parameter, or a list of parameters.

Example: "adobe.DTMEventBasedRuleHasRun": { "name": "Add to Cart", "triggerType": "click", "triggerElement": "button#cartAdd" }

Example: "adobe.DTMEventBasedRuleHasRun": [ { "name": "Add to Cart", "triggerType": "click", "triggerElement": "button#cartAdd" }, { "name": "Shipping Info Popup", "triggerType": "click", "triggerElement": "a#terms_conditions" } ]

#### `adobe.VisitorIDServiceLoaded`

A test to check whether the Marketing Cloud Visitor ID Service has loaded on the page.

Example: "adobe.VisitorIDServiceLoaded": true

#### `adobe.VisitorIDServiceMinVersion`

Tests the version of the MCVID against a minimum version.

Example: "adobe.VisitorIDServiceMinVersion": "1.5"

#### `adobe.VisitorIDServiceVersionBelow`

You can test that the MCVID is below a certain version.

Example: "adobe.VisitorIDServiceVersionBelow": "2"

#### `adobe.AnalyticsCodeLoaded`

Allows you to test whether Analytics Javascript code has loaded in the page.

Example: "adobe.AnalyticsCodeLoaded": true

#### `adobe.AnalyticsCodeType`

Tests whether the Analytics code is "AppMeasurement" or "legacy" (H.xx.y and before).

Example: "adobe.AnalyticsCodeType": "AppMeasurement"

#### `adobe.AnalyticsCodeMinVersion`

You can test the Analytics Javascript code against a minimum version.

Example: "adobe.AnalyticsCodeMinVersion": "1.6"

#### `adobe.AnalyticsTagForReportSuiteFired`

Checks whether there has been a tracking request against the specified report suite.

*Note*: the parameters can be one single parameter, or a list of parameters.

Example: "adobe.AnalyticsTagForReportSuiteFired": "myrsid"

This works with multi-suite tagging!

Example: "adobe.AnalyticsTagForReportSuiteFired": "myrsid,myglobalrsid"

You can supply multiple rsids, which would test for multiple, independent tracking calls.

Example: "adobe.AnalyticsTagForReportSuiteFired": [ "myrsid1", "myrsid2" ] 

#### `adobe.AnalyticsTagForReportSuiteFiredAfterDelay`

As above, but the check happens after a delay.

Example: "adobe.AnalyticsTagForReportSuiteFiresAfterDelay": { "rsid": "myrsid", "delay": 2000 }

Supports the same features as above.

#### `adobe.TargetCodeLoaded`

Tests whether the Target Javascript code has loaded

Example: "adobe.TargetCodeLoaded": true

#### `adobe.TargetCodeType`

Tests whether the Target code is "atjs" (using at.js) or "legacy" (using mbox.js).

Example: "adobe.TargetCodeType": "atjs"

#### `adobe.TargetCodeMinVersion`

You can test the Target Javascript code against a minimum version.

Example: "adobe.TargetCodeMinVersion": "0.9.1"

*Note*: Makes most sense in conjunction with adobe.TargetCodeType!

#### `adobe.TargetGlobalMboxExists`

Allows you to tests whether the Target global mbox with a specific name exists.

Example: "adobe.TargetGlobalMboxExists": "global-mbox"

### Other elements

TBD
