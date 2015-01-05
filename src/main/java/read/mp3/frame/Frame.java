package read.mp3.frame;

/**
 * Created by BiliaievaTatiana on 12/17/14.
 */
public class Frame {
    private int id3FrameSize;
    private String frameName;
    private String frameValue;
    private int frameSize;
    private int positionInFile;
    private boolean rewrited;

    public Frame(String frameName) {
        this.frameName = frameName;
        this.positionInFile = -1;
    }

    public String getFrameName() {
        return frameName;
    }

    public String getFrameValue() {
        return frameValue;
    }

    public int getFrameSize() {
        return frameSize;
    }

    public int getPositionInFile() {
        return positionInFile;
    }

    public int getId3FrameSize() {
        return id3FrameSize;
    }

    public void setFrameValue(String frameValue) {
        this.frameValue = frameValue;
    }

    public void setId3FrameSize(int id3FrameSize1) {
        this.id3FrameSize = id3FrameSize1;
    }

    public void setPositionInFile(int positionInFile) {
        this.positionInFile = positionInFile;
    }

    public void setFrameSize(int frameSize) {
        this.frameSize = frameSize;
    }

    public boolean isRewrited() {
        return rewrited;
    }

    public void setRewrited(boolean rewrited) {
        this.rewrited = rewrited;
    }
}
