package cucumber.runtime.io;

import cucumber.runtime.CucumberException;

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;

/**
 * Substitutes the original Helpers.
 * Delegates to the fragment specific implementation.
 * 
 * A direct substitution is not possible: Helpers is used in a static way.
 * Static imports are not loaded from fragment when loading class. (Why?)
 * 
 * @author Heiko
 *
 */
public class Helpers {
    private Helpers() {
    }

    static boolean hasSuffix(String suffix, String name) {
       return BundleHelpers.hasSuffix(suffix, name);
    }

    static String filePath(URL fileUrl) {
       return BundleHelpers.filePath(fileUrl);
    }

    static String jarFilePath(URL jarUrl) {
        return BundleHelpers.jarFilePath(jarUrl);
    }
}