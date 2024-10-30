package seedu.classes;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

public class WiagiLogger {
    public static Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

    public static void initLogger() {
        LogManager.getLogManager().reset();
        logger.setLevel(Level.ALL);

        try {
            FileHandler fh = new FileHandler("logger.log");
            logger.addHandler(fh);
        } catch (IOException e) {
            Ui.printWithTab("Error loading file handler for logger");
        }
    }
}
