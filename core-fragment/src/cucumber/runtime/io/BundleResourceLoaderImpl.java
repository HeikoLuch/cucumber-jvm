package cucumber.runtime.io;

import java.io.IOException;
import java.net.URL;
import java.util.Enumeration;
import java.util.Vector;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;

public class BundleResourceLoaderImpl extends BundleResourceLoader {

	// Logger log = LoggerFactory.get

	private boolean DEBUG = true;

	@Override
	public Enumeration<URL> iteratorPushResources( String path, String suffix,
			ResourceIteratorFactory resourceIteratorFactory) {
		Vector<URL> res = new Vector<URL>();
		try {

			// Get this bundle and bundle context
			Bundle currentBundle = org.osgi.framework.FrameworkUtil.getBundle(BundleResourceLoaderImpl.class);
			BundleContext bc = currentBundle.getBundleContext();
			if (bc == null) {
				currentBundle.start();
				bc = currentBundle.getBundleContext();
			}
			// printBundleInfo(currentBundle);
			printBundleState(currentBundle);
			// Get all known Bundles
			Bundle[] bundles = bc.getBundles();
			for (Bundle bundle : bundles) {
				String symbolicName = bundle.getSymbolicName();
				//if (symbolicName.startsWith("com.avenqo.cucumber.example.appl.swtbot")) {
			    {	
			    	System.out.println(symbolicName);
					//printBundleInfo1(bundle);
					//printBundleState(bundle);

					// path = "/bin/com/avenqo/pep/appl/ui/test/steps", suffix = "*.class";
//findEntries('/', '*.class', true): bundleentry://15.fwk1302227152/bin/com/avenqo/pep/appl/ui/test/cucumber/runner/TestRunner.class

//					Enumeration<URL> resourcesUrls = bundle.getResources("com/avenqo/pep/appl/ui/test/cucumber/steps");
//					resourcesUrls = bundle.getResources("com/avenqo/pep/appl/ui/test/cucumber/steps/*.class");
//					resourcesUrls = bundle.getResources("/com/avenqo/pep/appl/ui/test/cucumber/steps/*.class");
//					resourcesUrls = bundle.getResources("*.class");

					Enumeration<URL> resources = findMatchingUrls(bundle, path, suffix);
					if (resources != null) {
						while (resources.hasMoreElements()) {
							res.addElement( resources.nextElement());
							
						}
					}

//					if (Bundle.INSTALLED == bundle.getState()) {
//						System.out.println("Please resolve this bundle: " + bundle.getSymbolicName());
//					}
//					else
//						System.out.println("Bundle: " + bundle.getSymbolicName());

//					Enumeration<URL> resources = bundle.findEntries("/bin/com/avenqo/pep/appl/ui/test/steps", "*.class",
//							true);
//resources = b.getResources("*.class");

//					BundleWiring wiring = currentBundle.adapt(BundleWiring.class);
//					// Collection<String> resourceCollection = wiring.listResources("/", "*class",
//					// BundleWiring.LISTRESOURCES_RECURSE);
//					Collection<String> resourceCollection = wiring.listResources("/", "*class",
//							BundleWiring.LISTRESOURCES_RECURSE | BundleWiring.LISTRESOURCES_LOCAL);
//					for (String s : resourceCollection) {
//						// System.out.println(" CLASS locale: " + s);
//					}

					// Enumeration<URL> resources =
					// b.findEntries("**/com/avenqo/pep/appl/ui/test/steps","*.class", true);

				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();

		}

		return (res.size() > 0) ? res.elements() : null;
	}

	private Enumeration<URL> findMatchingUrls(Bundle bundle, String path, String suffix) {
		Vector<URL> resources = new Vector<URL>();

		Enumeration<URL> urls = bundle.findEntries("/", "*" + suffix, true);
		 //urls = bundle.findEntries("/", "*.class", true);
		if (urls != null) {
			while (urls.hasMoreElements()) {
				URL url = urls.nextElement();

				// Check if file is reslovable
				if ((url != null) && url.toString().contains(path))
					try {
						String filepath = Helpers.filePath(url);
						if (filepath != null) {
							resources.add(url);
						}

					} catch (Throwable t) {
						// Ignore this url
						t.printStackTrace();
					}
			}
		}

		return resources.size() > 0 ? resources.elements() : null;
	}

	private void printBundleInfo1(Bundle b) throws IOException {
		if (DEBUG) {
			System.out.println("\n==== INFO: Current Bundle Symbolic Name '" + b.getSymbolicName() + " ====\n");

			// Listet die eigenen Classen auf. Ausgabe bspw.
			// findEntries('/', '*.class', true):
			// bundleentry://30.fwk1302227152/cucumber/api/event/TestCaseFinished.class
			//
			System.out.println("  ---- Find entries ----");
			Enumeration<URL> urls = b.findEntries("/", "*.class", true);
			if (urls != null) {
				while (urls.hasMoreElements()) {
					URL url = urls.nextElement();
					System.out.println("  findEntries('/', '*.class', true): " + url.toExternalForm());

					// Versuche, den Filepath zu ermitteln
					try {
						String filepath = Helpers.filePath(url);
						System.out.println("     filepath = '" + filepath + "'");

					} catch (Throwable t) {
						t.printStackTrace();
					}
				}
			}
			// printResources1(b);
			System.out.println("======== END: '" + b.getSymbolicName() + " ==========\n");
		}
	}

	private void printResources(Bundle b) throws IOException {
		if (DEBUG) {
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
	}

	private void printBundleState(Bundle b) {
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
