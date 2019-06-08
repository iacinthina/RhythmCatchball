package org.rhythmcatchball.service;
import org.rhythmcatchball.core.GameManager;
import org.rhythmcatchball.gameplay.Player;

public abstract class Controller {
	protected Player player;
	protected boolean catchCheck;

	protected Controller() {
		player = null;
		catchCheck = false;
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
		catchCheck = false;
	}
	
	protected static int getBeatrate() {
		return GameManager.getref().modeBeatrate;
	}
}

//GameManager에서 Player에서 변수 2개 생성하여 KeyboardController, OnlineController, AIController 중 1개
