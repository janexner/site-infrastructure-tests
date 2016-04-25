#GenericTest4AnalyticsProject

This project contains code that you can use to build your own test suite.

## How it works

A JSON file (testdescription.json) contains all pages to be tested along with their tests. If you run the test, the AllTests test suite reads that file, then generates a dynamic test suite for each page. All of those will then run.

## Format of testdescription.json file

The `testdescription.json` file has two top level elements:

- name
- pagesToTest

### 1 `name` element

The `name` element is used to give the test suite a name.

Example: "name": "my-test-site.com"

### 2 `pagesToTest` element

The `pagesToTest` element contains a list of pages and the corresponding tests that should be run on them.

Each sub-element of `pagesToTest` must contain a `pageURL` element. It can further contain elements that specify which tests should be run.

### 2.1 `pageURL` element

The `pageURL` element specifies which page the test should load before it executes the tests specified for the page.

Example: "pageURL": "http://test.com/"

*Note*: this element is mandatory

### 2.2 `tagManagementDTMActive` element

The `tagManagementDTMActive` element lets you specify whether a page should be tested for DTM (presence & debug mode). It has two sub-elements:

Example: "tagManagementDTMActive": {"testLoaded": true, "testDebugMode": true}

### 2.2.1 `testLoaded` element

Specify `true` or `false` to enable or disable testing for the presence of DTM. The test checks whether the `_satellite` Javascript object exists or not.

### 2.2.2 `testDebugMode` element

Specify `true` or `false` to enable or disable testing for whether DTM is in debug mode.

### 2.3 `dataLayerElementsThatMustExist` element

The `dataLayerElementsThatMustExist` element allows you to specify a list of Javascript variables that will be checked for existence.

This works very well with a [CEDDL-compatible data layer][ceddl]!

Example: "dataLayerElementsThatMustExist": ["digitalData.page.pageInfo.pageName", "digitalData.page.pageInfo.language"]

### 2.4 `dataLayerElementsThatMustExistAfterDelay` element

The `dataLayerElementsThatMustExistAfterDelay` element can be used to test for the existence of data layer elements after a delay.

Each sub-element specifies a data layer element and a time to wait.

Example: "dataLayerElementsThatMustExistAfterDelay" : [{"name": "digitalData.page.pageInfo.pageName", "delay": 1500}]

### 2.4.1 `name` element

The `name` element specifies the name of the data layer element to test.

### 2.4.2 `delay` element

The `delay` element lets you specify a time in milliseconds. The test for the existence of the data layer element will be run after the delay.

### 2.5 `dataLayerElementsThatMustHaveASpecificValue` element

Use the `dataLayerElementsThatMustHaveASpecificValue` element when you want to test the data layer for values.

Example: "dataLayerElementsThatMustHaveASpecificValue": [{"name": "digitalData.page.pageInfo.pageName", "value": "Home"}]

### 2.5.1 `name` element

The `name` element specifies the name of the data layer element to test.

### 2.5.2 `value` element

The `value` element specifies the expected value of the data layer element.

### 2.6 `dataLayerElementsThatMustHaveASpecificValueAfterDelay` element

The `dataLayerElementsThatMustHaveASpecificValueAfterDelay` element works just like the `dataLayerElementsThatMustHaveASpecificValue` element, but it allows to specify a delay before the test is made.

Example: "dataLayerElementsThatMustHaveASpecificValueAfterDelay": [{"name": "dataLayer.page.pageInfo.pageName", "value": "Home", "delay": 1500}]

### 2.6.1 `name` element

Similar to 2.5.1

### 2.6.2 `value` element

Similar to 2.5.2

### 2.6.3 `delay` element

The `delay` element lets you specify a time in milliseconds. The test for the existence of the data layer element will be run after the delay.

### 2.7 `pageLoadRulesThatMustExist` element

The `pageLoadRulesThatMustExits` element allows you to specify a list of names of DTM Page Load Rules. The test will see whether all the PLRs are defined in DTM.

Example: "pageLoadRulesThatMustExist": [ "Normal Page Load" ]

*Note*: since the existence of a rule does not depend on the currently loaded page, you can run this test on any page. It is also enough if you run it once.

### 2.8 `directCallRulesThatMustExist` element

Same as 2.7, but for DTM Direct-call Rules.

*Note*: since the existence of a rule does not depend on the currently loaded page, you can run this test on any page. It is also enough if you run it once.

### 2.9 `eventBasedRulesThatMustExist` element

Same as 2.7, but for DTM Event-based Rules.

*Note*: since the existence of a rule does not depend on the currently loaded page, you can run this test on any page. It is also enough if you run it once.

*Note*: so far, only the "click" trigger has been implemented.

### 2.10 `pageLoadRulesThatMustHaveRun` element

The `pageLoadRulesThatMustHaveRun` element lets you specify a list of PLRs that must have executed on the current page.

Example: "pageLoadRulesThatMustHaveRun": [ "Normal Page Load", "Product Page Load" ]

*Note*: unlike the `pageLoadRulesThatMustExist` element, this one has to be tested on all the pages where you want the rule to fire.

### 2.11 `eventBasedRulesThatMustFire` element

The `eventBasedRulesThatMustFire` element contains a list of EBRs along with triggers. The test will try to use the trigger, then see if the EBR actually fired.

Example: "eventBasedRulesThatMustFire": [{"name": "Header Click (random)", "triggerType": "click", "triggerElement": "h1"}]

### 2.11.1 `name` element

The `name` element specifies the name of the data layer element to test.

### 2.11.2 `triggerType` element

The `triggerType` element specifies which type of trigger should be used. This is similar to the "Event Type" drop down in DTM.

### 2.11.3 `triggerElement` element

The `triggerElement` element lets you sepcify on which element in the page the trigger should be applied. This is similar to the "Element Tag or Selector" field in DTM.

### 2.12 `dataElementsThatMustHaveASpecificValue` element

The `dataElementsThatMustHaveASpecificValue` element lets you specify a list of DTM Data Elements and the corresponding expected value for each of them.

Example: "dataElementsThatMustHaveASpecificValue": [{"name": "Pagename","value": "Homepage"}]

### 2.12.1 `name` element

The `name` element specifies the DTM Data Element.

### 2.12.2 `value` element

The `value` element lets you specify the expected value for the DTM Data Element.

### 2.13 `dataElementsThatMustHaveASpecificValueAfterDelay` element

Similar to the difference between 2.5 and 2.6, this is like 2.12, but with a delay.

Example: "dataElementsThatMustHaveASpecificValue": [{"name": "Pagename", "value": "Homepage", "delay": 1500}]

### 2.13.1 `name` element

See 2.12.1

### 2.13.2 `value` element

See 2.13.2

### 2.13.3 `delay` element

The `delay` element lets you specify a time in milliseconds. The test for the value of the DTM Data Element will be run after the delay.

### 2.14 `analyticsTagForReportSuiteFired` element

The 

Example: "analyticsTagForReportSuiteFired": [ "jexnertest" ]


[ceddl]https://www.w3.org/community/custexpdata/
