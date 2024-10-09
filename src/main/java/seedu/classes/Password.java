package seedu.classes;

import seedu.storage.Storage;

public class Login {
    public int createNewUser(Ui ui) {
        Ui.printSeparator();
        Ui.printWithTab("Hi! You seem to be new, are you ready?!");
        Ui.printWithTab("Please enter your new account password:");
        String password = ui.readCommand();
        Ui.printSeparator();
        return password.hashCode();
    }

    public boolean validate(String loginCredentials, Storage storage) {
        int loginCredentialsHash = loginCredentials.hashCode();
        int storedPasswordHash = storage.getPasswordHash();
        if (storedPasswordHash != loginCredentialsHash) {
            Ui.printWithTab("Login Failed :<");
            return false;
        }
        Ui.printWithTab("Login Success!");
        return true;
    }
}
