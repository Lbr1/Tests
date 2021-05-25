Feature: Customers
  Add a new customer

  Scenario: Add a new customer
    Given There is a new customer with name "Joao" and number "968978801"
    When We want to add the customer
    Then You can Get the new customer

