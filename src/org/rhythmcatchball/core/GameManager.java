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
	public int modeBeatrate = 40;
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
	 * purpose : 1프레임 진행
	 * mechanism : gameInst 내의 모든 GameObj의 Update를 실행한다. isAlive()가 false면 리스트에서 제거한다
	 * comment : 
	 */
	public void UpdateGame() {
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
			if (!o.isAlive()) {
				removeList.add(o);
				continue;
			}
			if (o.isActive()) {
				o.update();
			}
			if (o.getVisible()) {
				spr = GameSprite.get(o.getSpriteKey());
				if(spr != null)
					buffg.drawImage(spr.getImage(), Math.round(o.xpos-spr.getxoff()), Math.round(o.ypos-spr.getyoff()), this);
				else {
					buffg.drawLine((int)o.xpos, (int)o.ypos - 30, (int)o.xpos, (int)o.ypos + 30);
					buffg.drawLine((int)o.xpos - 30, (int)o.ypos, (int)o.xpos + 30, (int)o.ypos);
				}
			}
		}
		for(GameObj rm : removeList) {
			gameInst.remove(rm);
		}
		removeList.clear();
	}
	
	@Override
	public void paint(Graphics g){
		if (buffImage != null)
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
	
	private void initSinglePlay() {
		int f_width = getResWidth();
		int f_height = getResHeight();
		RoundManager rm = (RoundManager) RoundManager.create(f_width*0.5f, f_height*0.5f);
    	rm.initPlayers();
    	
		KeyboardController P1C = new KeyboardController(userConfig.getKey1Set());
		P1C.setPlayer(rm.getEntry(0));
		rm.addController(P1C);
		addKeyListener(P1C);
		requestFocus();

    	AIController P2C = new AIController(null);
		P2C.setPlayer(rm.getEntry(1));
		rm.addController(P2C);
	}
	
	private void initLocalMulti() {
		int f_width = getResWidth();
		int f_height = getResHeight();
		RoundManager rm = (RoundManager) RoundManager.create(f_width*0.5f, f_height*0.5f);
    	rm.initPlayers();
    	
		KeyboardController P1C = new KeyboardController(userConfig.getKey1Set());
		P1C.setPlayer(rm.getEntry(0));
		rm.addController(P1C);
		addKeyListener(P1C);

		KeyboardController P2C = new KeyboardController(userConfig.getKey2Set());
		P2C.setPlayer(rm.getEntry(1));
		rm.addController(P2C);
		addKeyListener(P2C);
		
		requestFocus();
	}
	
	public static void main(String[] args) {
		GameManager gm = GameManager.getref();
		GameSprite.loadImages(gm);
		int f_width = 640;
		int f_height = 360;

		gm.setSize(f_width, f_height);
		//gm.setLayout(new FlowLayout(FlowLayout.CENTER));

		TutorialUI tutorialUI_single = new TutorialUI();
		TutorialUI tutorialUI_local = new TutorialUI();
		MainUI mainUI = new MainUI();
		OnlineUI onlineUI = new OnlineUI(gm);
		
		mainUI.setActionListener("close", new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	System.exit(0);
            }
		});
		
		mainUI.setActionListener("onePlay", gm.UIchanger(tutorialUI_single));
		mainUI.setActionListener("twoPlay", gm.UIchanger(tutorialUI_local));
		mainUI.setActionListener("onlinePlay", gm.UIchanger(onlineUI));
		tutorialUI_single.setActionListener("goBack", gm.UIchanger(mainUI));
		tutorialUI_local.setActionListener("goBack", gm.UIchanger(mainUI));
		onlineUI.setActionListener("goBack", gm.UIchanger(mainUI));
		
		tutorialUI_single.setActionListener("proceed", new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	gm.remove(gm.currentUI);
            	gm.initSinglePlay();
            }
		});
		tutorialUI_local.setActionListener("proceed", new ActionListener() {
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
				gm.UpdateGame();
				gm.repaint();
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
	
	public int getResWidth() {
		return userConfig.getResWidth();
	}
	
	public int getResHeight() {
		return userConfig.getResHeight();
	}
}

