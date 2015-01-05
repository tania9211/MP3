package read.mp3;

import java.io.File;
import java.io.IOException;

/**
 * Created by BiliaievaTatiana on 1/5/15.
 */
public interface MP3Manager {

    public void write(MP3Properties properties) throws IOException;

    public MP3Properties read(File file) throws IOException;
}
