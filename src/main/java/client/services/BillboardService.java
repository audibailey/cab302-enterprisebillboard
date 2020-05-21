package client.services;

import common.models.Billboard;
import common.models.Session;
import common.models.User;
import common.models.UserPermissions;
import common.router.IActionResult;
import common.router.Status;
import common.utils.ClientSocketFactory;

import java.util.ArrayList;
import java.util.List;

public class BillboardService {
    private List<Billboard> billboards;

    protected BillboardService() {
        this.billboards = new ArrayList<>();

        for (int i = 1; i <= 20; i++) {
            this.billboards.add(Billboard.Random(1));
        }
    }

    private static class BillboardServiceHolder {
        private final static BillboardService INSTANCE = new BillboardService();
    }

    public static List<Billboard> getInstance() { return BillboardServiceHolder.INSTANCE.billboards; }

    public static List<Billboard> refresh() {
        Session session = SessionService.getInstance();

        IActionResult result = new ClientSocketFactory("/billboard/get", session.token, null).Connect();

        if (result != null && result.status == Status.SUCCESS && result.body instanceof List) {
            BillboardServiceHolder.INSTANCE.billboards = (List<Billboard>) result.body;
        }

        return BillboardServiceHolder.INSTANCE.billboards;
    }

    public static List<Billboard> insert(Billboard b) {
        Session session = SessionService.getInstance();
        new ClientSocketFactory("/billboard/insert", session.token, null, b).Connect();
        refresh();
        return BillboardServiceHolder.INSTANCE.billboards;
    }

    public static List<Billboard> update(Billboard b) {
        Session session = SessionService.getInstance();
        new ClientSocketFactory("/billboard/update", session.token, null, b).Connect();
        refresh();
        return BillboardServiceHolder.INSTANCE.billboards;
    }

    public static List<Billboard> delete(Billboard b) {
        Session session = SessionService.getInstance();
        new ClientSocketFactory("/billboard/delete", session.token, null, b).Connect();
        refresh();
        return BillboardServiceHolder.INSTANCE.billboards;
    }
}
