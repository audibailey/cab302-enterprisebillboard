package server.sql;

import java.lang.reflect.Field;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

public class Collection<T> {
    private final Class<T> clazz;

    public Collection(Class<T> clazz) {
        this.clazz = clazz;

        // DO AUTOMAGIC SCHEMA STUFF HERE??

    }

    public List<T> get(Predicate<T> predicate) throws Exception {
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

    public void insert(T object) throws Exception {
        PreparedStatement psmt = StatementBuilder.insert(object);

        psmt.executeUpdate();
        // Clean up query
        psmt.close();
    }

    public void update(T object) throws Exception {
        PreparedStatement psmt = StatementBuilder.update(object);

        psmt.executeUpdate();
        // Clean up query
        psmt.close();
    }

    public void delete(T object) throws Exception {
        PreparedStatement psmt = StatementBuilder.delete(object);

        psmt.executeUpdate();
        // Clean up query
        psmt.close();
    }


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

    private T convertInstanceOfObject(Object o) {
        try {
            return (T) o;
        } catch (ClassCastException e) {
            return null;
        }
    }
}
