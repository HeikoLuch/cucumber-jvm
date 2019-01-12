package com.avenqo.cucumber.example.appl.swtbot.steps.calculator;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.logging.Logger;

import org.eclipse.swtbot.e4.finder.widgets.SWTBotView;
import org.eclipse.swtbot.e4.finder.widgets.SWTWorkbenchBot;

public class PageObjectCalculatorView {

	private static final Logger log = Logger.getLogger(PageObjectCalculatorView.class.getName());

	private static final String PART_TITLE = "Calculator";

	private SWTWorkbenchBot workbenchBot;

	public PageObjectCalculatorView(SWTWorkbenchBot wb) {
		workbenchBot = wb;
	}

	public void iCheckAvailability() {
		SWTBotView part = workbenchBot.partByTitle(PART_TITLE);
		assertTrue(part.getPart().isVisible());
		assertFalse(part.getPart().isDirty());
	}

	public String getDisplayValue() {
		String s = workbenchBot.text().getText();
		log.info("getDisplayValue() returns: '" + s + "'");
		return s;
	}

	public void enter(Integer arg1) {
		log.info("enter (" + arg1 + ")");
		String s = arg1.toString();
		assertNotNull(s);
		assertFalse(s.length() == 0);
		for (int i = 0; i < s.length(); i++) {
			char c = s.charAt(i);
			pushButtonNum(new String(c + ""));
		}
	}

	private void pushButtonNum(String val) {
		log.info("pushButtonNum(" + val + ")");
		workbenchBot.button(val).click();
	}

	public void pushButtonPlus() {
		log.info("pushButtonPlus()");
		workbenchBot.button("+").click();
	}

	public Double getDisplayValueAsDouble() {
		log.info("getDisplayValueAsDouble()");
		String s = getDisplayValue();
		return new Double(s);
	}

	public void pushButtonResult() {
		log.info("pushButtonResult()");
		workbenchBot.button("=").click();
	}

	public void clearMemory() {
		log.info("clearMemory()");
		workbenchBot.button("MC").click();
	}

	public void pushButtonWithLabel(String operation) {
		log.info("pushButtonWithLabel('" + operation + "')");
		workbenchBot.button(operation).click();
	}

	public void pushMemoryAdd() {
		log.info("pushMemoryAdd()");
		workbenchBot.button("M+").click();
	}

	public void memoryRecall() {
		log.info("memoryRecall()");
		workbenchBot.button("MR").click();
		
		log.info("Display shows: " + workbenchBot.text().getText());
	}

}
