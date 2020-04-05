package common;

/**
 * The interface for the user object methods.
 */
public interface IUser {
    /**
     * Change the password of the user.
     *
     * @param newPass, the new password hash.
     */
    void changePassword(String newPass);

    /**
     * Try login with the password for this user.
     *
     * @param pass, the password input
     */
    boolean tryLogin(String pass);
}
