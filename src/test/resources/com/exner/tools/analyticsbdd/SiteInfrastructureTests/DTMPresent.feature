#Author: jan.exner@gmx.net
#Description: this feature focuses on a specific thing across pages, a somewhat 'horizontal' feature, so to speak

@analytics
Feature: DTM is present in all templates
  As an analytics team
  I want all pages to contain DTM
  so I can use it to manage my tracking

  @trackingtools
  Scenario: The home page loads and contains DTM
    Given the page "https://www.jan-exner.de/" is loaded
		And Launch is present
		And the Launch property is called "jan-exner.de"

	@trackingtools
  Scenario: The astro photos page loads and contains DTM
    Given the page "https://www.jan-exner.de/astro/" is loaded
		And Launch is present
		And the Launch property is called "jan-exner.de"

  @trackingtools
  Scenario: The blog overview page loads and contains DTM
    Given the page "https://www.jan-exner.de/berichte/" is loaded
		And Launch is present
		And the Launch property is called "jan-exner.de"

  @trackingtools
  Scenario: The photos page loads and contains DTM
    Given the page "https://www.jan-exner.de/photos/" is loaded
		And Launch is present
		And the Launch property is called "jan-exner.de"

	@trackingtools
  Scenario: The about page loads and contains DTM
    Given the page "https://www.jan-exner.de/about/" is loaded
		And Launch is present
		And the Launch property is called "jan-exner.de"

  @trackingtools
  Scenario: The blog article page loads and contains DTM
    Given the page "https://www.jan-exner.de/berichte/20170713" is loaded
		And Launch is present
		And the Launch property is called "jan-exner.de"

  @trackingtools
  Scenario: The search results page loads and contains DTM
    Given the page "https://www.jan-exner.de/search/?q=abc" is loaded
		And Launch is present
		And the Launch property is called "jan-exner.de"
