package server.sql;

import server.services.DataService;

import java.io.ByteArrayInputStream;
import java.lang.reflect.Field;
import java.sql.Connection;
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

    public static <T> PreparedStatement get(Connection conn, Class<T> clazz) throws Exception {
        return conn.prepareStatement(createGetStatement(clazz));
    }

    public static <T> String createGetStatement(Class<T> clazz) {
        return "SELECT * FROM " + clazz.getSimpleName().toUpperCase();
    }

    public static <T> PreparedStatement insert(Connection conn, T object) throws Exception {
        Class clazz = object.getClass();
        PreparedStatement pstmt = conn.prepareStatement(createInsertStatement(clazz));

        int j = 1;

        for (Field field : getFields(object.getClass())) {
            if (field.getName() != "id") {
                Object value = field.get(object);
                if (value instanceof byte[])
                    pstmt.setBinaryStream(j, new ByteArrayInputStream((byte[]) value));
                else
                    pstmt.setObject(j, value);

                j++;
            }
        }

        return pstmt;
    }

    public static <T> String createInsertStatement(Class<T> clazz) {
        List<Field> fields = getFields(clazz);
        int lastField = fields.toArray().length - 1;

        var names = new StringBuilder("INSERT INTO " + clazz.getSimpleName().toUpperCase() + " (");
        var values = new StringBuilder(" VALUES (");

        int i = 1;

        for (Field field : fields) {
            if (field.getName() != "id") {
                field.setAccessible(true);

                if (i == lastField) {
                    names.append(field.getName() + ")");
                    values.append("?)");
                } else {
                    names.append(field.getName() + ", ");
                    values.append("?, ");
                }

                i++;
            }
        }

        return names.toString() + values.toString();
    }

    public static <T> PreparedStatement update(Connection conn, T object) throws Exception {
        Class clazz = object.getClass();
        PreparedStatement pstmt = conn.prepareStatement(createUpdateStatement(clazz));

        int id = clazz.getDeclaredField("id").getInt(object);
        int j = 1;

        for (Field field : getFields(object.getClass())) {
            if (field.getName() != "id") {
                Object value = field.get(object);
                if (value instanceof byte[])
                    pstmt.setBinaryStream(j, new ByteArrayInputStream((byte[])value));
                else
                    pstmt.setObject(j, value);

                j++;
            }
        }

        pstmt.setObject(j, id);

        return pstmt;
    }

    public static <T> String createUpdateStatement(Class<T> clazz) throws Exception {
        List<Field> fields = getFields(clazz);
        int lastField = fields.toArray().length - 1;

        StringBuilder names = new StringBuilder("UPDATE " + clazz.getSimpleName().toUpperCase() + " SET ");

        int i = 1;

        for (Field field : fields) {
            if (field.getName() != "id") {
                field.setAccessible(true);

                if (i == lastField)
                    names.append(field.getName() + " = ?");
                else
                    names.append(field.getName() + " = ?, ");

                i++;
            }
        }

        return names.append(" WHERE ID = ?").toString();
    }

    public static <T> PreparedStatement delete(Connection conn, T object) throws Exception {
        Class clazz = object.getClass();

        int id = clazz.getDeclaredField("id").getInt(object);

        PreparedStatement pstmt = conn.prepareStatement(createDeleteStatement(clazz));

        pstmt.setObject(1, id);

        return pstmt;
    }

    public static <T> String createDeleteStatement(Class<T> clazz) {
        return "DELETE FROM " + clazz.getSimpleName().toUpperCase() + " WHERE ID = ?";
    }

    /* HELPER FUNCTIONS */

    private static <T> List<Field> getFields(Class<T> clazz) {
        return Arrays.asList(clazz.getDeclaredFields());
    }
}
