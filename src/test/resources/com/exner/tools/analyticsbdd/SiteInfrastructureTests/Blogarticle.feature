#Author: jan.exner@gmx.net

@analytics
Feature: Tracking infrastructure on blog article page is ok
  As an analytics team
  I want the blog article page to contain tracking infrastructure
  so I can track data and analyse it

  @trackingdata
  Scenario: The blog article page loads and all data is present
    Given the page "https://www.jan-exner.de/berichte/20170713" is loaded
    And the "digitalData.page.pageInfo.language" data layer element is "de-DE"
    And the "digitalData.page.pageInfo.pageName" data layer element is "berichte/b170713.php"
    And the "digitalData.page.pageInfo.author" data layer element is "Jan Exner"
    And there is a data layer element called "digitalData.product[0]"
    And the "digitalData.product[0].productInfo.productID" data layer element is "berichte/20170713"

	@trackingtools
  Scenario: The blog article page loads with all necessary tools
    Given the page "https://www.jan-exner.de/berichte/20170713" is loaded
    Then jQuery is present
    And the jQuery version is "2.0" or later
    And the jQuery version is below "3"
		And Launch is present
		And the Launch property is called "jan-exner.de"
		