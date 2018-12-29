package cucumber.runtime.io;

import cucumber.runtime.ClassFinder;

import java.io.File;
import java.util.Collection;
import java.util.HashSet;

public class ResourceLoaderClassFinder implements ClassFinder {
    private final ResourceLoader resourceLoader;
    private final ClassLoader classLoader;

    public ResourceLoaderClassFinder(ResourceLoader resourceLoader, ClassLoader classLoader) {
        this.resourceLoader = resourceLoader; //i.e. MultiLoader
        this.classLoader = classLoader; // i.e. EquinoxClassLoader
    }

    /* (non-Javadoc)
     * @see cucumber.runtime.ClassFinder#getDescendants(java.lang.Class, java.lang.String)
     */
    @Override
    public <T> Collection<Class<? extends T>> getDescendants(Class<T> parentType, String packageName) {
        Collection<Class<? extends T>> result = new HashSet<Class<? extends T>>();
        String packagePath = "classpath:" + packageName.replace('.', '/').replace(File.separatorChar, '/');
        for (Resource classResource : resourceLoader.resources(packagePath, ".class")) {
            String className = classResource.getClassName(".class");
//TODO Remove
if (className.contains("JavaBackend"))  {
	int z=0;
}           
            try {
            	 Class<?> clazz;
            	if (classResource instanceof BundleResource) {
            		clazz = ((BundleResource) classResource).loadClass();
            	}
            	else {
            		clazz = loadClass(className);
            	}
                boolean a = !parentType.equals(clazz);
                boolean b = parentType.isAssignableFrom(clazz);
                if (clazz != null && !parentType.equals(clazz) && parentType.isAssignableFrom(clazz)) {
                    result.add(clazz.asSubclass(parentType));
                }
            } catch (ClassNotFoundException ignore) {
            	ignore.printStackTrace();
            } catch (NoClassDefFoundError ignore) {
            	ignore.printStackTrace();
            }
        }
        return result;
    }

    /* (non-Javadoc)
     * @see cucumber.runtime.ClassFinder#loadClass(java.lang.String)
     * 
     * classname i.e. 'com.avenqo.cucumber.example.appl.swtbot.runner.RunCukesTest.class'
     */
    public <T> Class<? extends T> loadClass(String className) throws ClassNotFoundException {
        return (Class<? extends T>) classLoader.loadClass(className);
    }
}
