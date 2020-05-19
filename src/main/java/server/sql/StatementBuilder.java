package server.sql;

import common.models.SQLITE;
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
        PreparedStatement pstmt = conn.prepareStatement(createInsertStatement(clazz));

        int j = 1;

        for (Field field : getFields(object.getClass())) {
            if (field.getName() != "id" && object.getClass().getAnnotationsByType(SQLITE.class).length > 0) {
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
        List<Field> fields = getFields(clazz);
        int lastField = fields.toArray().length - 1;

        var names = new StringBuilder("INSERT INTO " + clazz.getSimpleName().toUpperCase() + " (");
        var values = new StringBuilder(" VALUES (");

        int i = 1;

        for (Field field : fields) {
            if (field.getName() != "id" && hasSQLAnnotation(clazz)) {
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
        PreparedStatement pstmt = conn.prepareStatement(createUpdateStatement(clazz));

        int id = clazz.getDeclaredField("id").getInt(object);
        int j = 1;

        for (Field field : getFields(object.getClass())) {
            if (field.getName() != "id" && hasSQLAnnotation(clazz)) {
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
        List<Field> fields = getFields(clazz);
        int lastField = fields.toArray().length - 1;

        StringBuilder names = new StringBuilder("UPDATE " + clazz.getSimpleName().toUpperCase() + " SET ");

        int i = 1;

        for (Field field : fields) {
            if (field.getName() != "id" && hasSQLAnnotation(clazz)) {
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

    private static List<Field> getFields(Class<?> clazz) {
        return Arrays.asList(clazz.getDeclaredFields());
    }

    private static boolean hasSQLAnnotation(Class<?> clazz) {
        return clazz.getAnnotationsByType(SQLITE.class).length > 0;
    }
}
