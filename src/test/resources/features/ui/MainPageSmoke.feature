@Smoke @Regression
Feature: As a User, I want to be able to view Restaurant location details so I could learn about the Restaurant and check its ratings
  Smoke Test the elements on Main Page to ensure proper functioning

  Background: When the web page is loaded
    Given the user is on the main page

  Scenario: Verify if the main page loads successfully
    Then the user should see the correct page title
    And the logo should be visible

  Scenario Outline: Verify if the main page elements are interactable
    Then this "<element>" should be interactable
    Examples:
      | element          |
      | sign in button   |
      | book table tab   |
      | main page tab    |
      | view menu button |

  Scenario: Check the content of the popular dishes section of the main page
  Then popular dishes should be visible

  Scenario: Check the content of the locations section of the main page
    Then locations should be visible
