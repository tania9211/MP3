package read;

import java.applet.Applet;
import java.awt.Panel;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import read.mp3.MP3ManagerImpl;
import read.mp3.MP3Properties;
import read.mp3.frame.FrameTypes;
import read.traverse.FolderTraversal;

import javax.swing.JFileChooser;
import javax.swing.SwingUtilities;
import javax.swing.filechooser.FileNameExtensionFilter;

public class StartApplet extends Applet {
	private List<File> fileList;
	private JFileChooser jFileChooser;

	final private Logger logger = LoggerFactory.getLogger(StartApplet.class);

	public void init() {
		fileList = new ArrayList<File>();

		jFileChooser = new JFileChooser();
		FileNameExtensionFilter filter = new FileNameExtensionFilter(
				"JPG & GIF Images", "mp3");
		jFileChooser.setFileFilter(filter);
		jFileChooser.setMultiSelectionEnabled(true);
		jFileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
	}

	public void popUpFileChooser() {
		try {
			SwingUtilities.invokeAndWait(new Runnable() {

				@Override
				public void run() {
					logger.debug("popUpFileChooser invoked");
					int result = jFileChooser.showOpenDialog(new Panel());
					if (result == JFileChooser.APPROVE_OPTION) {
						getFileList(jFileChooser);
					}
				}
			});
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}

	private void getFileList(JFileChooser jFileChooser) {
		try {
			if (fileList.size() > 0) {
				fileList.clear();
			}

			final FolderTraversal folderTraversal = new FolderTraversal();
			final File[] files = jFileChooser.getSelectedFiles();

			for (int i = 0; i < files.length; i++) {
				if (files[i].isDirectory()) {
					fileList.addAll(folderTraversal.traversal(files[i]
							.getPath()));
				} else {
					fileList.add(files[i]);
				}
			}

		} catch (Throwable e) {
			e.printStackTrace();
		}
	}

	public JSONArray getFilePath() {
		JSONArray jsonArray = new JSONArray();

		logger.debug("JSONArray getFilePath invoked");

		for (int i = 0; i < fileList.size(); i++) {
			JSONObject jsonObject = new JSONObject();

			//logger.debug("put " + fileList.get(i).getAbsolutePath());

			jsonObject.put("name", fileList.get(i).getName());
			jsonObject.put("fullPath", fileList.get(i).getAbsolutePath());

			jsonArray.add(jsonObject);
		}
		return jsonArray;
	}

	public JSONObject getSongInfo(String sonfPath) {
		try {
			JSONObject jsonObject = new JSONObject();

			MP3ManagerImpl mp3Manager = new MP3ManagerImpl();
			MP3Properties mp3Properties = mp3Manager.read(new File(sonfPath));

			jsonObject.put(FrameTypes.GENRE, mp3Properties.getGenre());
			jsonObject.put(FrameTypes.ALBUM, mp3Properties.getAlbum());
			jsonObject.put(FrameTypes.SONG, mp3Properties.getName());
			jsonObject.put(FrameTypes.BAND, mp3Properties.getBand());
			jsonObject.put(FrameTypes.YEAR, mp3Properties.getYear());

			logger.debug("JSONObject getSongInfo invoked");

			return jsonObject;
		} catch (Throwable e) {
			e.printStackTrace();
		}
		return null;
	}

	public void setSongInformation(String songInfo) {
		try {
			JSONParser jsonParser = new JSONParser();
			JSONArray jsonArray = (JSONArray) jsonParser.parse(songInfo);

			for (int i = 0; i < jsonArray.size(); i++) {
				JSONObject jsonObject = (JSONObject) jsonArray.get(i);
				String columnValue = (String) jsonObject.get("columnValue");
				String columnType = (String) jsonObject.get("columnType");
				logger.debug("getInfo " + columnValue + "  !!     " + columnType);
			}

			logger.debug("setSongInformation invoked");
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}

}
