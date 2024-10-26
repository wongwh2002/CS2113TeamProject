package seedu.classes;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class PasswordTest {
    @Test
    public void validate_correctPassword_success() {
        String password = "password";
        int hashCode = password.hashCode();
        assertTrue(Password.validate(hashCode, password));
    }

    @Test
    public void validate_wrongPassword_failure() {
        String password = "password";
        String wrongPassword = "pw";
        int hashCode = password.hashCode();
        assertFalse(Password.validate(hashCode, wrongPassword));
    }
}
