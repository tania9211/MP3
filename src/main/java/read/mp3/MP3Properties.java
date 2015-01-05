package read.mp3;

import read.mp3.frame.Frame;

import java.io.File;
import java.util.Map;

/**
 * Created by BiliaievaTatiana on 1/5/15.
 */
public interface MP3Properties {
    public String getGenre();
    public String getAlbum();
    public String getBand();
    public String getYear();
    public String getName();

    public void setName(String name);
    public void setBand(String band);
    public void setGenre(String genre);
    public void setYear(String artist);
    public void setAlbum(String album);

    public File getFile();

    public Map<String, Frame> getFrameList();
}
