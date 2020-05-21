package server.sql;

import common.models.SQLITE;
import server.services.DataService;

import java.io.ByteArrayInputStream;
import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

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
     * @return PreparedStatement: The SELECT SQL statement.
     * @throws Exception: A pass-through internal server exception.
     */
    public static PreparedStatement get(Connection conn, Class<?> className) throws Exception {
        return conn.prepareStatement(createGetStatement(className));
    }

    /**
     * Creates a SELECT SQL statement string based on given class type.
     *
     * @param className: The provided class type.
     * @return String: The SELECT SQL statement string.
     */
    public static String createGetStatement(Class<?> className) {
        return "SELECT * FROM " + className.getSimpleName().toUpperCase();
    }

    public static PreparedStatement insert(Connection conn, Object object) throws Exception {
        Class clazz = object.getClass();
        var fields = getFields(clazz);
        PreparedStatement pstmt = conn.prepareStatement(createInsertStatement(clazz));

        int j = 1;

        for (Object obj : fields) {
            var field = (Field) obj;
            if (field.getName() != "id") {
                Object value = field.get(object);
                pstmt.setObject(j, value);

                j++;
            }
        }
        return pstmt;
    }

    /**
     * Creates an INSERT SQL statement string based on given class type.
     *
     * @param clazz: The provided class type.
     * @return String: The SELECT SQL statement string.
     */
    public static String createInsertStatement(Class<?> clazz) {
        var fields = getFields(clazz);
        int lastField = fields.length - 1;

        var names = new StringBuilder("INSERT INTO " + clazz.getSimpleName().toUpperCase() + " (");
        var values = new StringBuilder(" VALUES (");

        int i = 1;

        for (Object obj : fields) {
            var field = (Field) obj;
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

    public static PreparedStatement update(Connection conn, Object object) throws Exception {
        Class clazz = object.getClass();
        var fields = getFields(clazz);
        String statement = createUpdateStatement(clazz);

        PreparedStatement pstmt = conn.prepareStatement(statement);

        int id = clazz.getDeclaredField("id").getInt(object);
        int j = 1;

        for (Object obj : fields) {
            var field = (Field) obj;
            if (field.getName() != "id") {
                Object value = field.get(object);
                pstmt.setObject(j, value);

                j++;
            }
        }

        pstmt.setObject(j, id);

        return pstmt;
    }

    /**
     * Creates an UPDATE SQL statement string based on given class type.
     *
     * @param clazz: The provided class type.
     * @return String: The SELECT SQL statement string.
     */
    public static String createUpdateStatement(Class<?> clazz) throws Exception {
        var fields = getFields(clazz);
        int lastField = fields.length - 1;

        StringBuilder names = new StringBuilder("UPDATE " + clazz.getSimpleName().toUpperCase() + " SET ");

        int i = 1;

        for (Object obj : fields) {
            var field = (Field) obj;

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

    public static PreparedStatement delete(Connection conn, Object object) throws Exception {
        Class clazz = object.getClass();

        int id = clazz.getDeclaredField("id").getInt(object);

        PreparedStatement pstmt = conn.prepareStatement(createDeleteStatement(clazz));

        pstmt.setObject(1, id);

        return pstmt;
    }

    /**
     * Creates a DELETE SQL statement string based on given class type.
     *
     * @param clazz: The provided class type.
     * @return String: The SELECT SQL statement string.
     */
    public static String createDeleteStatement(Class<?> clazz) {
        return "DELETE FROM " + clazz.getSimpleName().toUpperCase() + " WHERE ID = ?";
    }

    /* HELPER FUNCTIONS */

    private static Object[] getFields(Class<?> clazz) {
        return Arrays.asList(clazz.getDeclaredFields()).stream().filter(f -> hasSQLAnnotation(f) && f.getName() != "serialVersionUID").toArray();
    }

    public static boolean hasSQLAnnotation(Field field) {
        return field.getAnnotationsByType(SQLITE.class).length > 0;
    }
}
