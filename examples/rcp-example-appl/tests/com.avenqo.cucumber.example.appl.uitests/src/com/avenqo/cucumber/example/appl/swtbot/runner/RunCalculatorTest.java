package com.avenqo.cucumber.example.appl.swtbot.runner;

import org.junit.Ignore;
import org.junit.runner.RunWith;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber4Eclipse;

@RunWith(Cucumber4Eclipse.class)
@CucumberOptions(
		glue = { "com.avenqo.cucumber.example.appl.swtbot.steps.calculator" }, 
		plugin = { "progress", "json:target/cucumber-report.json" }, 
		features = { "features/calculator" })
public class RunCalculatorTest {
}
