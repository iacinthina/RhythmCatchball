package org.rhythmcatchball.service;
import java.util.ArrayList;

import org.rhythmcatchball.core.GameManager;
import org.rhythmcatchball.gameplay.GameObj;
import org.rhythmcatchball.gameplay.Player;

public abstract class Controller {
	private static final int SCORE_LENGTH = 5;
	private static final float DEFAULT_GAP = 16;
	private static final float DEFAULT_HEIGHT = 48;
	private static final String SCORE_FONT = "number_russo_";
	private static final String BALLCOUNT_FONT = "number_russo_";
	
	protected Player player;
	protected boolean catchCheck;
	
	protected int score;
	protected ArrayList<GameObj> displayScore;
	protected GameObj displayBall;
	protected float scorePos[];
	protected float ballNumY;

	protected Controller() {
		player = null;
		catchCheck = false;
		
		setScorePos(-DEFAULT_GAP*(SCORE_LENGTH-1)/2, -DEFAULT_HEIGHT, DEFAULT_GAP);
		ballNumY = DEFAULT_HEIGHT;
		
		displayBall = GameObj.create(0, 0);
		
		displayScore = GameObj.createMass(SCORE_LENGTH, 0, 0);
	}
	
	public void setPlayer(Player playertoset) {
		this.player = playertoset;
	}
	
	public Player getPlayer() {
		return player;
	}
	
	public boolean catchCheck() {
		return catchCheck;
	}
	
	public void update(int beatcount) {
		updateDisplay();
		catchCheck = false;
	}
	
	protected static int getBeatrate() {
		return GameManager.getref().modeBeatrate;
	}
	
	protected void updateDisplay() {
		if (player == null) return;

		//score count
		if (scorePos == null || scorePos.length < 3) {
			setScorePos(0, 0, DEFAULT_GAP);
		}
		
		int score = player.getScore();
		float xpos = player.xpos + scorePos[0];
		float ypos = player.ypos + scorePos[1];
		GameObj.displayNumber(displayScore, score, xpos, ypos, DEFAULT_GAP, SCORE_FONT);
		
		//ball count
		if (displayBall != null) {
			displayBall.xpos = player.xpos;
			displayBall.ypos = player.ypos + ballNumY;
			displayBall.setSpriteKey(BALLCOUNT_FONT+player.countBall());
		}
	}
	
	protected void setScorePos(float xpos, float ypos, float gap) {
		float scorePos[] = {xpos, ypos, gap};
		this.scorePos = scorePos;
	}
}

//GameManager에서 Player에서 변수 2개 생성하여 KeyboardController, OnlineController, AIController 중 1개
