package read;

import java.applet.Applet;
import java.awt.Panel;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JFileChooser;
import javax.swing.SwingUtilities;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import read.mp3.MP3ManagerImpl;
import read.mp3.MP3Properties;
import read.mp3.frame.Config;
import read.mp3.frame.FrameTypes;
import read.traverse.FolderTraversal;

public class StartApplet extends Applet {
	private List<File> fileList;
	private JFileChooser jFileChooser;
	private Map<String, MP3Properties> readSongMap;

	final private Logger logger = LoggerFactory.getLogger(StartApplet.class);

	public void init() {
		fileList = new ArrayList<File>();
		readSongMap = new HashMap<String, MP3Properties>();

		jFileChooser = new JFileChooser();
		FileNameExtensionFilter filter = new FileNameExtensionFilter(
				"JPG & GIF Images", Config.MP3_FORMAT);
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

	public String getFilePath() {
		final StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("[");

		for (int i = 0; i < fileList.size(); i++) {
			stringBuilder.append("{ \"");
			stringBuilder.append(Config.SONG_NAME);
			stringBuilder.append("\":\"");
			stringBuilder.append(fileList.get(i).getName());
			stringBuilder.append("\",\"");
			stringBuilder.append(Config.SONG_PATH);
			stringBuilder.append("\":\"");
			stringBuilder.append(fileList.get(i).getAbsolutePath());
			stringBuilder.append("\"}");
			if (i < fileList.size() - 1) {
				stringBuilder.append(",");
			}
		}
		stringBuilder.append("]");
		return stringBuilder.toString();
	}

	public String getSongInfo(String sonfPath) {
		try {
			final MP3ManagerImpl mp3Manager = new MP3ManagerImpl();
			final MP3Properties mp3Properties = mp3Manager.read(new File(
					sonfPath));

			final String songInfoJson = mp3Properties.toJSONString();

			if (readSongMap.get(sonfPath) == null) {
				readSongMap.put(sonfPath, mp3Properties);
			}

			logger.debug("Send json to javascript " + songInfoJson);
			return songInfoJson;
		} catch (Throwable e) {
			e.printStackTrace();
		}
		return null;
	}

	public void setSongInformation(String songPath, String songInfo) {
		try {
			logger.debug("setSongInformation " + songPath + "  " + songInfo);
			final MP3ManagerImpl mp3Manager = new MP3ManagerImpl();
			final MP3Properties mp3Properties = readSongMap.get(songPath);
			final Map<String, String> map = parseJson(songInfo);
			
		/*	if (!mp3Properties.getName().equals(map.get(FrameTypes.SONG))) {
				mp3Properties.setName(map.get(FrameTypes.SONG));
			}	
			if (!mp3Properties.getAlbum().equals(map.get(FrameTypes.ALBUM))) {
				mp3Properties.setAlbum(map.get(FrameTypes.ALBUM));
			}		
			if (!mp3Properties.getBand().equals(map.get(FrameTypes.BAND))) {
				mp3Properties.setBand(map.get(FrameTypes.BAND));
			}
			if (!mp3Properties.getGenre().equals(map.get(FrameTypes.GENRE))) {
				mp3Properties.setGenre(map.get(FrameTypes.GENRE));
			}
			if (!mp3Properties.getYear().equals(map.get(FrameTypes.YEAR))) {
				mp3Properties.setYear(map.get(FrameTypes.YEAR));
			}
			
			mp3Manager.write(mp3Properties);*/

			logger.error("Name  "
					+ mp3Properties.getName().equals(map.get(FrameTypes.SONG))
					+ mp3Properties.getName() + "  " + map.get(FrameTypes.SONG));

			logger.error("ALBUM  "
					+ mp3Properties.getAlbum()
							.equals(map.get(FrameTypes.ALBUM))
					+ mp3Properties.getAlbum() + "  "
					+ map.get(FrameTypes.ALBUM));

			logger.error("BAND  "
					+ mp3Properties.getBand().equals(map.get(FrameTypes.BAND))
					+ mp3Properties.getBand() + "  " + map.get(FrameTypes.BAND));

			logger.error("YEAR  "
					+ mp3Properties.getYear().equals(map.get(FrameTypes.YEAR))
					+ mp3Properties.getYear() + "  " + map.get(FrameTypes.YEAR));

			logger.error("GENRE  "
					+ mp3Properties.getGenre()
							.equals(map.get(FrameTypes.GENRE))
					+ mp3Properties.getGenre() + "  "
					+ map.get(FrameTypes.GENRE));

		} catch (Throwable e) {
			e.printStackTrace();
		}
	}

	private Map<String, String> parseJson(String json) {
		final Map<String, String> map = new HashMap<String, String>();
		logger.debug("Get json: " + json);
		final String[] songInfoArray = json.substring(1, json.length() - 1)
				.split("}");

		for (int i = 0; i < songInfoArray.length; i++) {
			final String[] columnInfoArray = songInfoArray[i].substring(2,
					songInfoArray[i].length()).split(",");
			String type = null;
			String value = null;
			for (int j = 0; j < columnInfoArray.length; j++) {
				final String[] columnValueArray = columnInfoArray[j].split(":");
				if (columnValueArray[0].substring(1,
						columnValueArray[0].length() - 1).equals(Config.COLUMN_TYPE)) {
					type = columnValueArray[1].substring(1,
							columnValueArray[1].length() - 1);
				}
				if (columnValueArray[0].substring(1,
						columnValueArray[0].length() - 1).equals(Config.COLUMN_VALUE)) {
					value = columnValueArray[1].substring(1,
							columnValueArray[1].length() - 1);
				}
			}
			map.put(type, value);
		}
		return map;
	}

}
