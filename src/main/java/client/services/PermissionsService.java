package client.services;

import common.models.*;
import common.router.Response;
import common.router.response.Status;
import common.utils.ClientSocketFactory;
import common.utils.session.HashingFactory;
import common.utils.session.Session;

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

    /**
     * initialise new permissions service
     */
    protected PermissionsService() {
        this.permissions = new ArrayList<>();
    }

    /**
     * static singleton holder for permissions service
     */
    private static class PermissionsServiceHolder {
        private final static PermissionsService INSTANCE = new PermissionsService();
    }

    /**
     * get the permissions service instance
     * @return
     */
    public static PermissionsService getInstance() { return PermissionsServiceHolder.INSTANCE; }

    /**
     * refreshes the permissions list
     * @return
     */
    public List<Permissions> refresh() {
        Session session = SessionService.getInstance();

        if (session != null) {
            Response result = new ClientSocketFactory("/permission/get", session.token, null).Connect();

            if (result != null && result.status == Status.SUCCESS && result.body instanceof List) {
                PermissionsServiceHolder.INSTANCE.permissions = (List<Permissions>) result.body;
            }
        }

        return PermissionsServiceHolder.INSTANCE.permissions;
    }

    /**
     * attempts to insert the given permissions on the server
     * @param b
     * @return
     */
    public List<Permissions> insert(UserPermissions up) {
        Session session = SessionService.getInstance();
        Response res = new ClientSocketFactory("/userpermissions/insert", session.token, null, up).Connect();
        return refresh();
    }

    /**
     * attempts to update the given permissions on the server
     * @param b
     * @return
     */
    public Boolean update(Permissions p) {
        Session session = SessionService.getInstance();
        Response res = new ClientSocketFactory("/permission/update", session.token, null, p).Connect();
        return !res.error;
    }

    /**
     * attempts to delete the given permissions on the server
     * @param u
     * @return
     */
    public List<Permissions> delete(User u) {
        Session session = SessionService.getInstance();

        HashMap<String, String> params = new HashMap<>();
        params.put("username", u.username);

        Response res = new ClientSocketFactory("/user/delete", session.token, params).Connect();
        return refresh();
    }

    /**
     * attempts to update the password of the given user id on the server
     * @param id
     * @param username
     * @param password
     * @return
     * @throws Exception
     */
    public List<Permissions> updatePassword(int id, String username, String password) throws Exception {
        Session session = SessionService.getInstance();
        
        HashMap<String, String> params = new HashMap<>();
        params.put("username", username);
        params.put("password", HashingFactory.hashPassword(password));

        Response res = new ClientSocketFactory("/user/update/password", session.token, params, null).Connect();
        return refresh();
    }
}
