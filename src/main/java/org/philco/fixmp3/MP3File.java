package org.philco.fixmp3;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.philco.fixmp3.fixmp3.patterns.Antipattern;
import org.philco.fixmp3.fixmp3.patterns.FileWithDiskDashDoubleIndexSeparatedBySpace;
import org.philco.fixmp3.fixmp3.patterns.FileWithDoubleIndexSeparatedByHyphen;
import org.philco.fixmp3.fixmp3.patterns.FileWithDoubleIndexSeparatedBySpace;

import java.io.File;
import java.io.FileFilter;

public class MP3File implements MP3Object {
    private static final Logger logger = LogManager.getLogger(MP3File.class.getName());
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
        logger.info("File {}", getName());
        for ( Antipattern antipattern : antipatterns ) {
            if ( antipattern.match(getName() )) {
                logger.debug("Matched antipattern {}", antipattern.getName());
                String newName = antipattern.fix(getName());

                if ( showMode )
                    logger.info("File would change from {} to {}", getName(), newName);
                else {
                    mp3File = MP3Directory.renameFile(mp3File, newName);
                    break;
                }
            } else
                logger.trace("No match to antipattern {} for file {}", antipattern.getName(), getName());
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
