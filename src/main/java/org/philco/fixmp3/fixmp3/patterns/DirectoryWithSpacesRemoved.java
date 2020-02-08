package org.philco.fixmp3.fixmp3.patterns;

import java.io.File;

public class DirectoryWithSpacesRemoved implements Antipattern {
    String pattern = "([A-Z][a-z0-9]+)([A-Z].*)";
    String replacement = "$1 $2";

    @Override
    public boolean match(String filename) {
        return filename.matches(pattern);
    }

    @Override
    public String getName() {
        return this.getClass().getSimpleName();
    }

    @Override
    public String fix(String filename) {
        return filename.replaceFirst(pattern, replacement);
    }
}
