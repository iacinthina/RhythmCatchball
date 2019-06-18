package org.rhythmcatchball.core;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.Control;
import javax.sound.sampled.LineListener;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.sound.sampled.Control.Type;
import javax.sound.sampled.Line.Info;
import javax.swing.ImageIcon;

public class GameSound {
	private static HashMap<String, Clip> sounds; //싱글톤 패턴, 모든 GameSprite마다 존재하면 안됨
	
	public static boolean loadSounds() {
		/*
		 * 파일을 돌아가며 설정. Image[] sprite에 넣는다.
		 */
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
				System.out.println("clipFramePosition"+clip.getFramePosition());
				clip.setFramePosition(0);
				clip.start();
				/*if (!clip.isRunning()) {
					sounds.get("snd_beat").loop(1);
					System.out.println("retry running");
					//clip.loop(1);
				}*/
			}
			/*clip.stop();
			clip.drain();
			//clip.loop(1);
			clip.start();
			*/
			//AudioInputStream stream = sounds.get(key);
		} else
			System.out.println("cannot found soundfile : "+key);
	}
}
