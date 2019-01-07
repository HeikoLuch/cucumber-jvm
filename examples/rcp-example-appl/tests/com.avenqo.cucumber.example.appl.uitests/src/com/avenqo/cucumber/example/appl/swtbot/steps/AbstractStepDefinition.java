package com.avenqo.cucumber.example.appl.swtbot.steps;


import org.eclipse.e4.core.contexts.EclipseContextFactory;
import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.swtbot.e4.finder.widgets.SWTWorkbenchBot;
import org.eclipse.swtbot.swt.finder.SWTBot;

import com.avenqo.cucumber.example.appl.E4LifeCycle;

import cucumber.runtime.CucumberException;

abstract class AbstractStepDefinition {

	private static SWTWorkbenchBot wbot;
	private static SWTBot bot;
	private static IEclipseContext context = EclipseContextFactory.create();
	
	protected static SWTBot bot() {
		if (bot == null) {
			bot = new SWTBot();
		}
		return bot;
	}
	
	protected static IEclipseContext getEclipseContext1() {
		if (context == null)
			context = EclipseContextFactory.create();
        return context;
	}
	
	protected  SWTWorkbenchBot getWorkbenchBot() {
		if (wbot==null)
			wbot = new SWTWorkbenchBot(getEclipseContext());
		return wbot;
	}
	
	protected  IEclipseContext getEclipseContext() {
		return E4LifeCycle.workbenchContext;
	}
	
	
	protected boolean string2boolean(String value) {
		boolean isTrue = value.toLowerCase().equals("true");
		boolean isFalse = value.toLowerCase().equals("false");
		
		if ((isTrue  && isFalse) || (!isTrue && !isFalse))
			throw new CucumberException ("Boolean expression (" + value + ") is invalid.");
		return isTrue;
	}
}
