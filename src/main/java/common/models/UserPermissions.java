package common.models;

public class UserPermissions {
    public User user;
    public Permissions permissions;

    public UserPermissions(User user, Permissions permissions) {
        this.user = user;
        this.permissions = permissions;
    }
}
