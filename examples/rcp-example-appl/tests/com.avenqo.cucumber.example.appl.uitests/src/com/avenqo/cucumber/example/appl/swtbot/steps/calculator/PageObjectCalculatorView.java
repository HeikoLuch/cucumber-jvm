package com.avenqo.cucumber.example.appl.swtbot.steps.calculator;

import static org.junit.Assert.assertFalse;

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

}
