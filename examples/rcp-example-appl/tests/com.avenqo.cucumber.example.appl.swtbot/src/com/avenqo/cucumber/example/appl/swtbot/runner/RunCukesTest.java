package com.avenqo.cucumber.example.appl.swtbot.runner;

import org.junit.runner.RunWith;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;

@RunWith(Cucumber.class)
@CucumberOptions(plugin = {"progress", "json:target/cucumber-report.json"}, features="features-cukes")

public class RunCukesTest {
}
