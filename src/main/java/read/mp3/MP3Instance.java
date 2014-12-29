package read.mp3;

import read.mp3.frame.Frame;
import read.mp3.frame.FrameTypes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by BiliaievaTatiana on 12/17/14.
 */
public class MP3Instance {
    private Map<String, Frame> frameList;
    private List<Frame>framesToWrite = new ArrayList<Frame>();
  //  private int tagSize;

    public MP3Instance() {
        frameList = new HashMap<String, Frame>();

        frameList.put(FrameTypes.GENRE, new Frame(FrameTypes.GENRE));
        frameList.put(FrameTypes.SONG, new Frame(FrameTypes.SONG));
        frameList.put(FrameTypes.BAND, new Frame(FrameTypes.BAND));
        frameList.put(FrameTypes.YEAR, new Frame(FrameTypes.YEAR));
        frameList.put(FrameTypes.ALBUM, new Frame(FrameTypes.ALBUM));
    }

 /*  void setTagSize(int tagSize) {
        this.tagSize = tagSize;
    }

    public int getTagSize() {
        return tagSize;
    }*/

    Map<String, Frame> getFrameList() {
        return frameList;
    }

    List<Frame> getFramesToWrite() {
        return framesToWrite;
    }

    void clearFramesToWrite() {
        framesToWrite.clear();
    }

    public String getBand() {
        return frameList.get(FrameTypes.BAND) != null && frameList.get(FrameTypes.BAND).getFrameValue() != null
                ? frameList.get(FrameTypes.BAND).getFrameValue() : "";
    }

    public void setBand(String band) {
        if (frameList.get(FrameTypes.BAND) != null) {
            frameList.get(FrameTypes.BAND).setFrameValue(band);
            framesToWrite.add(frameList.get(FrameTypes.BAND));
        }
    }

    public String getSongName() {
        return frameList.get(FrameTypes.SONG) != null && frameList.get(FrameTypes.SONG).getFrameValue() != null
                ? frameList.get(FrameTypes.SONG).getFrameValue() : "";
    }

    public void setSongName(String songName) {
        if (frameList.get(FrameTypes.SONG) != null) {
            frameList.get(FrameTypes.SONG).setFrameValue(songName);
            framesToWrite.add(frameList.get(FrameTypes.SONG));
        }
    }

    public String getAlbum() {
        return frameList.get(FrameTypes.ALBUM) != null && frameList.get(FrameTypes.ALBUM).getFrameValue() != null
                ? frameList.get(FrameTypes.ALBUM).getFrameValue() : "";
    }

    public void setAlbum(String album) {
        if (frameList.get(FrameTypes.ALBUM) != null) {
            frameList.get(FrameTypes.ALBUM).setFrameValue(album);
            framesToWrite.add(frameList.get(FrameTypes.ALBUM));
        }
    }

    public String getYear() {
        return frameList.get(FrameTypes.YEAR) != null && frameList.get(FrameTypes.YEAR).getFrameValue() != null
                ? frameList.get(FrameTypes.YEAR).getFrameValue() : "";
    }

    public void setYear(String year) {
        if (frameList.get(FrameTypes.YEAR) != null) {
            frameList.get(FrameTypes.YEAR).setFrameValue(year);
            framesToWrite.add(frameList.get(FrameTypes.YEAR));
        }
    }

    public String getGenre() {
        return frameList.get(FrameTypes.GENRE) != null && frameList.get(FrameTypes.GENRE).getFrameValue() != null
                ? frameList.get(FrameTypes.GENRE).getFrameValue() : "";
    }

    public void setGenre(String genre) {
        if (frameList.get(FrameTypes.GENRE) != null) {
            frameList.get(FrameTypes.GENRE).setFrameValue(genre);
            framesToWrite.add(frameList.get(FrameTypes.GENRE));
        }
    }
}
