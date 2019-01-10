package com.avenqo.cucumber.example.appl.swtbot.steps.calculator;

import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

import java.util.List;

import org.eclipse.swtbot.e4.finder.widgets.SWTBotView;

import com.avenqo.cucumber.example.appl.swtbot.steps.AbstractStepDefinition;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class RpnCalculatorStepdefs extends AbstractStepDefinition{
    
	
	private PageObjectCalculatorView calculatorPageObject = null;;
	
    @Given("a calculator I just turned on")
    public void a_calculator_I_just_turned_on() {
    	calculatorPageObject = new PageObjectCalculatorView (getWorkbenchBot());
    	calculatorPageObject.isStarted();
    }

    @When("I add {int} and {int}")
    public void adding(int arg1, int arg2) {
    }

    @Given("I press (.+)")
    public void I_press(String what) {
     
    }

    @Then("the result is {int}")
    public void the_result_is(double expected) {
     
    }

    @Before("not @foo")
    public void before(Scenario scenario) {
        scenario.write("Runs before scenarios *not* tagged with @foo");
    }

    @After
    public void after(Scenario scenario) {
        // result.write("HELLLLOO");
    }

    @Given("the previous entries:")
    public void thePreviousEntries(List<Entry> entries) {
    }

    static final class Entry {
        private Integer first;
        private Integer second;
        private String operation;

        public Integer getFirst() {
            return first;
        }

        public void setFirst(Integer first) {
            this.first = first;
        }

        public Integer getSecond() {
            return second;
        }

        public void setSecond(Integer second) {
            this.second = second;
        }

        public String getOperation() {
            return operation;
        }

        public void setOperation(String operation) {
            this.operation = operation;
        }
    }
}
