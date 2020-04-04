package common;

/**
 * This class consists of the user object and its associated methods.
 *
 * @author Perdana Bailey
 * @author Kevin Huynh
 * @author Jamie Martinn
 */
public class User implements IUser {
    /**
     * The variables of the object User
     */
    private int userID;
    private String username;
    private String password;
    private boolean canCreateBillboard;
    private boolean canEditBillboard;
    private boolean canScheduleBillboard;
    private boolean canEditUser;
    private boolean canViewBillboard;

    /**
     * User object constructor
     *
     * @param userID
     * @param username
     * @param canCreateBillboard
     * @param canEditBillboard
     * @param canScheduleBillboard
     * @param canEditUser
     * @param canViewBillboard
     */
    public User(int userID, String username, boolean canCreateBillboard, boolean canEditBillboard, boolean canScheduleBillboard, boolean canEditUser, boolean canViewBillboard) {
        this.userID = userID;
        this.username = username;
        this.canCreateBillboard = canCreateBillboard;
        this.canEditBillboard = canEditBillboard;
        this.canScheduleBillboard = canScheduleBillboard;
        this.canEditUser = canEditUser;
        this.canViewBillboard = canViewBillboard;
    }

    /**
     * Change the password of the user
     *
     * @param newPass, the new password hash.
     */
    public void changePassword(String newPass) {
        this.password = newPass;
    }
}
