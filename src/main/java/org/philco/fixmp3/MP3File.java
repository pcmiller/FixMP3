package org.philco.fixmp3;

import org.philco.fixmp3.fixmp3.patterns.Antipattern;

import java.io.File;
import java.io.FileFilter;

public class MP3File {
    private String name;

    private Antipattern[] antipatterns;

    private static FileFilter mp3FileFilter = new FileFilter() {
        public boolean accept(File pathname) {
            return pathname.getName().endsWith(".mp3") && pathname.isFile();
        }
    };

    public static MP3File[] listFiles(MP3Directory mp3Directory) {
        File[] files = (new File(mp3Directory.getName()).listFiles(mp3FileFilter));
        if ( files == null )
            files = new File[0];

        MP3File[] mp3Files = new MP3File[files.length];

        for (int i=0; i<files.length; i++)
            mp3Files[i] = new MP3File(files[i]);

        return mp3Files;
    }

    public void workflow() {
        System.out.println("...workflow for " + name);
    }

    public MP3File(File file) {
        this(file.getName());
    }

    public MP3File(String name) {
        this.name = name;
    }
}
