package server.sql;

import server.services.DataService;

import java.io.ByteArrayInputStream;
import java.lang.reflect.Field;
import java.sql.PreparedStatement;
import java.util.Arrays;
import java.util.List;

/**
 * A class to generate SQL statements given a class type.
 *
 * @author Jamie Martin
 * @author Perdana Bailey
 */
public class StatementBuilder {

    /**
     * Creates a SELECT SQL statement based on given class type.
     *
     * @param className: The provided class type.
     * @param <T>: The type of the provided class type.
     * @return PreparedStatement: The SELECT SQL statement.
     * @throws Exception: A pass-through internal server exception.
     */
    public static <T> PreparedStatement get(Class<T> className) throws Exception {
        return DataService.getConnection().prepareStatement("SELECT * FROM " + className.getSimpleName());
    }

    /**
     * Creates an INSERT SQL statement based on given class type and an object of that class type to insert.
     *
     * @param object: An object of the provided class type to insert.
     * @param <T>: The type of the provided class type.
     * @return PreparedStatement: The INSERT SQL statement.
     * @throws Exception: A pass-through internal server exception.
     */
    public static <T> PreparedStatement insert(T object) throws Exception {
        // Gets the class name from the inserted object class.
        Class className = object.getClass();

        // Creates a list of fields from the class object fields.
        List<Field> fields = Arrays.asList(className.getDeclaredFields());
        // Saves the length of the list of fields to ensure it terminates the PreparedStatement correctly.
        int lastField = fields.toArray().length - 1;

        // Create a StringBuilder for the names and the values, when combined create the full SQL query string.
        StringBuilder names = new StringBuilder("INSERT INTO " + className.getSimpleName() + " (");
        StringBuilder values = new StringBuilder(" VALUES (");

        int i = 1;

        for (Field field : fields) {
            if (field.getName() != "id") {
                field.setAccessible(true);

                if (i == lastField) {
                    names.append(field.getName() + ")");
                    values.append("?)");
                } else {
                    names.append(field.getName() + ',');
                    values.append("?,");
                }

                i++;
            }
        }

        String query = names.toString() + values.toString();
        PreparedStatement pstmt = DataService.getConnection().prepareStatement(query);

        int j = 1;

        for (Field field : fields) {
            if (field.getName() != "id") {
                Object value = field.get(object);
                if (value instanceof byte[]) {
                    pstmt.setBinaryStream(j, new ByteArrayInputStream((byte[]) value));
                } else {
                    pstmt.setObject(j, value);
                }
                j++;
            }
        }

        return pstmt;
    }

    public static <T> PreparedStatement update(T object) throws Exception {
        Class clazz = object.getClass();
        List<Field> fields = Arrays.asList(clazz.getDeclaredFields());
        int lastField = fields.toArray().length - 1;

        StringBuilder names = new StringBuilder("UPDATE " + clazz.getSimpleName() + " SET ");

        int id = clazz.getDeclaredField("id").getInt(object);

        int i = 1;

        for (Field field : fields) {
            if (field.getName() != "id") {
                field.setAccessible(true);

                if (i == lastField) {
                    names.append(field.getName() + " = ?");
                } else {
                    names.append(field.getName() + " = ?,");
                }
            }

            i++;
        }

        names.append(" WHERE ID = ?");

        String query = names.toString();
        PreparedStatement pstmt = DataService.getConnection().prepareStatement(query);

        int j = 1;

        for (Field field : fields) {
            Object value = field.get(object);
            if (value instanceof byte[]) {
                pstmt.setBinaryStream(j, new ByteArrayInputStream((byte[])value));
            } else {
                pstmt.setObject(j, value);
            }
            j++;
        }

        pstmt.setObject(j, id);

        return pstmt;
    }

    public static <T> PreparedStatement delete(T object) throws Exception {
        Class clazz = object.getClass();

        int id = clazz.getDeclaredField("id").getInt(object);

        PreparedStatement pstmt = DataService.getConnection().prepareStatement("UPDATE " + clazz.getSimpleName() + " SET WHERE ID = ?");

        pstmt.setObject(1, id);

        return pstmt;
    }
}
