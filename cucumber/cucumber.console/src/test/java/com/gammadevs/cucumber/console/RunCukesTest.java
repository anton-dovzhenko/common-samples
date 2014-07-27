package com.gammadevs.cucumber.console;

import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

/**
 * Project: cucumber.console
 * User: Anton_Dovzhenko - Gammadevs inc
 * Date: 2/8/14
 */
@RunWith(Cucumber.class)
@Cucumber.Options(format = {"html:target/cucumber-html-report", "json:target/cucumber-json-report.json"})
public class RunCukesTest {
}
