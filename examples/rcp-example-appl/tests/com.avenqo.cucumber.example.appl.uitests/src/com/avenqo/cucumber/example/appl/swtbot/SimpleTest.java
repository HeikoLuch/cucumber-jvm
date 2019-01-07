package com.avenqo.cucumber.example.appl.swtbot;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.eclipse.swtbot.swt.finder.SWTBot;
import org.eclipse.swtbot.swt.finder.junit.SWTBotJunit4ClassRunner;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotButton;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotMenu;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotShell;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.*;
import org.junit.runner.RunWith;

@Ignore
@RunWith(SWTBotJunit4ClassRunner.class)
public class SimpleTest {

	private static SWTBot bot;

	@BeforeClass
	public static void beforeClass() throws Exception {
		// don't use SWTWorkbenchBot here which relies on Platform 3.x
		bot = new SWTBot();
	}

	@Test
	public void executeExit() {
		// testing on hard-coded String just to have
		// a simple example

		// Call about dialog
		SWTBotMenu helpMenu = bot.menu("Help");
		Assert.assertNotNull(helpMenu);
		SWTBotMenu aboutMenu = helpMenu.menu("About");
		Assert.assertNotNull(aboutMenu);
		aboutMenu.click();

		// check dialog content and close it
		SWTBotShell dialogAbout = bot.shell("About");
		assertNotNull(dialogAbout);
		dialogAbout.activate();
		dialogAbout.bot().button("OK").click();
		assertFalse(dialogAbout.isOpen());

		// Leave Application

		SWTBotMenu fileMenu = bot.menu("File");
		Assert.assertNotNull(fileMenu);
		SWTBotMenu exitMenu = fileMenu.menu("Quit");
		Assert.assertNotNull(exitMenu);
		exitMenu.click();

		// Confirm exit via dialog
		SWTBotShell dialogConfirmation = bot.shell("Confirmation");
		assertNotNull(dialogConfirmation);
		dialogConfirmation.activate();

		assertTrue(dialogConfirmation.bot().button("Cancel").isEnabled());
		assertTrue(dialogConfirmation.bot().button("Cancel").isVisible());
		SWTBotButton btnOk = dialogConfirmation.bot().button("OK");

		assertTrue(btnOk.isEnabled());
		assertTrue(btnOk.isVisible());
		//fail("Rhabarbar");
		//Den Button kann man zwar klicken, führt aber zu einem IndexOutOfBoundException
		//btnOk.click();
		// dialogConfirmation.bot().button("OK").click();

	}

	@AfterClass
	public static void sleep() {
		bot.sleep(2000);
	}
}
