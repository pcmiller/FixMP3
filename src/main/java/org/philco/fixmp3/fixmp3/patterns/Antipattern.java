package org.philco.fixmp3.fixmp3.patterns;

import java.io.File;

public interface Antipattern {
    public boolean match(String filename);
    public String fix(String filename);
    public String getName();
}
