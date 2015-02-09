package read.traverse;

import read.mp3.MP3Instance;
import read.mp3.MP3Manager12;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by BiliaievaTatiana on 12/29/14.
 */
public class FolderTraversal {
	private List<File> fileList;
	private Logger logger = LoggerFactory.getLogger(FolderTraversal.class);

	public List<File> traversal(String path) {
		fileList = new ArrayList<File>();

		recursive(new File(path));

		return fileList;
	}

	private void recursive(File file) {
		logger.debug(file.getPath());
		if (file.getName().indexOf("mp3") > 0) {
			fileList.add(file);
		}

		if (file.isDirectory()) {
			String[] subnode = file.list();

			for (int i = 0; i < subnode.length; i++) {
				recursive(new File(file, subnode[i]));
			}
		}
	}

}
