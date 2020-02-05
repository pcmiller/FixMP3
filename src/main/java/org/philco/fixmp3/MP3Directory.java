package org.philco.fixmp3;

import java.io.File;
import java.io.FileFilter;
import java.io.FilenameFilter;

public class MP3Directory {
    private String name;
    public String getName() {
        return name;
    }

    private File[] mp3Files;
    public File[] getMp3Files() {
        return mp3Files;
    }

    private File[] subdirectories;
    public File[] getSubdirectories() {
        return subdirectories;
    }

    private FilenameFilter directoryFilter;
    private FilenameFilter mp3FileFilter;

    public MP3Directory(String directoryName) {
        this.name = directoryName;
        File directory = new File(directoryName);

        try {
            subdirectories = directory.listFiles(new FileFilter() {
                public boolean accept(File pathname) {
                    return pathname.isDirectory();
                }
            });

            mp3Files = directory.listFiles(new FileFilter() {
                public boolean accept(File pathname) {
                    return pathname.getName().endsWith(".mp3") && pathname.isFile();
                }
            });
        } catch (SecurityException se) {
            System.err.println("Directory " + directoryName + " is inaccessible.");
            subdirectories = new File[0];
            mp3Files = new File[0];
        }
    }

    public void workflow() {
        for (File directory : subdirectories) {
            System.out.println("Directory " + directory.getName());
        }

        for (File mp3File : mp3Files) {
            System.out.println("MP3 file " + mp3File.getName());
        }
    }
}
