package cucumber.runtime.io;

import cucumber.runtime.CucumberException;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLDecoder;
import java.util.Enumeration;
import java.util.Iterator;

public class ClasspathResourceIterable implements Iterable<Resource> {
	private final ResourceIteratorFactory resourceIteratorFactory = new DelegatingResourceIteratorFactory(
			new ZipThenFileResourceIteratorFactory());

	// ======== Determine implementation for Bundle Fragment =======
	private static final BundleResourceLoader IMPL;
	static {
		// try to load class FactoryDateTimeImpl from fragment
		IMPL = (BundleResourceLoader) ImplementationLoader.newInstance(BundleResourceLoader.class);
	}
	// ======== END: Determine implementation for Bundle Fragment =======

	private final ClassLoader classLoader;
	private final String path;
	private final String suffix;

	public ClasspathResourceIterable(ClassLoader classLoader, String path, String suffix) {
		this.classLoader = classLoader;
		this.path = path;
		this.suffix = suffix;
	}

	@Override
	public Iterator<Resource> iterator() {
		try {
			FlatteningIterator<Resource> iterator = new FlatteningIterator<Resource>();

			Enumeration<URL> resources = (IMPL != null)
					? IMPL.iteratorPushResources(path, suffix, resourceIteratorFactory)
					: classLoader.getResources(path);

			while (resources.hasMoreElements()) {
				URL url = resources.nextElement();
				Iterator<Resource> resourceIterator = resourceIteratorFactory.createIterator(url, path, suffix);
				iterator.push(resourceIterator);
			}

			return iterator;
		} catch (IOException e) {
			throw new CucumberException(e);
		}
	}

}
