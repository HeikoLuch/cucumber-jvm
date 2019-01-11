package com.avenqo.cucumber.example.appl.swtbot.runner;

import org.junit.Ignore;
import org.junit.runner.RunWith;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;

@RunWith(Cucumber.class)
@CucumberOptions(
		glue = { "com.avenqo.cucumber.example.appl.swtbot.steps" }, 
		plugin = { "progress", "json:target/cucumber-report.json" }, 
		features = { "features-example-appl" })
public class SWTBotTestRunner {
}
