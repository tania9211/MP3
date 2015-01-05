package read;

import read.mp3.MP3ManagerImpl;
import read.mp3.MP3Properties;

import java.io.File;
import java.io.IOException;

/**
 * Created by BiliaievaTatiana on 12/17/14.
 */
public class Start {
    public static void main(String[] args) {
      /*  MP3Manager12 mp3Manager12 = new MP3Manager12();
        MP3Instance mp3Instance = new MP3Instance();
        File file = new File("C:/Users/biliaievaTatiana/Downloads/01 Bye Bye Beautiful (Live).mp3");

        FolderTraversal folderTraversal = new FolderTraversal();
        folderTraversal.traversal("C:\\Users\\biliaievaTatiana\\Downloads");*/

        try {
            MP3ManagerImpl mp3Manager = new MP3ManagerImpl();
            MP3Properties mp3Properties = mp3Manager.read(new File("C:/Users/biliaievaTatiana/Downloads/khanna_-_luchshe_net_(zaycev.net).mp3"));

            System.out.println(mp3Properties.getBand());
            System.out.println(mp3Properties.getAlbum());
            System.out.println(mp3Properties.getYear());
            System.out.println(mp3Properties.getGenre());
            System.out.println(mp3Properties.getName());

            mp3Properties.setYear("2004");
            mp3Properties.setName("SongName");
            mp3Properties.setAlbum("Album");
            mp3Properties.setYear("5678");
            mp3Properties.setBand("Tania band");
            mp3Properties.setGenre("Pop");

            mp3Manager.write(mp3Properties);

            MP3Properties mp3Properties1 = mp3Manager.read(new File("C:/Users/biliaievaTatiana/Downloads/khanna_-_luchshe_net_(zaycev.net).mp3"));

            System.out.println(mp3Properties1.getBand());
            System.out.println(mp3Properties1.getAlbum());
            System.out.println(mp3Properties1.getYear());
            System.out.println(mp3Properties1.getGenre());
            System.out.println(mp3Properties1.getName());
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
