package org.rhythmcatchball.core;
import java.awt.Graphics;
import java.awt.Image;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.*;

import org.rhythmcatchball.gameplay.FloatMessage;
import org.rhythmcatchball.gameplay.GameObj;

/**
 * GameManager.java
 * @author pc1
 * @date   2019. 5. 10.
 * 게임 총괄. 시작부터 끝까지 항상 존재해야 한다.
 */

@SuppressWarnings("serial")
public class GameManager extends JFrame {
	private static GameManager singleton;
	public static GameManager getref() {
		if (singleton == null)
			singleton = new GameManager();
		return singleton;
	} //싱글톤 디자인 패턴
	
	public Graphics buffg;
	public Image buffImage;
	private ArrayList<GameObj> gameInst; //게임 진행중에 활성화된 오브젝트는 전부 여기로 들어간다.
	public int modeBeatrate;
	public int modeTimeLimit;
	
	/**
	 * 생성자
	 * @throws IOException 
	 */
	
	private GameManager() {
		gameInst = new ArrayList<GameObj>();
		buffg = null;
		buffImage = null;
	}
	
	/**
	 * purpose : gui에 이미지 그리기
	 * mechanism : gameInst를 돌아가면서 스프라이트 그림
	 * comment : Draw에서 그릴거 다그려둠.
	 */
	public void draw() {
		GameSprite spr = null; 
		for(GameObj o : gameInst) {
			System.out.println(o);
			if (o.getVisible()) { //보여야 그릴 수 있다.(?)
				spr = GameSprite.get(o.getSpriteKey());

				System.out.println(spr);
				System.out.println(o.getSpriteKey());
				
				if(spr==null) continue;
				System.out.println(spr.xoffset);
				System.out.println(spr.yoffset);
				System.out.println(spr.img);
				System.out.println(spr.getxoff());
				System.out.println(spr.getyoff());
				System.out.println(spr.getImage());
				
				
				buffg.drawImage(spr.getImage(), (int)o.xpos-spr.getxoff(), (int)o.ypos-spr.getyoff(), this);
				
				buffg.drawLine((int)o.xpos, (int)o.ypos - 30, (int)o.xpos, (int)o.ypos + 30);
				
				buffg.drawLine((int)o.xpos - 30, (int)o.ypos, (int)o.xpos + 30, (int)o.ypos);

			}
		}
	}
	
	
	/**
	 * purpose : 1프레임 진행
	 * mechanism : gameInst 내의 모든 GameObj의 Update를 실행한다. isAlive()가 false면 리스트에서 제거한다
	 * comment : 
	 */
	public void Update() {
		for(GameObj o : gameInst) {
			System.out.println(o);
			if (o.isAlive()) { //보여야 그릴 수 있다.(?)
				o.update();
			}
		}
	}
	
	public void paint(Graphics g){
		int f_width = 640;
		int f_height = 360;
		buffImage = createImage(f_width, f_height); 
		buffg = buffImage.getGraphics();
		
		/*GameSprite spr = null;
		int xpos = 0;
		int ypos = 0;
		for(int i=0; i<11; i++) {
			spr = GameSprite.get("spr_message_"+i);
			if (spr == null) continue;
			xpos = 40+i*40;//-spr.img.getWidth(this)/2;
			ypos = 30+i*30;//-spr.img.getHeight(this)/2;
			buffg.drawImage(spr.img, xpos-spr.xoffset, ypos-spr.yoffset, this);
			//System.out.println("is same? "+spr.xoffset+" ?= "+spr.img.getWidth(this)/2);
			//System.out.println("is same? "+spr.yoffset+" ?= "+spr.img.getHeight(this)/2);
			buffg.drawLine(xpos, ypos - 30, xpos, ypos + 30);
			buffg.drawLine(xpos - 30, ypos, xpos + 30, ypos);

		}*/
		
		draw();

		g.drawImage(buffImage, 0, 0, this);
	}
	
	/**
	 * purpose : gameInstances 리스트에 추가
	 * mechanism : 
	 * comment : 
	 */
	public void addInstance(GameObj instance) {
		gameInst.add(instance);
	}
	
	public static void main(String[] args)
	{
		GameManager gm;
		gm = GameManager.getref();
		GameSprite.loadImages(gm);
		int f_width = 640;
		int f_height = 360;
		int xpos = 0;
		int ypos = 0;
		String sprkey;
		GameObj o = null;
		for(int i=0; i<11; i++) {
			sprkey = "spr_message_"+i;
			xpos = 40+i*40;
			ypos = 30+i*30;

			/*o = new GameObj();
			o.xpos = xpos;
			o.ypos = ypos;
			o.setSpriteKey(sprkey);
			o.setActive(true);
			o.setVisible(true);
			gm.gameInst.add(o);
			
			System.out.println("i = "+i+" "+(gm.gameInst.get(i).xpos == o.xpos));

			System.out.println("i = "+i+" "+(gm.gameInst.get(i).getVisible()));
			*/
			FloatMessage.create(xpos, ypos, sprkey, false);
			
		}
		System.out.println(gm.gameInst.size());
		
		gm.setSize(f_width, f_height);
		gm.setLayout(null);
		gm.setVisible(true);

		//gm.repaint();
		
		try {
			while(true) {
				gm.repaint();
				Thread.sleep(20);
				gm.Update();
			}
		} catch (Exception e) {}
	}

}

