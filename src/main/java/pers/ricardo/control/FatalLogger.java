package pers.ricardo.control;

import java.util.logging.Level;
import java.util.logging.Logger;

public class FatalLogger {

    public static final Logger LOGGER = Logger.getLogger(FatalLogger.class.getName()); //This could be used any other framework like log4j or slf4j

    public static void fatal(Throwable throwable) {
        LOGGER.log(Level.SEVERE, throwable.getMessage(), throwable);
    }
}
