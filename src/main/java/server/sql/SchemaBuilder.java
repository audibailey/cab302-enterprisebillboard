package server.sql;

import common.models.SQL;
import common.models.SQLITE;

import java.io.ByteArrayInputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.sql.*;
import java.util.Arrays;
import java.util.List;

/**
 * This class initialises the database and it's schema.
 *
 * @author Jamie Martin
 */
public class SchemaBuilder {

    public Connection connection;
    public boolean sqlite;
    public Class[] classes;

    public <T> SchemaBuilder(Connection connection, boolean sqlite, Class<?>... classes) {
        this.connection = connection;
        this.sqlite = sqlite;
        this.classes = classes;
    }

    public void build() throws Exception {


        for (var classType : classes) {
            // Create statement
            Statement sqlStatement = connection.createStatement();

            String sql = sqlite ? tableStringSQLITE(classType) : tableStringSQL(classType);
            System.out.println(sql);
            sqlStatement.executeUpdate(sql);
        }
    }

    public String tableStringSQLITE(Class classType) throws Exception {
        var query = new StringBuilder("CREATE TABLE IF NOT EXISTS " + classType.getSimpleName().toUpperCase() + " (");
        List<Field> fields = Arrays.asList(classType.getFields());

        var classAnnotations = classType.getAnnotationsByType(SQLITE.class);
        var SQLAddition = (SQLITE) Arrays.stream(classAnnotations).findFirst().get();

        for (int i = 0; i < fields.size(); i++) {
            fields.get(i).setAccessible(true);
            var field = fields.get(i);

            var annotations = field.getAnnotationsByType(SQLITE.class);
            var annotation = Arrays.stream(annotations).findFirst();

            if (annotation.isPresent()) {
                query.append(fields.get(i).getName() + " " + annotation.get().type());

                if (i != fields.size() - 1 ) {
                    query.append(", ");
                }
            }
        }

        if (!SQLAddition.type().equals("")) {
            query.append(", " + SQLAddition.type());
        }

        query.append(")");
        return query.toString();
    }

    public String tableStringSQL(Class classType) throws Exception {
        var query = new StringBuilder("CREATE TABLE IF NOT EXISTS " + classType.getSimpleName().toUpperCase() + " (");
        List<Field> fields = Arrays.asList(classType.getFields());

        var classAnnotations = classType.getAnnotationsByType(SQL.class);
        var SQLAddition = (SQL) Arrays.stream(classAnnotations).findFirst().get();

        for (int i = 0; i < fields.size(); i++) {
            fields.get(i).setAccessible(true);
            var field = fields.get(i);

            var annotations = field.getAnnotationsByType(SQL.class);
            var annotation = Arrays.stream(annotations).findFirst();

            if (annotation.isPresent()) {
                query.append(fields.get(i).getName() + " " + annotation.get().type());

                if (i != fields.size() - 1 ) {
                    query.append(", ");
                }
            }
        }

        if (!SQLAddition.type().equals("")) {
            query.append(", " + SQLAddition.type());
        }

        query.append(")");
        return query.toString();
    }
}
