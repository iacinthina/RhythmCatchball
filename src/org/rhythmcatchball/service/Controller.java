package org.rhythmcatchball.service;
import org.rhythmcatchball.gameplay.Player;

public interface Controller {
	public void setPlayer(Player playertoset);
	public Player getPlayer();
	public boolean catchCheck();
	public void update(int beatcount);
}

//GameManager에서 Player에서 변수 2개 생성하여 KeyboardController, OnlineController, AIController 중 1개
