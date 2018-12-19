package cucumber.runtime.io;

import java.net.URL;

abstract class BundleFileLocator {

	/**
	 * 
	 * @param fileUrl
	 * @return
	 */
	public abstract String getAbsolutePathAsString(URL fileUrl);

}
