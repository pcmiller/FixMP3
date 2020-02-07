package org.philco.fixmp3;

import java.io.File;

public class Main {
    static MP3Directory mp3Directory;


    public static void main(String[] args) {
        for (String arg : args ) {
            if ( ! (new File(arg).isDirectory() ))
                System.err.println(arg + " is not a directory, skipped.");
            else {
                mp3Directory = new MP3Directory(new File(arg));
                mp3Directory.workflow();
            }
        }
    }
}
