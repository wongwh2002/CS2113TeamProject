package seedu.storage;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import seedu.classes.Ui;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.classes.Constants.SEPARATOR;
import static seedu.classes.Constants.TAB;

/**
 * The type Login storage test.
 */
public class LoginStorageTest {
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;
    private final PrintStream originalErr = System.err;

    /**
     * Sets up.
     */
    @BeforeEach
    public void setUp() {
        System.setOut(new PrintStream(outContent));
        System.setErr(new PrintStream(errContent));
    }

    /**
     * Restore.
     */
    @AfterEach
    public void restore() {
        System.setOut(originalOut);
        System.setErr(originalErr);
    }

    /**
     * Login storage user password correct hash code.
     */
    @Test
    public void loginStorage_userPassword_correctHashCode() {
        File loginFile = new File("./password.txt");
        loginFile.delete();
        String userPassword = "password";
        int hashCode = userPassword.hashCode();
        Ui.userInputForTest("password" + System.lineSeparator() + "1" + System.lineSeparator() + "1"
                + System.lineSeparator() + "1");
        LoginStorage.load();
        assertEquals(hashCode, Storage.password);
    }

    /**
     * Load file is empty error message.
     */
    @Test
    public void load_fileIsEmpty_errorMessage() {
        File loginFile = new File("./password.txt");
        try {
            loginFile.delete();
            loginFile.createNewFile();
            LoginStorage.load();
        } catch (NoSuchElementException | IOException e) {
            assertEquals(TAB + "Hmmmm, seems to have some issues loading your password, hard resetting... " +
                    "deleting files..." + System.lineSeparator() + TAB + SEPARATOR + System.lineSeparator() +
                    TAB + "Hi! You seem to be new, are you ready?!" + System.lineSeparator() +
                    TAB + "Please enter your new account password:" + System.lineSeparator(), outContent.toString());
        }
    }
}
