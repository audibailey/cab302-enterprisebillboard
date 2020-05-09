package server.sql;

import java.util.HashMap;
import java.util.Map;

public class CollectionFactory {
    public static Map<String, Collection> collections = new HashMap<String, Collection>();

    public static <T> Collection<T> getInstance(Class<T> clazz) throws Exception {
        String clazzName = clazz.getSimpleName();
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
