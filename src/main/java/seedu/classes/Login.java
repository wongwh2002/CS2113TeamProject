package seedu.classes;

public class Login {
    private boolean isLoginSuccess = false;

    public String createNewUser(Ui ui) {
        Ui.printSeparator();
        Ui.printWithTab("Hi! You seem to be new, are you ready?!");
        Ui.printWithTab("Please enter your new account password:");
        String password = ui.readCommand();
        Ui.printSeparator();
        return Integer.toString(password.hashCode());
    }

    public void validateLoginCredentials(String password, Storage storage) {
        int passwordHash = password.hashCode();
        if (storage.getPasswordHash() == passwordHash) {
            isLoginSuccess = true;
        }
    }

    public boolean getLoginSuccess() {
        if (!isLoginSuccess) {
            Ui.printWithTab("Login Failed :<");
        } else {
            Ui.printWithTab("Login Success!");
        }
        return isLoginSuccess;
    }
}
