package cucumber.runtime.io;

import cucumber.runtime.CucumberException;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLDecoder;
import java.util.Enumeration;
import java.util.Iterator;

abstract class BundleResourceLoader {
	public abstract Enumeration<URL> iteratorPushResources( String path, String suffix, ResourceIteratorFactory resourceIteratorFactory);	
}
