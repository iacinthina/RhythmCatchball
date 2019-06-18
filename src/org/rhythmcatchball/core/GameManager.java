package org.rhythmcatchball.core;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.*;

import org.rhythmcatchball.gameplay.GameObj;
import org.rhythmcatchball.gameplay.RoundManager;
import org.rhythmcatchball.service.AIController;
import org.rhythmcatchball.service.ConfigureUI;
import org.rhythmcatchball.service.Connection;
import org.rhythmcatchball.service.KeyboardController;
import org.rhythmcatchball.service.MainUI;
import org.rhythmcatchball.service.OnlineController;
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
	

	private int frameWidth;
	private int frameHeight;
	
	private Graphics buffg;
	private Image buffImage;
	private Panel currentUI;
	
	private ArrayList<GameObj> gameInst; //게임 진행중에 활성화된 오브젝트는 전부 여기로 들어간다.
	public int modeBeatrate = 40;
	public int modeTimeLimit = 60;
	private UserConfig userConfig;
	
	private MainUI mainUI;
	private int finitiveTimeoutCounter;
	
	/**
	 * 생성자
	 * @throws IOException 
	 */
	
	private GameManager() {
		gameInst = new ArrayList<>();
		buffg = null;
		buffImage = null;
		currentUI = null;
		userConfig = new UserConfig();
		frameWidth = 640;//getResWidth();
		frameHeight = 360;//getResHeight();
		finitiveTimeoutCounter = 0;
	}
	
	/**
	 * purpose : 1프레임 진행
	 * mechanism : gameInst 내의 모든 GameObj의 Update를 실행한다. isAlive()가 false면 리스트에서 제거한다
	 * comment : 
	 */
	public void updateGame() {
		ArrayList<GameObj> removeList = new ArrayList<>();
		GameSprite spr = null; 
		buffImage = createImage(frameWidth, frameHeight); 
		buffg = buffImage.getGraphics();
		
		int i;
		int instnum;
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
		changeUI(null);
		
		RoundManager rm = (RoundManager) RoundManager.create(frameWidth*0.5f, frameHeight*0.5f);
    	rm.initPlayers();
    	
		KeyboardController player1 = new KeyboardController(userConfig.getKey1Set());
		player1.setPlayer(rm.getEntry(0));
		rm.addController(player1);
		addKeyListener(player1);
		requestFocus();

    	AIController player2 = new AIController(null);
		player2.setPlayer(rm.getEntry(1));
		rm.addController(player2);
	}
	
	private void initLocalMulti() {
		changeUI(null);
		
		RoundManager rm = (RoundManager) RoundManager.create(frameWidth*0.5f, frameHeight*0.5f);
    	rm.initPlayers();
    	
		KeyboardController player1Control = new KeyboardController(userConfig.getKey1Set());
		player1Control.setPlayer(rm.getEntry(0));
		rm.addController(player1Control);
		addKeyListener(player1Control);

		KeyboardController player2Control = new KeyboardController(userConfig.getKey2Set());
		player2Control.setPlayer(rm.getEntry(1));
		rm.addController(player2Control);
		addKeyListener(player2Control);
		
		requestFocus();
	}
	
	public void initOnlineMulti(Connection connection) {
		changeUI(null);
		
		RoundManager rm = (RoundManager) RoundManager.create(frameWidth*0.5f, frameHeight*0.5f);
    	rm.initPlayers();
    	
		OnlineController player1Control = new OnlineController(connection, userConfig.getKey1Set());
		player1Control.setPlayer(rm.getEntry(0));
		rm.addController(player1Control);
		addKeyListener(player1Control);
		rm.getEntry(0).onlineIsSelf();

		OnlineController player2Control = new OnlineController(connection);
		player2Control.setPlayer(rm.getEntry(1));
		rm.addController(player2Control);
		rm.getEntry(1).onlineIsOther();
		
		requestFocus();
	}
	
	public void restoreMainScreen() {
		KeyListener removeListeners[] = this.getKeyListeners();
		for(KeyListener l : removeListeners) {
			System.out.println("remove : " + l);
			this.removeKeyListener(l);
		}
		for(GameObj flush : gameInst) {
			flush.destroy();
		}
		changeUI(mainUI);
		finitiveTimeoutCounter = 0;
	}
	
	public static void main(String[] args) {
		GameManager gm = GameManager.getref();
		GameSprite.loadImages(gm);
		GameSound.loadSounds();
		GameSound.updateVolume();

		gm.setSize(gm.frameWidth, gm.frameHeight);
		//gm.setLayout(new FlowLayout(FlowLayout.CENTER));

		TutorialUI tutoUISingle = new TutorialUI();
		TutorialUI tutoUILocal = new TutorialUI();
		MainUI mainUI = new MainUI();
		gm.mainUI = mainUI;
		OnlineUI onlineUI = new OnlineUI(gm);
		ConfigureUI configureUI = new ConfigureUI(gm, gm.userConfig);
		
		mainUI.setActionListener("close", (ActionEvent e) -> System.exit(0));
		
		mainUI.setActionListener("onePlay", gm.uiChanger(tutoUISingle));
		mainUI.setActionListener("twoPlay", gm.uiChanger(tutoUILocal));
		mainUI.setActionListener("onlinePlay", gm.uiChanger(onlineUI));
		mainUI.setActionListener("preference", gm.uiChanger(configureUI));
		
		String backToTitle = "goBack";
		tutoUISingle.setActionListener(backToTitle, gm.uiChanger(mainUI));
		tutoUILocal.setActionListener(backToTitle, gm.uiChanger(mainUI));
		onlineUI.setActionListener(backToTitle, gm.uiChanger(mainUI));
		configureUI.setActionListener(backToTitle, gm.uiChanger(mainUI));
		
		tutoUISingle.setActionListener("proceed", 
			(ActionEvent e) -> {
				gm.initSinglePlay();
			}
		);
		tutoUILocal.setActionListener("proceed", 
			(ActionEvent e) -> {
				gm.initLocalMulti();
			}
		);

		gm.changeUI(mainUI);

		gm.setTitle("리듬캐치볼");
		gm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		gm.setVisible(true);
		
		try {
			while(gm.finitiveTimeoutCounter == Integer.MAX_VALUE) {
				gm.finitiveTimeoutCounter = 0;
				Thread.sleep(16);
				gm.updateGame();
				gm.repaint();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private ActionListener uiChanger(Panel uiClass) {
		return (ActionEvent e) -> {
			changeUI(uiClass);
			setVisible(true);
			GameSound.play("snd_beat");
		};
	}
	
	private void changeUI(Panel uiClass) {
		if (currentUI != null)
			remove(currentUI);
		if (uiClass != null) {
			add(uiClass);
		}
		currentUI = uiClass;
	}
	
	public int getResWidth() {
		return userConfig.getResWidth();
	}
	
	public int getResHeight() {
		return userConfig.getResHeight();
	}

	public float getVolume() {
		return userConfig.getVolume();
	}
	
	public void changeConfig(UserConfig newConfig) {
		if (newConfig != null)
			userConfig = newConfig;
	}
	
	public static void playSound(String key) {
		GameSound.play(key);
	}
}

