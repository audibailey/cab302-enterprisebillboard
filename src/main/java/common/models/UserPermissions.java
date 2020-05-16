package common.models;

import java.io.Serializable;

public class UserPermissions implements Serializable {
    public User user;
    public Permissions permissions;

    public UserPermissions(User user, Permissions permissions) {
        this.user = user;
        this.permissions = permissions;
    }
}
