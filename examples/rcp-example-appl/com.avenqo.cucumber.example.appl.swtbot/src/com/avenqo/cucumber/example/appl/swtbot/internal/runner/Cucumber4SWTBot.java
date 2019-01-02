package com.avenqo.cucumber.example.appl.swtbot.internal.runner;

import org.eclipse.swtbot.swt.finder.junit.internal.CapturingFrameworkMethod;
import org.eclipse.swtbot.swt.finder.junit.internal.ScreenshotCaptureNotifier;
import org.junit.runner.Description;
import org.junit.runner.notification.RunNotifier;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.InitializationError;

import cucumber.api.junit.Cucumber;
import cucumber.runtime.junit.FeatureRunner;

public class Cucumber4SWTBot extends Cucumber {

	public Cucumber4SWTBot(Class clazz) throws InitializationError {
		super(clazz);
	}

	@Override
	public void run(RunNotifier notifier) {
		if (notifier instanceof ScreenshotCaptureNotifier) {
			super.run(notifier);
		} else {
			RunNotifier wrappedNotifier = new ScreenshotCaptureNotifier(notifier);
			super.run(wrappedNotifier);
		}
	}
	
	 @Override
	    protected void runChild(FeatureRunner child, RunNotifier notifier) {
		Description description = describeChild(child);
		if (isIgnored(child)) {
			notifier.fireTestIgnored(description);
		} else {
			FrameworkMethod toRun = null;
			if (notifier instanceof ScreenshotCaptureNotifier) {
				toRun = new CapturingFrameworkMethod(null, description,
						(ScreenshotCaptureNotifier) notifier);
			}
			//runLeaf(methodBlock(toRun), description, notifier);
		}
	}
}
