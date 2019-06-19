package org.rhythmcatchball.core;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map.Entry;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.sound.sampled.FloatControl;

public class GameSound {
	private static HashMap<String, Clip> sounds;

	private GameSound() {
		 throw new IllegalStateException("Utility class");
	}
	public static boolean loadSounds() {
		AudioInputStream stream;
		Clip clip;
		File file = new File("sound/asset.txt");
		boolean result = false;
		// 파일 directory 바꿔줘야함

		FileReader filereader = null;
		BufferedReader bufferedreader = null;

		sounds = new HashMap<>();
		try {
			filereader = new FileReader(file);
			bufferedreader = new BufferedReader(filereader);

			while (true) {
				String filename = bufferedreader.readLine();
				if (filename == null)
					break;
				file = new File("sound/" + filename + ".wav");
				stream = AudioSystem.getAudioInputStream(file);

				clip = AudioSystem.getClip();
				clip.open(stream);
				sounds.put(filename, clip);
			}
			// 파일로부터 파일명 읽어서 그대로 키값으로 활용, 해쉬맵에 추가
			result = true;
		} catch (IOException|UnsupportedAudioFileException|LineUnavailableException e) {
			e.printStackTrace();
		} finally {
			try {
				if (filereader != null)
					filereader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			try {
				if (bufferedreader != null)
					bufferedreader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return result;// 성공시 true
	}

	public static void play(String key) {
		if (sounds.containsKey(key)) {
			Clip clip = sounds.get(key);
			if (clip != null) {
				clip.setFramePosition(0);
				clip.start();
			}
		}
	}

	public static void play(String key, float volume) {
		if (sounds.containsKey(key)) {
			Clip clip = sounds.get(key);
			if (clip != null) {
				FloatControl volumeCtrl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
				volumeCtrl.setValue(translate(volume));
				clip.setFramePosition(0);
				clip.start();
			}
		} 
	}

	public static void updateVolume() {

		float volume = translate(GameManager.getref().getVolume());

		if (sounds != null) {
			Clip clip;
			FloatControl volumeCtrl;
			for (Entry<String, Clip> entry : sounds.entrySet()) {
				String key = entry.getKey();
				clip = sounds.get(key);
				clip.stop();
				clip.setFramePosition(0);
				volumeCtrl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
				volumeCtrl.setValue(volume);
			}
		}
	}

	private static float translate(float setting) {
		float minvol = -80F;
		float volume;
		if (setting > 1)
		{
			setting = 1;
		}
		volume = minvol * (1 - setting) / 2 + 5;
		volume = Math.min(volume, 5);
		return volume;
	}
}
