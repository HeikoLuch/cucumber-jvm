package cucumber.runtime.io;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Arrays;
import java.util.Dictionary;
import java.util.Enumeration;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.wiring.BundleRevision;
import org.osgi.framework.wiring.BundleWiring;

import cucumber.runtime.CucumberException;

public class BundleResource implements Resource {

	private final URL url;
	private Bundle bundle;
	

	public BundleResource(Bundle b,  URL u) {
		url = u;
		bundle = b;
	}

	/* (non-Javadoc)
	 * @see cucumber.runtime.io.Resource#getPath()
	 * 
	 * Returns i.e. "/bin/com/avenqo/cucumber/example/appl/swtbot/runner/RunCukesTest.class"
	 */
	@Override
	public String getPath() {
		return url.getPath();
	}

	@Override
	public String getAbsolutePath() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public InputStream getInputStream() throws IOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getClassName(String extension) {
		
		//URL hostUrl = bundle.getResource("/");
		if (!url.toString().startsWith("bundle"))
			throw new CucumberException("Don't kno how to handle URL: " + url);
		String p = url.getPath();
		
		String s = p.replaceAll("/", ".");
		if (s.startsWith("."))
			s = s.substring(1);
		
		if (s.endsWith(extension)) {
			int index = s.lastIndexOf(extension);
			s = s.substring(0,  index);
		}
		return s;


	}

	public Class<?> loadClass() throws ClassNotFoundException {
		return bundle.loadClass(getClassName(".class"));
	}

}
