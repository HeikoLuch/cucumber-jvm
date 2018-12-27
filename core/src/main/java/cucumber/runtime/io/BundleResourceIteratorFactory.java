package cucumber.runtime.io;

import java.io.IOException;
import java.net.URL;
import java.util.Iterator;

import cucumber.runtime.CucumberException;

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
