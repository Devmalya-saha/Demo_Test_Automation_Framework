@Regression
Feature: As a user I want to have the possibility to sign up (register) so I could create an account with my custom settings
  US_1_User Profile Registration (R08)

  Background:
    Given the user is on the sign up page

  @Smoke @UserCreation
  Scenario: Check for the registration of valid users
    When the valid inputs for each required fields are given
    And user should be able to login with the same user name and password

  @UserCreation
  Scenario: Check if once a user has registered the user can register again with the same email or not
    Given a user has already registered
    When create an account button is clicked
    Then the user returns to the registration page again
    And the user tries to register with the same e-mail account
    Then create an account button is clicked
    And the user should be displayed a proper error message showing e-mail already exists

  Scenario Outline: Verify invalid registration by the user due to invalid first name
    When the invalid input is given in the first name "<firstName>" field
    Then create an account button is clicked
    And the invalid first name  message should be displayed to the user properly
    Examples:
      | firstName                                           |
      | 12345                                               |
      | John@                                               |
      | *Emma                                               |
      | Christophe123                                       |
      | Ορέστης                                             |
      | 李                                                   |
      | O'Connor-Smith-Is-Very-Very-Very-Very-LongFirstName |
      | HelloWorldHelloWorldHelloWorldHelloWorld!@#         |
      |                                                     |



  Scenario Outline: Verify invalid registration by the user due to invalid last name
    When the invalid input is given in the last name "<lastName>" field
    Then create an account button is clicked
    And the invalid last name  message should be displayed to the user properly
    Examples:
      | lastName                                            |
      | 12345                                               |
      | John@                                               |
      | *Emma                                               |
      | Christophe123                                       |
      | Ορέστης                                             |
      | 李                                                   |
      | O'Connor-Smith-Is-Very-Very-Very-Very-LongFirstName |
      | HelloWorldHelloWorldHelloWorldHelloWorld!@#         |
      |                                                     |

  Scenario Outline: Verify invalid registration by the user due to invalid last name
    When the invalid input is given in the last name "<lastName>" field
    Then create an account button is clicked
    And the invalid last name  message should be displayed to the user properly
    Examples:
      | lastName                                            |
      | 12345                                               |
      | John@                                               |
      | *Emma                                               |
      | Christophe123                                       |
      | Ορέστης                                             |
      | 李                                                   |
      | O'Connor-Smith-Is-Very-Very-Very-Very-LongFirstName |
      | HelloWorldHelloWorldHelloWorldHelloWorld!@#         |
      |                                                     |


  Scenario Outline: Verify invalid registration by the user due to invalid e-mail
    When the invalid invalid input for the e-mail  is given in the e-mail field <e-mail>
    Then create an account button is clicked
    And the invalid e-mail message should be displayed to the user properly
    Examples:
      | e-mail                 |
      | "plainaddress"         |
      | "@no-local-part.com"   |
      | "username@"            |
      | "username@.com"        |
      | "username@com"         |
      | "username@.com."       |
     # | "username@domain..com" |
      | ".username@yahoo.com"  |
      | "username@yahoo.com."  |
      | "user name@yahoo.com"  |
     # | "username@-yahoo.com"  |
      | "username@#$.com"      |
      | "user@name@domain.com" |
      | "user\\n@domain.com"   |


  Scenario: Verify unsuccessful registration of a user due to mismatch of password
    When the password and the confirm password doesn't match
    Then the error message should be visible to the user


  @UserCreation
  Scenario Outline: Verify the correct role is allocated to a user or not
    When the user is registering for first time if his "<role>" is already assigned as waiter by admin the user will be waiter or else he will be a customer
    And the "<role>" should be properly visible in the user page
    Examples:
      | role     |
      | CUSTOMER |
      | WAITER   |
      





