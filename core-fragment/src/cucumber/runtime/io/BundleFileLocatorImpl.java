package cucumber.runtime.io;

import java.net.URI;
import java.net.URL;

import org.eclipse.core.runtime.FileLocator;

public class BundleFileLocatorImpl extends BundleFileLocator{
	

	
	@Override
	public String getAbsolutePathAsString(URL fileUrl) {

		String sRet = null;
			
		//System.out.println("Fragment Called for [" + fileUrl + "].");
		try {
			URL blu = FileLocator.resolve(fileUrl);
			URI uri = blu.toURI();
	
			sRet = uri.getSchemeSpecificPart();
			if (sRet.startsWith("file:/")) {
				sRet = sRet.substring(5);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//System.out.println("Returning [" + sRet + "].");
		return sRet;
	}
}
