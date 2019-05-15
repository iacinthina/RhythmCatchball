package org.rhythmcatchball.core;
import java.awt.Graphics;
import java.awt.Image;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.*;

import org.rhythmcatchball.gameplay.Ball;
import org.rhythmcatchball.gameplay.FloatMessage;
import org.rhythmcatchball.gameplay.GameObj;
import org.rhythmcatchball.gameplay.Player;

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
	public int modeBeatrate = 30;
	public int modeTimeLimit = 60;
	
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
			if (o.getVisible()) { //보여야 그릴 수 있다.(?)
				spr = GameSprite.get(o.getSpriteKey());
				if(spr != null)
					buffg.drawImage(spr.getImage(), Math.round(o.xpos-spr.getxoff()), Math.round(o.ypos-spr.getyoff()), this);
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
		ArrayList<GameObj> removeList = new ArrayList<GameObj>();
		for(GameObj o : gameInst) {
			if (o.isAlive()) {
				if (o.isActive())
					o.update();
			} else {
				System.out.println("remove "+o);
				removeList.add(o);
			}
		}
		for(GameObj o : removeList) {
			gameInst.remove(o);
		}
	}
	
	public void paint(Graphics g){
		int f_width = 640;
		int f_height = 360;
		buffImage = createImage(f_width, f_height); 
		buffg = buffImage.getGraphics();
		
		draw();

		g.drawImage(buffImage, 0, 0, this);
	}
	
	/**
	 * purpose : gameInstances 리스트에 추가
	 * mechanism : 
	 * comment : 
	 */
	public boolean addInstance(GameObj instance) {
		return gameInst.add(instance);
	}
	
	public static void main(String[] args)
	{
		GameManager gm;
		gm = GameManager.getref();
		GameSprite.loadImages(gm);
		int f_width = 640;
		int f_height = 360;
		
		//test
		int xpos = 0;
		int ypos = 0;
		String sprkey;
		GameObj o = null;
		for(int i=0; i<11; i++) {
			sprkey = "spr_message_"+i;
			xpos = 40+i*40;
			ypos = 30+i*30;

			FloatMessage.create(xpos, ypos, sprkey, true);
		}
		
		gm.setSize(f_width, f_height);
		gm.setLayout(null);
		gm.setVisible(true);

		Player P1 = (Player) Player.create(40, 300);
		Player P2 = (Player) Player.create(600, 300);
		P1.opponent = P2;
		P2.opponent = P1;
		P1.takeBall((Ball) Ball.create(40, 300));
		P1.takeBall((Ball) Ball.create(40, 300));
		P1.takeBall((Ball) Ball.create(40, 300));
		
		try {
			while(true) {
				gm.repaint();
				Thread.sleep(100);
				gm.Update();
				System.out.println("objcount = "+gm.gameInst.size());
			}
		} catch (Exception e) {}
	}

}

