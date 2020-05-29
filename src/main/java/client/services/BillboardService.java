package client.services;

import common.models.*;
import common.router.Response;
import common.router.response.Status;
import common.utils.ClientSocketFactory;
import common.utils.session.Session;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * This class is responsible for the backend billboard service for the client/viewer.
 *
 * @author Jamie Martin
 */
public class BillboardService extends DataService<Billboard> {
    public List<Billboard> billboards;

    protected BillboardService() {
        this.billboards = new ArrayList<>();
    }

    private static class BillboardServiceHolder {
        private final static BillboardService INSTANCE = new BillboardService();
    }

    public static BillboardService getInstance() { return BillboardServiceHolder.INSTANCE; }

    public List<Billboard> refresh() {
        Session session = SessionService.getInstance();

        if (session != null) {
            Response result = new ClientSocketFactory("/billboard/get", session.token, null).Connect();

            if (result != null && result.status == Status.SUCCESS && result.body instanceof List) {
                BillboardServiceHolder.INSTANCE.billboards = (List<Billboard>) result.body;
            }
        }

        return BillboardServiceHolder.INSTANCE.billboards;
    }

    public List<Billboard> insert(Billboard b) {
        Session session = SessionService.getInstance();
        Response res = new ClientSocketFactory("/billboard/insert", session.token, null, b).Connect();
        return refresh();
    }

    public Boolean update(Billboard b) {
        Session session = SessionService.getInstance();
        Response res = new ClientSocketFactory("/billboard/update", session.token, null, b).Connect();
        return !res.error;
    }

    public List<Billboard> delete(Billboard b) {
        Session session = SessionService.getInstance();

        HashMap<String, String> params = new HashMap<>();
        params.put("bName", b.name);

        Response res = new ClientSocketFactory("/billboard/delete", session.token, params).Connect();
        return refresh();
    }
}
