package org.rhythmcatchball.core;

import java.awt.Image;
import java.awt.image.ImageObserver;
import java.util.HashMap;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;

import javax.swing.ImageIcon;

/**
 * GameSprite.java
 * @author pc1
 * @date   2019. 5. 14.
 * 게임 스프라이트 로더, 내부에 서브클래스가 필요함
 */
public class GameSprite {
	private static HashMap<String, GameSprite> sprites; //싱글톤 패턴, 모든 GameSprite마다 존재하면 안됨
	
	
	int xoffset;
	int yoffset;
	Image img;
	
	public GameSprite(int xoffset, int yoffset, Image img) {
		this.xoffset = xoffset;
		this.yoffset = yoffset;
		this.img = img;
	}
	
	public int getxoff() {return xoffset;}
	public int getyoff() {return yoffset;}
	public Image getImage() {return img;}
	
	/**
	 * purpose : 
	 * mechanism : 
	 * comment : 
	 * @throws IOException 
	 */
	public static boolean loadImages(ImageObserver arg0) {
		/*
		 * 파일을 돌아가며 설정. Image[] sprite에 넣는다.
		 */
		Image loadimg;
		GameSprite spr;
		File file = new File("sprites/spritesName.txt");
		boolean result = false;
		//파일 directory 바꿔줘야함
		
		FileReader filereader;
		
		sprites = new HashMap<String, GameSprite>();
		try {
			filereader = new FileReader(file);
		
			BufferedReader bufferedreader = new BufferedReader(filereader);
			
			while(true)
			{
				String filename = bufferedreader.readLine();
				if(filename == null)
					break;
				loadimg = new ImageIcon("sprites/"+filename + ".png").getImage();//이미지 파일 로드시 오류처리? 필요?
				spr = new GameSprite(loadimg.getWidth(arg0)/2, loadimg.getHeight(arg0)/2, loadimg);
				System.out.println("spr = "+spr+" fname = "+filename);
				sprites.put(filename, spr);
			}
			//파일로부터 파일명 읽어서 그대로 키값으로 활용, 해쉬맵에 추가 
			result = true;
			bufferedreader.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;//성공시 true
	}
	
	/**
	 * purpose : 키에 따라 HashMap sprites에서 GameSprite를 불러오기
	 * mechanism : 
	 * comment : 
	 */
	//해당 키값에 대해 해쉬맵에 value존재하는지 확인, 있다면 sprite값 리턴, 없으면 null 리턴 
	public static GameSprite get(String key) {
		GameSprite spr = null;
		
		spr = sprites.get(key);
		
		if(!sprites.containsKey(key)) {
			return null;
		}
		
		return spr;
	}
}
