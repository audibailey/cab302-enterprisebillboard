package client.services;

import common.models.*;
import common.router.IActionResult;
import common.router.Status;
import common.utils.ClientSocketFactory;

import java.util.ArrayList;
import java.util.List;

public class PermissionsService {
    private List<Permissions> permissions;

    protected PermissionsService() {
        this.permissions = new ArrayList<>();
    }

    private static class PermissionsServiceHolder {
        private final static PermissionsService INSTANCE = new PermissionsService();
    }

    public static List<Permissions> getInstance() { return PermissionsServiceHolder.INSTANCE.permissions; }

    public static void refresh() {
        Session session = SessionService.getInstance();

        IActionResult result = new ClientSocketFactory("/permission/get", session.token, null).Connect();

        if (result != null && result.status == Status.SUCCESS && result.body instanceof List) {
            PermissionsServiceHolder.INSTANCE.permissions = (List<Permissions>) result.body;
        }
    }

    public static List<Permissions> insert(UserPermissions up) {
        Session session = SessionService.getInstance();
        new ClientSocketFactory("/userpermissions/insert", session.token, null, up).Connect();
        refresh();
        return PermissionsServiceHolder.INSTANCE.permissions;
    }

    public static void updatePassword(User u, String password) {

    }

    // TODO: Might need to be permissions instead? We don't use User on FE
    public static List<Permissions> delete(User u) {
        Session session = SessionService.getInstance();
        new ClientSocketFactory("/user/delete", session.token, null, u).Connect();
        refresh();
        return PermissionsServiceHolder.INSTANCE.permissions;
    }
}
