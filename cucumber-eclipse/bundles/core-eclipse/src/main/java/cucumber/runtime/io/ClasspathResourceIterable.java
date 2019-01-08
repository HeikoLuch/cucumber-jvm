package cucumber.runtime.io;

import java.net.URL;
import java.util.Enumeration;
import java.util.Iterator;

import cucumber.runtime.CucumberException;
//TODO: Move to fragment
public class ClasspathResourceIterable implements Iterable<Resource> {
	
	//Gibt entweder eine andere ResourceIteratorFactory zurück (java.lang.ServiceLoader) oder die Fallback-Variante.
	private final ResourceIteratorFactory resourceIteratorFactory = new DelegatingResourceIteratorFactory(
			new ZipThenFileResourceIteratorFactory()); //fallback

//	// ======== Determine implementation for Bundle Fragment =======
//	private static final BundleResourceLoader IMPL;
//	static {
//		// try to load class FactoryDateTimeImpl from fragment
//		IMPL = (BundleResourceLoader) ImplementationLoader.newInstance(BundleResourceLoader.class);
//	}
//	// ======== END: Determine implementation for Bundle Fragment =======

	private final ClassLoader classLoader;
	private final String path;
	private final String suffix;

	public ClasspathResourceIterable(ClassLoader classLoader, String path, String suffix) {
		System.out.println("********************* 3. Fragment loaded *****************************************");
        
		this.classLoader = classLoader; //ignore in OSGI
		this.path = path;
		this.suffix = suffix;
	}

	@Override
	public Iterator<Resource> iterator() {
		try {
			FlatteningIterator<Resource> iterator = new FlatteningIterator<Resource>();
			//Original:
			//Enumeration<URL> resources = classLoader.getResources(path);
			//New:
			Enumeration<URL> resources = new BundleResourceAccessor().getResources(path, suffix); 


			while (resources.hasMoreElements()) {
				URL url = resources.nextElement();
//System.out.println("Investigating URL: " + url.toString());				
				
				//url: 		bundleentry://5.fwk1645547422/bin/com/avenqo/cucumber/example/appl/swtbot/runner/RunCukesTest.class
				//path: 	com/avenqo/cucumber/example/appl/swtbot/runner
				//suffix: 	.class
				Iterator<Resource> resourceIterator = resourceIteratorFactory.createIterator(url, path, suffix);
				iterator.push(resourceIterator);
			}

			return iterator;
		} catch (Exception e) { 
			throw new CucumberException(e);
		}
	}

}
