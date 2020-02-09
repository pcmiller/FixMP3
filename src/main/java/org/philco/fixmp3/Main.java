package org.philco.fixmp3;

import java.io.File;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.LoggerContext;
import org.apache.logging.log4j.core.config.Configuration;
import org.apache.logging.log4j.core.config.LoggerConfig;

public class Main {
    static MP3Directory mp3Directory;
    static boolean showMode = false;
    private static final Logger logger = LogManager.getLogger(Main.class.getName());

    public static void main(String[] args) {
        logger.info("Starting MP3 Directory Editor");

        for (String arg : args ) {
            if ( arg.startsWith("-")) {
                switch (arg) {
                    case "-n": showMode = true; break;
                    case "-q": setLogLevel(Level.WARN); break;
                    case "-qq": setLogLevel(Level.ERROR); break;
                    case "-v": setLogLevel(Level.DEBUG); break;
                    case "-vv": setLogLevel(Level.TRACE); break;
                    case "-vvv": setLogLevel(Level.ALL); break;
                    default: logger.warn("Ignored unrecognized flag: {}", arg);
                }
            }
            else if ( ! (new File(arg).isDirectory() ))
                logger.warn("{} is not a directory, skipped.", arg);
            else {
                new MP3Directory(arg, showMode).workflow();
            }
        }
    }

    // Default log level is INFO
    //    Standard Level	intLevel
    //    OFF	            0
    //    FATAL	            100
    //    ERROR	            200
    //    WARN	            300
    //    INFO	            400
    //    DEBUG	            500
    //    TRACE	            600
    //    ALL	            Integer.MAX_VALUE

    static void setLogLevel(Level level) {
        LoggerContext ctx = (LoggerContext) LogManager.getContext(false);
        Configuration config = ctx.getConfiguration();
        LoggerConfig loggerConfig = config.getLoggerConfig(LogManager.ROOT_LOGGER_NAME);
        loggerConfig.setLevel(level);
        ctx.updateLoggers();  // This causes all Loggers to refetch information from their LoggerConfig.
    }
}
