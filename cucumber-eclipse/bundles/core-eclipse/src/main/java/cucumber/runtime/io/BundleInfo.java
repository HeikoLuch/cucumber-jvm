package cucumber.runtime.io;

import java.io.IOException;
import java.net.URL;
import java.util.Enumeration;

import org.osgi.framework.Bundle;

public class BundleInfo {

	public static void printBundleInfo1(Bundle b) throws IOException {

		System.out.println("\n==== INFO: Current Bundle Symbolic Name '" + b.getSymbolicName() + " ====\n");

		// Lists classes like this:
		// findEntries('/', '*.class', true)
		// bundleentry://30.fwk1302227152/cucumber/api/event/TestCaseFinished.class
		//
		System.out.println("  ---- Find entries ----");
		Enumeration<URL> urls = b.findEntries("/", "*.class", true);
		if (urls != null) {
			while (urls.hasMoreElements()) {
				URL url = urls.nextElement();
				System.out.println("  findEntries('/', '*.class', true): " + url.toExternalForm());

				try {
					String filepath = BundleHelpers.filePath(url);
					System.out.println("     filepath = '" + filepath + "'");

				} catch (Throwable t) {
					t.printStackTrace();
				}
			}
		}
		// printResources1(b);
		System.out.println("======== END: '" + b.getSymbolicName() + " ==========\n");

	}

	public static void printResources(Bundle b) throws IOException {

		System.out.println("\n  ---- Get Resources ----");

		String name = "";
		Enumeration<URL> urls = b.getResources("");
		if (urls != null) {
			while (urls.hasMoreElements()) {
				URL url = urls.nextElement();
				System.out.println("  getResources('" + name + "'): " + url.toExternalForm());
			}
		} else
			System.out.println("  getResources('" + name + "'): " + "returned NOTHING");

	}

	public static void printBundleState(Bundle b) {
		// Determine bundle state as string
		int state = b.getState();
		String strState = "";

		if ((state & Bundle.INSTALLED) > 0)
			strState += "INSTALLED ";
		if ((state & Bundle.ACTIVE) > 0)
			strState += "ACTIVE ";
		if ((state & Bundle.RESOLVED) > 0)
			strState += "RESOLVED ";
		if ((state & Bundle.UNINSTALLED) > 0)
			strState += "UNINSTALLED ";
		if ((state & Bundle.STARTING) > 0)
			strState += "STARTING ";
		if ((state & Bundle.STOPPING) > 0)
			strState += "STOPPING ";
		if ((state & Bundle.START_TRANSIENT) > 0)
			strState += "START_TRANSIENT ";
		if ((state & Bundle.START_ACTIVATION_POLICY) > 0)
			strState += "START_ACTIVATION_POLICY ";
		if ((state & Bundle.STOP_TRANSIENT) > 0)
			strState += "STOP_TRANSIENT ";
		if (strState.length() == 0)
			strState = "Starteing, Stopping , ???";

		System.out.println("  Bundle State '" + b.getSymbolicName() + "': " + b.getState() + " [" + strState + "] ");
	}
}
