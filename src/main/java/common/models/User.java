package common.models;

/**
 * This class consists of the user object and its associated methods.
 *
 * @author Perdana Bailey
 * @author Kevin Huynh
 * @author Jamie Martin
 */
public class User {
    /**
     * The variables of the object User
     */
    public int id;
    public String username;
    public String password;
    public boolean canCreateBillboard;
    public boolean canEditBillboard;
    public boolean canScheduleBillboard;
    public boolean canEditUser;
    public boolean canViewBillboard;

    public User() {

    }

    /**
     * User object constructor
     *
     * @param id
     * @param username
     * @param canCreateBillboard
     * @param canEditBillboard
     * @param canScheduleBillboard
     * @param canEditUser
     * @param canViewBillboard
     */
    public User(int id, String username, boolean canCreateBillboard, boolean canEditBillboard, boolean canScheduleBillboard, boolean canEditUser, boolean canViewBillboard) {
        this.id = id;
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

    /**
     * Try login with the password for this user.
     *
     * @param pass, the password input
     */
    public boolean tryLogin(String pass) {
        return this.password.equals(pass);
    }

    /**
     * Parses the XML string and returns a User object
     * @param xml
     * @return
     */
    public static User fromXML(String xml) {
        return new User();
    }

    /**
     * Parses the Object and returns an XML String
     * @param u
     * @return
     */
    public static String fromObject(User u) {
        return "";
    }
}
