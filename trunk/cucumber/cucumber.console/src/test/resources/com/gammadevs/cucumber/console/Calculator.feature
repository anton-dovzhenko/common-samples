Feature: Calculator

    @addition
    Scenario: Simple addition with calculator
    Given I have a calculator
    When I add 3 to 5
    Then result is 8

    @addition
        Scenario: Complicated addition with calculator
        Given I have a calculator
        When I add 300 to 5557
        Then result is 5857

    @subtraction
        Scenario: Simple subtraction with calculator
        Given I have a calculator
        When I subtract 3 from 5
        Then result is 2

    @subtraction
        Scenario: Complicated subtraction with calculator
        Given I have a calculator
        When I subtract 333 from 5555
        Then result is 5222