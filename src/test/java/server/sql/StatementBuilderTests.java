package server.sql;

import common.models.Billboard;
import org.junit.jupiter.api.Test;
import java.sql.Connection;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class StatementBuilderTests {
    // Test the statements are working correctly for different cases. Testing the prepared statement given back

    @Test
    public void GetStatement() throws Exception {
        String stmt = StatementBuilder.createGetStatement(Billboard.class);
        assertEquals(stmt, "SELECT * FROM BILLBOARD");
    }

    @Test
    public void InsertStatement() throws Exception {
        String stmt = StatementBuilder.createInsertStatement(Billboard.class);
        assertEquals(stmt, "INSERT INTO BILLBOARD (name, message, messageColor, picture, backgroundColor, information, informationColor, locked, userId) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)");
    }

    @Test
    public void UpdateStatement() throws Exception {
        String stmt = StatementBuilder.createUpdateStatement(Billboard.class);
        assertEquals(stmt, "UPDATE BILLBOARD SET name = ?, message = ?, messageColor = ?, picture = ?, backgroundColor = ?, information = ?, informationColor = ?, locked = ?, userId = ? WHERE ID = ?");
    }

    @Test
    public void DeleteStatement() throws Exception {
        String stmt = StatementBuilder.createDeleteStatement(Billboard.class);
        assertEquals(stmt, "DELETE FROM BILLBOARD WHERE ID = ?");
    }

}
