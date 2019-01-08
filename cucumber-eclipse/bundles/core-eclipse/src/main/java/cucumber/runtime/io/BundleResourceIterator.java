package cucumber.runtime.io;

import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.osgi.framework.Bundle;

import cucumber.runtime.CucumberException;

/**
 * Provides an separate iterator containing one url.
 * Implemented for compatibility reasons only.
 * 
 * @author Heiko
 *
 */
public class BundleResourceIterator implements Iterator<Resource> {

	private final FlatteningIterator<Resource> flatteningIterator = new FlatteningIterator<Resource>();

	/**
	 * @param url    i.e.
	 *               bundleentry://5.fwk1645547422/com/avenqo/cucumber/example/appl/swtbot/runner/RunCukesTest.class
	 */
	public BundleResourceIterator(URL url) {
		List<Resource> listResources = new ArrayList<Resource>();
		
		BundleResourceAccessor bra = new BundleResourceAccessor();
		Bundle bundle = bra.getBundle(url);

		if (bundle != null) {
			if (!bra.isFragment(bundle)) {
				listResources.add(new BundleResource(bundle, url));
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
