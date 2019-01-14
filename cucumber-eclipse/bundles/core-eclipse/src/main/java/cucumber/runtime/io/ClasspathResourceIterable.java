package cucumber.runtime.io;

import java.net.URL;
import java.util.Enumeration;
import java.util.Iterator;

import cucumber.runtime.CucumberException;

public class ClasspathResourceIterable implements Iterable<Resource> {

	private final ResourceIteratorFactory resourceIteratorFactory = new DelegatingResourceIteratorFactory(
			new ZipThenFileResourceIteratorFactory()); // fallback

	// private final ClassLoader classLoader;
	private final String path;
	private final String suffix;

	public ClasspathResourceIterable(ClassLoader classLoader, String path, String suffix) {
		// this.classLoader = classLoader; //ignored in OSGI
		this.path = path;
		this.suffix = suffix;
	}

	@Override
	public Iterator<Resource> iterator() {
		try {
			FlatteningIterator<Resource> iterator = new FlatteningIterator<Resource>();
			Enumeration<URL> resources = new BundleResourceAccessor().getResources(path, suffix);

			while (resources.hasMoreElements()) {
				URL url = resources.nextElement();

				// url:
				// bundleentry://5.fwk1645547422/bin/com/avenqo/cucumber/example/appl/swtbot/runner/RunCukesTest.class
				// path: com/avenqo/cucumber/example/appl/swtbot/runner
				// suffix: .class
				Iterator<Resource> resourceIterator = resourceIteratorFactory.createIterator(url, path, suffix);
				iterator.push(resourceIterator);
			}

			return iterator;
		} catch (Exception e) {
			e.printStackTrace();
			throw new CucumberException(e);
		}
	}

}
