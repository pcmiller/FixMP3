package org.philco.fixmp3;

import org.philco.fixmp3.fixmp3.patterns.Antipattern;
import org.philco.fixmp3.fixmp3.patterns.DirectoryWithSpacesRemoved;

import java.io.File;
import java.io.FileFilter;
import java.util.Arrays;

public class MP3Directory implements MP3Object {
    private File mp3Directory;

    @Override
    public boolean exists() {
        return mp3Directory.exists();
    }

    @Override
    public String toString() {
        return getName();
    }

    public String getName() {
        return mp3Directory.getName();
    }

    @SuppressWarnings("FieldCanBeLocal")
    private boolean showMode;

    private MP3File[] mp3Files;
    public MP3File[] getMp3Files() {
        return mp3Files;
    }

    private MP3Directory[] mp3Directories;
    public MP3Directory[] getMp3Directories() {
        return mp3Directories;
    }

    private static Antipattern[] antipatterns = {
        new DirectoryWithSpacesRemoved(),
    };

    @SuppressWarnings("FieldCanBeLocal")
    private FileFilter directoryFilter = File::isDirectory;
    private FileFilter mp3FileFilter = mp3File -> mp3File.getName().endsWith(".mp3") && mp3File.isFile();

    private MP3Directory(File directory, boolean showMode) {
        this.showMode = showMode;
        this.mp3Directory = directory;

        try {
            File[] directories = mp3Directory.listFiles(directoryFilter);
            if ( directories == null)
                directories = new File[0];

            mp3Directories = new MP3Directory[directories.length];
            for (int i=0; i<directories.length; i++)
                mp3Directories[i] = new MP3Directory(directories[i], showMode);

            mp3Files = listFiles(showMode);
        } catch (SecurityException se) {
            System.err.println("*** Directory " + mp3Directory.getName() + " is inaccessible.");
            mp3Directories = new MP3Directory[0];
            mp3Files = new MP3File[0];
        }
    }

    public MP3Directory(String directoryName) {
        this(new File(directoryName), false);
    }

    public MP3Directory(String directoryName, boolean showMode) {
        this(new File(directoryName), showMode);
    }

    public void workflow() {
        System.out.println("--- Directory " + getName() + " ---");
        for ( Antipattern antipattern : antipatterns ) {
            if ( antipattern.match(getName() )) {
                System.out.println("   ...matched antipattern " + antipattern.getName());
                String newName = antipattern.fix(getName());
                if ( showMode )
                    System.out.println("   ...Directory would change from " + getName() + " to " + newName);
                else {
                    System.out.println("   ...Directory name changed from " + getName() + " to> " + newName);
                    mp3Directory = renameFile(mp3Directory, newName);
                    break;
                }
            } else
                System.out.println("   ...no match to antipattern " + antipattern.getName() + " for directory " + getName());
        }

        for (MP3Directory mp3Directory : mp3Directories)
            mp3Directory.workflow();

        for (MP3File mp3File : mp3Files)
            mp3File.workflow();
    }

    private MP3File[] listFiles(boolean showMode) {
        File[] files = this.mp3Directory.listFiles(mp3FileFilter);
        if ( files == null )
            files = new File[0];

        MP3File[] mp3Files = new MP3File[files.length];

        for (int i=0; i<files.length; i++)
            mp3Files[i] = new MP3File(files[i], showMode);

        return mp3Files;
    }

    static File renameFile(File oldFile, String newName) {
        File newFile = new File(newName);

        try {
            if (! oldFile.renameTo(newFile)) {
                System.out.println("*** Name change failed for " + oldFile.getName());
                return oldFile;
            } else
                return newFile;
        } catch (Exception e) {
            System.out.println(" failed with exception.");
            System.err.println("Exception: " + e.getMessage());
            return oldFile;
        }
    }
}
