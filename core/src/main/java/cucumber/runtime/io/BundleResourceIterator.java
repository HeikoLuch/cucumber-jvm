package cucumber.runtime.io;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Vector;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.BundleException;

import cucumber.runtime.CucumberException;

/**
 * Provides an iterator containing all resources of the the given url (bundle )
 * matching path and suffix.
 * 
 * @author Heiko
 *
 */
public class BundleResourceIterator implements Iterator<Resource> {

	// private final Iterator<Resource> iterator;
	private final FlatteningIterator<Resource> flatteningIterator = new FlatteningIterator<Resource>();

	/**
	 * @param url    i.e.
	 *               bundleentry://5.fwk1645547422/com/avenqo/cucumber/example/appl/swtbot/runner/RunCukesTest.class
	 * @param path   ignored
	 * @param suffix i.e. .class
	 */
	public BundleResourceIterator(URL url) {
		List<Resource> listResources = new ArrayList<Resource>();
		
		BundleResourceAccessor bra = new BundleResourceAccessor();
		/*
		 * // Get this bundle and bundle context Bundle currentBundle =
		 * org.osgi.framework.FrameworkUtil.getBundle(BundleResourceIterator.class);
		 * BundleContext bc = currentBundle.getBundleContext(); if (bc == null) { // try
		 * to start the bundle try { currentBundle.start(); bc =
		 * currentBundle.getBundleContext(); } catch (BundleException be) { throw new
		 * CucumberException("Bundle couldn't be started: " +
		 * currentBundle.getBundleId(), be); } }
		 * 
		 * 
		 * Bundle[] bundles = bc.getBundles(); for (Bundle bundle : bundles) {
		 * 
		 * Enumeration<URL> resources = BundleResourceAccessor.findMatchingUrls(bundle,
		 * path, suffix); if (resources != null) { while (resources.hasMoreElements()) {
		 * listResources.add(new BundleResource(bundle, resources.nextElement())); } } }
		 */

		Bundle bundle = bra.getBundle(url);

		if (bundle != null) {
			if (!bra.isFragment(bundle)) {
				listResources.add(new BundleResource(url));
				
				
//				Enumeration<URL> resources = BundleResourceAccessor.findMatchingUrls(bundle, path, suffix);
//				if (resources != null) {
//					while (resources.hasMoreElements()) {
//						URL resource = resources.nextElement();
//						listResources.add(new BundleResource(bundle, resource));
//						try {
//
//							bundle.loadClass("/com/avenqo/cucumber/example/appl/swtbot/runner/RunCukesTest.class");
//						} catch (ClassNotFoundException e) {
//							// TODO Auto-generated catch block
//							e.printStackTrace();
//						}
//					}
//				}
			}
		} else
			throw new CucumberException("No matching bundle found.");
		flatteningIterator.push(listResources.iterator());
	}

	@Override
	public boolean hasNext() {
		return flatteningIterator.hasNext();
	}

	@Override
	public Resource next() {
		return flatteningIterator.next();
	}

	@Override
	public void remove() {
		throw new UnsupportedOperationException();
	}
}
