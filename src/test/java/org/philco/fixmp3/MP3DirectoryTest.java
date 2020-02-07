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

    String[] directoryNames = {
            "src/test/resources/BadDirectory/Nested Bad Directory",
    };

    @Before
    public void setup() {
        badMp3Directory = new MP3Directory(new File(badDirectoryName));
    }

    @Test
    public void testGetName() {
        assert(badMp3Directory.getName().equals(new File(badDirectoryName).getAbsolutePath()));
    }

    @Test
    public void testGetMp3Files() {
        File dir = new File(badMp3Directory.getName());
        assert dir.exists();

        ArrayList<String> fileNamesAL = new ArrayList<>();
        for (MP3File mp3 : badMp3Directory.getMp3Files()) {
            String mp3FN = mp3.getName();
            assert new File(mp3FN).exists();
            fileNamesAL.add(mp3FN);
        }

        for(String fn : fileNames) {
            String fqfn = badMp3Directory.getName() + "/" + fn;
            assert new File(fqfn).exists();
            assert fileNamesAL.contains(fqfn);
        }
    }

    @Test
    public void testGetMp3Directories() {
        assert badMp3Directory.getName().equals(new File(badDirectoryName).getAbsolutePath());

        MP3Directory[] nestedBadDirectory = badMp3Directory.getMp3Directories();
        assert nestedBadDirectory.length == 1;
        assert nestedBadDirectory[0].getName().equals(new File(nestedBadDirectoryName).getAbsolutePath());

        MP3Directory[] deeplyBadDirectory = nestedBadDirectory[0].getMp3Directories();
        assert deeplyBadDirectory.length == 1;
        assert deeplyBadDirectory[0].getName().equals(new File(deepBadDirectoryName).getAbsolutePath());
    }
}