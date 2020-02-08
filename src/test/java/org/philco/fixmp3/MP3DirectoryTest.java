package org.philco.fixmp3;

import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.util.ArrayList;

public class MP3DirectoryTest {
    MP3Directory badMp3Directory, goodMp3Directory;
    String[] fileNames = {
        "01 001-badfile.mp3",
        "01 01-badfile.mp3",
        "01 badfile-01.mp3",
        "01-goodfile.mp3",
        "1-01-001 badfile.mp3",
    };
    final String badDirectoryName = "src/test/resources/BadDirectory";
    final String nestedBadDirectoryName = "src/test/resources/BadDirectory/Nested Bad Directory";
    final String deepBadDirectoryName = "src/test/resources/BadDirectory/Nested Bad Directory/Deeply Nested Bad Directory";

    @Before
    public void setup() {
    }

    @Test
    public void GetMp3FilesTest() {
        badMp3Directory = new MP3Directory(badDirectoryName, true);
        assert badMp3Directory.exists();

        for (MP3Directory mp3Directory : badMp3Directory.getMp3Directories())
            assert mp3Directory.exists();

        for (MP3File mp3File : badMp3Directory.getMp3Files()) {
            assert mp3File.exists();
        }
    }

    @Test
    public void GetMp3DirectoriesTest() {
        badMp3Directory = new MP3Directory(badDirectoryName, true);
        assert badMp3Directory.getName().equals(new File(badDirectoryName).getName());
        assert badMp3Directory.exists();

        MP3Directory[] nestedBadDirectories = badMp3Directory.getMp3Directories();
        assert nestedBadDirectories.length == 1;
        MP3Directory mp3Directory = nestedBadDirectories[0];
        assert mp3Directory.exists();

        MP3Directory[] deeplyBadDirectories = nestedBadDirectories[0].getMp3Directories();
        assert deeplyBadDirectories.length == 1;
        MP3Directory deeplyBadDirectory = deeplyBadDirectories[0];
        assert deeplyBadDirectory.exists();
    }
}