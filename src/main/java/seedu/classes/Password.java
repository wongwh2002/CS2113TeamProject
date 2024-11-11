package seedu.classes;

public class Password {
    /**
     * Validates the password with the login credentials after hashing
     * @param password
     * @param loginCredentials
     * @return
     */
    public static boolean validate(int password, String loginCredentials) {
        int loginCredentialsHash = loginCredentials.hashCode();
        if (password != loginCredentialsHash) {
            Ui.printWithTab("Incorrect password! Login Failed :<");
            return false;
        }
        Ui.printWithTab("Login Success!");
        return true;
    }
}
