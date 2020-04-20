package server.endpoints;

import server.database.DataService;
import server.endpoints.billboard.BillboardHandler;

/**
 * This class handles the multiple endpoints for the server.
 *
 * @author Perdana Bailey
 */
public class EndpointHandler {
    private DataService db;
    public BillboardHandler billboard;

    /**
     * Generates a Endpoint Handler Instance.
     *
     * @param db: This is the database connection the endpoints will use.
     */
    public EndpointHandler(DataService db) {
        this.db = db;
        this.billboard = new BillboardHandler(this.db);
    }
}
