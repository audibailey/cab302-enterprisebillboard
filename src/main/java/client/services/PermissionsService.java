package client.services;

import common.models.*;
import common.router.IActionResult;
import common.router.Status;
import common.utils.ClientSocketFactory;
import common.utils.HashingFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * This class is responsible for the backend permissions service for the client/viewer.
 *
 * @author Jamie Martin
 */
public class PermissionsService extends DataService<Permissions> {
    private List<Permissions> permissions;

    protected PermissionsService() {
        this.permissions = new ArrayList<>();
    }

    private static class PermissionsServiceHolder {
        private final static PermissionsService INSTANCE = new PermissionsService();
    }

    public static PermissionsService getInstance() { return PermissionsServiceHolder.INSTANCE; }

    public List<Permissions> refresh() {
        Session session = SessionService.getInstance();

        if (session != null) {
            IActionResult result = new ClientSocketFactory("/permission/get", session.token, null).Connect();

            if (result != null && result.status == Status.SUCCESS && result.body instanceof List) {
                PermissionsServiceHolder.INSTANCE.permissions = (List<Permissions>) result.body;
            }
        }

        return PermissionsServiceHolder.INSTANCE.permissions;
    }

    public List<Permissions> insert(UserPermissions up) {
        Session session = SessionService.getInstance();
        IActionResult res = new ClientSocketFactory("/userpermissions/insert", session.token, null, up).Connect();
        return refresh();
    }

    public Boolean update(Permissions p) {
        Session session = SessionService.getInstance();
        IActionResult res = new ClientSocketFactory("/permission/update", session.token, null, p).Connect();
        return !res.error;
    }

    public List<Permissions> delete(User u) {
        Session session = SessionService.getInstance();

        HashMap<String, String> params = new HashMap<>();
        params.put("username", u.username);

        IActionResult res = new ClientSocketFactory("/user/delete", session.token, params).Connect();
        return refresh();
    }


    public List<Permissions> updatePassword(int id, String username, String password) throws Exception {
        Session session = SessionService.getInstance();
        HashMap<String, String> params = new HashMap<>();
        params.put("username", username);
        params.put("password", HashingFactory.hashPassword(password));
        IActionResult res = new ClientSocketFactory("/user/update/password", session.token, params, null).Connect();
        return refresh();
    }
}
