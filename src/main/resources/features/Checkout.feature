Feature: Checkout
  Checkout process with placing an order

  Scenario: Testing the purchase of two items
    Given I go to the Website
    When I add two elements to the cart
    And I proceed to checkout
    And I confirm address, shipping, payment and final order
    Then The elements are bought