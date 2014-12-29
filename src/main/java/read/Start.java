package read;

import read.mp3.MP3Instance;
import read.mp3.MP3Manager;
import read.traverse.FolderTraversal;

import java.io.File;

/**
 * Created by BiliaievaTatiana on 12/17/14.
 */
public class Start {
    public static void main(String[] args) {
        MP3Manager mp3Manager = new MP3Manager();
        MP3Instance mp3Instance = new MP3Instance();
        File file = new File("C:/Users/biliaievaTatiana/Downloads/01 Bye Bye Beautiful (Live).mp3");

        FolderTraversal folderTraversal = new FolderTraversal();
        folderTraversal.traversal("C:\\Users\\biliaievaTatiana\\Downloads");
    }
}
