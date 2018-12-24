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

public class BundleResource implements Resource {

	private final URL url;
	private final Bundle bundle;

	public BundleResource(Bundle b, URL u) {
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
		
		//TODO Sicheren Weg finden!
		String s = getPath().replaceAll("/", ".");
		if (s.startsWith(".bin.")) {
			s = s.substring(5);
		}
		else if (s.startsWith(".")) {
			s = s.substring(5);
		}
		return s;
		//Funz nicht in fragement
//		while (true) {
//			try {
//				bundle.loadClass(classname);
//				return classname;
//			} catch (ClassNotFoundException e) {
//				// maybe located within a sub path?
//				int pos = classname.indexOf(".");
//				if (pos < 0)
//					break;
//				classname = classname.substring(++pos);
//			}
//		}


	}

}
