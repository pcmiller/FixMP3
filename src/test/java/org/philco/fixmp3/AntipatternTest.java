package org.philco.fixmp3;

import org.junit.Test;
import org.philco.fixmp3.fixmp3.patterns.DirectoryWithSpacesRemoved;
import org.philco.fixmp3.fixmp3.patterns.FileWithDiskDashDoubleIndexSeparatedBySpace;
import org.philco.fixmp3.fixmp3.patterns.FileWithDoubleIndexSeparatedByHyphen;
import org.philco.fixmp3.fixmp3.patterns.FileWithDoubleIndexSeparatedBySpace;

public class AntipatternTest {
    @Test
    public void testDirectoryWithSpacesRemoved() {
        String input = "BadDirectory";
        String expected = "Bad Directory";
        assert new DirectoryWithSpacesRemoved().match(input);

        String actual = new DirectoryWithSpacesRemoved().fix(input);
        assert actual.equals(expected);
    }

    @Test
    public void testFileWithDoubleIndexSeparatedBySpace() {
        String input = "01 01-foo.mp3";
        assert new FileWithDoubleIndexSeparatedBySpace().match(input);

        String input2 = "01 02-foo.mp3";
        assert ! (new FileWithDoubleIndexSeparatedBySpace().match(input2));

        String expected = "01-foo.mp3";
        String actual = new FileWithDoubleIndexSeparatedBySpace().fix(input);
        assert expected.equals(actual);
    }

    @Test
    public void testFileWithDoubleIndexSeparatedByHyphen() {
        String input = "01-01-foo.mp3";
        assert new FileWithDoubleIndexSeparatedByHyphen().match(input);

        String input2 = "01-02-foo.mp3";
        assert ! (new FileWithDoubleIndexSeparatedByHyphen().match(input2));

        String expected = "01-foo.mp3";
        String actual = new FileWithDoubleIndexSeparatedByHyphen().fix(input);
        assert expected.equals(actual);
    }
    // FileWithDiskDashDoubleIndexSeparatedBySpace

    @Test
    public void testFileWithDiskDashDoubleIndexSeparatedBySpace() {
        String input = "1-02 02-foo.mp3";
        assert new FileWithDiskDashDoubleIndexSeparatedBySpace().match(input);

        String input2 = "1-01 02-foo.mp3";
        assert ! (new FileWithDiskDashDoubleIndexSeparatedBySpace().match(input2));

        String expected = "02-foo.mp3";
        String actual = new FileWithDiskDashDoubleIndexSeparatedBySpace().fix(input);
        assert expected.equals(actual);
    }


}
