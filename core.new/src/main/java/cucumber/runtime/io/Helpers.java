package cucumber.runtime.io;

import cucumber.runtime.CucumberException;

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;



public class Helpers {
	
	// -----------------------------------------------
	private static final BundleFileLocator IMPL;
	static {
		//try to load class FactoryDateTimeImpl from fragment
		IMPL = (BundleFileLocator) ImplementationLoader.newInstance (BundleFileLocator.class);
	}
	// --------------------------------------------------
	
	private Helpers() {
	}

	static boolean hasSuffix(String suffix, String name) {
		return suffix == null || name.endsWith(suffix);
	}

	static String filePath(URL fileUrl) {

		
		//Added implementation vor OSGI environment
		if ("bundleresource".equals(fileUrl.getProtocol())
				|| "bundleentry".equals(fileUrl.getProtocol())) {
			System.out.println("Hallo Heiko 1 - starting OSGI variant");
			String s = IMPL.getAbsolutePathAsString(fileUrl);
			return s;
		} else {	//The original implementation
			if (!"file".equals(fileUrl.getProtocol())) {
				throw new CucumberException("Expected a file URL:" + fileUrl.toExternalForm());
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
}
