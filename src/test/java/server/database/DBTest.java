package server.database;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import server.database.*;

public class DBTest {

    DB testDB;

    @BeforeEach
    public void createDatabase() throws Exception {
        this.testDB = new DB();
    }

    @Test
    public void testClose() throws Exception {
        this.testDB.closeConnection();
    }
}
