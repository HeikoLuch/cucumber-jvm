package cucumber.runtime.io;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;
import java.util.Vector;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.BundleException;
import org.osgi.framework.wiring.BundleRevision;
import org.osgi.framework.wiring.BundleWiring;

import cucumber.runtime.CucumberException;

/**
 * Utility to load classes and resources being provided by the OSGI environment.
 * Searches in all Bundles of the OSGI environment.
 * 
 * @author Heiko
 *
 */
public class BundleResourceAccessor {

	// Logger log = LoggerFactory.get

	private static final boolean DEBUG = true;
	private List<Bundle> alBundles = null;;

	/**
	 * @param clazz
	 */
	public BundleResourceAccessor() {
		try {
			// Get this bundle and bundle context
			Bundle currentBundle = org.osgi.framework.FrameworkUtil.getBundle(BundleResourceAccessor.class);
			BundleContext bc = currentBundle.getBundleContext();
			if (bc == null) {
				currentBundle.start();
				bc = currentBundle.getBundleContext();
			}

			// printBundleInfo(currentBundle);
			printBundleState(currentBundle);
			// Get all known Bundles
			alBundles = new ArrayList<Bundle>(Arrays.asList(bc.getBundles()));
		} catch (BundleException be) {
			be.printStackTrace(); // TODO: remove. is not necessary
			throw new CucumberException(be);
		}
	}

	public boolean isFragment(Bundle bundle) {
		boolean b = (bundle.adapt(BundleRevision.class).getTypes() & BundleRevision.TYPE_FRAGMENT) != 0;
		return b;
	}

	public Enumeration<URL> getResources(String path, String suffix) {

		Vector<URL> res = new Vector<URL>();

		for (Bundle bundle : alBundles) {
			Enumeration<URL> resources = findMatchingUrls(bundle, path, suffix);
			if (resources != null) {
				while (resources.hasMoreElements()) {
					URL elementURL = resources.nextElement();
					if (!res.contains(elementURL))
						res.addElement(elementURL);
				}
			}
		}
		return res.elements();
	}

	/**
	 * The returned resources may be loaded by the classloader of the considered
	 * bundle. The returned resources are not necessarily part of the bundle.
	 * 
	 * @param bundle The bundle to be searched.
	 * @param path   The path must be contained in the resource, i.e.
	 *               "com/avenqo/cucumber/example/appl/swtbot/runner".
	 * @param suffix The found resources must end with this suffix.
	 * @return
	 */
	public static Enumeration<URL> findMatchingUrls(Bundle bundle, String path, String suffix) {
		Vector<URL> vectorUrl = new Vector<URL>();
		String name = bundle.getSymbolicName();

		URL bundleUrl = bundle.getEntry("/");

		boolean bDebug = false;
		if (name.startsWith("com.avenqo.")) {
			bDebug = true;
			System.out.println(" =========== Investigating Bundle '" + name + "' ===============");

//			if (name.equals("com.avenqo.cucumber.example.appl.swtbot")) {
//				try {
//					Class<?> instance = bundle.loadClass("com.avenqo.cucumber.example.appl.swtbot.runner.RunCukesTest");
//					// Class<?> instance =
//					// bundle.loadClass("com.avenqo.cucumber.example.appl.mail.Activator");
//					if (instance == null)
//						System.out.println("Mist.");
//				} catch (ClassNotFoundException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//			}
		}

		BundleWiring bundleWiring = bundle.adapt(BundleWiring.class);

		// Getting all the matching files from classpath,; including imported packages
		Collection<String> resources = bundleWiring.listResources("/", "*" + suffix,
				BundleWiring.LISTRESOURCES_RECURSE);

		List<String> classNamesOfCurrentBundle = new ArrayList<String>();
		for (String resource : resources) {

			if (resource.toString().contains("$"))// ignore inner classes
				continue;

			String stringUrl = bundleUrl.toString() + resource;
			if (resource.contains(path))
				try {
					URL url = new URL(stringUrl);

					// check if loadable
					boolean isValid = false;
					try {
						Class<?> instance = bundle.loadClass(new BundleResource(url).getClassName(suffix));
						if (instance != null) {
							isValid = true;
						}
					} catch (ClassNotFoundException | NoClassDefFoundError e) {
						// ignore
					}
					if (isValid)
						vectorUrl.addElement(url);
				} catch (MalformedURLException e1) {
					e1.printStackTrace();
				}

//			//is the clasUninteressant, ob der kram im Bundle oder im Fragment liegt
//			//Kann ich es laden?
//			String className = resource.replaceAll("/", ".");
//			int suffixIndex =  className.indexOf(".class");
//			if (suffixIndex<0)
//				continue;
//			className = className.substring(0, suffixIndex);
//			try {
//				Class<?> instance = bundle.loadClass(className);
//				if (instance!=null) {
//					classNamesOfCurrentBundle.add(className);
//					if (bDebug)
//						System.out.println("Owning class: " + className);
//				}
//			} catch (ClassNotFoundException | NoClassDefFoundError e) {
//				if (bDebug)
//					System.out.println("Ignoring: " + className);				
//			}

		}
		return vectorUrl.size() > 0 ? vectorUrl.elements() : null;
	}

	/**
	 * Get the bundle matching the given URL.
	 * 
	 * @param url
	 * @return NUll if no matching bundle was found.
	 */
	public Bundle getBundle(URL url) {

		String urlHost = url.getHost(); // i.e. "5.fwk1645547422"
		for (Bundle bundle : alBundles) {

			URL urlBundle = bundle.getEntry("/");
			String hostBundle = urlBundle.getHost();

			if (urlHost.equals(hostBundle)) { // i.e. "0.fwk1645547422"
				return bundle;
			}
		}

		return null;
	}

	// ============ Grave Yard =================
	/**
	 * TODO: Remove
	 * 
	 * @param path
	 * @param suffix
	 * @param resourceIteratorFactory
	 * @return
	 */
	public Enumeration<URL> iteratorPushResources(String path, String suffix,
			ResourceIteratorFactory resourceIteratorFactory) {
		Vector<URL> res = new Vector<URL>();
		try {

			// Get this bundle and bundle context
			Bundle currentBundle = org.osgi.framework.FrameworkUtil.getBundle(BundleResourceAccessor.class);
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
				// if (symbolicName.startsWith("com.avenqo.cucumber.example.appl.swtbot")) {
				{
					System.out.println(symbolicName);
					// printBundleInfo1(bundle);
					// printBundleState(bundle);

					// path = "/bin/com/avenqo/pep/appl/ui/test/steps", suffix = "*.class";
//findEntries('/', '*.class', true): bundleentry://15.fwk1302227152/bin/com/avenqo/pep/appl/ui/test/cucumber/runner/TestRunner.class

//					Enumeration<URL> resourcesUrls = bundle.getResources("com/avenqo/pep/appl/ui/test/cucumber/steps");
//					resourcesUrls = bundle.getResources("com/avenqo/pep/appl/ui/test/cucumber/steps/*.class");
//					resourcesUrls = bundle.getResources("/com/avenqo/pep/appl/ui/test/cucumber/steps/*.class");
//					resourcesUrls = bundle.getResources("*.class");

					Enumeration<URL> resources = findMatchingUrls(bundle, path, suffix);
					if (resources != null) {
						while (resources.hasMoreElements()) {
							res.addElement(resources.nextElement());

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
