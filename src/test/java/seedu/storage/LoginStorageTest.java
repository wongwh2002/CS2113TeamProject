package seedu.storage;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class LoginStorageTest {
    private final InputStream stdin = System.in;

    private void provideInput(String data) {
        ByteArrayInputStream testIn = new ByteArrayInputStream(data.getBytes());
        System.setIn(testIn);
    }

    @BeforeEach
    public void setUp() {
        provideInput("password");
    }

    @Test
    void loginStorage_userPassword_correctHashCode() {
        String userPassword = "password";
        int hashCode = userPassword.hashCode();
        //provideInput("password");
        LoginStorage.load();
        assertEquals(hashCode, Storage.password);
    }
    @AfterEach
    public void restore() {
        System.setIn(stdin);
    }
}
