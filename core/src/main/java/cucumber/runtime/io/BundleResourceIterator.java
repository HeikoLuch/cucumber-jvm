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

public class BundleResourceIterator implements Iterator<Resource>{

	//private final Iterator<Resource> iterator;
	private final FlatteningIterator<Resource> flatteningIterator = new FlatteningIterator<Resource>();

	
    /**
     * @param url i.e. bundleentry://5.fwk1645547422/bin/com/avenqo/cucumber/example/appl/swtbot/runner/RunCukesTest.class
     * @param path i.e. com/avenqo/cucumber/example/appl/swtbot/runner
     * @param suffix i.e. .class
     */
    public BundleResourceIterator(URL url, String path, String suffix) {
    	// Get this bundle and bundle context
    	Bundle currentBundle = org.osgi.framework.FrameworkUtil.getBundle(BundleResourceIterator.class);
    	BundleContext bc = currentBundle.getBundleContext();
    	if (bc == null) {  //try to start the bundle
    		try {
    			currentBundle.start();
    			bc = currentBundle.getBundleContext();
    		} catch (BundleException be) {
    			throw new CucumberException("Bundle couldn't be started: " + currentBundle.getBundleId(), be);
    		}
    	}
    	
    	List<Resource> listResources = new ArrayList<Resource>();
    	Bundle[] bundles = bc.getBundles();
		for (Bundle bundle : bundles) {
		
			Enumeration<URL> resources = findMatchingUrls(bundle, path, suffix);
			if (resources != null) {
				while (resources.hasMoreElements()) {
					listResources.add( new BundleResource( bundle, resources.nextElement()));
				}
			}
		}
		flatteningIterator.push(listResources.iterator());
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
