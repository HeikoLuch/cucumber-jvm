package com.avenqo.cucumber.example.appl.swtbot.steps;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

//import org.eclipse.swtbot.e4.finder.widgets.SWTBotView;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotMenu;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotShell;
import org.junit.Assert;

//import cucumber.api.java.en.Given;
//import cucumber.api.java.en.Then;
//import cucumber.api.java.en.When;

public class ExampleApplSteps extends AbstractStepDefinition 
{
	private static final String PART_TITLE = "Sample Part";

	/*
	@Given("^the menu Help is enabled$")
	public void menuHelpIsEnabled() throws Throwable {
		// Call about dialog
		SWTBotMenu helpMenu = bot().menu("Help");
		Assert.assertNotNull(helpMenu);
		Assert.assertTrue(helpMenu.isEnabled());
	}

	
	@Given("^the textfield is empty$")
	public void textfieldIsEmpty() throws Throwable {
		String txt = bot().text().getText();
		assertEquals("", txt);
	}

	@Given("^the view is not dirty$")
	public void viewIsNotDirty() throws Throwable {
		SWTBotView part = getWorkbenchBot().partByTitle(PART_TITLE);
		assertFalse (part.getPart().isDirty());
	}

	
	@Given("the MenuItem {string} {string} enabled state is {string}")
	public void checkToolbarItemEnabledState(String menuItem1, String menuItem2, String enabled) {
		SWTBotMenu item = getWorkbenchBot().menu(menuItem1).menu(menuItem2);
		assertEquals (string2boolean(enabled), item.isEnabled());
	}
		
	


	@When ("I enter the text {string}")
    public void enterText(String txt) {
		bot().text().setText(txt);
	}
	
	
	@Then ("^the view is dirty$")
	public void viewIsDirty() throws Throwable {
		SWTBotView part = getWorkbenchBot().partByTitle(PART_TITLE);
		assertTrue (part.getPart().isDirty());
	}
    
	@When("^I click then menu Help$")
	public void clickHelpMenu() throws Throwable {
		bot().menu("Help").menu("About").click();
	}

	@Then("^the About-dialog is open$")
	public void dialogAboutIsOpen() throws Throwable {
		SWTBotShell dialogAbout = bot().shell("About");
		//assertNull(dialogAbout);
		assertNotNull(dialogAbout);
		dialogAbout.bot().button().click();
	}
	
	*/
}
