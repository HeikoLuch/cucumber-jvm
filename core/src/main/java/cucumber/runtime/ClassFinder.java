package cucumber.runtime;

import java.util.Collection;

public interface ClassFinder {
    /**
     * Sucht im Package packagename nach Klassen, die von parentType abgeleitet sind.
     * @param parentType
     * @param packageName
     * @return
     */
    <T> Collection<Class<? extends T>> getDescendants(Class<T> parentType, String packageName);

    <T> Class<? extends T> loadClass(String className) throws ClassNotFoundException;
}
