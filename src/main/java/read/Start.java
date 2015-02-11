package read;

import read.mp3.MP3ManagerImpl;
import read.mp3.MP3Properties;
import read.mp3.frame.FrameTypes;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by BiliaievaTatiana on 12/17/14.
 */
public class Start {
	public static void main(String[] args) {
		final Logger logger = LoggerFactory.getLogger(StartApplet.class);

		try {
			long nanoTime = System.nanoTime();
			long time = System.currentTimeMillis();
			int amount = 100;
			for (int i = 0; i < amount; i++) {
				JSONObject jsonObject = new JSONObject(
						new HashMap<String, String>());

				MP3ManagerImpl mp3Manager = new MP3ManagerImpl();
				MP3Properties mp3Properties = mp3Manager
						.read(new File(
								"/Users/tatianaBeliaieva/Desktop/music/testFolder/Обнимай (zaycev.net).mp3"));

				jsonObject.put(FrameTypes.GENRE, mp3Properties.getGenre());
				jsonObject.put(FrameTypes.ALBUM, mp3Properties.getAlbum());
				jsonObject.put(FrameTypes.SONG, mp3Properties.getName());
				jsonObject.put(FrameTypes.BAND, mp3Properties.getBand());
				jsonObject.put(FrameTypes.YEAR, mp3Properties.getYear());
			}

			nanoTime = System.nanoTime() - nanoTime;
			time = System.currentTimeMillis() - time;

			logger.error("simple.Json result !  nanoTime: " + nanoTime
					+ " time: " + time);

			nanoTime = System.nanoTime();
			time = System.currentTimeMillis();
			for (int i = 0; i < amount; i++) {
				net.minidev.json.JSONObject jsonObject = new net.minidev.json.JSONObject();
				MP3ManagerImpl mp3Manager = new MP3ManagerImpl();
				MP3Properties mp3Properties = mp3Manager
						.read(new File(
								"/Users/tatianaBeliaieva/Desktop/music/testFolder/Обнимай (zaycev.net).mp3"));

				jsonObject.put(FrameTypes.GENRE, mp3Properties.getGenre());
				jsonObject.put(FrameTypes.ALBUM, mp3Properties.getAlbum());
				jsonObject.put(FrameTypes.SONG, mp3Properties.getName());
				jsonObject.put(FrameTypes.BAND, mp3Properties.getBand());
				jsonObject.put(FrameTypes.YEAR, mp3Properties.getYear());
			}

			nanoTime = System.nanoTime() - nanoTime;
			time = System.currentTimeMillis() - time;

			logger.error("smart.Json result !  nanoTime: " + nanoTime
					+ " time: " + time);

			nanoTime = System.nanoTime();
			time = System.currentTimeMillis();

			for (int i = 0; i < amount; i++) {
				MP3ManagerImpl mp3Manager = new MP3ManagerImpl();
				MP3Properties mp3Properties = mp3Manager
						.read(new File(
								"/Users/tatianaBeliaieva/Desktop/music/testFolder/Обнимай (zaycev.net).mp3"));
				mp3Properties.toJSONString();
			}

			nanoTime = System.nanoTime() - nanoTime;
			time = System.currentTimeMillis() - time;

			logger.error("String method result  !  nanoTime: " + nanoTime
					+ " time: " + time);

			nanoTime = System.nanoTime();
			time = System.currentTimeMillis();

			for (int i = 0; i < amount; i++) {
				MP3ManagerImpl mp3Manager = new MP3ManagerImpl();
				MP3Properties mp3Properties = mp3Manager
						.read(new File(
								"/Users/tatianaBeliaieva/Desktop/music/testFolder/Обнимай (zaycev.net).mp3"));
				mp3Properties.toJSONStringBuilder();
			}

			nanoTime = System.nanoTime() - nanoTime;
			time = System.currentTimeMillis() - time;

			logger.error("StringBuilder method result  !  nanoTime: "
					+ nanoTime + " time: " + time);

			nanoTime = System.nanoTime();
			time = System.currentTimeMillis();

			for (int i = 0; i < amount; i++) {
				MP3ManagerImpl mp3Manager = new MP3ManagerImpl();
				MP3Properties mp3Properties = mp3Manager
						.read(new File(
								"/Users/tatianaBeliaieva/Desktop/music/testFolder/Обнимай (zaycev.net).mp3"));
				mp3Properties.toJSONStringBuffer();
			}

			nanoTime = System.nanoTime() - nanoTime;
			time = System.currentTimeMillis() - time;

			logger.error("StringBuffer method result  !  nanoTime: " + nanoTime
					+ " time: " + time);

			
			JSONArray jsonArray = new JSONArray();
			
			//	net.minidev.json.JSONObject jsonObject = new net.minidev.json.JSONObject();

				logger.debug("JSONArray getFilePath invoked");

				for (int i = 0; i < 2; i++) {
					JSONObject jsonObject = new JSONObject();

					jsonObject.put("name", "This is name");
					jsonObject.put("fullPath", "This is path");

					jsonArray.add(jsonObject);
				}
				System.out.println(jsonArray);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}
