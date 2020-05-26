package common.models;

import java.io.Serializable;

/**
 * This class consists of the user and permission combined object.
 *
 * @author Perdana Bailey
 * @author Kevin Huynh
 * @author Jamie Martin
 */
public class UserPermissions implements Serializable {
    public User user;
    public Permissions permissions;

    public UserPermissions(User user, Permissions permissions) {
        this.user = user;
        this.permissions = permissions;
    }
}
