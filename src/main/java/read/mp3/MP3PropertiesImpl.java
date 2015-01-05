package read.mp3;

import read.mp3.frame.Frame;
import read.mp3.frame.FrameTypes;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by BiliaievaTatiana on 1/5/15.
 */
public class MP3PropertiesImpl implements MP3Properties {
    private Map<String, Frame> frameList;
    private File file;

    public MP3PropertiesImpl(File file) {
        this.file = file;
        this.frameList = new HashMap<String, Frame>();

        frameList.put(FrameTypes.GENRE, new Frame(FrameTypes.GENRE));
        frameList.put(FrameTypes.SONG, new Frame(FrameTypes.SONG));
        frameList.put(FrameTypes.BAND, new Frame(FrameTypes.BAND));
        frameList.put(FrameTypes.YEAR, new Frame(FrameTypes.YEAR));
        frameList.put(FrameTypes.ALBUM, new Frame(FrameTypes.ALBUM));
    }

    public Map<String, Frame> getFrameList() {
        return frameList;
    }

    public File getFile() {
        return file;
    }

    @Override
    public String getGenre() {
        return frameList.get(FrameTypes.GENRE) != null && frameList.get(FrameTypes.GENRE).getFrameValue() != null
                ? frameList.get(FrameTypes.GENRE).getFrameValue() : "";
    }

    @Override
    public String getAlbum() {
        return frameList.get(FrameTypes.ALBUM) != null && frameList.get(FrameTypes.ALBUM).getFrameValue() != null
                ? frameList.get(FrameTypes.ALBUM).getFrameValue() : "";
    }

    @Override
    public String getBand() {
        return frameList.get(FrameTypes.BAND) != null && frameList.get(FrameTypes.BAND).getFrameValue() != null
                ? frameList.get(FrameTypes.BAND).getFrameValue() : "";
    }

    @Override
    public String getYear() {
        return frameList.get(FrameTypes.YEAR) != null && frameList.get(FrameTypes.YEAR).getFrameValue() != null
                ? frameList.get(FrameTypes.YEAR).getFrameValue() : "";
    }

    @Override
    public String getName() {
        return frameList.get(FrameTypes.SONG) != null && frameList.get(FrameTypes.SONG).getFrameValue() != null
                ? frameList.get(FrameTypes.SONG).getFrameValue() : "";
    }

    @Override
    public void setName(String name) {
        if (frameList.get(FrameTypes.SONG) != null) {
            frameList.get(FrameTypes.SONG).setFrameValue(name);
            frameList.get(FrameTypes.SONG).setRewrited(true);
        }
    }

    @Override
    public void setBand(String band) {
        if (frameList.get(FrameTypes.BAND) != null) {
            frameList.get(FrameTypes.BAND).setFrameValue(band);
            frameList.get(FrameTypes.BAND).setRewrited(true);
        }
    }

    @Override
    public void setGenre(String genre) {
        if (frameList.get(FrameTypes.GENRE) != null) {
            frameList.get(FrameTypes.GENRE).setFrameValue(genre);
            frameList.get(FrameTypes.GENRE).setRewrited(true);
        }
    }

    @Override
    public void setYear(String year) {
        if (frameList.get(FrameTypes.YEAR) != null) {
            frameList.get(FrameTypes.YEAR).setFrameValue(year);
            frameList.get(FrameTypes.YEAR).setRewrited(true);
        }
    }

    @Override
    public void setAlbum(String album) {
        if (frameList.get(FrameTypes.ALBUM) != null) {
            frameList.get(FrameTypes.ALBUM).setFrameValue(album);
            frameList.get(FrameTypes.ALBUM).setRewrited(true);
        }
    }
}
