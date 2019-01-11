package com.avenqo.cucumber.example.appl.swtbot.steps.calculator;

import static org.junit.Assert.assertEquals;

import java.util.List;
import java.util.logging.Logger;

import com.avenqo.cucumber.example.appl.swtbot.steps.AbstractStepDefinition;

import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class RpnCalculatorStepdefs extends AbstractStepDefinition {

	//private static final Logger log = Logger.getLogger(RpnCalculatorStepdefs.class.getName());

	private static PageObjectCalculatorView calculatorPageObject = null;;

	
	@Given("a calculator I just turned on")
	public void a_calculator_i_just_turned_on() {
		//log.info("a_calculator_i_just_turned_on()");
		calculatorPageObject = new PageObjectCalculatorView(getWorkbenchBot());
		calculatorPageObject.iCheckAvailability();
	}

	@Given("the memory is cleared")
	public void the_memory_is_cleared() {
		//log.info("the_memory_is_cleared()");
		calculatorPageObject.clearMemory();
	}

	@When("I add {int} and {int}")
	public void i_add_int_and_int(int arg1, int arg2) {
		//log.info("i_add_int_and_int(" + arg1 + ", " + arg2 + ")");
		calculatorPageObject.enter(arg1);
		calculatorPageObject.pushButtonPlus();
		calculatorPageObject.enter(arg2);
		calculatorPageObject.pushButtonResult();
	}

	@When("I recall memory")
	public void i_recall_memory() {
		//log.info("i_recall_memory()");
		calculatorPageObject.memoryRecall();
	}

	@Given("I press (.+)")
	public void I_press(String buttonLabel) {
		//log.info("i_press ('" + buttonLabel + "')");
		calculatorPageObject.pushButtonWithLabel(buttonLabel);
	}

	@Given("some calculations were performed and added to the memory")
	public void some_calculations_were_performed_and_added_to_the_memory(List<Entry> entries) {
		//log.info("some_calculations_were_performed_and_added_to_the_memory(List with " + entries.size() + " entries)");
		for (Entry entry : entries) {
			calculatorPageObject.enter(entry.first);
			calculatorPageObject.pushButtonWithLabel(entry.operation);
			calculatorPageObject.enter(entry.second);
			calculatorPageObject.pushButtonResult();
			calculatorPageObject.pushMemoryAdd();
		}
	}

	@Then("the result is {int}")
	public void the_result_is(double expected) {
		//log.info("the_result_is(" + expected + ")");
		assertEquals((Double) expected, calculatorPageObject.getDisplayValueAsDouble());
	}

	@Before("not @foo")
	public void before(Scenario scenario) {
		//log.info("before (Scenario: " + scenario.getName() + ")");
		scenario.write("Runs before scenarios *not* tagged with @foo");
	}

	@After
	public void after(Scenario scenario) {
		//log.info("after (Scenario: " + scenario.getName() + ")");
		// result.write("HELLLLOO");
	}


	static final class Entry {
		// Naming corresponds with table header
		private Integer first;
		private Integer second;
		private String operation;

		public Entry(Integer _first, Integer _second, String _operation) {
			first = _first;
			second = _second;
			operation = _operation;
		}

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
