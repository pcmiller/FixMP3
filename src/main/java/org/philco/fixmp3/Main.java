package org.philco.fixmp3;

import java.io.File;

public class Main {
    static MP3Directory mp3Directory;
    static boolean showMode = false;

    public static void main(String[] args) {
        for (String arg : args ) {
            if ( arg.startsWith("-")) {
                switch (arg) {
                    case "-n": showMode = true; break;
                    default: System.err.println("*** Ignored unrecognized flag: " + arg);
                }
            }
            else if ( ! (new File(arg).isDirectory() ))
                System.err.println("*** " + arg + " is not a directory, skipped.");
            else {
                new MP3Directory(arg, showMode).workflow();
            }
        }
    }
}
