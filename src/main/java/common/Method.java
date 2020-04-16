package common;

import common.models.Billboard;
import common.models.Response;
import server.endpoints.billboard.BillboardHandler;

import java.sql.SQLException;

public enum Method {
    GET_BILLBOARDS {
        public Response<Billboard> run(BillboardHandler h) throws SQLException {
            return h.delete();
        }
    },
    GET_LOCKED_BILLBOARDS {
        public Response<Billboard> run(BillboardHandler h) throws SQLException {

            return new Response<Billboard>();
        }
    },
    GET_UNLOCKED_BILLBOARDS {
        public Response<Billboard> run(BillboardHandler h) throws SQLException {

            return new Response<Billboard>();
        }
    },
    POST_BILLBOARD {
        public Response<Billboard> run(BillboardHandler h) throws SQLException {

            return new Response<Billboard>();
        }
    },
    UPDATE_BILLBOARD {
        public Response<Billboard> run(BillboardHandler h) throws SQLException {

            return new Response<Billboard>();
        }
    },
    DELETE_BILLBOARD {
        public Response<Billboard> run(BillboardHandler h) throws SQLException {

            return new Response<Billboard>();
        }
    };

    public abstract Response<Billboard> run(BillboardHandler h) throws SQLException;
}
