package read.traverse;

import read.mp3.MP3Instance;
import read.mp3.MP3Manager;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

/**
 * Created by BiliaievaTatiana on 12/29/14.
 */
public class FolderTraversal {
    private final String fileFornat = "audio/mpeg";

    public void traversal(String path) {
        recursive(new File(path));
    }

    private void recursive(File file) {
        MP3Manager mp3Manager = new MP3Manager();
        try {
            if (fileFornat.equals(Files.probeContentType(file.toPath()))) {
                MP3Instance mp3Instance = new MP3Instance();
                mp3Manager.read(file, mp3Instance);
                System.out.println(mp3Instance.getSongName() + "  " + mp3Instance.getBand());
            }

            if (file.isDirectory()) {
                String[] subnode = file.list();

                for (int i = 0; i < subnode.length; i++) {
                    recursive(new File(file, subnode[i]));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
