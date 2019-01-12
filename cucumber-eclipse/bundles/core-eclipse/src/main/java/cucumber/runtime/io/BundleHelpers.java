package cucumber.runtime.io;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import org.eclipse.core.runtime.FileLocator;
import cucumber.runtime.CucumberException;

/**
 * Indirect substitute of the original Helpers class.
 * 
 * A direct substitution is not possible: Helpers is used in a static way.
 * Static imports are not loaded from fragment when loading class. (Why?)
 * 
 * @author Heiko
 */
public class BundleHelpers {

	private BundleHelpers() {
	}

	static boolean hasSuffix(String suffix, String name) {
		return suffix == null || name.endsWith(suffix);
	}

	static String filePath(URL fileUrl) {
		// Added implementation vor OSGI environment
		String protocol = fileUrl.getProtocol();
		System.out.println("Protocol is: " + protocol);
		if ("bundleresource".equals(protocol) || "bundleentry".equals(fileUrl.getProtocol())) {
			return getAbsolutePathAsString(fileUrl);

		} else { // The original implementation
			if (!"file".equals(fileUrl.getProtocol())) {
				throw new CucumberException("Expected a file URL: " + fileUrl);
			}
			try {
				return fileUrl.toURI().getSchemeSpecificPart();
			} catch (URISyntaxException e) {
				throw new CucumberException(e);
			}
		}
	}

	static String jarFilePath(URL jarUrl) {
		String urlFile = jarUrl.getFile();

		int separatorIndex = urlFile.indexOf("!/");
		if (separatorIndex == -1) {
			throw new CucumberException("Expected a jar URL: " + jarUrl.toExternalForm());
		}
		try {
			URL fileUrl = new URL(urlFile.substring(0, separatorIndex));
			return filePath(fileUrl);
		} catch (MalformedURLException e) {
			throw new CucumberException(e);
		}
	}

	private static String getAbsolutePathAsString(URL fileUrl) {
		String sRet = null;
		try {
			URL blu = FileLocator.resolve(fileUrl);
			URI uri = blu.toURI();
			sRet = uri.getSchemeSpecificPart();
			if (sRet.startsWith("file:/")) {
				sRet = sRet.substring(5);
			} else
				throw new CucumberException("Don't know how to handle SchemeSpecificPart in URL: " + fileUrl);

		} catch (Exception e) {
			throw new CucumberException(e);
		}
		return sRet;
	}
}