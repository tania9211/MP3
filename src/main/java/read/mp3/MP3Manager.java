package read.mp3;

import read.mp3.frame.Config;
import read.mp3.frame.Frame;
import read.mp3.frame.FrameTypes;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by BiliaievaTatiana on 12/17/14.
 */
public class MP3Manager {

    public MP3Instance read(File file, MP3Instance mp3Instance) {
        try {
            FileInputStream fileInputStream = new FileInputStream(file);
            byte[] headerArray = new byte[Config.HEADER_ARRAY_SIZE];
            fileInputStream.read(headerArray);
            int tagSize = (headerArray[9] & 0xFF) | ((headerArray[8] & 0xFF) << 7) | ((headerArray[7] & 0xFF) << 14) | ((headerArray[6] & 0xFF) << 21);
            tagSize = tagSize + 10;
            byte[] buffer = new byte[tagSize];

            int lenght = buffer.length;
            int pos = 0;
            fileInputStream.read(buffer);
            fileInputStream.close();
            int id3FrameSize = headerArray[3] < Config.OLD_TAG_VERSION ? Config.ID3_FRAME_SIZE_OLD : Config.ID3_FRAME_SIZE_NEW; // if tag version > 3 (temp)
     //       System.out.println("--------------Read----------");
            while (true) {
                int remBytes = lenght - pos;
                if (remBytes < id3FrameSize) {
                    break;
                }
                if (buffer[pos] < 'A' || buffer[pos] > 'Z') {
                    break;
                }

                String frameName;
                int frameSize;
                if (headerArray[3] < Config.OLD_TAG_VERSION) {
                    frameName = new String(buffer, pos, 3);
                    frameSize = ((buffer[pos + 5] & 0xFF) << 8) | ((buffer[pos + 4] & 0xFF) << 16) | ((buffer[pos + 3] & 0xFF) << 24);

                } else {
                    frameName = new String(buffer, pos, 4);
                    frameSize = (buffer[pos + 7] & 0xFF) | ((buffer[pos + 6] & 0xFF) << 8) | ((buffer[pos + 5] & 0xFF) << 16) | ((buffer[pos + 4] & 0xFF) << 24);
                }

                if (frameName.equals(FrameTypes.BAND)) {
                    String name = parseTextField(buffer, pos + id3FrameSize, frameSize);
             //       System.out.println(name + "  " + frameName + "   frameSize " + frameSize + "   pos " + pos);
                    setFrameInfo(mp3Instance.getFrameList().get(FrameTypes.BAND), parseTextField(buffer, pos + id3FrameSize, frameSize), frameSize, id3FrameSize, pos);
                }
                if (frameName.equals(FrameTypes.SONG)) {
                    String name = parseTextField(buffer, pos + id3FrameSize, frameSize);
               //     System.out.println(name + "  " + frameName + "   frameSize " + frameSize + "   pos " + pos);
                    setFrameInfo(mp3Instance.getFrameList().get(FrameTypes.SONG), parseTextField(buffer, pos + id3FrameSize, frameSize), frameSize, id3FrameSize, pos);
                }
                if (frameName.equals(FrameTypes.ALBUM)) {
                    String name = parseTextField(buffer, pos + id3FrameSize, frameSize);
           //         System.out.println(name + "  " + frameName + "   frameSize " + frameSize + "   pos " + pos);
                    setFrameInfo(mp3Instance.getFrameList().get(FrameTypes.ALBUM), parseTextField(buffer, pos + id3FrameSize, frameSize), frameSize, id3FrameSize, pos);
                }
                if (frameName.equals(FrameTypes.YEAR)) {
                    String name = parseTextField(buffer, pos + id3FrameSize, frameSize);
        //            System.out.println(name + "  " + frameName + "   frameSize " + frameSize + "   pos " + pos);
                    setFrameInfo(mp3Instance.getFrameList().get(FrameTypes.YEAR), parseTextField(buffer, pos + id3FrameSize, frameSize), frameSize, id3FrameSize, pos);
                }
                if (frameName.equals(FrameTypes.GENRE)) {
                    String name = parseTextField(buffer, pos + id3FrameSize, frameSize);
         //           System.out.println(name + "  " + frameName + "   frameSize " + frameSize + "   pos " + pos);
                    setFrameInfo(mp3Instance.getFrameList().get(FrameTypes.GENRE), parseTextField(buffer, pos + id3FrameSize, frameSize), frameSize, id3FrameSize, pos);
                }

                if (pos + frameSize > lenght) {
                    break;
                }
                pos += frameSize + id3FrameSize;
                continue;
            }
       //     System.out.println("---------------End Read-----------");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return mp3Instance;
    }

    private String parseTextField(final byte[] buffer, int pos, int size) {
        if (size < 2) return null;
        Charset charset;
        int charcode = buffer[pos];
        if (charcode == 0) charset = Charset.forName("ISO-8859-1");
        else if (charcode == 3) charset = Charset.forName("UTF-8");
        else charset = Charset.forName("UTF-16");
        return charset.decode(ByteBuffer.wrap(buffer, pos + 1, size - 1)).toString();
    }

    private void setFrameInfo(Frame frame, String value, int frameSize, int id3FrameSize, int pos) {
        frame.setFrameValue(value);
        frame.setFrameSize(frameSize);
        frame.setId3FrameSize(id3FrameSize);
        frame.setPositionInFile(pos);
    }

    public void write(File file, MP3Instance mp3Instance) {
        List<Frame> frames = mp3Instance.getFramesToWrite();

        Collections.sort(frames, new Comparator<Frame>() {
            @Override
            public int compare(Frame o1, Frame o2) {
                return o1.getPositionInFile() - o2.getPositionInFile();
            }
        });

        try {
            RandomAccessFile randomAccessFile = new RandomAccessFile(file, "rw");
            byte[] headerArray = new byte[Config.HEADER_ARRAY_SIZE];
            randomAccessFile.read(headerArray);
            int tagSize = (headerArray[9] & 0xFF) | ((headerArray[8] & 0xFF) << 7) | ((headerArray[7] & 0xFF) << 14) | ((headerArray[6] & 0xFF) << 21) + 10;
            byte[] buffer = new byte[tagSize];
            randomAccessFile.read(buffer);
            byte[] anotherSongPart;
            if ((int) randomAccessFile.length() - Config.HEADER_ARRAY_SIZE - tagSize < 0) {
                anotherSongPart = new byte[0];
            } else {
                anotherSongPart = new byte[(int) randomAccessFile.length() - Config.HEADER_ARRAY_SIZE - tagSize];
                randomAccessFile.read(anotherSongPart);
            }

            int lengthDiff = 0;
            for (int i = 0; i < frames.size(); i++) {
                if (frames.get(i).getPositionInFile() >= 0) {
                    lengthDiff = lengthDiff + (frames.get(i).getFrameValue().length() - frames.get(i).getFrameSize() + 1);
                } else {
                    lengthDiff = lengthDiff + frames.get(i).getFrameValue().length() + Config.ENCODE_VALUE_LENHGT
                            + Config.FRAME_FLAGS_LENGHT + Config.FRAME_TYPE_LENGTH + Config.FRAMESIZE_LENGHT;
                }
            }

            int newFileSize = headerArray.length + tagSize + lengthDiff + anotherSongPart.length; // without another part
            byte[] newTagArray = new byte[newFileSize];

            for (int i = 0; i < Config.HEADER_ARRAY_SIZE; i++) {
                newTagArray[i] = headerArray[i];
            }

            int lenght = Config.HEADER_ARRAY_SIZE;

            int diff = 0;

            for (int i = 0; i < frames.size(); i++) {
                if (frames.get(i).getPositionInFile() > -1) {
                    for (int j = lenght, k = 0; j < frames.get(i).getPositionInFile() + frames.get(i).getId3FrameSize() + Config.HEADER_ARRAY_SIZE + diff; j++, k++) {
                        newTagArray[j] = buffer[lenght - diff - Config.HEADER_ARRAY_SIZE + k];
                    }

                    changeFrameSize(newTagArray, frames.get(i).getFrameValue(), frames.get(i).getPositionInFile() + diff);

                    lenght = frames.get(i).getPositionInFile() + frames.get(i).getId3FrameSize() + Config.HEADER_ARRAY_SIZE + diff;
                    diff = diff + frames.get(i).getFrameValue().length() - frames.get(i).getFrameSize() + 1;

                    int d = (frames.get(i).getPositionInFile() + diff);

                    //        System.out.println(frames.get(i).getFrameValue() + "  pos  " + d + " size " + frames.get(i).getFrameSize() + "  len " + (lenght - 20));
                    setFrameInfo(frames.get(i), frames.get(i).getFrameValue(), frames.get(i).getFrameValue().length() + 1, frames.get(i).getId3FrameSize(), lenght - Config.HEADER_ARRAY_SIZE - Config.ID3_FRAME_SIZE_NEW);

                    lenght++;
                    byte[] valueArray = frames.get(i).getFrameValue().getBytes();
                    for (int j = lenght, k = 0; j < lenght + valueArray.length; j++, k++) {
                        newTagArray[j] = valueArray[k];
                    }
                    lenght = lenght + valueArray.length;
                }
            }

            for (int i = 0; i < frames.size(); i++) {
                if (frames.get(i).getPositionInFile() < 0) {
                    byte[] frameTypeArray = frames.get(i).getFrameName().getBytes();
                    byte[] frameSizeArray = toByteArray(frames.get(i).getFrameValue().length() + 1);
                    byte[] frameValueArray = frames.get(i).getFrameValue().getBytes();

                    for (int j = lenght, k = 0; j < lenght + frameTypeArray.length; j++, k++) {
                        newTagArray[j] = frameTypeArray[k];
                    }
                    lenght = lenght + frameTypeArray.length;

                    for (int j = lenght, k = 0; j < lenght + frameSizeArray.length; j++, k++) {
                        newTagArray[j] = frameSizeArray[k];
                    }
                    lenght = lenght + frameSizeArray.length;

                    for (int j = lenght; j < lenght + Config.FRAME_FLAGS_LENGHT + 1; j++) {
                        newTagArray[j] = 0;
                    }

                    lenght = lenght + Config.FRAME_FLAGS_LENGHT + 1;
                    //    System.out.println(" < 0" + frames.get(i).getFrameValue() + "  pos  " + (lenght - 10));
                    setFrameInfo(frames.get(i), frames.get(i).getFrameValue(), frames.get(i).getFrameValue().length() + 1, Config.ID3_FRAME_SIZE_NEW, lenght - Config.HEADER_ARRAY_SIZE - Config.ID3_FRAME_SIZE_NEW - 1);

                    for (int j = lenght, k = 0; j < lenght + frameValueArray.length; j++, k++) {
                        newTagArray[j] = frameValueArray[k];
                    }
                    lenght = lenght + frameValueArray.length;
                }
            }

            for (int j = lenght; j < tagSize + diff; j++) {
                newTagArray[j] = buffer[j - diff - Config.HEADER_ARRAY_SIZE];
            }
            lenght = tagSize + diff;


            for (int i = lenght, k = 0; i < lenght + anotherSongPart.length; i++, k++) {
                newTagArray[i] = anotherSongPart[k];
            }

            tagSize = tagSize + lengthDiff;
            changeTagSize(newTagArray, tagSize);
            randomAccessFile.seek(0);

            randomAccessFile.write(newTagArray);
            mp3Instance.clearFramesToWrite();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void changeFrameSize(byte[] newTagArray, String value, int position) {
        byte[] valueToBytes = toByteArray(value.length() + 1);
        final int beginPosTag = 4;

        for (int i = 0; i < valueToBytes.length; i++) {
            newTagArray[position + beginPosTag + Config.HEADER_ARRAY_SIZE + i] = valueToBytes[i];
        }
    }


    private void changeTagSize(byte[] array, int tagSize) {
        byte[] sizeBytes = toByteArray(tagSize - 10);
        final int tagSizeBeginPosition = 6;

        for (int i = 0; i < sizeBytes.length; i++) {
            array[tagSizeBeginPosition + i] = sizeBytes[i];
        }
    }

    private byte[] toByteArray(int value) {
        return new byte[]{
                (byte) ((value >> 21) & 0xFF),
                (byte) ((value >> 14) & 0xFF),
                (byte) ((value >> 7) & 0xFF),
                (byte) ((value) & 0xFF)};
    }
}
