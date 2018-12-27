package cucumber.api;

import java.util.Locale;

/**
 * Since cucumber 3.0.0:
 * 
 * You can use Cucumber Expressions as described on the new docs site. 
 * To add custom parameters you have to place an implementation of 
 * cucumber.api.TypeRegistryConfigurer on the glue path.
 * 
 * @author Heiko
 *
 */
public interface TypeRegistryConfigurer {

    Locale locale();

    void configureTypeRegistry(TypeRegistry typeRegistry);
}
