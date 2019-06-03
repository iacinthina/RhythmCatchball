package org.rhythmcatchball.core;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.*;

import org.rhythmcatchball.gameplay.Ball;
import org.rhythmcatchball.gameplay.FloatMessage;
import org.rhythmcatchball.gameplay.GameObj;
import org.rhythmcatchball.gameplay.RoundManager;
import org.rhythmcatchball.service.AIController;
import org.rhythmcatchball.service.KeyboardController;
import org.rhythmcatchball.service.MainUI;
import org.rhythmcatchball.service.OnlineUI;
import org.rhythmcatchball.service.TutorialUI;

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
	private Panel currentUI = null;
	
	private ArrayList<GameObj> gameInst; //게임 진행중에 활성화된 오브젝트는 전부 여기로 들어간다.
	public int modeBeatrate = 30;
	public int modeTimeLimit = 60;
	private UserConfig userConfig;
	
	/**
	 * 생성자
	 * @throws IOException 
	 */
	
	private GameManager() {
		gameInst = new ArrayList<GameObj>();
		buffg = null;
		buffImage = null;
		userConfig = new UserConfig();
	}
	
	/**
	 * purpose : gui에 이미지 그리기
	 * mechanism : gameInst를 돌아가면서 스프라이트 그림
	 * comment : Draw에서 그릴거 다그려둠.
	 */
	public void draw(Graphics buffg) {
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
		GameSprite spr = null; 
		int f_width = getResWidth();
		int f_height = getResHeight();
		buffImage = createImage(f_width, f_height); 
		buffg = buffImage.getGraphics();
		
		
		int i, instnum;
		GameObj o = null;
		instnum = gameInst.size();
		for(i = 0; i < instnum; i++) {
			o = gameInst.get(i);
			if (o.isAlive()) {
				if (o.isActive()) {
					o.update();
					if (o.getVisible()) { //보여야 그릴 수 있다.(?)
						spr = GameSprite.get(o.getSpriteKey());
						if(spr != null)
							buffg.drawImage(spr.getImage(), Math.round(o.xpos-spr.getxoff()), Math.round(o.ypos-spr.getyoff()), this);
						buffg.drawLine((int)o.xpos, (int)o.ypos - 30, (int)o.xpos, (int)o.ypos + 30);
						buffg.drawLine((int)o.xpos - 30, (int)o.ypos, (int)o.xpos + 30, (int)o.ypos);
					}
				}
			} else {
				removeList.add(o);
			}
		}
		for(GameObj rm : removeList) {
			gameInst.remove(rm);
		}
		removeList.clear();
	}
	
	public void paint(Graphics g){
		if (buffImage != null)
			g.drawImage(buffImage, 0, 0, this);
	}
	
	public void testBeat() {
		GameObj o;
		int i, instnum = gameInst.size();
		for(i = 0; i < instnum; i++) {
			o = gameInst.get(i);
			if (o.isAlive() && o.isActive())
				o.onBeat();
		}
	}
	
	/**
	 * purpose : gameInstances 리스트에 추가
	 * mechanism : 
	 * comment : 
	 */
	public boolean addInstance(GameObj instance) {
		return gameInst.add(instance);
	}
	
	public void initLocalMulti() {
		int f_width = getResWidth();
		int f_height = getResHeight();
		RoundManager rm = (RoundManager) RoundManager.create(f_width*0.5f, f_height*0.5f);
    	rm.launchLocal();
    	
    	AIController P2C = new AIController(null);
		P2C.setPlayer(rm.getEntry(1));
		rm.addController(P2C);
		
		KeyboardController P1C = new KeyboardController();
		P1C.setPlayer(rm.getEntry(0));
		P1C.keyval[0] = 68;
		P1C.keyval[1] = 83;
		P1C.keyval[2] = 65;
		P1C.keyval[3] = 32;
		
		addKeyListener(P1C);
	}
	
	public static void main(String[] args) {
		GameManager gm = GameManager.getref();
		GameSprite.loadImages(gm);
		int f_width = 640;
		int f_height = 360;

		gm.setSize(f_width, f_height);
		//gm.setLayout(new FlowLayout(FlowLayout.CENTER));
		
		TutorialUI tutorialUI = new TutorialUI();
		MainUI mainUI = new MainUI();
		OnlineUI onlineUI = new OnlineUI();
		
		mainUI.setActionListener("close", new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	System.exit(0);
            }
		});
		
		mainUI.setActionListener("onePlay", gm.UIchanger(tutorialUI));
		mainUI.setActionListener("twoPlay", gm.UIchanger(tutorialUI));
		mainUI.setActionListener("onlinePlay", gm.UIchanger(onlineUI));
		tutorialUI.setActionListener("goBack", gm.UIchanger(mainUI));
		onlineUI.setActionListener("goBack", gm.UIchanger(mainUI));
		
		tutorialUI.setActionListener("proceed", new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	gm.remove(gm.currentUI);
            	gm.initLocalMulti();
            }
		});

		gm.currentUI = mainUI;
		gm.add(gm.currentUI);

		gm.setTitle("리듬캐치볼");
		gm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		gm.setVisible(true);
		
		try {
			while(true) {
				Thread.sleep(16);
				gm.Update();
				gm.repaint();
        		//gm.setVisible(true);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private ActionListener UIchanger(Panel UIclass) {
		return new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	remove(currentUI);
            	add(UIclass);
        		currentUI = UIclass;
        		setVisible(true);
            }
		};
	}
	
	private ActionListener GameStarter(String key) {
		return new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	remove(currentUI);
        		currentUI = null;
        		switch(key) {
        		case "single":
        			break;
        		default:
        			break;
        		}
        		setVisible(true);
            }
		};
	}
	
	private void launchSingle() {
		
	}

	public int getResWidth() {
		return userConfig.getResWidth();
	}
	public int getResHeight() {
		return userConfig.getResHeight();
	}
}

