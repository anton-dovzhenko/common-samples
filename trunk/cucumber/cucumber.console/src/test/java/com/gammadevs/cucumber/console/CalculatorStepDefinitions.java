package com.gammadevs.cucumber.console;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.junit.Assert;

/**
 * Project: cucumber.console
 * User: Anton_Dovzhenko - Gammadevs inc
 * Date: 2/8/14
 */
public class CalculatorStepDefinitions {

    private Calculator calculator;
    private int result;

    @Given("^I have a calculator")
    public void I_have_a_calculator() {
        calculator = new Calculator();
    }

    @When("^I add (\\d+) to (\\d+)")
    public void I_add(int a, int b) {
        result = calculator.add(a, b);
    }

    @When("^I subtract (\\d+) from (\\d+)")
    public void I_subtract(int a, int b) {
        result = calculator.subtract(b, a);
    }

    @Then("^result is (\\d+)")
    public void resultIs(int expected) {
        Assert.assertEquals(expected, result);
    }
}
