package server.sql;

import java.lang.reflect.Field;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

/**
 * A Collection class to manage the CRUD updates of a given class
 *
 * @author Jamie Martin
 */
public class Collection<T> {
    private final Class<T> clazz;

    public Collection(Class<T> clazz) {
        this.clazz = clazz;

        // TODO: DO AUTOMAGIC SCHEMA STUFF HERE??
    }

    /**
     * Get all items from the DataService
     * @param predicate: used to filter out specific items, x -> true, for all
     * @return A List of the gotten object T
     * @throws Exception
     */
    public List<T> get(Predicate<T> predicate) throws Exception {
        // Prepares the statement using the StatementBuilder
        PreparedStatement psmt = StatementBuilder.get(clazz);

        // Billboard to be returned
        List<T> arr = new ArrayList<>();

        ResultSet result = psmt.executeQuery();

        // Use the result of the database query to create billboard object and add it to the returned list
        while (result.next()) {
            T res = fromSQL(result);
            if (predicate.test(res)) {
                arr.add(res);
            }
        }

        // Clean up query
        psmt.close();

        return arr;
    }

    /**
     * Inserts a given object into the DataService
     * @param object: the object you want inserted
     * @throws Exception
     */
    public void insert(T object) throws Exception {
        // Prepares the statement using the StatementBuilder
        PreparedStatement psmt = StatementBuilder.insert(object);

        psmt.executeUpdate();
        // Clean up query
        psmt.close();
    }

    /**
     * Updates a given object into the DataService
     * @param object: the object you want inserted
     * @throws Exception
     */
    public void update(T object) throws Exception {
        // Prepares the statement using the StatementBuilder
        PreparedStatement psmt = StatementBuilder.update(object);

        psmt.executeUpdate();
        // Clean up query
        psmt.close();
    }

    /**
     * Deletes a given object into the DataService
     * @param object: the object you want inserted
     * @throws Exception
     */
    public void delete(T object) throws Exception {
        // Prepares the statement using the StatementBuilder
        PreparedStatement psmt = StatementBuilder.delete(object);

        psmt.executeUpdate();
        // Clean up query
        psmt.close();
    }

    /**
     * Parses the SQL result set and returns a Billboard object.
     *
     * @param resultSet: the result set from an SQL SELECT query.
     * @return T: the object after converting from SQL.
     * @throws SQLException : this is thrown when there is an issue with getting values from the query.
     */
    public T fromSQL(ResultSet resultSet) throws Exception {
        List<Field> fields = Arrays.asList(this.clazz.getDeclaredFields());
        T dto = this.clazz.getConstructor().newInstance();

        for (Field field : fields) {
            field.setAccessible(true);

            String name = field.getName();
            Object value = resultSet.getObject(name);

            var o = (T) convertInstanceOfObject(value);
            field.set(dto, o);
        }

        return dto;
    }

    /**
     * Casts an object to the class
     * @param o: the Object
     * @return the Object of type T
     */
    private T convertInstanceOfObject(Object o) {
        try {
            return (T) o;
        } catch (ClassCastException e) {
            return null;
        }
    }
}
