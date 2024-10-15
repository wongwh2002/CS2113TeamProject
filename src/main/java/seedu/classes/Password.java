package seedu.classes;

public class Password {
    public static boolean validate(int password, String loginCredentials) {
        int loginCredentialsHash = loginCredentials.hashCode();
        if (password != loginCredentialsHash) {
            Ui.printWithTab("Login Failed :<");
            return false;
        }
        Ui.printWithTab("Login Success!");
        return true;
    }
}
