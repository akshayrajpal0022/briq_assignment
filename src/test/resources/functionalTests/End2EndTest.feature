Feature: Get All pins Data from milwaukee

  Scenario: milwaukee homepage
    Given User is on homepage
    When user goes to map view
    Then user should be able to get all the details of cards
