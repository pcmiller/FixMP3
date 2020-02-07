package org.philco.fixmp3;

import org.philco.fixmp3.fixmp3.patterns.Antipattern;

import java.io.File;
import java.io.FileFilter;

public class MP3Directory {
    private String name;
    public String getName() {
        return name;
    }

    private MP3File[] mp3Files;
    public MP3File[] getMp3Files() {
        return mp3Files;
    }

    private MP3Directory[] mp3Directories;
    public MP3Directory[] getMp3Directories() {
        return mp3Directories;
    }

    private Antipattern[] antipatterns;

    private FileFilter directoryFilter = new FileFilter() {
        public boolean accept(File pathname) {
            return pathname.isDirectory();
        }
    };

    public MP3Directory(File directory) {
        this(directory.getAbsolutePath());
    }

    private MP3Directory(String directoryName) {
        this.name = directoryName;
        File directory = new File(directoryName);

        try {
            File[] directories = directory.listFiles(directoryFilter);
            if ( directories == null)
                directories = new File[0];

            mp3Directories = new MP3Directory[directories.length];
            for (int i=0; i<directories.length; i++)
                mp3Directories[i] = new MP3Directory(directories[i]);

            mp3Files = MP3File.listFiles(this);
        } catch (SecurityException se) {
            System.err.println("Directory " + directoryName + " is inaccessible.");
            mp3Directories = new MP3Directory[0];
            mp3Files = new MP3File[0];
        }
    }

    public void workflow() {
        System.out.println("Directory " + getName());

        for (MP3Directory mp3Directory : mp3Directories)
            mp3Directory.workflow();

        for (MP3File mp3File : mp3Files)
            mp3File.workflow();
    }
}
