package server.sql;

import java.util.HashMap;
import java.util.Map;

/**
 * A CollectionFactory class to generate Collections given a class
 *
 * @author Jamie Martin
 */
public class CollectionFactory {
    // Class Name, Collection Key Value Pair
    public static Map<String, Collection> collections = new HashMap<String, Collection>();

    /**
     * Returns an Instance of Collection<T> given the class
     * If there isn't a Collection, it creates one.
     * @param clazz: The Class you want to manage
     * @param <T>: T the Type of the Class you want to manage
     * @return The Collection
     * @throws Exception
     */
    public static <T> Collection<T> getInstance(Class<T> clazz) throws Exception {
        // get the class name
        String clazzName = clazz.getSimpleName();
        //
        Object instance = collections.get(clazzName);

        if (instance == null){
            synchronized (clazz) {
                instance = new Collection(clazz);
                collections.put(clazzName, (Collection) instance);
            }
        }

        return (Collection<T>) instance;
    }
}
