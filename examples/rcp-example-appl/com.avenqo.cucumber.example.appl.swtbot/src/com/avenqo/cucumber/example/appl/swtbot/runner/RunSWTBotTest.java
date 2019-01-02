package com.avenqo.cucumber.example.appl.swtbot.runner;

import org.junit.runner.RunWith;

import com.avenqo.cucumber.example.appl.swtbot.internal.runner.Cucumber4SWTBot;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;

@RunWith(Cucumber.class)
@CucumberOptions(glue = { "com.avenqo.cucumber.example.appl.swtbot.steps" }, plugin = { "progress",
		"json:target/cucumber-report.json" }, features = { "features-example-appl" })
public class RunSWTBotTest {
}
