package client.services;

import common.models.*;
import common.router.IActionResult;
import common.router.Status;
import common.utils.ClientSocketFactory;

import java.util.ArrayList;
import java.util.List;

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
            IActionResult result = new ClientSocketFactory("/billboard/get", session.token, null).Connect();

            if (result != null && result.status == Status.SUCCESS && result.body instanceof List) {
                BillboardServiceHolder.INSTANCE.billboards = (List<Billboard>) result.body;
            }
        }

        return BillboardServiceHolder.INSTANCE.billboards;
    }

    public List<Billboard> insert(Billboard b) {
        Session session = SessionService.getInstance();
        IActionResult res = new ClientSocketFactory("/billboard/insert", session.token, null, b).Connect();
        return refresh();
    }

    public Boolean update(Billboard b) {
        Session session = SessionService.getInstance();
        IActionResult res = new ClientSocketFactory("/billboard/update", session.token, null, b).Connect();
        return !res.error;
    }

    public List<Billboard> delete(Billboard b) {
        Session session = SessionService.getInstance();
        IActionResult res = new ClientSocketFactory("/billboard/delete", session.token, null, b).Connect();
        return refresh();
    }
}
