package cucumber.runtime.io;

import cucumber.runtime.ClassFinder;

import java.io.File;
import java.util.Collection;
import java.util.HashSet;

public class BundleResourceLoaderClassFinder implements ClassFinder {
    private final ResourceLoader resourceLoader;
    private final ClassLoader classLoader;

    public BundleResourceLoaderClassFinder(ResourceLoader resourceLoader, ClassLoader classLoader) {
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

            try {
                Class<?> clazz = loadClass(className);
//                boolean b = parentType.equals(clazz);
//                boolean c = parentType.isAssignableFrom(clazz);
                if (clazz != null && !parentType.equals(clazz) && parentType.isAssignableFrom(clazz)) {
                    result.add(clazz.asSubclass(parentType));
                }
            } catch (ClassNotFoundException ignore) {
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
