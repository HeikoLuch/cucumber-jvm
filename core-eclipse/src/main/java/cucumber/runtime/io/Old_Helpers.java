package cucumber.runtime.io;

import cucumber.runtime.CucumberException;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;




public class Old_Helpers {
	
//	// -----------------------------------------------
//	private static final BundleFileLocator IMPL;
//	static {
//		//try to load class FactoryDateTimeImpl from fragment
//		IMPL = (BundleFileLocator) ImplementationLoader.newInstance (BundleFileLocator.class);
//	}
//	// --------------------------------------------------
	
	private Old_Helpers() {
	}

	static boolean hasSuffix(String suffix, String name) {
		return suffix == null || name.endsWith(suffix);
	}

	static String filePath(URL fileUrl) {
		//Added implementation vor OSGI environment
		String protocol = fileUrl.getProtocol();
		System.out.println("Protocol is: " + protocol);
		if ("bundleresource".equals(protocol)
				|| "bundleentry".equals(fileUrl.getProtocol())) {
			return getAbsolutePathAsString(fileUrl);

		} else {	//The original implementation
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

	private static String getAbsolutePathAsString(URL url) {
		String sRet = null;
		try {
			
			
			throw new CucumberException("Not implemented for URL: " + url);

		} catch (Exception e) {
			throw new CucumberException(e);
		}

	}
	
//	private static String getAbsolutePathAsString(URL fileUrl) {
//		String sRet = null;
//		try {
//			URL blu = FileLocator.resolve(fileUrl);
//			URI uri = blu.toURI();
//			sRet = uri.getSchemeSpecificPart();
//			if (sRet.startsWith("file:/")) {
//				sRet = sRet.substring(5);
//			}
//			else
//				throw new CucumberException("Don't know how to handle SchemeSpecificPart in URL: " + fileUrl);
//
//		} catch (Exception e) {
//			throw new CucumberException(e);
//		}
//		return sRet;
//	}
}