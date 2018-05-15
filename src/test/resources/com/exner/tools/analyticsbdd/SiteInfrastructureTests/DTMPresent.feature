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
		Then DTM is present
		And the DTM library is "satelliteLib-f898bce177301e894492bf685fe6bc28e8eca6c5"

	@trackingtools
  Scenario: The astro photos page loads and contains DTM
    Given the page "https://www.jan-exner.de/astro/" is loaded
		Then DTM is present
		And the DTM library is "satelliteLib-f898bce177301e894492bf685fe6bc28e8eca6c5"

  @trackingtools
  Scenario: The blog overview page loads and contains DTM
    Given the page "https://www.jan-exner.de/berichte/" is loaded
		Then DTM is present
		And the DTM library is "satelliteLib-f898bce177301e894492bf685fe6bc28e8eca6c5"

  @trackingtools
  Scenario: The photos page loads and contains DTM
    Given the page "https://www.jan-exner.de/photos/" is loaded
		Then DTM is present
		And the DTM library is "satelliteLib-f898bce177301e894492bf685fe6bc28e8eca6c5"

	@trackingtools
  Scenario: The about page loads and contains DTM
    Given the page "https://www.jan-exner.de/about/" is loaded
		Then DTM is present
		And the DTM library is "satelliteLib-f898bce177301e894492bf685fe6bc28e8eca6c5"

  @trackingtools
  Scenario: The blog article page loads and contains DTM
    Given the page "https://www.jan-exner.de/berichte/20170713" is loaded
		Then DTM is present
		And the DTM library is "satelliteLib-f898bce177301e894492bf685fe6bc28e8eca6c5"

  @trackingtools
  Scenario: The search results page loads and contains DTM
    Given the page "https://www.jan-exner.de/search/?q=abc" is loaded
		Then DTM is present
		And the DTM library is "satelliteLib-f898bce177301e894492bf685fe6bc28e8eca6c5"
