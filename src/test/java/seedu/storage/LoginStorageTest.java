package seedu.storage;

import org.junit.jupiter.api.Test;
import seedu.classes.Ui;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class LoginStorageTest {
    @Test
    public void loginStorage_userPassword_correctHashCode() {
        String userPassword = "password";
        int hashCode = userPassword.hashCode();
        Ui.userInputForTest("password");
        LoginStorage.load();
        assertEquals(hashCode, Storage.password);
    }
}
