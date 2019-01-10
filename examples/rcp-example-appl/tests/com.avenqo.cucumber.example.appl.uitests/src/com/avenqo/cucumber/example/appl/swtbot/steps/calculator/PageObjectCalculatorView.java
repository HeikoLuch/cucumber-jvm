package com.avenqo.cucumber.example.appl.swtbot.steps.calculator;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

import org.eclipse.swtbot.e4.finder.widgets.SWTBotView;
import org.eclipse.swtbot.e4.finder.widgets.SWTWorkbenchBot;

public class PageObjectCalculatorView {

	private static final String PART_TITLE = "Sample Part";
	
	private SWTWorkbenchBot workbenchBot;
	
	public PageObjectCalculatorView(SWTWorkbenchBot wb) {
		workbenchBot = wb;
	}

	public  void isStarted() {
		SWTBotView part = workbenchBot.partByTitle(PART_TITLE);
		assertFalse (part.getPart().isDirty());
	}

	public String getDisplayValue() {
		// TODO Auto-generated method stub
		return workbenchBot.text().getText();
	}

	public void enter(Integer arg1) {
		String s = arg1.toString();
		assertNotNull(s);
		assertFalse(s.length()==0);
		for (int i=0; i<s.length(); i++)
		{
			char c = s.charAt(i);
			pushButtonNum(new String(c + ""));
			
		}
	}

	private void pushButtonNum(String val) {
		workbenchBot.button(val).click();
	}

	public void pushPlus() {
		workbenchBot.button("+").click();
	}

	public Double getDisplayValueAsDouble() {
		String s = getDisplayValue();
		return new Double(s);
	}

	public void pushResult() {
		workbenchBot.button("=").click();
		
	}

}
