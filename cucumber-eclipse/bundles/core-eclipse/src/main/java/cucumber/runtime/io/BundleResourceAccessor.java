package cucumber.runtime.io;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.BundleException;
import org.osgi.framework.wiring.BundleRevision;
import org.osgi.framework.wiring.BundleWiring;

import cucumber.api.BundleProperties;
import cucumber.runtime.CucumberException;

/**
 * Utility to load classes and resources being provided by the OSGI environment.
 * Searches in all Bundles of the OSGI environment.
 * 
 * @author Heiko
 *
 */
public class BundleResourceAccessor {

	private List<Bundle> alBundles = null;

	// --- Cache for bundle resources in context of suffix ---
	public class CacheKey {
		String bundleName;
		String version;
		String suffix;

		CacheKey(Bundle bundle, String _suffix) {
			bundleName = bundle.getSymbolicName();
			version = bundle.getVersion().toString();
			suffix = _suffix;
		}

		@Override
		public boolean equals(Object other) {
			if (other instanceof CacheKey) {
				CacheKey otherKey = (CacheKey) other;
				return bundleName.equals(otherKey.bundleName) && version.equals(otherKey.version)
						&& suffix.equals(otherKey.suffix);
			}
			return false;
		}

		@Override
		public int hashCode() {
			int result = (bundleName != null ? bundleName.hashCode() : 0);
			result = 31 * result + (version != null ? version.hashCode() : 0);
			result = 31 * result + (suffix != null ? suffix.hashCode() : 0);
			return result;

		}
	}

	private static Map<CacheKey, Collection<String>> resourceCache;
	// --- END: Cache for bundle resources in context of suffix ---

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

			// Get all known Bundles
			alBundles = new ArrayList<Bundle>(Arrays.asList(bc.getBundles()));
		} catch (BundleException be) {
			throw new CucumberException(be);
		}

		if (resourceCache == null)
			resourceCache = new HashMap<CacheKey, Collection<String>>();
	}

	public boolean isFragment(Bundle bundle) {
		boolean b = (bundle.adapt(BundleRevision.class).getTypes() & BundleRevision.TYPE_FRAGMENT) != 0;
		return b;
	}

	public Enumeration<URL> getResources(String path, String suffix) {
		Vector<URL> res = new Vector<URL>();

		for (Bundle bundle : alBundles) {
			
			if (!isFilteredBundle(bundle))
				continue;
			
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
	 * Check if the bundle's name is part of the filter OR contains "cucumber".
	 * @param bundle
	 * @return True if the bundle name contains a filter string at least partially.
	 */
	private boolean isFilteredBundle(Bundle bundle) {
		String prop = System.getProperty(BundleProperties.OSGI_BUNDLE_FILTER);
		if (prop != null && prop.length() > 0) {
			String name = bundle.getSymbolicName();
			if (name.contains("cucumber"))
				return true;
			String[] strBundles = prop.split(",");
			for (String s : strBundles) {
				s = s.trim();
				if (s.length()>0 && name.contains(s))
					return true;
			}						
		}
		else return true;
		return false;
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
	public Enumeration<URL> findMatchingUrls(Bundle bundle, String path, String suffix) {

		Vector<URL> vectorUrl = new Vector<URL>();
		BundleWiring bundleWiring = bundle.adapt(BundleWiring.class);

		// Getting all the matching files from classpath,; including imported packages
		Collection<String> resources = null;
		CacheKey key = new CacheKey(bundle, suffix);
		// String key = bundle.getSymbolicName();
		if (resourceCache.containsKey(key))
			resources = resourceCache.get(key);
		else {
			resources = bundleWiring.listResources("/", "*" + suffix, BundleWiring.LISTRESOURCES_RECURSE);
			resourceCache.put(key, resources);
		}
		URL bundleUrl = bundle.getEntry("/");
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
						Class<?> instance = bundle.loadClass(new BundleResource(bundle, url).getClassName(suffix));
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

}
