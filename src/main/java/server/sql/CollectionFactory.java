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
    public static Map<Class, Collection> collections = new HashMap<>();

    /**
     * Returns an Instance of Collection<T> given the class
     * If there isn't a Collection, it creates one.
     * @param clazz: The Class you want to manage
     * @param <T>: T the Type of the Class you want to manage
     * @return The Collection
     * @throws Exception
     */
    public static <T> Collection<T> getInstance(Class<T> clazz) throws Exception {
        Object instance = collections.get(clazz);

        if (instance == null){
            synchronized (clazz) {
                instance = new Collection(clazz);
                collections.put(clazz, (Collection) instance);
            }
        }

        return (Collection<T>) instance;
    }
}
