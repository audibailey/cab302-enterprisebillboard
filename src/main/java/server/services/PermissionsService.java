package server.services;

import common.models.User;
import server.router.Request;

public class PermissionsService {

    protected PermissionsService() { }

    private static class PermissionsServiceHolder {
        private final static PermissionsService INSTANCE = new PermissionsService();
    }

    public static PermissionsService getInstance() {
        return PermissionsServiceHolder.INSTANCE;
    }

    /**
     * This function ensures the user with the token is the same with the user created the billboard
     *
     * @param token: The supplied user token.
     * @param data   : The supplied  data
     * @return boolean: The status of the users requested permission.
     */
    public Boolean checkOwnBillboard(Request req) {
        // Check if data is a billboard
//        if (data instanceof Billboard) {
//            Billboard bb = (Billboard) data;
//            try {
//                // Fetch the user from the database and return it
//                Optional<User> fetchedUser = db.users.get(sessionTokens.get(token).getKey());
//                // Fetch the user base on billboard's ID
//                Optional<User> billboardUser = db.users.get(bb.userId);
//
//                // Return true if the user in billboard and user with the token is the same
//                return fetchedUser.equals(billboardUser);
//            } catch (Exception e) {
//                // TODO: Console Log this
//                return false;
//            }
//        } else {
//            return false;
//        }
        return false;
    }

    /**
     * This function ensures the user with the token is the same with the user created the billboard
     *
     * @param token: The supplied user token.
     * @param data   : The supplied  data
     * @return boolean: The status of the users requested permission.
     */
    public Boolean checkSameUser(String token, User user) {
        // Check if data is a billboard
//        if (data instanceof Permissions) {
//            Permissions permissions = (Permissions) data;
//            try {
//                // Fetch the user from the database and return it
//                Optional<User> fetchedUser = db.users.get(sessionTokens.get(token).getKey());
//                // Fetch the user base on billboard's ID
//                Optional<User> billboardUser = db.users.get(permissions.username);
//
//                // Return true if the user in billboard and user with the token is the same
//                return fetchedUser.equals(billboardUser);
//            } catch (Exception e) {
//                // TODO: Console Log this
//                return false;
//            }
//        } else {
//            return false;
//        }
        return false;
    }

    /**
     * This function ensures the user has the required permissions to create a billboard.
     *
     * @param token: The supplied user token.
     * @return boolean: The status of the users requested permission.
     */
    public Boolean checkCanCreateBillboard(String token) {
//        try {
//            // Fetch the permission from the database and return it
//            Optional<Permissions> fetchedPerms = db.permissions.get(sessionTokens.get(token).getKey());
//            AtomicBoolean canCreateBillboard = new AtomicBoolean(false);
//            fetchedPerms.ifPresent(permissions -> canCreateBillboard.set(permissions.canCreateBillboard));
//            return canCreateBillboard.get();
//        } catch (Exception e) {
//            // TODO: Console Log this
//            return false;
//        }
        return false;
    }

    /**
     * This function ensures the user has the required permissions to edit a billboard.
     *
     * @param token: The supplied user token.
     * @return boolean: The status of the users requested permission.
     */
    public boolean checkCanEditBillboard(String token) {
//        try {
//            // Fetch the permission from the database and return it
//            Optional<Permissions> fetchedPerms = db.permissions.get(sessionTokens.get(token).getKey());
//            AtomicBoolean canEditBillboard = new AtomicBoolean(false);
//            fetchedPerms.ifPresent(permissions -> canEditBillboard.set(permissions.canEditBillboard));
//            return canEditBillboard.get();
//        } catch (Exception e) {
//            // TODO: Console Log this
//            return false;
//        }
        return false;
    }

    /**
     * This function ensures the user has the required permissions to schedule a billboard.
     *
     * @param token: The supplied user token.
     * @return boolean: The status of the users requested permission.
     */
    public boolean checkCanScheduleBillboard(String token) {
//        try {
//            // Fetch the permission from the database and return it
//            Optional<Permissions> fetchedPerms = db.permissions.get(sessionTokens.get(token).getKey());
//            AtomicBoolean canScheduleBillboard = new AtomicBoolean(false);
//            fetchedPerms.ifPresent(permissions -> canScheduleBillboard.set(permissions.canScheduleBillboard));
//            return canScheduleBillboard.get();
//        } catch (Exception e) {
//            // TODO: Console Log this
//            return false;
//        }
        return false;
    }

    /**
     * This function ensures the user has the required permissions to edit a user.
     *
     * @param token: The supplied user token.
     * @return boolean: The status of the users requested permission.
     */
    public boolean checkCanEditUser(String token) {
//        try {
//            // Fetch the permission from the database and return it
//            Optional<Permissions> fetchedPerms = db.permissions.get(sessionTokens.get(token).getKey());
//            AtomicBoolean canEditUser = new AtomicBoolean(false);
//            fetchedPerms.ifPresent(permissions -> canEditUser.set(permissions.canEditUser));
//            return canEditUser.get();
//        } catch (Exception e) {
//            // TODO: Console Log this
//            return false;
//        }
        return false;
    }

    /**
     * This function ensures the user has the required permissions to view a billboard.
     *
     * @param token: The supplied user token.
     * @return boolean: The status of the users requested permission.
     */
    public boolean checkCanViewBillboard(String token) {
//        try {
//            // Fetch the permission from the database and return it
//            Optional<Permissions> fetchedPerms = db.permissions.get(sessionTokens.get(token).getKey());
//            AtomicBoolean canViewBillboard = new AtomicBoolean(false);
//            fetchedPerms.ifPresent(permissions -> canViewBillboard.set(permissions.canViewBillboard));
//            return canViewBillboard.get();
//        } catch (Exception e) {
//            // TODO: Console Log this
//            return false;
//        }
        return false;
    }
}
