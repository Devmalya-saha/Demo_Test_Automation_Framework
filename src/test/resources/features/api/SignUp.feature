@SignUp @Regression
Feature: As a user I want to have the possibility to sign up (register) so I could create an account with my custom settings
  US_1_USER_PROFILE_REGISTRATION


  @Smoke
  Scenario Outline: Verify successful registration of a new user
    Given a valid SignUp request is prepared
    When a POST request is sent to the sign up end point
    Then the response status code should be <expectedStatusCode>
    And the response should have message <expectedMessage>
    Examples:
      | expectedStatusCode | expectedMessage                |
      | 201                | "User registered successfully" |

  Scenario Outline: Verify unsuccessful registration of an existing user
    Given a valid SignUp request is prepared
    When a POST request is sent to the sign up end point twice
    Then the response status code should be <expectedStatusCode>
    And the response should have message <expectedMessage>
    Examples:
      | expectedStatusCode | expectedMessage                                  |
      | 409                | "A user with this email address already exists." |

  Scenario Outline: Verify unsuccessful registration for an user due to invalid password format (length)
    Given a request body is created for a new user with <password>
    When a POST request is sent to the sign up end point
    Then the response status code should be 400
    And the response should have message "Password must be 8-16 characters long and include at least one uppercase letter, one lowercase letter, one number, and one special character."
    Examples:
      | password              |
      | "Simple"              |
      | "SimpleDevQRRCT@1234" |
      | "simpledev@123"       |
      | "SIMPLEDEV@123"       |
      | "SIMPLEDEV2366"       |
      | "SIMPLEDEV^^*^"       |
      | "SIMPLEDEVFUTURE"     |

  Scenario Outline: Verify unsuccessful registration of a user in case of incorrect email
    Given a request body is created for a new user with invalid <email> format
    When a POST request is sent to the sign up end point
    Then the response status code should be 400
    And the response should have message "Invalid email"

    Examples:
      | email                  |
      | "plainaddress"         |
      | "@no-local-part.com"   |
      | "username@"            |
      | "username@.com"        |
      | "username@com"         |
      | "username@.com."       |
      | "username@domain..com" |
      | ".username@yahoo.com"  |
      | "username@yahoo.com."  |
      | "user name@yahoo.com"  |
      | "username@-yahoo.com"  |
      | "username@#$.com"      |
      | "user@name@domain.com" |
      | "user\n@domain.com"    |

  Scenario Outline: Verify unsuccessful registration for an invalid firstName and lastName
    Given a request body is created with invalid "<field>" <invalidname>
    When a POST request is sent to the sign up end point
    Then the response status code should be 400
    And the response should have message <expectedMessage>

    Examples:
      | field     | invalidname                                             | expectedMessage      |
      | firstName | "John123"                                               | "Invalid first name" |
      | firstName | "王伟"                                                   | "Invalid first name" |
      | lastName  | "Doe123"                                                | "Invalid last name"  |
      | lastName  | "O’Connor 😊"                                           | "Invalid last name"  |
      | lastName  | "NULL"                                                  | "Invalid last name"  |
      | firstName | "NULL"                                                  | "Invalid first name" |
      | firstName | "ANameThatIsWayTooLongForValidationAndExceedsAllLimits" | "Invalid first name" |
      | lastName  | "ANameThatIsWayTooLongForValidationAndExceedsAllLimits" | "Invalid last name"  |

  Scenario Outline: Verify unsuccessful registration for empty required fields
    Given a request body is created with empty "<field>" <invalidname>
    When a POST request is sent to the sign up end point
    Then the response status code should be 400
    And the response should have message <expectedMessage>

    Examples:
      | field     | invalidname | expectedMessage                                                                                                                                 |
      | firstName | ""          | "Invalid first name"                                                                                                                            |
      | lastName  | ""          | "Invalid last name"                                                                                                                             |
      | email     | ""          | "Invalid email"                                                                                                                                 |
      | password  | ""          | "Password must be 8-16 characters long and include at least one uppercase letter, one lowercase letter, one number, and one special character." |

  Scenario Outline: Verify unsuccessful registration for null required fields
    Given a request body is created with null "<field>"
    When a POST request is sent to the sign up end point
    Then the response status code should be 400
    And the response should have message <expectedMessage>
    Examples:
      | field     | expectedMessage                                                                                                                                 |
      | firstName | "Invalid first name"                                                                                                                            |
      | lastName  | "Invalid last name"                                                                                                                             |
      | email     | "Invalid email"                                                                                                                                 |
      | password  | "Password must be 8-16 characters long and include at least one uppercase letter, one lowercase letter, one number, and one special character." |
