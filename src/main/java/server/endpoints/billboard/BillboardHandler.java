package server.endpoints.billboard;

import common.models.Billboard;
import common.models.Request;
import common.models.Response;
import server.database.DataService;

import java.sql.SQLException;

public class BillboardHandler {

    DataService db;
    Request<Billboard> request;

    public BillboardHandler(DataService db) {
        this.db = db;
    }

    public void setRequest(Request<Billboard> newReq) {
        request = newReq;
    }

    public Response<Billboard> delete() throws SQLException {

        db.billboards.delete(request.data);

        return new Response<>();
    }



}
