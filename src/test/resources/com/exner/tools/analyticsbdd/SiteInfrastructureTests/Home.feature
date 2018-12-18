#Author: jan.exner@gmx.net

@analytics
Feature: Tracking infrastructure on the home page is ok
  As an analytics team
  I want the home page to contain tracking infrastructure
  so I can track data and analyse it

  @trackingdata
  Scenario: The home page loads and all data is present
    Given the page "https://www.jan-exner.de/" is loaded
    And the snippet "window.jan = true" is executed
    And we wait for 1 second
    Then there is a data layer element called "digitalData.page.pageInfo.language"
    And the "digitalData.page.pageInfo.pageName" data layer element is "Home"
    And the snippet "return window.jan" returns true

  @trackingtools
  Scenario: The  page loads and contains DTM
    Given the page "https://www.jan-exner.de/" is loaded
    Then jQuery is present
    And the jQuery version is "2.0" or later
    And the jQuery version is below "3"
		And Launch is present
		And the Launch property is called "jan-exner.de"
