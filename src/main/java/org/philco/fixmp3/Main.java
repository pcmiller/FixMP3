package org.philco.fixmp3;

import java.io.File;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

public class Main {
    static MP3Directory mp3Directory;
    static boolean showMode = false;
    private static final Logger logger = LogManager.getLogger(Main.class.getName());

    public static void main(String[] args) {
        logger.info("Starting MP3 Directory Editor");
//        logger.trace("Classpath: {}", System.getProperty("java.class.path"));

        for (String arg : args ) {
            if ( arg.startsWith("-")) {
                switch (arg) {
                    case "-n": showMode = true; break;
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
}
