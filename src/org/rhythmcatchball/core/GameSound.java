package org.rhythmcatchball.core;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.sound.sampled.FloatControl;

public class GameSound {
	private static HashMap<String, Clip> sounds;
	
	public static boolean loadSounds() {
		AudioInputStream stream;
		Clip clip;
		File file = new File("sound/asset.txt");
		boolean result = false;
		//파일 directory 바꿔줘야함
		
		FileReader filereader = null;
		BufferedReader bufferedreader = null;
		
		sounds = new HashMap<>();
		try {
			filereader = new FileReader(file);
			bufferedreader = new BufferedReader(filereader);
			
			while(true)
			{
				String filename = bufferedreader.readLine();
				if(filename == null)
					break;
				file = new File("sound/"+filename+".wav");
				stream = AudioSystem.getAudioInputStream(file);
				
	            clip = AudioSystem.getClip();
	            clip.open(stream);
	            //clip.start();
	            //System.out.println("clip = "+clip+" fname = "+filename);
				sounds.put(filename, clip);
	            //sounds.put(filename, stream);
			}
			//파일로부터 파일명 읽어서 그대로 키값으로 활용, 해쉬맵에 추가 
			result = true;
		} catch (FileNotFoundException e) {
			System.out.println("file does not exists");
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (UnsupportedAudioFileException e) {
			e.printStackTrace();
		} catch (LineUnavailableException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if(filereader != null)
					filereader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			try {
				if(bufferedreader != null)
					bufferedreader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return result;//성공시 true
	}
	
	public static void play(String key) {
		if (sounds.containsKey(key)) {
			Clip clip = sounds.get(key);
			if (clip != null) {
		        clip.setFramePosition(0);
				clip.start();
				/*if (!clip.isRunning()) {
					sounds.get("snd_beat").loop(1);
					System.out.println("retry running");
					//clip.loop(1);
				}*/
			}
		} else
			System.out.println("cannot found soundfile : "+key);
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
		} else
			System.out.println("cannot found soundfile : "+key);
	}
	
	public static void updateVolume() {
        
		float volume = translate(GameManager.getref().getVolume());
		
        if (sounds != null) {
			Clip clip;
	        FloatControl volumeCtrl;
			for(String key : sounds.keySet()) {
				clip = sounds.get(key);
				clip.stop();
		        clip.setFramePosition(0);
		        volumeCtrl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
		        volumeCtrl.setValue(volume);
			}
        }
	}
	
	private static float translate(float setting) {
		float MINVOL = -80F;
		float volume;
		if (setting > 1) setting = 1;
		if (setting <= 0) volume = MINVOL;
        volume = MINVOL*(1-setting)/2 + 5;
        volume = Math.min(volume, 5);
        return volume;
	}
}
