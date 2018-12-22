package cucumber.runtime.io;

import java.io.File;

public class BundleFileResourceImpl extends BundleFileResource {

	/* (non-Javadoc)
	 * @see cucumber.runtime.io.BundleFileResource#extractPath2Class(java.io.File)
	 * Input is FileResource.file i.e.
	 * 
	 * 
	 */
	@Override
	public String extractPath2Class(File file) {
		String path = file.getAbsolutePath();
		//this.getClass().getClassLoader().
		int i = path.indexOf("\\bin\\");
		String s = path.substring(i);
		
		s="com.avenqo.pep.appl.ui.test.cucumber.steps.WelcomeSteps.class";
		return s;
	}

}
