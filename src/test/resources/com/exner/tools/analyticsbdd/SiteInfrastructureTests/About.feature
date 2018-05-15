#Author: jan.exner@gmx.net

@analytics
Feature: Tracking infrastructure on the about page is ok
  As an analytics team
  I want the about page to contain tracking infrastructure
  so I can track data and analyse it

  @trackingdata
  Scenario: The about page loads and all data is present
    Given the page "https://www.jan-exner.de/about/" is loaded
    And the "digitalData.page.pageInfo.language" data layer element is "en-GB"
    And the "digitalData.page.pageInfo.pageName" data layer element is "About me"
    And the DOM element "a.amzWishlist" exists
    And the DOM element "a.amzWishlist" exists 2 times

  @trackingtools
  Scenario: The about page loads with all necessary tools
    Given the page "https://www.jan-exner.de/about/" is loaded
    Then jQuery is present
    And the jQuery version is "2.0" or later
    And the jQuery version is below "3"
		And DTM is present
		And the DTM library is "satelliteLib-f898bce177301e894492bf685fe6bc28e8eca6c5"
