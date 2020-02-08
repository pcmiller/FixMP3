package org.philco.fixmp3;

import org.philco.fixmp3.fixmp3.patterns.Antipattern;
import org.philco.fixmp3.fixmp3.patterns.FileWithDiskDashDoubleIndexSeparatedBySpace;
import org.philco.fixmp3.fixmp3.patterns.FileWithDoubleIndexSeparatedByHyphen;
import org.philco.fixmp3.fixmp3.patterns.FileWithDoubleIndexSeparatedBySpace;

import java.io.File;
import java.io.FileFilter;

public class MP3File implements MP3Object {
    private boolean showMode;
    private File mp3File;
    public String getName() {
        return mp3File.getName();
    }

    @Override
    public String toString() {
        return getName();
    }

    private Antipattern[] antipatterns = {
        new FileWithDoubleIndexSeparatedBySpace(),
        new FileWithDoubleIndexSeparatedByHyphen(),
        new FileWithDiskDashDoubleIndexSeparatedBySpace(),
    };

    public void workflow() {
        System.out.println("File " + getName());
        for ( Antipattern antipattern : antipatterns ) {
            if ( antipattern.match(getName() )) {
                System.out.println("...matched antipattern " + antipattern.getName());
                String newName = antipattern.fix(getName());
                if ( showMode )
                    System.out.println("...File would change from " + getName() + " -> " + newName);
                else {
                    mp3File = MP3Directory.renameFile(mp3File, newName);
                    break;
                }
            } else
                System.out.println("...no match to antipattern " + antipattern.getName() + " for file " + getName());
        }
    }

    @Override
    public boolean exists() {
        return mp3File.exists();
    }

    public MP3File(File mp3File, boolean showMode) {
        this.mp3File = mp3File;
        this.showMode = showMode;
    }
}
