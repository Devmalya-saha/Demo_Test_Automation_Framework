@Now @Regression
Feature:As an Admin, I want the application to automatically assign roles to new users based on predefined criteria,
  So that users can have appropriate access and permissions.
  US_3_AUTOMATIC_ROLE_ASSIGNMENT

Scenario Outline: Verify automatic role allocation
Given I have a valid request body for creating a <role>
When I have to register and then signin for that <role>
Then the response status code should be 200
And the role should be <role>

Examples:
| role       |
| "CUSTOMER" |
| "WAITER"   |