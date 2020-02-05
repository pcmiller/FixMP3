package org.philco.fixmp3.fixmp3.patterns;

import java.io.File;

public interface Antipattern {
    public boolean match(File file, boolean isDirectory);
    public File fix(File file);
}
