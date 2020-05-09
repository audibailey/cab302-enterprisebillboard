package server.sql;

import server.services.DataService;

import java.io.ByteArrayInputStream;
import java.lang.reflect.Field;
import java.sql.PreparedStatement;
import java.util.Arrays;
import java.util.List;

public class StatementBuilder {

    public static <T> PreparedStatement get(Class<T> clazz) throws Exception {
        return DataService.getConnection().prepareStatement("SELECT * FROM " + clazz.getSimpleName());
    }

    public static <T> PreparedStatement insert(T object) throws Exception {
        Class clazz = object.getClass();
        List<Field> fields = Arrays.asList(clazz.getDeclaredFields());
        int lastField = fields.toArray().length - 1;

        StringBuilder names = new StringBuilder("INSERT INTO " + clazz.getSimpleName() + " (");
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
