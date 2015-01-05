import org.junit.Test;
import read.mp3.MP3Instance;
import read.mp3.MP3Manager12;
import read.mp3.frame.Config;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

/**
 * Created by BiliaievaTatiana on 12/26/14.
 */
public class TestReadWrite {
    private byte[] toByteArray(int value) {
        return new byte[]{
                (byte) (value >> 21),
                (byte) (value >> 14),
                (byte) (value >> 7),
                (byte) value};
    }

    private byte[] setHeaderArray() {
        byte[] headerArray = new byte[Config.HEADER_ARRAY_SIZE];
        byte[] id3Bytes = "ID3".getBytes();
        //set header name
        for (int i = 0; i < id3Bytes.length; i++) {
            headerArray[i] = id3Bytes[i];
        }
        //set version
        headerArray[3] = 4;

        //set flags
        headerArray[4] = 0;
        headerArray[5] = 0;

        //set tag size
        byte[] tagSizeArray = toByteArray(0);
        for (int i = 0; i < tagSizeArray.length; i++) {
            headerArray[i + 6] = tagSizeArray[i];
        }

        return headerArray;
    }

    private File initSong() {
        File file = new File("C:/Users/biliaievaTatiana/Downloads/testFile.mp3");

        try {
            RandomAccessFile randomAccessFile = new RandomAccessFile(file, "rw");
            byte[] headerArray = setHeaderArray();

            randomAccessFile.write(headerArray);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return file;
    }

    @Test
    public void testWriteInfo() {
        File file = initSong();
        MP3Manager12 mp3Manager12 = new MP3Manager12();
        MP3Instance mp3Instance = new MP3Instance();

        mp3Manager12.read(file, mp3Instance);

        mp3Instance.setGenre("Pop");
        mp3Instance.setYear("3080");
        mp3Instance.setAlbum("My album");
        mp3Instance.setSongName("Tania song");
        mp3Instance.setBand("My band");

        mp3Manager12.write(file, mp3Instance);

        mp3Instance.setSongName("Tania1");
        mp3Instance.setBand("TaniaBand");

        mp3Manager12.write(file, mp3Instance);
        mp3Manager12.read(file, mp3Instance);
    }

    @Test
    public void testInfo() {
        File file = initSong();
        MP3Manager12 mp3Manager12 = new MP3Manager12();
        MP3Instance mp3Instance = new MP3Instance();

     //   mp3Manager12.read(file, mp3Instance);

        mp3Instance.setGenre("Pop");
        mp3Instance.setYear("3080");
        mp3Instance.setAlbum("My album");
        mp3Instance.setSongName("Tania song");
        mp3Instance.setBand("My band");

        mp3Manager12.write(file, mp3Instance);
        mp3Manager12.read(file, mp3Instance);
    }
}
