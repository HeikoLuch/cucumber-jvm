package cucumber.runtime.io;

import java.net.URL;
import java.util.Iterator;

public class BundleResourceIteratorFactory implements ResourceIteratorFactory {

	@Override
	public boolean isFactoryFor(URL url) {
		return url.toString().startsWith("bundleentry://") || url.toString().startsWith("bundleresource://");
	}

	@Override
	public Iterator<Resource> createIterator(URL url, String path, String suffix) {

		return new BundleResourceIterator(url);

	}

}
