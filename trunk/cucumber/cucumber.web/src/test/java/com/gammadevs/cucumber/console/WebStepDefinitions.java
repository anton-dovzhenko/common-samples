package com.gammadevs.cucumber.console;

import com.google.common.base.Function;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.concurrent.TimeUnit;

/**
 * Project: web-push
 * User: Anton_Dovzhenko - Gammadevs inc
 * Date: 2/9/14
 */
public class WebStepDefinitions {

    WebDriver webDriver = BrowserDriver.getCurrentDriver();

    @Given("^I go to login page")
    public void I_go_to_login_page() {
        webDriver.get("http://vk.com/login");
        webDriver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
    }

    @When("^I enter name \"([^\"]*)\"$")
    public void I_enter_name(String name) {
        WebElement emailInput = webDriver.findElement(By.id("quick_email"));
        emailInput.sendKeys(name);
    }

    @When("^I enter password \"([^\"]*)\"$")
     public void I_enter_password(String password) {
        WebElement passwordInput = webDriver.findElement(By.id("quick_pass"));
        passwordInput.sendKeys(password);
    }

    @When("^I click on Enter button")
    public void I_click_on_Enter_button() {
        WebElement enterButton = webDriver.findElement(By.id("quick_login_button"));
        enterButton.click();
    }

    @Then("^I successfully logged in")
    public void I_successfully_logged_in() {
        Wait<WebDriver> wait = new FluentWait<WebDriver>(webDriver)
                .withTimeout(30, TimeUnit.SECONDS)
                .pollingEvery(5, TimeUnit.SECONDS)
                .ignoring(NoSuchElementException.class);

        WebElement myPageDiv = wait.until(new Function<WebDriver, WebElement>() {
            public WebElement apply(WebDriver driver) {
                return driver.findElement(By.id("myprofile_wrap"));
            }
        });
    }

    @Then("^I get login error message")
    public void I_get_login_error_message() {
        Wait<WebDriver> wait = new FluentWait<WebDriver>(webDriver)
                .withTimeout(30, TimeUnit.SECONDS)
                .pollingEvery(5, TimeUnit.SECONDS)
                .ignoring(NoSuchElementException.class);

        WebElement messageDiv = wait.until(new Function<WebDriver, WebElement>() {
            public WebElement apply(WebDriver driver) {
                return driver.findElement(By.id("message"));
            }
        });
        Assert.assertTrue(messageDiv.getText().startsWith("Не удается войти."));
    }

    @When("^I logout")
    public void I_logout() {
        WebElement logoutAnchor = webDriver.findElement(By.id("logout_link"));
        logoutAnchor.click();
    }
}
