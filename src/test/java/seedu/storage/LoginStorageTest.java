package seedu.storage;

import org.junit.jupiter.api.Test;
import seedu.classes.Ui;

import java.io.ByteArrayInputStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class LoginStorageTest {
    void provideInput(String data) {
        ByteArrayInputStream testIn = new ByteArrayInputStream(data.getBytes());
        System.setIn(testIn);
    }

    @Test
    void loginStorage_userPassword_correctHashCode() {
        String userPassword = "password";
        int hashCode = userPassword.hashCode();
        Ui ui = new Ui();
        provideInput("password");
        LoginStorage.load(ui);
        assertEquals(hashCode, Storage.password);
    }
}
